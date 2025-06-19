package com.ueprojet.appSignalement.signalement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ueprojet.appSignalement.signalement.model.Probleme;
import com.ueprojet.appSignalement.signalement.service.ProblemeService;

import java.util.List;

@RestController
@RequestMapping("/api/problemes")
public class ProblemeController {

    @Autowired
    private ProblemeService problemeService;

    @PostMapping("/create")
    public Probleme createProbleme(@RequestBody Probleme probleme) {
        return problemeService.createProbleme(probleme);
    }

    @GetMapping("/getAllProblemes")
    public List<Probleme> getAllProblemes() {
        return problemeService.getAllProblemes();
    }

    @DeleteMapping("/deleteProbleme/{id}")
    public void deleteProbleme(@PathVariable Long id) {
        problemeService.deleteProbleme(id);
    }
}