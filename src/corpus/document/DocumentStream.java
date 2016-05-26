/**
 * 
 */
package corpus.document;

/**
 * @author nawalouldamer
 *
 */


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.apache.tika.language.LanguageIdentifier;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author ould
 *
 */
public class DocumentStream {

	/**
	 * @param args
	 */



	public static FileWriter pw_correct ;
	public static String path_file_url ="";
	public static String path_documents_collection_xml ="./xml/";
	public static String path_documents_collection_text ="./text/";

	public static void main(String[] args) throws IOException {		
		//init();
		Scanner sn = new Scanner(new File("./files/collection_2015.csv"));
		int i = 0;
		while (sn.hasNextLine()) {
			
			String vect[] = sn.nextLine().split(",");
			if(vect.length==4){
				getTextUrl(vect[0],vect[3],i, vect[2]);	
				System.out.println(vect[0]);

			}
			else {
				getTextUrl(vect[0],"",i, vect[2]);
				System.err.println(vect[0]);
			}
		}
		sn.close();
	}


	public static void getTextUrl(String id_doc ,String title,int id ,String url) throws IOException{
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
			LanguageIdentifier identifier = new LanguageIdentifier(doc.text());
			String language = identifier.getLanguage();
			if(language.equals("en")){
				System.out.println(id_doc);
				fileDocSave(id_doc,id, title, doc.text(), url);
				fileTextSave(id_doc,id, title, doc.text());
			}

		} catch (Exception e) {
		}
	}


	public static void fileDocSave(String id_doc,int id, String title, String text, String url){
		try
		{
			FileWriter pw = new FileWriter("./xml/"+id_doc+".xml", true);
			pw.write("<doc>"+"\n");
			pw.write("<docno>" + id_doc + "</docno>" +"\n");
			pw.write("<url>" + url + "</url>" +"\n");
			pw.write("<title>" + title + "</title>" +"\n");
			pw.write("<text>" + "\n");
			pw.write(text + "\n");
			pw.write("</text>" + "\n");
			pw.write("</doc>");
			pw.close();
		}
		catch (IOException exception)
		{
			System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		}
	}

	public static void fileTextSave(String id_doc,int id, String title, String text){
		try
		{
			FileWriter pw = new FileWriter("./text/"+id_doc+".txt", true);
			pw.write(title+" "+text);
			pw.close();
		}
		catch (IOException exception)
		{
			System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		}
	} 
}
