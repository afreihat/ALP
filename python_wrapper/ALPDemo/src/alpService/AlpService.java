/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alpService;

import alp.Alp;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import opennlp.tools.util.Span;

/**
 *
 * @author Abdelhakim Fraihat
 */
public class AlpService {

    private static ServerSocket server;
    private static int port = 9877;
    private static Alp alp;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        readProperties();
        server = new ServerSocket(port);
        alp = new Alp(null);
        boolean exit = false;
        while (true) {
            Socket socket = server.accept();
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            String query = (String) input.readObject();
            try {
                query = java.net.URLDecoder.decode(query, StandardCharsets.UTF_8.name());
            } catch (UnsupportedEncodingException e) {
                // not going to happen - value came from JDK's own StandardCharsets
            }
            System.out.println(query);
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

            //NAMED ENTITY TAGGING
            String[] queryParts = query.split(" ");
            String result = "";
            String alpQuery = query.replace(queryParts[0] + " ", "");
            if (queryParts.length > 1) {
                if (queryParts[0].equalsIgnoreCase("-NER")) {
                    result = alp.findNes(alpQuery);

                    output.writeObject(encode(result));

                } else {

                    //POSTAGGING
                    if (queryParts[0].equalsIgnoreCase("-POS")) {
                        result = alp.posTag(alpQuery).replace(" ", "#");
                        output.writeObject(encode(result));
                        System.out.println(alp.posTag(alpQuery));
                    } else {

                        //TOGANIZING  (wORD SEGMENTATION)
                        if (queryParts[0].equalsIgnoreCase("-TOK")) {
                            result = alp.tokenize(alpQuery).replace(" ", "#");;
                            output.writeObject(encode(result));
                        } else {

                             // LEMMATIZATION :
                            // setting the boolean option to true will show the lemmas of relevant tokens 
                            //such as nouns, verbs, adjectives, NEs
                            // Change the option to false to see all lemmas including the other non relevant tokens
                            if (queryParts[0].equalsIgnoreCase("-LEM")) {
                                String lem = lemmatize(alpQuery);;
                                lem = lem.replace(" ", "#");
                                output.writeObject(encode(lem));
                            } else {

                                //TOKINIZING + POSTAGGING
                                if (queryParts[0].equalsIgnoreCase("-POS_TOK")) {
                                    result = alp.posTagTokenized(alpQuery).replace(" ", "#");
                                    output.writeObject(result);
                                } else {
                                    if (queryParts[0].equalsIgnoreCase("-POS_FIL")) {
                                        posTagFile(queryParts[1], queryParts[2]);
                                    } else {
                                        if (queryParts[0].equalsIgnoreCase("-LEM_FIL")) {
                                            lemTagFile(queryParts[1], queryParts[2]);
                                        } else {
                                            result = "OPTION NOT FOUND!".replace(" ", "#");
                                            output.writeObject(encode(result));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                if (query.equalsIgnoreCase("-exit")) {
                    output.writeObject(encode("Exiting the service".replace(" ", "#")));
                    exit = true;
                } else {
                    output.writeObject(encode(("Could not uunderstand the query:" + query).replace(" ", "#")));

                }
            }
            input.close();
            output.close();
            socket.close();
            if (exit) {
                break;
            }
        }

   //     alp = new Alp(null);
    //     String l = "يذهب الأولاد إلى المدارس";
     //     System.out.println(lemmatize(l));
    }

    private static String encode(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

    public static List<String> read(String file) {
        String txt = "", line;
        List<String> lines = new LinkedList();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), "UTF8"));

            int i = 0;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                lines.add(line.trim().trim());
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 1000000; i++);
        return lines;
    }

    private static void write(List<String> lines, String fileName) {

        try {
            PrintWriter writer = new PrintWriter(fileName, "UTF8");
            for (String line : lines) {
                writer.print(line);
            }
            writer.close();
        } catch (Exception e) {
        }
    }

    private static void posTagFile(String srcFile, String trgtFile) {
        List<String> lines = read(srcFile);
        List<String> linesL = new LinkedList();
        for (String line : lines) {
            linesL.add(alp.posTag(line));
        }

        write(linesL, trgtFile);
    }

    private static void lemTagFile(String srcFile, String trgtFile) {
        List<String> lines = read(srcFile);
        List<String> linesL = new LinkedList();
        for (String line : lines) {
            linesL.add(lemmatize(line));
        }

        write(linesL, trgtFile);
    }

    static String lemmatize(String l) {
        String s = alp.posTagTokenized(l);
        String[] tokens, tags, words, parts;
        tokens = s.split(" ");
        words = new String[tokens.length];
        tags = new String[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            parts = tokens[i].split("_");
            words[i] = parts[0];
            tags[i] = parts[1];
        }
        String lemmasTxt = "";
        String[] lemmas = alp.lemmatize(l, false);
        for (int i = 0; i < lemmas.length; i++) {
            lemmasTxt += lemmas[i] + "_" + tags[i] + " ";
        }

        return lemmasTxt.trim() + "\n";
    }

    static String lemmatizeOld(String l) {

        String[] lemmas = alp.lemmatize(l, true);
        String lem = "";
        for (int i = 0; i < lemmas.length; i++) {
            lem += lemmas[i] + " ";
        }
        System.out.println(lem);
        return lem.trim() + "\n";
    }

    private static void readProperties() {
        try (InputStream input = new FileInputStream("./util/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            port = Integer.parseInt(prop.getProperty("PORT"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
