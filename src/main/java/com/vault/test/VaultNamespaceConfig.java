package com.vault.test;

import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.util.Assert;
import org.springframework.vault.authentication.AppRoleAuthentication;
import org.springframework.vault.authentication.AppRoleAuthenticationOptions;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.ClientCertificateAuthentication;
import org.springframework.vault.authentication.LifecycleAwareSessionManager;
import org.springframework.vault.authentication.SessionManager;
import org.springframework.vault.authentication.SimpleSessionManager;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.authentication.AppRoleAuthenticationOptions.AppRoleAuthenticationOptionsBuilder;
import org.springframework.vault.client.RestTemplateBuilder;
import org.springframework.vault.client.RestTemplateCustomizer;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.client.VaultEndpointProvider;
import org.springframework.vault.client.VaultHttpHeaders;
import org.springframework.vault.config.AbstractVaultConfiguration;
import org.springframework.vault.config.EnvironmentVaultConfiguration;

@Configuration
public class VaultNamespaceConfig extends AbstractVaultConfiguration {

  Logger logger = LoggerFactory.getLogger(VaultNamespaceConfig.class);

  @Value("${vault.uri}")
  URI vaultUri;

  @Value("${vault.namespace}")
  String namespace;

  @Value("${vault.roleId}")
  String roleId;
  
  @Value("${vault.secretId}")
  String secretId;


  /**
   * Specify an endpoint that was injected as URI.
   */
  @Override
  public VaultEndpoint vaultEndpoint() {
    return VaultEndpoint.from(vaultUri);
  }

  /**
   * Configure a Client Certificate authentication. {@link RestOperations} can be obtained from
   * {@link #restOperations()}.
   */
  @Override
  public ClientAuthentication clientAuthentication() {
    AppRoleAuthenticationOptions authOptions = AppRoleAuthenticationOptions.builder()
        .roleId(AppRoleAuthenticationOptions.RoleId.provided(roleId))
        .secretId(AppRoleAuthenticationOptions.SecretId.provided(secretId)).build();
    return new AppRoleAuthentication(authOptions, restOperations());
  }

  @Override
  protected RestTemplateBuilder restTemplateBuilder(VaultEndpointProvider endpointProvider,
      ClientHttpRequestFactory requestFactory) {

    RestTemplateBuilder restTemplateBuilder =
        super.restTemplateBuilder(endpointProvider, requestFactory);
    logger.info("Override restTemplateBuilder with namespace {}", namespace);

    return restTemplateBuilder.defaultHeader(VaultHttpHeaders.VAULT_NAMESPACE, namespace);
  }


  // Override with simple session manager
  // @Bean
  // public SessionManager sessionManager() {
  //
  // ClientAuthentication clientAuthentication = clientAuthentication();
  //
  // Assert.notNull(clientAuthentication, "ClientAuthentication must not be null");
  //
  // logger.info("Overrided session manager");
  //
  // return new SimpleSessionManager(clientAuthentication);
  // }

}
