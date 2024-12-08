package com.example.CRM_App.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CRM_App.Models.Client;

public interface ClientRepository extends JpaRepository<Client,Integer> {
 
	public Client findByEmail(String email);
}
