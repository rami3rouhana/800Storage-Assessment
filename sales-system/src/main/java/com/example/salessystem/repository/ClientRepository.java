package com.example.salessystem.repository;

import com.example.salessystem.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Client entity.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}