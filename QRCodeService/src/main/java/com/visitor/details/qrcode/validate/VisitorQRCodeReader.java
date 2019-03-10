package com.visitor.details.qrcode.validate;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.visitor.details.qrcode.decrypt.ValidateQRCode;

@Component
public class VisitorQRCodeReader {
	
	private static final String SIGNDATA_PATH = "MyData/SignedData.txt";
	
	@Autowired
	private ValidateQRCode validateQRCode;
	
	private List<byte[]> list;

	@SuppressWarnings("unchecked")
	public String decodeQRCode(File qrCodeimage) throws Exception {
		
		BufferedImage bufferedImage = ImageIO.read(qrCodeimage);
		LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(SIGNDATA_PATH));
	    this.list = (List<byte[]>) in.readObject();
	    in.close();

		try {
			Result result = new MultiFormatReader().decode(bitmap);
			String decodedText =  result.getText();
			System.out.println("The decodedText Data is :"+decodedText);
			boolean verified = validateQRCode.verifySignature(decodedText,list.get(0));
			if(verified) {
				System.out.println("Signature Verified successfully");
				return decodedText;
			}else {
				System.out.println("Signature verification failed");
				return "Could not verify the signature.";
			}
			
		} catch (NotFoundException e) {
			System.out.println("There is no QR code in the image");
			return null;
		}
	}

}
