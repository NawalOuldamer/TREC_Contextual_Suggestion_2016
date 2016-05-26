/**
 * 
 */
package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author nawalouldamer
 *
 */
public class CheckError {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		//test();

		HashSet<String> list_url_Uncrawled = new HashSet<String>();
		list_url_Uncrawled = getUncrawledWebPage();
		Scanner sn = new Scanner(new File("collection_2015.csv"));
		FileWriter file_erro = new FileWriter("/home/ould/TREC_Collection/crawl_collection_170715_CS15/error_2.txt");

		while (sn.hasNextLine()) {				
			String vect[] = sn.nextLine().split(",");
			if(list_url_Uncrawled.contains(vect[0])){				
				try {
					Document doc = Jsoup.connect(vect[2]).get();
					FileWriter file = new FileWriter("/home/ould/TREC_Collection/crawl_collection_170715_CS15/uncrawled_2/"+vect[0]);
					file.write(doc.toString());
					file.close();		
				} catch (Exception e) {
					file_erro.write(vect[0]+ " "+ e +"\n");	
					file_erro.flush();
				}		
			}
		}
		sn.close();
		file_erro.close();


	}

	public static HashSet<String> getUncrawledWebPage() throws FileNotFoundException{
		HashSet<String> list_url_Uncrawled = new HashSet<String>();
		Scanner s = new Scanner(new File("/home/ould/TREC_Collection/crawl_collection_170715_CS15/error.txt"));
		while (s.hasNextLine()) {
			list_url_Uncrawled.add(s.nextLine());
		}
		s.close();
		return list_url_Uncrawled;
	}


	public static void test (){
		try {
			Document doc = Jsoup.connect("http://www.greatfallsmt.net/people_offices/park_rec/gibson.php").get();			
			System.out.println(doc.toString());

		} catch (Exception e) {

			System.out.println(e);
		}	

	}

}
