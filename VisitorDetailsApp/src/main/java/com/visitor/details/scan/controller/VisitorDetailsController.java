package com.visitor.details.scan.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/visitor")
public class VisitorDetailsController {

	protected Logger logger = LoggerFactory.getLogger(VisitorDetailsController.class.getName());

	@ApiOperation(value = "Reads the content of Image file uploaded via Scanner")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created", response = String.class, reference = "hidden"),
			@ApiResponse(code = 400, message = "Bad Request", response = String.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = String.class),
			@ApiResponse(code = 403, message = "Forbidden", response = String.class),
			@ApiResponse(code = 404, message = "Not Found", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class),
			@ApiResponse(code = 502, message = "Bad Gateway", response = String.class),
			@ApiResponse(code = 504, message = "Gateway Timeout", response = String.class) })
	@RequestMapping(value = "/readTextfromImage", method = RequestMethod.GET)
	@ResponseBody
	public String getTextfromScannedImage() {
		try {
			File pdf = new File("D:\\workspaces\\hackathon_2019\\Common_Application_format_nie.pdf");
            System.out.println("File readin started");

			PDDocument document = PDDocument.load(pdf);
			document.getClass();

	        if (!document.isEncrypted()) {
	        	System.out.println("File not Encrypted");
	            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
	            stripper.setSortByPosition(true);

	            PDFTextStripper tStripper = new PDFTextStripper();

	            String pdfFileInText = tStripper.getText(document);
	            String lines[] = pdfFileInText.split("\\r?\\n");
	            List<String> pdfLines = new ArrayList<>();
	            StringBuilder sb = new StringBuilder();
	            for (String line : lines) {
	                System.out.println(line);
	                pdfLines.add(line);
	                sb.append(line + "\n");
	            }
	            return sb.toString();
	        }

		} catch (Exception e) {
		}
		return null;
	}

}
