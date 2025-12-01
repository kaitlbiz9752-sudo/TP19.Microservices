package com.example.service_voiture.controllers;

import com.example.service_voiture.entities.Client;
import com.example.service_voiture.entities.Voiture;
import com.example.service_voiture.respository.VoitureRepository;
import com.example.service_voiture.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VoitureController {

    @Autowired
    private VoitureRepository voitureRepository;

    @Autowired
    private ClientService clientService;

    @GetMapping("/voitures")
    public List<Voiture> findAll() {
        List<Voiture> voitures = voitureRepository.findAll();

        // Pour chaque voiture, on va chercher le client correspondant
        voitures.forEach(v -> {
            Client c = clientService.clientById(v.getId_client());
            v.setClient(c);
        });

        return voitures;
    }

    @GetMapping("/voiture/{id}")
    public Voiture findById(@PathVariable Long id) throws Exception {
        Voiture voiture = voitureRepository.findById(id)
                .orElseThrow(() -> new Exception("Voiture non trouvée"));

        Client c = clientService.clientById(voiture.getId_client());
        voiture.setClient(c);

        return voiture;
    }
}
