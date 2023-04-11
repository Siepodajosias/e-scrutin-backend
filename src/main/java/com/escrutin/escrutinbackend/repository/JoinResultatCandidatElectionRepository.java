package com.escrutin.escrutinbackend.repository;

import com.escrutin.escrutinbackend.entity.BureauVote;
import com.escrutin.escrutinbackend.entity.Circonscription;
import com.escrutin.escrutinbackend.entity.CommissionLocale;
import com.escrutin.escrutinbackend.entity.Election;
import com.escrutin.escrutinbackend.entity.JoinResultatCandidatElection;
import com.escrutin.escrutinbackend.entity.LieuVote;
import com.escrutin.escrutinbackend.entity.Resultat;
import com.escrutin.escrutinbackend.enums.Decoupage;
import com.escrutin.escrutinbackend.enums.TourScrutin;
import com.escrutin.escrutinbackend.enums.TypeScrutin;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Function;

import static java.util.Objects.nonNull;
import static java.util.Optional.empty;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.tuple.Pair.of;

@Repository
public class JoinResultatCandidatElectionRepository implements JpaRepository<JoinResultatCandidatElection, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public Set<JoinResultatCandidatElection> listerResultatParElection(long annee, TypeScrutin typeScrutin,
                                                                       TourScrutin tour) {
        String queryString = "select * from join_resultat_candidat_election jrce \n" +
                "join resultat r on r.id = jrce.resultat_id \n" +
                "join election e on r.election_id = e.id \n" +
                "where e.annee = :annee and e.\"type\" = :type and e.tour = :tour";

        Query query = entityManager.createNativeQuery(queryString, JoinResultatCandidatElection.class)
                .setParameter("annee", annee)
                .setParameter("type", typeScrutin.toString())
                .setParameter("tour", tour.toString());

        return new HashSet<>(query.getResultList());
    }


    public Map<Pair<Triple<String, String, String>, Triple<String,String, Boolean>>, Map<String,Long>> recupererAgregationParRegroupement(long annee, TourScrutin tour, TypeScrutin typeScrutin, Set<String> regionCodes,
                                                                                                                                          Set<String> circonscriptionCodes, Set<String> codesCommissionLocale){
        String queryCirconscriptionString = "select z4.designation as region, z3.designation as departement, z2.designation as sous_prefecture,  c.code as code_regroupement, c.designation designation_regroupement, count(distinct lv.id) as nb_lv, count( distinct bv.id) as nb_bv, sum(r2.nombre_inscrits) as nb_inscrits, sum(r2.nombre_votants) as nb_votants, \n" +
                "sum(r2.nombre_hommes_votants) as nb_hommes, sum(r2.nombre_femmes_votantes) as nb_femmes, sum(r2.suffrages_exprimes) as suffrage, sum(r2.nombre_bulletins_nuls) as nb_bul_nuls,\n" +
                "sum(r2.nombre_bulletins_blancs) as nb_bul_blans\n" +
                "from resultat r2\n" +
                "                join election e on r2.election_id = e.id\n" +
                "                join bureau_vote bv on bv.id = r2.bureau_vote_id \n" +
                "                join lieu_vote lv on lv.id = bv.lieu_vote_id \n" +
                "                join commission_locale cl on cl.id = lv.commission_locale_id \n" +
                "                join circonscription c on c.id = cl.circonscription_id \n" +
                "                join \"zone\" z on z.id = bv.zone_id \n" +
                "                join \"zone\" z2 on z2.id = z.zone_id \n" +
                "                join \"zone\" z3 on z3.id = z2.zone_id \n" +
                "                join \"zone\" z4 on z4.id = z3.zone_id\n" +
                "                where e.annee = :anneeElection and e.\"type\" = :typeElection and e.tour = :tourElection \n" +
                (nonNull(regionCodes) && !regionCodes.isEmpty() ? " and z4.code in (:regions) \n" : "") +
                (nonNull(circonscriptionCodes) && !circonscriptionCodes.isEmpty() ? " and c.code in (:circonscriptions) \n" : "") +
                (nonNull(codesCommissionLocale) && !codesCommissionLocale.isEmpty() ? " and cl.code in (:commissionLocales) \n" : "")+
                "                group by z4.designation, z3.designation, z2.designation, c.code, c.designation;";

        String queryCommissionString = "select z4.designation as region, z3.designation as departement, z2.designation as sous_prefecture, cl.code as code_regroupement, cl.designation designation_regroupement, count(distinct lv.id) as nb_lv, count( distinct bv.id) as nb_bv, sum(r2.nombre_inscrits) as nb_inscrits, sum(r2.nombre_votants) as nb_votants, \n" +
                "sum(r2.nombre_hommes_votants) as nb_hommes, sum(r2.nombre_femmes_votantes) as nb_femmes, sum(r2.suffrages_exprimes) as suffrage, sum(r2.nombre_bulletins_nuls) as nb_bul_nuls,\n" +
                "sum(r2.nombre_bulletins_blancs) as nb_bul_blans\n" +
                "from resultat r2\n" +
                "                join election e on r2.election_id = e.id\n" +
                "                join bureau_vote bv on bv.id = r2.bureau_vote_id \n" +
                "                join lieu_vote lv on lv.id = bv.lieu_vote_id \n" +
                "                join commission_locale cl on cl.id = lv.commission_locale_id \n" +
                "                join circonscription c on c.id = cl.circonscription_id \n" +
                "                join \"zone\" z on z.id = bv.zone_id \n" +
                "                join \"zone\" z2 on z2.id = z.zone_id \n" +
                "                join \"zone\" z3 on z3.id = z2.zone_id \n" +
                "                join \"zone\" z4 on z4.id = z3.zone_id\n" +
                "                where e.annee = :anneeElection and e.\"type\" = :typeElection and e.tour = :tourElection \n" +
                (nonNull(regionCodes) && !regionCodes.isEmpty() ? " and z4.code in (:regions) \n" : "") +
                (nonNull(circonscriptionCodes) && !circonscriptionCodes.isEmpty() ? " and c.code in (:circonscriptions) \n" : "") +
                (nonNull(codesCommissionLocale) && !codesCommissionLocale.isEmpty() ? " and cl.code in (:commissionLocales) \n" : "")+
                "                group by z4.designation, z3.designation, z2.designation, cl.code, cl.designation;";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("anneeElection", annee);
        parameters.addValue("typeElection", typeScrutin.toString());
        parameters.addValue("tourElection", tour.toString());

        if (nonNull(regionCodes) && !regionCodes.isEmpty()) {
            parameters.addValue("regions", regionCodes);
        }
        if (nonNull(circonscriptionCodes) && !circonscriptionCodes.isEmpty()) {
            parameters.addValue("circonscriptions",circonscriptionCodes);
        }
        if (nonNull(codesCommissionLocale) && !codesCommissionLocale.isEmpty()) {
            parameters.addValue("commissionLocales", codesCommissionLocale);
        }

        ColumnMapRowMapper rowMapper = new ColumnMapRowMapper();
        Map<Pair<Triple<String, String, String>, Triple<String,String, Boolean>>, Map<String,Long>> resultat = new HashMap<>();
        boolean circonscription = circonscriptionCodes.size() != 1;
        for (Map<String, Object> ligne : jdbcTemplate.query( circonscription ? queryCirconscriptionString : queryCommissionString, parameters, rowMapper)) {
            Map<String, Long> map =  ligne.entrySet().stream()
                    .filter(stringObjectEntry -> !stringObjectEntry.getKey().equals("code_regroupement") && !stringObjectEntry.getKey().equals("designation_regroupement") && !stringObjectEntry.getKey().equals("region") && !stringObjectEntry.getKey().equals("departement") && !stringObjectEntry.getKey().equals("sous_prefecture"))
                    .collect(toMap(Map.Entry::getKey, stringObjectEntry -> stringObjectEntry.getValue() instanceof BigDecimal ? ((BigDecimal) stringObjectEntry.getValue()).longValue() : (Long) stringObjectEntry.getValue()));
            resultat.put(Pair.of(Triple.of((String) ligne.get("region"), (String) ligne.get("departement"), (String) ligne.get("sous_prefecture")), Triple.of((String) ligne.get("code_regroupement"), (String) ligne.get("designation_regroupement"), circonscription)), map);
        }

        return resultat;

    }

    public Map<Triple<String,String, Boolean>, Map<Triple<String,String,String>,Triple<Long, Long, Long>>> recupererResultatParCandidat(long annee, TourScrutin tour, TypeScrutin typeScrutin, Set<String> regionCodes,
                                                                                                   Set<String> circonscriptionCodes, Set<String> codesCommissionLocale){
        String queryCirconscriptionString = "select c.code as code_regroupement, c.designation designation_regroupement, c2.nom as nom, pp.nom as parti_politique, pp.code_couleur as code_couleur, sum(r.suffrages_exprimes) as suffrage, " +
                "sum(jrce.voix) as voix , row_number() over(partition by c.designation order by c.designation, sum(jrce.voix) desc) ordre\n" +
                "from join_resultat_candidat_election jrce\n" +
                "                join resultat r on r.id = jrce.resultat_id\n" +
                "                join election e on r.election_id = e.id\n" +
                "                join bureau_vote bv on bv.id = r.bureau_vote_id \n" +
                "                join candidat c2 on c2.id = jrce.candidat_id \n" +
                "                join parti_politique pp on pp.id = c2.parti_politique_id \n" +
                "                join lieu_vote lv on lv.id = bv.lieu_vote_id \n" +
                "                join commission_locale cl on cl.id = lv.commission_locale_id \n" +
                "                join circonscription c on c.id = cl.circonscription_id \n" +
                "                join \"zone\" z on z.id = bv.zone_id \n" +
                "                join \"zone\" z2 on z2.id = z.zone_id \n" +
                "                join \"zone\" z3 on z3.id = z2.zone_id \n" +
                "                join \"zone\" z4 on z4.id = z3.zone_id\n" +
                "                where e.annee = :anneeElection and e.\"type\" = :typeElection and e.tour = :tourElection\n" +
                (nonNull(regionCodes) && !regionCodes.isEmpty() ? " and z4.code in (:regions) \n" : "") +
                (nonNull(circonscriptionCodes) && !circonscriptionCodes.isEmpty() ? " and c.code in (:circonscriptions) \n" : "") +
                (nonNull(codesCommissionLocale) && !codesCommissionLocale.isEmpty() ? " and cl.code in (:commissionLocales) \n" : "")+
                "                group by c.code, c.designation,c2.nom, pp.nom, pp.code_couleur;";

        String queryCommissionString = "select cl.code as code_regroupement, cl.designation designation_regroupement, c2.nom as nom, pp.nom as parti_politique, pp.code_couleur as code_couleur, sum(r.suffrages_exprimes) as suffrage , sum(jrce.voix) as voix, " +
                "row_number() over(partition by cl.designation order by cl.designation, sum(jrce.voix) desc) ordre \n" +
                "from join_resultat_candidat_election jrce\n" +
                "                join resultat r on r.id = jrce.resultat_id\n" +
                "                join election e on r.election_id = e.id\n" +
                "                join bureau_vote bv on bv.id = r.bureau_vote_id \n" +
                "                join candidat c2 on c2.id = jrce.candidat_id \n" +
                "                join parti_politique pp on pp.id = c2.parti_politique_id \n" +
                "                join lieu_vote lv on lv.id = bv.lieu_vote_id \n" +
                "                join commission_locale cl on cl.id = lv.commission_locale_id \n" +
                "                join circonscription c on c.id = cl.circonscription_id \n" +
                "                join \"zone\" z on z.id = bv.zone_id \n" +
                "                join \"zone\" z2 on z2.id = z.zone_id \n" +
                "                join \"zone\" z3 on z3.id = z2.zone_id \n" +
                "                join \"zone\" z4 on z4.id = z3.zone_id\n" +
                "                where e.annee = :anneeElection and e.\"type\" = :typeElection and e.tour = :tourElection\n" +
                (nonNull(regionCodes) && !regionCodes.isEmpty() ? " and z4.code in (:regions) \n" : "") +
                (nonNull(circonscriptionCodes) && !circonscriptionCodes.isEmpty() ? " and c.code in (:circonscriptions) \n" : "") +
                (nonNull(codesCommissionLocale) && !codesCommissionLocale.isEmpty() ? " and cl.code in (:commissionLocales) \n" : "")+
                "                group by cl.code, cl.designation, c2.nom, pp.nom, pp.code_couleur;";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("anneeElection", annee);
        parameters.addValue("typeElection", typeScrutin.toString());
        parameters.addValue("tourElection", tour.toString());

        if (nonNull(regionCodes) && !regionCodes.isEmpty()) {
            parameters.addValue("regions", regionCodes);
        }
        if (nonNull(circonscriptionCodes) && !circonscriptionCodes.isEmpty()) {
            parameters.addValue("circonscriptions",circonscriptionCodes);
        }
        if (nonNull(codesCommissionLocale) && !codesCommissionLocale.isEmpty()) {
            parameters.addValue("commissionLocales", codesCommissionLocale);
        }

        ColumnMapRowMapper rowMapper = new ColumnMapRowMapper();
        Map<Triple<String,String, Boolean>, Map<Triple<String,String,String>,Triple<Long, Long, Long>>> resultat = new HashMap<>();
        boolean circonscription = circonscriptionCodes.size() != 1;
        for (Map<String, Object> ligne : jdbcTemplate.query(circonscription? queryCirconscriptionString : queryCommissionString, parameters, rowMapper)) {
            Map<Triple<String, String, String>, Triple<Long, Long, Long>> map = resultat.computeIfAbsent(Triple.of((String) ligne.get("code_regroupement"), (String) ligne.get("designation_regroupement"), circonscription), k -> new HashMap<>());
            map.put(Triple.of((String) ligne.get("nom"), (String) ligne.get("parti_politique"), (String) ligne.get("code_couleur")), Triple.of(((BigDecimal) ligne.get("voix")).longValue(),
                    (Long) ligne.get("ordre"), ((BigDecimal) ligne.get("suffrage")).longValue()));
            resultat.put(Triple.of((String) ligne.get("code_regroupement"), (String) ligne.get("designation_regroupement"), circonscription), map);
        }

        return resultat;

    }

    /**
     * Récupère la liste des résultats selon les filtres passés en paramètre
     *
     * @param annee
     * @param typeScrutin
     * @param regionCodes
     * @param circonscriptionCodes
     * @param codesCommissionLocale
     * @return la liste des résultats
     */
    public Set<JoinResultatCandidatElection> findAllResultats(long annee, TourScrutin tour, TypeScrutin typeScrutin, Set<String> regionCodes, Set<String> circonscriptionCodes, Set<String> codesCommissionLocale) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<JoinResultatCandidatElection> query = builder.createQuery(JoinResultatCandidatElection.class);
        Root<JoinResultatCandidatElection> root = query.from(JoinResultatCandidatElection.class);
        Join<JoinResultatCandidatElection, Resultat> joinResultat = root.join("resultat");

        List<Predicate> predicates = new ArrayList<>();
        Join<Resultat, Election> joinElection = joinResultat.join("election");
        predicates.add(builder.equal(joinElection.get("annee"), annee));
        predicates.add(builder.equal(joinElection.get("type"), typeScrutin));
        predicates.add(builder.equal(joinElection.get("tour"), tour));

        Join<Resultat, BureauVote> joinBureauVote = joinResultat.join("bureauVote");
        Join<BureauVote, LieuVote> joinLieuVote = joinBureauVote.join("lieuVote");
        Join<LieuVote, CommissionLocale> joinCommissionLocale = joinLieuVote.join("commissionLocale");
        Join<CommissionLocale, Circonscription> joinCirconscription = joinCommissionLocale.join("circonscription");

        if (circonscriptionCodes != null && !circonscriptionCodes.isEmpty()) {
            predicates.add(joinCirconscription.get("code").in(circonscriptionCodes));
        }

        if (codesCommissionLocale != null && !codesCommissionLocale.isEmpty()) {
            predicates.add(joinCommissionLocale.get("code").in(codesCommissionLocale));
        }

        query.where(predicates.toArray((Predicate[]) Array.newInstance(Predicate.class, predicates.size())));

        return entityManager.createQuery(query).getResultList().stream()
                .filter(joinResultatCandidatElection -> regionCodes.isEmpty() || regionCodes.contains(joinResultatCandidatElection.codeRegion()))
                .collect(toSet());
    }


    public Map<String, Long> resultatPourTableauBord(long annee, TourScrutin tour, TypeScrutin typeScrutin, Set<String> regionCodes,
                                                     Set<String> circonscriptionCodes, Set<String> codesCommissionLocale) {
        String queryString = "select count(distinct lv.id) as nb_lv, count( distinct bv.id) as nb_bv, sum(r.nombre_inscrits) as nb_inscrits, sum(r.nombre_votants) as nb_votants, \n" +
                "sum(r.nombre_hommes_votants) as nb_hommes, sum(r.nombre_femmes_votantes) as nb_femmes, sum(r.suffrages_exprimes) as suffrage, sum(r.nombre_bulletins_nuls) as nb_bul_nuls,\n" +
                "sum(r.nombre_bulletins_blancs) as nb_bul_blans\n" +
                "from resultat r\n" +
                "                join election e on r.election_id = e.id\n" +
                "                join bureau_vote bv on bv.id = r.bureau_vote_id \n" +
                "                join lieu_vote lv on lv.id = bv.lieu_vote_id \n" +
                "                join commission_locale cl on cl.id = lv.commission_locale_id \n" +
                "                join circonscription c on c.id = cl.circonscription_id \n" +
                "                join \"zone\" z on z.id = bv.zone_id \n" +
                "                join \"zone\" z2 on z2.id = z.zone_id \n" +
                "                join \"zone\" z3 on z3.id = z2.zone_id \n" +
                "                join \"zone\" z4 on z4.id = z3.zone_id\n" +
                "                where e.annee = :anneeElection and e.\"type\" = :typeElection and e.tour = :tourElection \n" +
                (nonNull(regionCodes) && !regionCodes.isEmpty() ? " and z4.code in (:regions) \n" : "") +
                (nonNull(circonscriptionCodes) && !circonscriptionCodes.isEmpty() ? " and c.code in (:circonscriptions) \n" : "") +
                (nonNull(codesCommissionLocale) && !codesCommissionLocale.isEmpty() ? " and cl.code in (:commissionLocales) \n" : "");

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("anneeElection", annee);
        parameters.addValue("typeElection", typeScrutin.toString());
        parameters.addValue("tourElection", tour.toString());

        if (nonNull(regionCodes) && !regionCodes.isEmpty()) {
            parameters.addValue("regions", regionCodes);
        }
        if (nonNull(circonscriptionCodes) && !circonscriptionCodes.isEmpty()) {
            parameters.addValue("circonscriptions", circonscriptionCodes);
        }
        if (nonNull(codesCommissionLocale) && !codesCommissionLocale.isEmpty()) {
            parameters.addValue("commissionLocales", codesCommissionLocale);
        }

        ColumnMapRowMapper rowMapper = new ColumnMapRowMapper();
        Map<String, Long> resultat = new HashMap<>();
        for (Map<String, Object> ligne : jdbcTemplate.query(queryString, parameters, rowMapper)) {

            ligne.forEach((key, value) -> resultat.put(key, value instanceof BigDecimal ? ((BigDecimal) value).longValue() : (Long) value));
        }

        return resultat;
    }

    public Map<Triple<String, String, Long>, Map<Triple<String, String, String>, Pair<Long, Long>>> recupererResultatsPourCarte(long annee, TypeScrutin typeScrutin, TourScrutin tourScrutin, Decoupage decoupage) {
        String queryRegionString = "\n" +
                "select code_decoupage, designation_decoupage, nom, couleur, count(circonscription) as nombre, row_number() over(partition  by designation_decoupage order by designation_decoupage, count(circonscription) desc) as ordre\n" +
                "from \n" +
                "(\n" +
                "select z4.code as code_decoupage, z4.designation as designation_decoupage, c.designation as circonscription, pp.nom as nom, pp.code_couleur as couleur, c2.nom as candidat,  \n" +
                "sum(jrce.voix) nbVoix, row_number() over(partition  by  c.designation order by z4.designation, c.designation, sum(jrce.voix) desc) ordre\n" +
                "from join_resultat_candidat_election jrce\n" +
                "                join resultat r on r.id = jrce.resultat_id\n" +
                "                join election e on r.election_id = e.id\n" +
                "                join bureau_vote bv on bv.id = r.bureau_vote_id \n" +
                "                join candidat c2 on c2.id = jrce.candidat_id \n" +
                "                join parti_politique pp on pp.id = c2.parti_politique_id \n" +
                "                join lieu_vote lv on lv.id = bv.lieu_vote_id \n" +
                "                join commission_locale cl on cl.id = lv.commission_locale_id \n" +
                "                join circonscription c on c.id = cl.circonscription_id \n" +
                "                join \"zone\" z on z.id = bv.zone_id \n" +
                "                join \"zone\" z2 on z2.id = z.zone_id \n" +
                "                join \"zone\" z3 on z3.id = z2.zone_id \n" +
                "                join \"zone\" z4 on z4.id = z3.zone_id\n" +
                "                where e.annee = :anneeElection and e.\"type\" = :typeElection and e.tour = :tourElection \n" +
                "                group by z4.code, z4.designation, pp.nom , pp.code_couleur , c.designation, c2.nom\n" +
                "                order by z4.designation, c.designation, nbVoix desc \n" +
                "                ) as resultat \n" +
                "where ordre = 1 \n" +
                "group by code_decoupage, designation_decoupage, nom, couleur";

        String queryCirconscriptionString = "select z.code code_decoupage, z.designation as designation_decoupage, c2.nom as nom, pp.nom as parti, pp.code_couleur as couleur, sum(r.suffrages_exprimes) as suffrage,  sum(jrce.voix) as nombre, row_number() over(partition  by  z.designation order by z.designation, sum(jrce.voix) desc) as ordre\n" +
                "from join_resultat_candidat_election jrce\n" +
                "                join resultat r on r.id = jrce.resultat_id\n" +
                "                join election e on r.election_id = e.id\n" +
                "                join bureau_vote bv on bv.id = r.bureau_vote_id \n" +
                "                join candidat c2 on c2.id = jrce.candidat_id \n" +
                "                join parti_politique pp on pp.id = c2.parti_politique_id \n" +
                "                join lieu_vote lv on lv.id = bv.lieu_vote_id \n" +
                "                join commission_locale cl on cl.id = lv.commission_locale_id \n" +
                "                join circonscription c on c.id = cl.circonscription_id \n" +
                "                join \"zone\" z on z.id = bv.zone_id \n" +
                "                join \"zone\" z2 on z2.id = z.zone_id \n" +
                "                join \"zone\" z3 on z3.id = z2.zone_id \n" +
                "                join \"zone\" z4 on z4.id = z3.zone_id\n" +
                "                where e.annee = :anneeElection and e.\"type\" = :typeElection and e.tour = :tourElection \n" +
                "                group by z.code, c2.nom , pp.code_couleur , z.designation, pp.nom \n" +
                "                order by z.designation, nombre desc;";

        String query = decoupage == Decoupage.REGION ? queryRegionString : queryCirconscriptionString;

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("anneeElection", annee);
        parameters.addValue("typeElection", typeScrutin.toString());
        parameters.addValue("tourElection", tourScrutin.toString());
        ColumnMapRowMapper rowMapper = new ColumnMapRowMapper();
        Map<Triple<String, String, Long>, Map<Triple<String, String, String>, Pair<Long, Long>>> resultatParDecoupage = new HashMap<>();
        for (Map<String, Object> ligne : jdbcTemplate.query(query, parameters, rowMapper)) {
            Map<Triple<String, String, String>, Pair<Long, Long>> map = resultatParDecoupage.computeIfAbsent(Triple.of((String) ligne.get("code_decoupage"), (String) ligne.get("designation_decoupage"),
                    decoupage == Decoupage.REGION ? null : ligne.get("suffrage") instanceof BigDecimal ? ((BigDecimal) ligne.get("suffrage")).longValue() : (Long) ligne.get("suffrage")), k -> new HashMap<>());
            if (decoupage == Decoupage.REGION) {
                map.put(Triple.of((String) ligne.get("nom"), (String) ligne.get("nom"), (String) ligne.get("couleur")), of(ligne.get("nombre") instanceof BigDecimal ? ((BigDecimal) ligne.get("nombre")).longValue() : (Long) ligne.get("nombre"), (Long) ligne.get("ordre")));
            } else {
                map.put(Triple.of((String) ligne.get("nom"), (String) ligne.computeIfAbsent("parti", k -> null), (String) ligne.get("couleur")), of(ligne.get("nombre") instanceof BigDecimal ? ((BigDecimal) ligne.get("nombre")).longValue() : (Long) ligne.get("nombre"), (Long) ligne.get("ordre")));
            }
            resultatParDecoupage.put(Triple.of((String) ligne.get("code_decoupage"), (String) ligne.get("designation_decoupage"), decoupage == Decoupage.REGION ? null : ligne.get("suffrage") instanceof BigDecimal ? ((BigDecimal) ligne.get("suffrage")).longValue() : (Long) ligne.get("suffrage")), map);
        }

        return resultatParDecoupage;
    }

    public Set<Map<String, Object>> recupererParticipations(long annee, TourScrutin tourScrutin,
                                                            TypeScrutin typeScrutin, Set<String> regionCodes,
                                                            Set<String> circonscriptionCodes,
                                                            Set<String> codesCommissionLocale) {
        String queryString = "select z4.id as id_region, z4.code as code_region, z4.designation as designation_region, c.id as id_circonscription, c.code as code_circonscriptiion, c.designation as designation_circonscription, cl.id as id_commission, cl.code as code_commission, cl.designation as designation_commission,\n" +
                "lv.code as code_lieu_vote, lv.designation as designation_lieu_vote, bv.code as code_bureau_vote, bv.designation as designation_bureau_vote, r.nombre_inscrits as nombre_inscrits, r.nombre_votants as total_votants,\n" +
                "r.taux_participation as taux_participation\n" +
                "from resultat r\n" +
                "join election e on r.election_id = e.id\n" +
                "join bureau_vote bv on bv.id = r.bureau_vote_id \n" +
                "join lieu_vote lv on lv.id = bv.lieu_vote_id \n" +
                "join commission_locale cl on cl.id = lv.commission_locale_id \n" +
                "join circonscription c on c.id = cl.circonscription_id \n" +
                "join \"zone\" z on z.id = bv.zone_id \n" +
                "join \"zone\" z2 on z2.id = z.zone_id \n" +
                "join \"zone\" z3 on z3.id = z2.zone_id \n" +
                "join \"zone\" z4 on z4.id = z3.zone_id\n" +
                "where e.annee = :anneeElection and e.\"type\" = :typeElection and e.tour = :tourElection" +
                (nonNull(regionCodes) && !regionCodes.isEmpty() ? " and z4.code in (:regions) \n" : "") +
                (nonNull(circonscriptionCodes) && !circonscriptionCodes.isEmpty() ? " and c.code in (:circonscriptions) \n" : "") +
                (nonNull(codesCommissionLocale) && !codesCommissionLocale.isEmpty() ? " and cl.code in (:commissionLocales) \n" : "");

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("anneeElection", annee);
        parameters.addValue("typeElection", typeScrutin.toString());
        parameters.addValue("tourElection", tourScrutin.toString());

        if (nonNull(regionCodes) && !regionCodes.isEmpty()) {
            parameters.addValue("regions", regionCodes);
        }
        if (nonNull(circonscriptionCodes) && !circonscriptionCodes.isEmpty()) {
            parameters.addValue("circonscriptions", circonscriptionCodes);
        }
        if (nonNull(codesCommissionLocale) && !codesCommissionLocale.isEmpty()) {
            parameters.addValue("commissionLocales", codesCommissionLocale);
        }

        ColumnMapRowMapper rowMapper = new ColumnMapRowMapper();

        return new HashSet<>(jdbcTemplate.query(queryString, parameters, rowMapper));
    }

    public Set<Map<String, Object>> recupererResultatParBureauVote(long annee, TourScrutin tourScrutin,
                                                                   TypeScrutin typeScrutin, String codesCommissionLocale) {
        String queryString = "select cl.code as code_commission, cl.designation as designation_commission, lv.code as code_lieu_vote, lv.designation as designation_lieu_vote, bv.code as code_bureau_vote, bv.designation as designation_bureau_vote, \n" +
                "r.nombre_inscrits as nombre_inscrits, r.nombre_votants as total_votants, r.nombre_hommes_votants as nb_hommes, r.nombre_femmes_votantes as nb_femmes, r.nombre_bulletins_nuls as nb_bul_nuls, r.nombre_bulletins_blancs as nb_bul_blancs,\n" +
                "r.suffrages_exprimes as suffrage, r.taux_participation as taux_participation \n" +
                "from resultat r\n" +
                "join election e on r.election_id = e.id\n" +
                "join bureau_vote bv on bv.id = r.bureau_vote_id \n" +
                "join lieu_vote lv on lv.id = bv.lieu_vote_id \n" +
                "join commission_locale cl on cl.id = lv.commission_locale_id \n" +
                "join circonscription c on c.id = cl.circonscription_id \n" +
                "join \"zone\" z on z.id = bv.zone_id \n" +
                "join \"zone\" z2 on z2.id = z.zone_id \n" +
                "join \"zone\" z3 on z3.id = z2.zone_id \n" +
                "join \"zone\" z4 on z4.id = z3.zone_id\n" +
                "where e.annee = :anneeElection and e.\"type\" = :typeElection and e.tour = :tourElection and cl.code = :codeCommissionLocale;";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("anneeElection", annee);
        parameters.addValue("typeElection", typeScrutin.toString());
        parameters.addValue("tourElection", tourScrutin.toString());
        parameters.addValue("codeCommissionLocale", codesCommissionLocale);

        ColumnMapRowMapper rowMapper = new ColumnMapRowMapper();

        return new HashSet<>(jdbcTemplate.query(queryString, parameters, rowMapper));
    }

    public Map<String, Map<Triple<String, String, String>, Triple<Integer, Long, Long>>> recupererCandidatParCommission(long annee, TourScrutin tourScrutin,
                                                                                                             TypeScrutin typeScrutin, String codesCommissionLocale) {
        String queryString = "select bv.code as code_bureau_vote, c2.nom as nom, pp.nom as parti_politique, c2.id as candidat_id, pp.code_couleur as code_couleur, c2.ordre as ordre, jrce.voix as voix\n" +
                "from join_resultat_candidat_election jrce \n" +
                "join resultat r on r.id  = jrce.resultat_id \n" +
                "join candidat c2 on c2.id  = jrce.candidat_id \n" +
                "join parti_politique pp on c2.parti_politique_id = pp.id\n" +
                "join election e on r.election_id = e.id\n" +
                "join bureau_vote bv on bv.id = r.bureau_vote_id \n" +
                "join lieu_vote lv on lv.id = bv.lieu_vote_id \n" +
                "join commission_locale cl on cl.id = lv.commission_locale_id \n" +
                "join circonscription c on c.id = cl.circonscription_id \n" +
                "join \"zone\" z on z.id = bv.zone_id \n" +
                "join \"zone\" z2 on z2.id = z.zone_id \n" +
                "join \"zone\" z3 on z3.id = z2.zone_id \n" +
                "join \"zone\" z4 on z4.id = z3.zone_id\n" +
                "where e.annee = :anneeElection and e.\"type\" = :typeElection and e.tour = :tourElection and cl.code = :codeCommissionLocale";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("anneeElection", annee);
        parameters.addValue("typeElection", typeScrutin.toString());
        parameters.addValue("tourElection", tourScrutin.toString());
        parameters.addValue("codeCommissionLocale", codesCommissionLocale);

        ColumnMapRowMapper rowMapper = new ColumnMapRowMapper();

        Map<String, Map<Triple<String, String, String>, Triple<Integer, Long, Long>>> resultat = new HashMap<>();
        for (Map<String, Object> ligne : jdbcTemplate.query(queryString, parameters, rowMapper)) {
            Map<Triple<String, String, String>, Triple<Integer, Long, Long>> map = resultat.computeIfAbsent((String) ligne.get("code_bureau_vote"), k -> new HashMap<>());
            map.put(Triple.of((String) ligne.get("nom"), (String) ligne.get("parti_politique"), (String) ligne.get("code_couleur")), Triple.of((Integer) ligne.get("ordre"), (Long) ligne.get("voix"), (Long) ligne.get("candidat_id")));
            resultat.put((String) ligne.get("code_bureau_vote"), map);
        }

        return resultat;
    }

    public Set<Triple<String, String, Long>> recupererResultatParPartiOuCandidat(long annee, TourScrutin tourScrutin,
                                                                                 TypeScrutin typeScrutin, Set<String> idsRegion,
                                                                                 Set<String> idsCirconscription,
                                                                                 Set<String> idsCommissionLocale) {
        String queryParCandidat = "select c2.nom as nom, pp.code_couleur as couleur, sum(jrce.voix) as nombre \n" +
                "from join_resultat_candidat_election jrce\n" +
                "                join resultat r on r.id = jrce.resultat_id\n" +
                "                join election e on r.election_id = e.id\n" +
                "                join bureau_vote bv on bv.id = r.bureau_vote_id \n" +
                "                join candidat c2 on c2.id = jrce.candidat_id \n" +
                "                join parti_politique pp on pp.id = c2.parti_politique_id \n" +
                "                join lieu_vote lv on lv.id = bv.lieu_vote_id \n" +
                "                join commission_locale cl on cl.id = lv.commission_locale_id \n" +
                "                join circonscription c on c.id = cl.circonscription_id \n" +
                "                join \"zone\" z on z.id = bv.zone_id \n" +
                "                join \"zone\" z2 on z2.id = z.zone_id \n" +
                "                join \"zone\" z3 on z3.id = z2.zone_id \n" +
                "                join \"zone\" z4 on z4.id = z3.zone_id\n" +
                "                where e.annee = 2018 and e.\"type\" = 'MUNICIPALE' and e.tour = 'PREMIER_TOUR'\n" +
                (nonNull(idsCirconscription) && idsCirconscription.size() == 1 ? " and c.code in (:circonscriptions) \n" : "") +
                (nonNull(idsCommissionLocale) && !idsCommissionLocale.isEmpty() ? " and cl.code in (:commissionLocales) \n" : "") +
                "                group by c2.nom , pp.code_couleur\n" +
                "                order by nombre desc;";

        String queryParParti = "select nom, couleur, count(circonscription) as nombre \n" +
                "                from \n" +
                "                (\n" +
                "                select c.designation as circonscription, pp.nom as nom, pp.code_couleur as couleur, c2.nom as candidat, \n" +
                "                sum(jrce.voix) as nbVoix, row_number() over(partition  by  c.designation order by c.designation, sum(jrce.voix) desc) ordre\n" +
                "                from join_resultat_candidat_election jrce\n" +
                "                                join resultat r on r.id = jrce.resultat_id\n" +
                "                                join election e on r.election_id = e.id\n" +
                "                                join bureau_vote bv on bv.id = r.bureau_vote_id \n" +
                "                                join candidat c2 on c2.id = jrce.candidat_id \n" +
                "                                join parti_politique pp on pp.id = c2.parti_politique_id \n" +
                "                                join lieu_vote lv on lv.id = bv.lieu_vote_id \n" +
                "                                join commission_locale cl on cl.id = lv.commission_locale_id \n" +
                "                                join circonscription c on c.id = cl.circonscription_id \n" +
                "                                join \"zone\" z on z.id = bv.zone_id \n" +
                "                                join \"zone\" z2 on z2.id = z.zone_id \n" +
                "                                join \"zone\" z3 on z3.id = z2.zone_id \n" +
                "                                join \"zone\" z4 on z4.id = z3.zone_id\n" +
                "                                where e.annee = :anneeElection and e.\"type\" = :typeElection and e.tour = :tourElection\n" +
                (nonNull(idsCirconscription) && idsCirconscription.size() > 1 ? " and c.code in (:circonscriptions) \n" : "") +
                (nonNull(idsRegion) && !idsRegion.isEmpty() ? " and z4.code in (:regions) \n" : "") +
                "                                group by pp.nom , pp.code_couleur , c.designation, c2.nom\n" +
                "                                order by c.designation, nbVoix desc \n" +
                "                                ) as resultat\n" +
                "               where ordre = 1\n" +
                "               group by nom, couleur;";

        String query = nonNull(idsCirconscription) && idsCirconscription.size() == 1 ? queryParCandidat : queryParParti;

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("anneeElection", annee);
        parameters.addValue("typeElection", typeScrutin.toString());
        parameters.addValue("tourElection", tourScrutin.toString());

        if (nonNull(idsRegion) && !idsRegion.isEmpty()) {
            parameters.addValue("regions", idsRegion);
        }
        if (nonNull(idsCirconscription) && !idsCirconscription.isEmpty()) {
            parameters.addValue("circonscriptions", idsCirconscription);
        }
        if (nonNull(idsCommissionLocale) && !idsCommissionLocale.isEmpty()) {
            parameters.addValue("commissionLocales", idsCommissionLocale);
        }

        ColumnMapRowMapper rowMapper = new ColumnMapRowMapper();
        Set<Triple<String, String, Long>> resultat = new HashSet<>();
        for (Map<String, Object> ligne : jdbcTemplate.query(query, parameters, rowMapper)) {
            resultat.add(Triple.of((String) ligne.get("nom"), (String) ligne.get("couleur"), ligne.get("nombre") instanceof BigDecimal ? ((BigDecimal) ligne.get("nombre")).longValue() : (Long) ligne.get("nombre")));
        }

        return resultat;
    }

    @Override
    public List<JoinResultatCandidatElection> findAll() {
        return null;
    }

    @Override
    public List<JoinResultatCandidatElection> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<JoinResultatCandidatElection> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<JoinResultatCandidatElection> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(JoinResultatCandidatElection entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends JoinResultatCandidatElection> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends JoinResultatCandidatElection> S save(S entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }

    }

    @Override
    public <S extends JoinResultatCandidatElection> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<JoinResultatCandidatElection> findById(Long aLong) {
        return empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends JoinResultatCandidatElection> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends JoinResultatCandidatElection> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<JoinResultatCandidatElection> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public JoinResultatCandidatElection getOne(Long aLong) {
        return null;
    }

    @Override
    public JoinResultatCandidatElection getById(Long aLong) {
        return null;
    }

    @Override
    public JoinResultatCandidatElection getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends JoinResultatCandidatElection> Optional<S> findOne(Example<S> example) {
        return empty();
    }

    @Override
    public <S extends JoinResultatCandidatElection> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends JoinResultatCandidatElection> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends JoinResultatCandidatElection> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends JoinResultatCandidatElection> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends JoinResultatCandidatElection> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends JoinResultatCandidatElection, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }


}
