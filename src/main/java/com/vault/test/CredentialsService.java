package com.vault.test;

import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;
import org.springframework.vault.support.VaultResponseSupport;

@Service
public class CredentialsService {

  @Autowired
  private VaultTemplate vaultTemplate;

  public void accessCredentials() throws URISyntaxException {
    
    VaultResponse read = vaultTemplate.read("path/to/key");
    System.out.println(read.getData());
  }

}
