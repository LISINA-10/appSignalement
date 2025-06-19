package com.ueprojet.appSignalement.signalement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ueprojet.appSignalement.signalement.model.Probleme;
import com.ueprojet.appSignalement.signalement.repository.ProblemeRepository;

import java.util.List;

@Service
public class ProblemeService {

    @Autowired
    private ProblemeRepository problemeRepository;

    public Probleme createProbleme(Probleme probleme) {
        return problemeRepository.save(probleme);
    }

    public List<Probleme> getAllProblemes() {
        return problemeRepository.findAll();
    }

    public void deleteProbleme(Long id) {
        problemeRepository.deleteById(id);
    }
}