package com.company.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.company.app.service.UploadFileService;

@SpringBootApplication
public class SpringBootWebUdemyApplication implements CommandLineRunner{

	@Autowired
	private UploadFileService uploadFileService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebUdemyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		uploadFileService.deleteAll();
		uploadFileService.init();
		
		String pass="123";
		for (int i = 0; i < 2; i++) {
			String bcrypPassword=passwordEncoder.encode(pass);
//			System.out.println(bcrypPassword);
		}
		
	}
	
}
