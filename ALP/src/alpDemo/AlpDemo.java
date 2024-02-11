/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alpDemo;

import alp.Alp;
import opennlp.tools.util.Span;

/**
 *
 * @author Abdelhakim Fraihat
 */
public class AlpDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Alp alp = new Alp(null);
        
String[] sentences = {
            "أثارت تعليقات من رئيس قسم الإعلانات بفيسبوك عقب صدور تقرير المحقق المستقل في التدخل الروسي بالانتخابات الأميركية المزيد من الانتقادات تتهمه بعدم الاستعداد لمنع التدخل وبضعف الحساسية السياسية.",
"وذكر تقرير لصحيفة وول ستريت جورنال أن فيسبوك يعالج حاليا موجة جديدة من الانتقادات لمحاولات مدير قسم الإعلان فيه روب غولدمان الرد على الانتقادات الواردة في تقرير المحقق مولر.",
"وكان التقرير قد وصف كيف استخدمت إحدى الشركات الروسية منصات الاتصال الاجتماعي مثل فيسبوك وتويتر ويوتيوب  لإثارة الفتنة والشقاق  في الولايات المتحدة منذ 2014، وأظهر التقرير أن وحدة تبادل الصور في فيسبوك وفرعها إنستغرام كانت على وجه الخصوص مركزية في عملية الاستخدام من قبل الروس للتأثير على الرأي العام الأميركي.",
"عدم استعداد",
"وقال باحثون إن تقرير مولر أظهر عدم استعداد فيسبوك لمثل المحاولات الروسية، مشيرين إلى أن التقرير تضمن القول إن فيسبوك لديه 25 ألف موظف، لكن أقل من مئة محرض روسي فقط مسلحين بالمعرفة والمعدات التكنولوجية استطاعوا توظيف منصته لسنوات.",
"وأشعلت تعليقات غولدمان المنشورة على تويتر تفاعلا كبيرا، إذ قال إن هناك أساليب سهلة لمواجهة الحملة الروسية أولها أن يكون المواطنون جيدي التعليم، وإن الهدف الرئيسي للروس ليس التأثير على نتائج الانتخابات، بل زرع الفتنة بين الأميركيين.",
"وأثارت تلك التغريدات أكثر من تسعة آلاف تعليق على تويتر، أغلبها غاضب. وقال أحد كبار المديرين بشركة إعلان عملاقة موجها حديثه لغولدمان : عليك ألا تلقي مواعظ، وتغريداتك الغريبة تسببت في خلق الغضب والبلبلة. ويكفي ما تسببتم فيه من أضرار لأميركا خلال أكثر من عامين. والصمت أفضل في غياب جهود حقيقية لإزالة هذه الأضرار.",
"تراجع",
"وأصدر جويل كابلان نائب رئيس فيسبوك للسياسة الدولية العامة بعد ذلك بيانا قال فيه: لم نجد شيئا يناقض الاتهامات التي وجهها تقرير المحقق المستقل.",
"وكان الرئيس الأميركي دونالد ترمب قد استشهد بتغريدة غولدمان لتأييد فكرة أن الأفعال الروسية لم يكن لها تأثير على الانتخابات.",
"وعقب الانتقادات الكثيفة ضده، قال غولدمان: إن من الواضح أن الحملة الروسية كانت مؤيدة لترمب.",
"وأعلن فيسبوك أنه سيتخذ عددا من الخطوات لمنع حدوث مثل ما قمت بالحملة الروسية مرة أخرى، ومن بين ذلك توظيف عشرة آلاف موظف للسيطرة على أحاديث الكراهية وإعداد وسائل لاستئصال الحسابات الوهمية.",
"وقال جون هندلر المدير السابق لقطاع التكنولوجيا باللجنة القومية الديمقراطية التابعة للحزب الديمقراطي والتي تعرضت لهجوم روسي قبيل الانتخابات: إن تصرف فيسبوك هو نموذج لتصرفات شركات سيليكون فالي المفعمة بالثقة المفرطة بالنفس وانعدام الحساسية عندما يتعلق الأمر بالشؤون السياسية."
};
        
        
        
        /*
NER: TEST SPAN METHOD:
       INPUT: STring 
       OUTPUT: Span ARRAY 
       @see: http://opennlp.apache.org/docs/1.8.4/manual/opennlp.html#tools.namefind
       @see the next test below
         */
        
        String testString = "عبّر وزير المالية والتخطيط الفلسطيني شكري بشارة عن ارتياحه لقرار المحكمة الفيدرالية في واشنطن بإسقاط دعوى قضائية قائمة ضد السلطة الفلسطينية ومنظمة التحرير الفلسطينية للحصول على تعويضات من قبل عائلات قتلى عملية وقعت في مستوطنة \"كرني شمرون\" (شمال الضفة الغربية المحتلة) عام 2002.";
        Span[] spans = alp.find(testString);
        for (Span span : spans) {
             System.out.println(span);
        }
       
         /*
         TEST ANNOTATING METHOD:
         INPUT: String
         OUTPUT: ANNOTATED STRING
         */
         System.out.println(alp.findNes(testString));
        
       //// OTHER TESTS 
        for (String testSentence : sentences) {
            /*1*/
            //NAMED ENTITY TAGGING
            //System.out.println(alp.findNes(testSentence));

            /*2*/
            //TOGANIZING  (wORD SEGMENTATION)
            //System.out.println(alp.tokenize(testSentence));
            /*3*/
            //POSTAGGING
            //System.out.println(alp.posTag(testSentence));
            /*4*/
            //TOKINIZING + POSTAGGING
         // System.out.println(alp.posTagTokenized(testSentence));
            
          /*5*/
            // LEMMATIZATION :
            // setting the boolean option to true will show the lemmas of relevant tokens 
            //such as nouns, verbs, adjectives, NEs
            // Change the option to false to see all lemmas including the other non relevant tokens
          
            String[] lemmas = alp.lemmatize(testSentence.trim(), true);
            for (int i = 0; i < lemmas.length; i++) {
              //  System.out.print(lemmas[i] + " ");
            }
            //System.out.println();
           
            /*6*/
            //Chunking
           String[][] chunks = alp.chunck(testSentence.trim());
           for(int i=0; i<chunks[0].length; i++){
       //  System.out.println(chunks[0][i]+"\t"+chunks[1][i]+"\t"+chunks[2][i]);
           }
         //  System.out.println();

        }
    }

}
