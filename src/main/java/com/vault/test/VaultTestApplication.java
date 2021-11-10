package com.vault.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VaultTestApplication  implements ApplicationRunner{
  
    @Autowired
    CredentialsService service;

	public static void main(String[] args)  {
		SpringApplication.run(VaultTestApplication.class, args);
	}

  @Override
  public void run(ApplicationArguments args) throws Exception {
    service.accessCredentials();
  }

}
