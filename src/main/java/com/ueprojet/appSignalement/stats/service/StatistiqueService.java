package com.ueprojet.appSignalement.stats.service;

import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ueprojet.appSignalement.signalement.repository.SignalementRepository;
import com.ueprojet.appSignalement.stats.dto.CroissanceUtilisateurDTO;
import com.ueprojet.appSignalement.stats.dto.CroissanceUtilisateurHebdoDTO;
import com.ueprojet.appSignalement.stats.dto.SignalementComparatifDTO;
import com.ueprojet.appSignalement.stats.dto.SignalementParArrondissementDTO;
import com.ueprojet.appSignalement.stats.dto.SignalementsParProblemeDTO;
import com.ueprojet.appSignalement.stats.dto.StatutSignalementDTO;
import com.ueprojet.appSignalement.usersmanagement.repositorie.UsersRepository;

@Service
public class StatistiqueService {

    @Autowired
    private SignalementRepository signalementRepository;

    @Autowired
    private UsersRepository usersRepository;

    public List<SignalementParArrondissementDTO> getStatsParArrondissementParSemaine() {
    List<Object[]> rows = signalementRepository.getStatsParArrondissementParSemaine();
    List<SignalementParArrondissementDTO> result = new ArrayList<>();

    for (Object[] row : rows) {
        String nomArrondissement = (String) row[0];
        String semaine = (String) row[1];
        Long total = ((Number) row[2]).longValue();

        result.add(new SignalementParArrondissementDTO(nomArrondissement, semaine, total));
    }

    return result;
}


    public List<CroissanceUtilisateurDTO> getCroissanceUtilisateursParJour() {
        return usersRepository.getCroissanceUtilisateursParJour();
    }

    public List<SignalementsParProblemeDTO> getSignalementsParProblemeParSemaine(Long arrondissementId) {
    List<Object[]> rawResults = signalementRepository.getSignalementsParProblemeParSemaine(arrondissementId);

    List<SignalementsParProblemeDTO> result = new ArrayList<>();
    for (Object[] row : rawResults) {
        result.add(new SignalementsParProblemeDTO(
            (String) row[0],                   // p.name
            (Long) row[1],                  // WEEK(s.date)
            ((BigInteger) row[2]).longValue()  // COUNT(*)
        ));
    }

    return result;
}


    public List<CroissanceUtilisateurHebdoDTO> getCroissanceUtilisateursHebdo(Long arrId) {
        List<Object[]> rawData = usersRepository.getCroissanceUtilisateursHebdomadaire(arrId);
        List<CroissanceUtilisateurHebdoDTO> result = new ArrayList<>();

        for (Object[] row : rawData) {
            String semaine = (String) row[0]; // ou cast selon besoin
            Long count = ((Number) row[1]).longValue();
            result.add(new CroissanceUtilisateurHebdoDTO(semaine, count));
        }

        return result;
    }


    public List<StatutSignalementDTO> getStatutSignalements(Long arrId) {
        return signalementRepository.getStatutSignalements(arrId);
    }
    //pour le citoyen
    public List<SignalementsParProblemeDTO> getSignalementsParProblemeParSemaineNational() {
        List<Object[]> rawResults = signalementRepository.getSignalementsParProblemeParSemaineNational();

        List<SignalementsParProblemeDTO> result = new ArrayList<>();
        for (Object[] row : rawResults) {
            String nomProbleme = (String) row[0];
            Long semaine = ((Long) row[1]);
            Long total = ((BigInteger) row[2]).longValue();

            result.add(new SignalementsParProblemeDTO(nomProbleme, semaine, total));
        }

        return result;
    }
    
   public List<SignalementComparatifDTO> getCourbeComparativeSignalements(Long arrId) {
    List<Object[]> results = signalementRepository.getCourbeComparativeSignalementsNative(arrId);

    return results.stream()
        .map(row -> new SignalementComparatifDTO(
            ((Date) row[0]).toLocalDate(),   // conversion SQL Date → LocalDate
            ((Number) row[1]).longValue(),
            ((Number) row[2]).longValue()
        ))
        .collect(Collectors.toList());
}






}
