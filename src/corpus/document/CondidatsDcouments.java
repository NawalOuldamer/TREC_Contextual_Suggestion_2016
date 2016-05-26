/**
 * 
 */
package corpus.document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.apache.tika.language.LanguageIdentifier;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author nawalouldamer
 *
 */
public class CondidatsDcouments {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {		
		HashSet<String> cadidate_document = new HashSet<String>();
		cadidate_document = getDocumentCandidate();
		System.out.println(cadidate_document.size());
		Scanner sn = new Scanner(new File("./files/collection_2015.csv"));
		int i = 0;
		while (sn.hasNextLine()) {			
			String vect[] = sn.nextLine().split(",");
			Object id_doc =vect[0];
			if(cadidate_document.contains(id_doc)){
				getTextUrl(vect[0],vect[3],i, vect[2]);	
			}
		}
		sn.close();
	}


	public static void fileDocSave(String id_doc,int id, String title, String text, String url){
		try
		{
			FileWriter pw = new FileWriter("./xml_doc_cadida/"+id_doc+".xml", true);
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

	public static void fileTextSave(String id_doc,int id, String title, String text){
		try
		{
			FileWriter pw = new FileWriter("./text_doc_cadida/"+id_doc+".txt", true);
			pw.write(title+" "+text);
			pw.close();
		}
		catch (IOException exception)
		{
			System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		}
	} 

	public static HashSet<String> getDocumentCandidate() throws IOException{
		HashSet<String> cadidate_document = new HashSet<String>();
		BufferedReader br = new BufferedReader(new FileReader("./files/batch_requests.json"));
		String line = "";
		while ((line = br.readLine()) != null) {
			cadidate_document.addAll(getSuggestionCandidates(line));
		}
		br.close();

		// store liste candidate document
		Set<String> aSet = new HashSet<String>(cadidate_document);
		return (HashSet<String>) aSet;
	}

	public static HashSet<String> getSuggestionCandidates(String user_line) {
		JsonElement jelement = new JsonParser().parse(user_line);
		JsonObject jobject = jelement.getAsJsonObject();
		JsonArray jarray = jobject.getAsJsonArray("candidates");
		HashSet<String> user_candidate = new HashSet<String>();
		for (int i = 0; i < jarray.size(); i++) {
			user_candidate.add(jarray.get(i).toString().substring(1, jarray.get(i).toString().length()-1));
		}
		return user_candidate;     
	}



}
