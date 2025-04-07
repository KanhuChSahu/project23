package com.rummy.auth.service;

import com.rummy.auth.model.ClientCredential;
import com.rummy.auth.repository.ClientCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientCredentialService {

    @Autowired
    private ClientCredentialRepository clientCredentialRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean validateClientCredentials(String clientId, String clientSecret) {
        return clientCredentialRepository.findByClientIdAndActiveTrue(clientId)
                .map(credential -> passwordEncoder.matches(clientSecret, credential.getClientSecret()))
                .orElse(false);
    }

    public void saveClientCredential(String clientId, String clientSecret) {
        ClientCredential credential = new ClientCredential();
        credential.setClientId(clientId);
        credential.setClientSecret(passwordEncoder.encode(clientSecret));
        clientCredentialRepository.save(credential);
    }
}