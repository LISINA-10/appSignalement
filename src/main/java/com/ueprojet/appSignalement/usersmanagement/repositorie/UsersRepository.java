package com.ueprojet.appSignalement.usersmanagement.repositorie;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ueprojet.appSignalement.stats.dto.CroissanceUtilisateurDTO;
//import com.ueprojet.appSignalement.stats.dto.CroissanceUtilisateurHebdoDTO;
import com.ueprojet.appSignalement.usersmanagement.enumeration.Role;
import com.ueprojet.appSignalement.usersmanagement.model.Agent;
import com.ueprojet.appSignalement.usersmanagement.model.Citizen;
import com.ueprojet.appSignalement.usersmanagement.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
    List<Users> findByRole(Role role);
    boolean existsByUsername(String username);

   @Query("SELECT c FROM Citizen c WHERE c.arrondissementId = :arrId")
    List<Citizen> findCitizensByArrondissementId(@Param("arrId") Long arrId);

    @Query("SELECT a FROM Agent a WHERE a.arrondissementId = :arrId")
    List<Agent> findAgentsByArrondissementId(@Param("arrId") Long arrId);

    @Query("SELECT new com.ueprojet.appSignalement.stats.dto.CroissanceUtilisateurDTO(" +
       "u.dateDeCreation, COUNT(u)) " +
       "FROM Users u " +
       "GROUP BY u.dateDeCreation " +
       "ORDER BY u.dateDeCreation ASC")
      List<CroissanceUtilisateurDTO> getCroissanceUtilisateursParJour();



    @Query(value = "SELECT FORMATDATETIME(u.date_creation, 'w') AS semaine, COUNT(*) AS count " +
       "FROM citizen u WHERE u.arrondissement_id = :arrId " +
       "GROUP BY FORMATDATETIME(u.date_creation, 'w') ORDER BY semaine", nativeQuery = true)
      List<Object[]> getCroissanceUtilisateursHebdomadaire(@Param("arrId") Long arrId);






}
