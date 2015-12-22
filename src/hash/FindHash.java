/*
 * Ayný özet koda sahip dosyalarý bulan program
 * Program verilen klasör içindeki dosyalarýn MD5 özet kodunu hesaplayarak
 * Ayný özet koda sahip olanlarý söyler
 * 
 * Yardýmcý Kaynaklar; http://www.codejava.net/coding/how-to-calculate-md5-and-sha-hash-values-in-java
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
			Ayný_Özet_Koda_Sahip_Dosyalarý_Bul("images");		
	}
	
	/*
	 * Ayný özet koda sahip olan dosyalarý bulan fonksiyon
	 * @param klasor adý
	 */
	private static void Ayný_Özet_Koda_Sahip_Dosyalarý_Bul(String folderName) {
		File folder = new File(folderName);
		File[] listOfFiles = folder.listFiles();
		String[] hashCodesOfFiles = new String[listOfFiles.length];

		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		    	//Dosyanýn MD5 kodunu hesapla
		    	hashCodesOfFiles[i] = hashFile(listOfFiles[i],"MD5");
		    	System.out.println(i+1 +".File: [" + listOfFiles[i].getName() + "] Hash Code:" + hashCodesOfFiles[i]);
		    	
		    	//Ayný özet koda sahip olanlarý göster
		    	for(int j = 0; j < i; ++j)
		    		if(hashCodesOfFiles[j].equals(hashCodesOfFiles[i]))
		    			System.out.println("ALERT!! File: [" + listOfFiles[i].getName() + "] same Hash Code with File: [" + listOfFiles[j].getName() + "]");
		        
		      }
		    }
	}

	/*
	 * Parametre olarak verilen dosyanýn belirtilen algoirtmaya göre MD5 özet kodunu hesaplayan fonksiyon
	 * @param file; hash kodu bulunacak dosya
	 * @param algorithm; özet kodun hesaplanacaðý algoritmanýn tipi
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
	
	//Dosyanýn özet kodunu bulmak için byte arrayini hexadecimal stringe çeviriyoruz
	private static String convertByteArrayToHexString(byte[] arrayBytes) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < arrayBytes.length; i++) {
			stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		}		
		return stringBuffer.toString();
	}

}
