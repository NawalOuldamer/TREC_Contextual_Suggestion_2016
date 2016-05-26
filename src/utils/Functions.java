/**
 * 
 */
package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

/**
 * @author nawalouldamer
 *
 */
public class Functions {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static void decompressFile(){
		String path_folder = "/home/ould/TREC_Collection/crawl_collection_170715_CS15/crawls/";
		String path_collection ="/home/ould/Trec_Contextual_Suggestion_collection/";
		String [] liste_file = new File(path_folder).list(); 
		for (int i=0; i<liste_file.length;i++) 
		{
			String [] liste_file_ = new File(path_folder+liste_file[i]+"/").list(); 
			for (int j=0; j<liste_file_.length;j++) {
				unGunzipFile(path_folder+liste_file[i]+"/"+liste_file_[j], liste_file_[j],path_collection);
			}
			
		}
	}
	public static void unGunzipFile(String compressedFile,String pth, String decompressedFile) {
		byte[] buffer = new byte[1024];
		try {
			FileInputStream fileIn = new FileInputStream(compressedFile);
			GZIPInputStream gZIPInputStream = new GZIPInputStream(fileIn);
			String decopressed_filepath = null;
			FileOutputStream fileOutputStream = new FileOutputStream(decopressed_filepath+pth.substring(0, pth.length() - 3));
			int bytes_read;
			System.out.println(decompressedFile+compressedFile.substring(0, compressedFile.length() - 3));
			while ((bytes_read = gZIPInputStream.read(buffer)) > 0) {

				fileOutputStream.write(buffer, 0, bytes_read);
			}
			gZIPInputStream.close();
			fileOutputStream.close();
			System.out.println("The file was decompressed successfully!");

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
