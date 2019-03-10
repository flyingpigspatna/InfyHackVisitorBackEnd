package com.visitor.details.qrcode.decrypt;

import java.io.File;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class ValidateQRCode {
	
	private static final String PUBLICKEY_PATH = "MyKeys/publicKey";
	

	//Method for signature verification that initializes with the Public Key, updates the data to be verified and then verifies them using the signature
	public boolean verifySignature(String data, byte[] signature) throws Exception {
		
		byte[] decoded = Base64.getDecoder().decode(data);
		Signature sig = Signature.getInstance("SHA1withRSA");
		sig.initVerify(getPublic(PUBLICKEY_PATH));
		sig.update(decoded);
		System.out.println("The verifySignature is :"+sig.verify(signature));
		return sig.verify(signature);
	}
	
	//Method to retrieve the Public Key from a file
	public PublicKey getPublic(String filename) throws Exception {
		byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(spec);
	}
	
}
