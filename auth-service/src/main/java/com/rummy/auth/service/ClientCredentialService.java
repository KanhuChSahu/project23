package com.rummy.auth.service;

import com.rummy.auth.model.ClientCredential;
import com.rummy.auth.repository.ClientCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientCredentialService {

    @Autowired
    private ClientCredentialRepository clientCredentialRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean validateClientCredentials(String clientId, String clientSecret) {
        if (!isValidUUID(clientSecret)) {
            return false;
        }
        return clientCredentialRepository.findByClientIdAndActiveTrue(clientId)
                .map(credential -> passwordEncoder.matches(clientSecret, credential.getClientSecret()))
                .orElse(false);
    }

    public String generateClientCredential(String clientId) {
        String clientSecret = UUID.randomUUID().toString();
        ClientCredential credential = new ClientCredential();
        credential.setClientId(clientId);
        credential.setClientSecret(passwordEncoder.encode(clientSecret));
        credential.setActive(true);
        clientCredentialRepository.save(credential);
        return clientSecret;
    }

    public void saveClientCredential(String clientId, String clientSecret) {
        if (!isValidUUID(clientSecret)) {
            throw new IllegalArgumentException("Client secret must be a valid UUID");
        }
        ClientCredential credential = new ClientCredential();
        credential.setClientId(clientId);
        credential.setClientSecret(passwordEncoder.encode(clientSecret));
        credential.setActive(true);
        clientCredentialRepository.save(credential);
    }

    private boolean isValidUUID(String str) {
        try {
            UUID.fromString(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}