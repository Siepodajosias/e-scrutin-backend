package com.escrutin.escrutinbackend.repository;

import com.escrutin.escrutinbackend.entity.BureauVote;
import com.escrutin.escrutinbackend.entity.Circonscription;
import com.escrutin.escrutinbackend.entity.CommissionLocale;
import com.escrutin.escrutinbackend.entity.LieuVote;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class CommissionLocaleRepository implements JpaRepository<CommissionLocale, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }


    /**
     * Récupère la liste des commissions locales selon la région et la circonscription passées en paramètre
     *
     * @param circonscriptionIds les codes des circonscriptions
     * @return la liste des commissions localess
     */
    public Set<CommissionLocale> recupererParCirconscription(Set<String> circonscriptionIds) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<BureauVote> query = builder.createQuery(BureauVote.class);
        Root<BureauVote> root = query.from(BureauVote.class);

        List<Predicate> predicates = new ArrayList<>();

        Join<BureauVote, LieuVote> joinLieuVote = root.join("lieuVote");
        Join<LieuVote, CommissionLocale> joinCommissionLocale = joinLieuVote.join("commissionLocale");
        Join<CommissionLocale, Circonscription> joinCirconscription = joinCommissionLocale.join("circonscription");

        if (!circonscriptionIds.isEmpty()) {
            predicates.add(joinCirconscription.get("code").in(circonscriptionIds));
        }

        query.where(predicates.toArray((Predicate[]) Array.newInstance(Predicate.class, predicates.size())));
        return entityManager.createQuery(query).getResultStream()
                .map(BureauVote::commissionLocale)
                .collect(Collectors.toSet());
    }

    public Optional<CommissionLocale> rechercherParCode(String code) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<CommissionLocale> query = builder.createQuery(CommissionLocale.class);
        Root<CommissionLocale> root = query.from(CommissionLocale.class);

        query.where(
                builder.equal(root.get("code"), code)
        ).distinct(true);

        try {
            return Optional.of(entityManager.createQuery(query).getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Liste toutes les commissions locales
     *
     * @return la liste des commissions locales
     */
    public List<CommissionLocale> listerEnList() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<CommissionLocale> query = criteriaBuilder.createQuery(CommissionLocale.class);
        Root<CommissionLocale> root = query.from(CommissionLocale.class);
        query.select(root);

        return entityManager.createQuery(query).getResultList();
    }


    @Override
    public List<CommissionLocale> findAll() {
        return null;
    }

    @Override
    public List<CommissionLocale> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<CommissionLocale> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<CommissionLocale> findAllById(Iterable<Long> longs) {
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
    public void delete(CommissionLocale entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends CommissionLocale> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends CommissionLocale> S save(S entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }
    }

    @Override
    public <S extends CommissionLocale> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<CommissionLocale> findById(Long aLong) {
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
    public <S extends CommissionLocale> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends CommissionLocale> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<CommissionLocale> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public CommissionLocale getOne(Long aLong) {
        return null;
    }

    @Override
    public CommissionLocale getById(Long aLong) {
        return null;
    }

    @Override
    public CommissionLocale getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends CommissionLocale> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends CommissionLocale> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends CommissionLocale> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends CommissionLocale> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends CommissionLocale> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends CommissionLocale> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends CommissionLocale, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
