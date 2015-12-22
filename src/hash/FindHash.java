/*
 * Ayn� �zet koda sahip dosyalar� bulan program
 * Program verilen klas�r i�indeki dosyalar�n MD5 �zet kodunu hesaplayarak
 * Ayn� �zet koda sahip olanlar� s�yler
 * 
 * Yard�mc� Kaynaklar; http://www.codejava.net/coding/how-to-calculate-md5-and-sha-hash-values-in-java
 * 					   http://javarevisited.blogspot.com.tr/2015/06/3-ways-to-find-duplicate-elements-in-array-java.html
 */

package hash;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FindHash {

	public static void main(String[] args) {
			
			//Resimlerimizin icinde oldugu images klasorumuzu fonksiyona veriyoruz
			Ayn�_�zet_Koda_Sahip_Dosyalar�_Bul("images");		
	}
	
	/*
	 * Ayn� �zet koda sahip olan dosyalar� bulan fonksiyon
	 * @param klasor ad�
	 */
	private static void Ayn�_�zet_Koda_Sahip_Dosyalar�_Bul(String folderName) {
		File folder = new File(folderName);
		File[] listOfFiles = folder.listFiles();
		String[] hashCodesOfFiles = new String[listOfFiles.length];

		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		    	//Dosyan�n MD5 kodunu hesapla
		    	hashCodesOfFiles[i] = hashFile(listOfFiles[i],"MD5");
		    	System.out.println(i+1 +".File: [" + listOfFiles[i].getName() + "] Hash Code:" + hashCodesOfFiles[i]);
		    	
		    	//Ayn� �zet koda sahip olanlar� g�ster
		    	for(int j = 0; j < i; ++j)
		    		if(hashCodesOfFiles[j].equals(hashCodesOfFiles[i]))
		    			System.out.println("ALERT!! File: [" + listOfFiles[i].getName() + "] same Hash Code with File: [" + listOfFiles[j].getName() + "]");
		        
		      }
		    }
	}

	/*
	 * Parametre olarak verilen dosyan�n belirtilen algoirtmaya g�re MD5 �zet kodunu hesaplayan fonksiyon
	 * @param file; hash kodu bulunacak dosya
	 * @param algorithm; �zet kodun hesaplanaca�� algoritman�n tipi
	 */
	@SuppressWarnings("resource")
	private static String hashFile(File file, String algorithm) {
	    	FileInputStream inputStream = null;
			try {
				inputStream = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
	        MessageDigest digest = null;
			
	        try {
				digest = MessageDigest.getInstance(algorithm);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 
	        byte[] bytesBuffer = new byte[1024];
	        int bytesRead = -1;
	 
	        try {
				while ((bytesRead = inputStream.read(bytesBuffer)) != -1) {
				    digest.update(bytesBuffer, 0, bytesRead);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	 
	        byte[] hashedBytes = digest.digest();
	 
	        return convertByteArrayToHexString(hashedBytes);
	}
	
	//Dosyan�n �zet kodunu bulmak i�in byte arrayini hexadecimal stringe �eviriyoruz
	private static String convertByteArrayToHexString(byte[] arrayBytes) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < arrayBytes.length; i++) {
			stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		}		
		return stringBuffer.toString();
	}

}
