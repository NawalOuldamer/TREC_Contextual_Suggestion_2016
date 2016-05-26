/**
 * 
 */
package w2v;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author nawalouldamer
 *
 */
public class DocumentW2V {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	
	static String collection_folder_path ="/home/ould/Trec_Contextual_Suggestion_collection/collection/";
	public static void main(String[] args) throws IOException {

		String [] liste_file = new File(collection_folder_path).list(); 
		for (int i=0; i<liste_file.length;i++) 
		{
			getText(liste_file[i]);
		}		
	}	

	public static void getText(String file_path) throws IOException{
		// load stored file
		String content_file ="";
		Scanner s = new Scanner(new File(collection_folder_path+file_path));
		while (s.hasNextLine()) {
			String line = s.nextLine();
			content_file = content_file + " "+ line;
		}
		s.close();


		// parsing a stored file to get a html content 
		String content ="";
		Pattern tagPattern = Pattern.compile("<html(.*)>(.+?)</html>");

		final Matcher matcher = tagPattern.matcher(content_file);
		while (matcher.find()) {
			content = content +" "+ matcher.group();
		}
		System.out.println(content);
		// get a content 
		Document doc = Jsoup.parse(content);	

		//save a document content
		String save_content =  doc.body().text();
		System.out.println(doc.body().text());
		storeTextFile(file_path.substring(0, file_path.length() - 3),save_content);
		storeXmlFile(file_path.substring(0, file_path.length() - 3), save_content);
	}
	
	public static void storeXmlFile(String id_doc, String content){
		try
		{
			FileWriter pw = new FileWriter(collection_folder_path+"documents_xml/"+id_doc+".xml", true);
			pw.write("<doc>"+"\n");
			pw.write("<docno>" + id_doc + "</docno>" +"\n");
			pw.write("<text>" + "\n");
			pw.write(content + "\n");
			pw.write("</text>" + "\n");
			pw.write("</doc>");
			pw.close();
		}
		catch (IOException exception)
		{
			System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		}
	}

	public static void storeTextFile(String id_doc, String content) throws IOException{
		FileWriter pw = new FileWriter(collection_folder_path+"documents_text/"+id_doc+".txt", true);
		pw.write(content);
		pw.close();
	}

	public static void test () throws FileNotFoundException{
		String content_file ="";
		Scanner s = new Scanner(new File("TRECCS-00157279-151.warc"));
		while (s.hasNextLine()) {
			String line = s.nextLine();
			content_file = content_file + " "+ line;
		}
		s.close();

		Pattern tagPattern = Pattern.compile("<html(.*)>(.+?)</html>");
		String content ="";

		final Matcher matcher = tagPattern.matcher(content_file);
		while (matcher.find()) {
			content = content +" "+ matcher.group();
		}
		// get a content 
		Document doc = Jsoup.parse(content);	

		//save a document content
		
		System.out.println(doc.body().text());

	}

}



