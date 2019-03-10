package com.visitor.details.qrcode.encrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Component;

@Component("SignQRCode")
public class SignQRCode {
	
	 private static final String SIGNDATA_PATH = "MyData/SignedData.txt";
	 private List<byte[]> list;

	// The method that signs the data using the private key that is stored in
	// keyFile path
	public String sign(String data, String keyFile) throws InvalidKeyException, Exception {
		Signature dsa = Signature.getInstance("SHA1withRSA");
		dsa.initSign(getPrivate(keyFile));
		dsa.update(data.getBytes());
		String encoded = Base64.getEncoder().encodeToString(dsa.sign());
		System.out.println("The encoded Data is :"+encoded);
		list = new ArrayList<byte[]>();
		list.add(encoded.getBytes());
		writeToFile();
		return encoded;
	}

	// Method to retrieve the Private Key from a file
	public PrivateKey getPrivate(String filename) throws Exception {
		byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(spec);
	}
	
		//Method to write the List of byte[] to a file
		private void writeToFile() throws FileNotFoundException, IOException {
			File f = new File(SIGNDATA_PATH);
			f.getParentFile().mkdirs();
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SIGNDATA_PATH));
		    out.writeObject(list);
			out.close();
			System.out.println("Your file is ready.");
		}

}
