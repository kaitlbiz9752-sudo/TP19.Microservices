package com.example.Client;

import com.example.Client.entities.Client;
import com.example.Client.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Bean
    CommandLineRunner initialiserBaseH2(ClientRepository clientRepository) {
        return args -> {
            clientRepository.save(new Client(null, "John", "Doe"));
            clientRepository.save(new Client(null, "Jane", "Smith"));
            clientRepository.save(new Client(null, "Ali", "Rahmani"));
        };
    }
}
