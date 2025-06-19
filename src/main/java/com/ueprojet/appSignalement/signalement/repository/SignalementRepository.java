package com.ueprojet.appSignalement.signalement.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ueprojet.appSignalement.signalement.enumeration.Status;
import com.ueprojet.appSignalement.signalement.model.Signalement;
import com.ueprojet.appSignalement.stats.dto.SignalementComparatifDTO;
import com.ueprojet.appSignalement.stats.dto.SignalementParArrondissementDTO;
//import com.ueprojet.appSignalement.stats.dto.SignalementsParProblemeDTO;
import com.ueprojet.appSignalement.stats.dto.StatutSignalementDTO;


@Repository
public interface SignalementRepository extends JpaRepository<Signalement, Long>{
    List<Signalement> findByUserId(Long userId);
    List<Signalement> findByArrondissementId(long arrondissementId);
    List<Signalement> findByArrondissementIdAndStatusIn(Long arrondissementId, List<Status> statuts);
    List<Signalement> findByDateBetweenAndArrondissementId(LocalDate startDate, LocalDate endDate, Long arrondissementId);

    //Nombre de signalements par citoyen aujourd'hui
   @Query("SELECT COUNT(s) FROM Signalement s WHERE s.userId = :citoyenId AND FUNCTION('FORMATDATETIME', s.date, 'yyyy-MM-dd') = FUNCTION('FORMATDATETIME', CURRENT_TIMESTAMP, 'yyyy-MM-dd')")
   long countByCitoyenToday(@Param("citoyenId") Long citoyenId);

    @Query(value = "SELECT a.nom AS nom_arrondissement, " +
               "CONCAT(EXTRACT(YEAR FROM s.date), '-W', LPAD(EXTRACT(WEEK FROM s.date), 2, '0')) AS semaine, " +
               "COUNT(*) AS total " +
               "FROM signalement s " +
               "JOIN arrondissement a ON s.arrondissement_id = a.id " +
               "GROUP BY a.nom, EXTRACT(YEAR FROM s.date), EXTRACT(WEEK FROM s.date) " +
               "ORDER BY EXTRACT(YEAR FROM s.date) DESC, EXTRACT(WEEK FROM s.date) DESC",
       nativeQuery = true)
      List<Object[]> getStatsParArrondissementParSemaine();

   //List<SignalementParArrondissementDTO> getStatParArrondissementParSemaine();

    @Query(value = """
      SELECT p.name AS nomProbleme, WEEK(s.date) AS semaine, COUNT(*) AS total
      FROM signalement s
      JOIN probleme p ON s.probleme_id = p.id
      WHERE s.arrondissement_id = :arrId
      GROUP BY WEEK(s.date), p.name
      ORDER BY semaine
   """, nativeQuery = true)
   List<Object[]> getSignalementsParProblemeParSemaine(@Param("arrId") Long arrondissementId);

    @Query("SELECT new com.ueprojet.appSignalement.stats.dto.StatutSignalementDTO(s.status, COUNT(s)) " +
      "FROM Signalement s WHERE s.arrondissementId = :arrId GROUP BY s.status")
    List<StatutSignalementDTO> getStatutSignalements(@Param("arrId") Long arrId);
    
    //citoyen
    @Query(value = """
      SELECT p.name AS nomProbleme, WEEK(s.date) AS semaine, COUNT(*) AS total
      FROM signalement s
      JOIN probleme p ON s.probleme_id = p.id
      GROUP BY WEEK(s.date), p.name
      ORDER BY semaine
   """, nativeQuery = true)
   List<Object[]> getSignalementsParProblemeParSemaineNational();

    //citoyen
    @Query(value = "SELECT CAST(s.date AS DATE) AS jour, " +
       "SUM(CASE WHEN s.status IN ('EN_ATTENTE', 'RECU') THEN 1 ELSE 0 END) AS enCours, " +
       "SUM(CASE WHEN s.status = 'TRAITE' THEN 1 ELSE 0 END) AS traites " +
       "FROM signalement s " +
       "WHERE s.arrondissement_id = :arrId " +
       "GROUP BY CAST(s.date AS DATE) " +
       "ORDER BY jour",
       nativeQuery = true)
   List<Object[]> getCourbeComparativeSignalementsNative(@Param("arrId") Long arrondissementId);




}
