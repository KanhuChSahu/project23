package com.rummy.auth.repository;

import com.rummy.auth.model.ClientCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientCredentialRepository extends JpaRepository<ClientCredential, String> {
    Optional<ClientCredential> findByClientIdAndActiveTrue(String clientId);
}