package com.escrutin.escrutinbackend.repository;

import com.escrutin.escrutinbackend.entity.PartiPolitique;
import com.escrutin.escrutinbackend.enums.TourScrutin;
import com.escrutin.escrutinbackend.enums.TypeScrutin;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

@Repository
public class PartiPolitiqueRepository implements JpaRepository<PartiPolitique, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * @param annee
     * @param typeScrutin
     * @return
     */
    public Set<Triple<String, String, Long>> recupererPartis(long annee, TypeScrutin typeScrutin, TourScrutin tourScrutin) {
        String queryString = "select nom, couleur, count(circonscription) as nombre \n" +
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
                "                                group by pp.nom , pp.code_couleur , c.designation, c2.nom\n" +
                "                                order by c.designation, nbVoix desc \n" +
                "                                ) as resultat\n" +
                "               where ordre = 1\n" +
                "               group by nom, couleur;";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("anneeElection", annee);
        parameters.addValue("typeElection", typeScrutin.toString());
        parameters.addValue("tourElection", tourScrutin.toString());
        ColumnMapRowMapper rowMapper = new ColumnMapRowMapper();
        Set<Triple<String, String, Long>> resultat = new HashSet<>();
        for (Map<String, Object> ligne : jdbcTemplate.query(queryString, parameters, rowMapper)) {
            resultat.add(Triple.of((String) ligne.get("nom"), (String) ligne.get("couleur"), (Long) ligne.get("nombre")));
        }

        return resultat;
    }


    @Override
    public List<PartiPolitique> findAll() {
        return null;
    }

    @Override
    public List<PartiPolitique> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<PartiPolitique> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<PartiPolitique> findAllById(Iterable<Long> longs) {
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
    public void delete(PartiPolitique entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends PartiPolitique> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends PartiPolitique> S save(S entity) {
        return null;
    }

    @Override
    public <S extends PartiPolitique> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<PartiPolitique> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends PartiPolitique> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends PartiPolitique> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<PartiPolitique> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public PartiPolitique getOne(Long aLong) {
        return null;
    }

    @Override
    public PartiPolitique getById(Long aLong) {
        return null;
    }

    @Override
    public PartiPolitique getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends PartiPolitique> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends PartiPolitique> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends PartiPolitique> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends PartiPolitique> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends PartiPolitique> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends PartiPolitique> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends PartiPolitique, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
