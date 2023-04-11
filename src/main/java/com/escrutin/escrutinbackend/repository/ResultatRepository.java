package com.escrutin.escrutinbackend.repository;

import com.escrutin.escrutinbackend.entity.BureauVote;
import com.escrutin.escrutinbackend.entity.Circonscription;
import com.escrutin.escrutinbackend.entity.CommissionLocale;
import com.escrutin.escrutinbackend.entity.Election;
import com.escrutin.escrutinbackend.entity.JoinResultatCandidatElection;
import com.escrutin.escrutinbackend.entity.LieuVote;
import com.escrutin.escrutinbackend.entity.Resultat;
import com.escrutin.escrutinbackend.enums.TourScrutin;
import com.escrutin.escrutinbackend.enums.TypeScrutin;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;

@Repository
public class ResultatRepository implements JpaRepository<Resultat, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

	public Optional<Resultat> findByBureauVoteAndElection(BureauVote bureauVote, Election election) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Resultat> query = builder.createQuery(Resultat.class);
        Root<Resultat> root = query.from(Resultat.class);

		List<Predicate> predicates = new ArrayList<>();

		Join<Resultat, Election> joinElection = root.join("election");
		Join<Resultat, BureauVote> joinBureauVote = root.join("bureauVote");

		predicates.add(builder.equal(joinElection, election));
		predicates.add(builder.equal(joinBureauVote, bureauVote));

		query.where(predicates.toArray((Predicate[]) Array.newInstance(Predicate.class, predicates.size())));

		try {
			return Optional.of(entityManager.createQuery(query).getSingleResult());
		}
		catch (NoResultException e) {
			return Optional.empty();
		}
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
	public Set<Resultat> findAllResultats(long annee, TourScrutin tour, TypeScrutin typeScrutin, Set<String> regionCodes, Set<String> circonscriptionCodes, Set<String> codesCommissionLocale) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Resultat> query = builder.createQuery(Resultat.class);
		Root<Resultat> root = query.from(Resultat.class);

		List<Predicate> predicates = new ArrayList<>();
		Join<Resultat, Election> joinElection = root.join("election");
		predicates.add(builder.equal(joinElection.get("annee"), annee));
		predicates.add(builder.equal(joinElection.get("type"), typeScrutin));
		predicates.add(builder.equal(joinElection.get("tour"), tour));

		Join<Resultat, BureauVote> joinBureauVote = root.join("bureauVote");
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
				.filter(resultat -> regionCodes == null || regionCodes.isEmpty() || regionCodes.contains(resultat.codeRegion()))
				.collect(toSet());
	}

    @Override
    public List<Resultat> findAll() {
        return null;
    }

    @Override
    public List<Resultat> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Resultat> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Resultat> findAllById(Iterable<Long> longs) {
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
    public void delete(Resultat entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Resultat> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Resultat> S save(S entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }

    }

    @Override
    public <S extends Resultat> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Resultat> findById(Long aLong) {
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
    public <S extends Resultat> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Resultat> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Resultat> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Resultat getOne(Long aLong) {
        return null;
    }

    @Override
    public Resultat getById(Long aLong) {
        return null;
    }

    @Override
    public Resultat getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Resultat> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Resultat> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Resultat> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Resultat> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Resultat> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Resultat> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Resultat, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
