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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author nawalouldamer
 *
 */
public class W2VUserDocument {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		FileWriter file = new FileWriter("document_w2v_all_users.txt");
		BufferedReader br = new BufferedReader(new FileReader("./files/batch_requests.json"));
		String line = "";
		while ((line = br.readLine()) != null) {
			FileWriter file_u = new FileWriter("./users_file_w2V/"+getUserId(line)+".txt");			
			Iterator<String> it = userDocument(getUserDocuments(line)).iterator();
			while (it.hasNext()) {
				String text = it.next();
				file_u.write(cleadText(text)+" ");
				file.write(cleadText(text)+" ");
			}
			file_u.close();
		}
		file.close();
		br.close();
	}
	public static HashSet<String> userDocument(HashSet<String> userDoc) throws FileNotFoundException{
		HashSet<String> documentUser = new HashSet<String>();
		Iterator<String> it = userDoc.iterator();
		while (it.hasNext()) {
			try {
				Scanner s = new Scanner(new File("./text/"+it.next()+".txt"));
				while (s.hasNextLine()) {					
					documentUser.add(cleadText(s.nextLine()));				
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

	public static String cleadText(String text){
		String text_cleaned = text.replaceAll("<[^<]*>", "").replaceAll("[^A-Za-z]"," ").toLowerCase();
		return text_cleaned;
	}
}
