package com.visitor.details.qrcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.visitor.details.qrcode")
public class QrCodeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QrCodeServiceApplication.class, args);
	}

}
