package com.example.service_voiture.respository;

import com.example.service_voiture.entities.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoitureRepository extends JpaRepository<Voiture, Long> {
    // AUCUNE méthode custom ici pour éviter les erreurs de génération de requêtes
}
