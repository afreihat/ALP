/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alpclient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;


/**
 *
 * @author Abdelhakim Fraihat
 */
public class ALPClient {
static int port= 9999;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
        readProperties();
        String arg = "";
        for(int i=0; i< args.length; i++){
        arg += args[i] + " ";
        }
        arg = arg.trim();
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
            socket = new Socket(host.getHostName(), port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            //oos.writeObject("-NER أرني كتاب العلوم القديم خالد أحمد");
            oos.writeObject(arg);
            ois = new ObjectInputStream(socket.getInputStream());
            String message = (String) ois.readObject();
            System.out.println( message);
            ois.close();
            oos.close();
    }
    
       private static void readProperties() {
        try (InputStream input = new FileInputStream("./util/config.properties")) {
            Properties prop= new Properties();
            prop.load(input);
            port = Integer.parseInt(prop.getProperty("PORT"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
