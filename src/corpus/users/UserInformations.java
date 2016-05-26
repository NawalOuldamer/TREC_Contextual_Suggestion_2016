/**
 * 
 */
package corpus.users;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author nawalouldamer
 *
 */

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;     
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class UserInformations {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("./files/batch_requests.json"));
		String line = "";
		while ((line = br.readLine()) != null) {
			//saveUserTextFile(getUserId(line), getUserTags(line));
			//saveUserXmlFile(getUserId(line), getUserTags(line));
			saveUserTextFile(getUserId(line), userDocument(getUserDocuments(line)));
			saveUserXmlFile(getUserId(line), userDocument(getUserDocuments(line)));


		}
		br.close();
	}


	public static HashSet<String> userDocument(HashSet<String> userDoc) throws FileNotFoundException{
		HashSet<String> documentUser = new HashSet<String>();
		Iterator<String> it = userDoc.iterator();
		while (it.hasNext()) {
			try {
				Scanner s = new Scanner(new File("./text/"+it.next()+".txt"));
				while (s.hasNextLine()) {
					documentUser.add(s.nextLine());				
				}
				s.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return documentUser;

	}
	public static String getUserId(String user_line){
		JsonElement jelement = new JsonParser().parse(user_line);
		JsonObject jobject = jelement.getAsJsonObject();
		jobject = jobject.getAsJsonObject("body");
		JsonObject jPersonObject = jobject.getAsJsonObject("person"); 
		return jPersonObject.get("id").toString();
	}

	public static HashSet<String> getUserDocuments(String user_line){
		JsonElement jelement = new JsonParser().parse(user_line);
		JsonObject jobject = jelement.getAsJsonObject();
		jobject = jobject.getAsJsonObject("body");
		JsonObject jPersonObject = jobject.getAsJsonObject("person");             
		JsonArray jarray = jPersonObject.getAsJsonArray("preferences");  
		HashSet<String> user_docs = new HashSet<String>();
		for (int i = 0; i < jarray.size(); i++) {
			try {
				JsonObject jPersonObject1 = jarray.get(i).getAsJsonObject();            	 
				user_docs.add(jPersonObject1.get("documentId").toString().substring(1, jPersonObject1.get("documentId").toString().length()-1));
			}
			catch (Exception e) {
			}
		} 
		return user_docs;
	}

	public static HashSet<String> getUserTags(String user_line){
		JsonElement jelement = new JsonParser().parse(user_line);
		JsonObject jobject = jelement.getAsJsonObject();
		jobject = jobject.getAsJsonObject("body");
		JsonObject jPersonObject = jobject.getAsJsonObject("person");             
		JsonArray jarray = jPersonObject.getAsJsonArray("preferences");  
		HashSet<String> user_tags = new HashSet<String>();
		for (int i = 0; i < jarray.size(); i++) {
			JsonObject jPersonObject1 = jarray.get(i).getAsJsonObject(); 
			try {
				JsonArray jTagArray = jPersonObject1.getAsJsonArray("tags");
				for (int i1 = 0; i1 < jTagArray.size(); i1++) {
					user_tags.add((jTagArray.get(i1).toString().substring(1, jTagArray.get(i1).toString().length()-1)));
				}
			} catch (Exception e) {
			}
		}
		return user_tags;
	}

	public static HashSet<String> getSuggestionCandidates(String user_line) {
		JsonElement jelement = new JsonParser().parse(user_line);
		JsonObject jobject = jelement.getAsJsonObject();
		JsonArray jarray = jobject.getAsJsonArray("candidates");
		HashSet<String> user_candidate = new HashSet<String>();
		for (int i = 0; i < jarray.size(); i++) {
			user_candidate.add(jarray.get(i).toString().substring(0, jarray.get(i).toString().length()-1));
		}
		return user_candidate;     
	}

	public static JsonArray getPreferences(String user_line) {
		JsonElement jelement = new JsonParser().parse(user_line);
		JsonObject jobject = jelement.getAsJsonObject();
		jobject = jobject.getAsJsonObject("body");
		JsonObject jPersonObject = jobject.getAsJsonObject("person");
		JsonArray jarray = jPersonObject.getAsJsonArray("preferences");
		return jarray;
	}

	public static String getProfileID(String user_line) {
		String profileID = "";
		JsonElement jelement = new JsonParser().parse(user_line);
		JsonObject jobject = jelement.getAsJsonObject();
		jobject = jobject.getAsJsonObject("body");
		JsonObject jPersonObject = jobject.getAsJsonObject("person");

		profileID = jPersonObject.get("id").toString();

		return profileID;
	}

	public static void saveUserXmlFile(String user_id, HashSet<String> user_tags) throws IOException{
		FileWriter file = new FileWriter("./users_xml_document_file/"+user_id+".xml");
		file.write("<DOC>\n");
		file.write("	<DOCNO>" + user_id + "</DOCNO> \n");

		file.write("	<TAGS> ");
		Iterator<String> it = user_tags.iterator();
		while (it.hasNext()) {
			file.write(it.next()+ " ");
		}
		file.write("	</TAGS> \n");		 
		file.write("</DOC>");
		file.close();
	}
	public static void saveUserTextFile(String user_id, HashSet<String> user_tags) throws IOException{
		FileWriter file = new FileWriter("./users_text_document_file/"+user_id+".txt");
		Iterator<String> it = user_tags.iterator();
		while (it.hasNext()) {
			file.write(it.next()+ " ");
		}
		file.close();
	}

}