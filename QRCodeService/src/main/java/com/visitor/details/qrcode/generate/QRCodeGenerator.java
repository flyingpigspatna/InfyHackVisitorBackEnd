package com.visitor.details.qrcode.generate;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.security.InvalidKeyException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.visitor.details.qrcode.encrypt.SignQRCode;

@Component
public class QRCodeGenerator {

	@Autowired
	private SignQRCode signQRCode;

	private static final String QR_CODE_IMAGE_PATH = "MyQRCode/MyQRCode.png";
	private static final String KEYPATH = "Mykeys/privateKey";
	
	private static final int WIDTH = 350;
	private static final int  HEIGHT = 350;
	
	public byte[] generateQRCodeImage(String text)
			throws InvalidKeyException, Exception {
		File f = new File(QR_CODE_IMAGE_PATH);
		f.getParentFile().mkdirs();
		System.out.println("Incoming data generateQRCodeImage"+text);
		String signedEncodedString = signQRCode.sign(text, KEYPATH);
		System.out.println("signedEncodedString:"+signedEncodedString);
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(signedEncodedString, BarcodeFormat.QR_CODE, WIDTH, HEIGHT);

		Path path = FileSystems.getDefault().getPath(QR_CODE_IMAGE_PATH);
	    ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
		MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
		byte[] pngData = pngOutputStream.toByteArray(); 
	    return pngData;
	}

}
