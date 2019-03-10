package com.visitor.details.qrcode.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.visitor.details.qrcode.generate.QRCodeGenerator;
import com.visitor.details.qrcode.validate.VisitorQRCodeReader;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/qrcode")
public class QRCodeController {

	protected Logger logger = LoggerFactory.getLogger(QRCodeController.class.getName());
	
	@Autowired
	private QRCodeGenerator qRCodeGenerator;
	
	@Autowired
	private VisitorQRCodeReader qRCodeReader;

	@ApiOperation(value = "Generates QR image for the visitor")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created", response = String.class, reference = "hidden"),
			@ApiResponse(code = 400, message = "Bad Request", response = String.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = String.class),
			@ApiResponse(code = 403, message = "Forbidden", response = String.class),
			@ApiResponse(code = 404, message = "Not Found", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class),
			@ApiResponse(code = 502, message = "Bad Gateway", response = String.class),
			@ApiResponse(code = 504, message = "Gateway Timeout", response = String.class) })
	@RequestMapping(value = "/generateQRImage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public byte[] generateQRCodeImage(@RequestBody String visitorDetails) {
		byte[] pngData = null;
		try {
			System.out.println("Incoming Data"+visitorDetails);
			pngData = qRCodeGenerator.generateQRCodeImage(visitorDetails);
			System.out.println("Outgoing Data"+pngData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pngData;
	}
	
	
	@ApiOperation(value = "Validates QR image for the visitor")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created", response = String.class, reference = "hidden"),
			@ApiResponse(code = 400, message = "Bad Request", response = String.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = String.class),
			@ApiResponse(code = 403, message = "Forbidden", response = String.class),
			@ApiResponse(code = 404, message = "Not Found", response = String.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = String.class),
			@ApiResponse(code = 502, message = "Bad Gateway", response = String.class),
			@ApiResponse(code = 504, message = "Gateway Timeout", response = String.class) })
	@RequestMapping(value = "/generateQRImage", method = RequestMethod.GET)
	@ResponseBody
	public String validateQRCodeImage() {
		try {
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
