package com.escrutin.escrutinbackend.repository;

import com.escrutin.escrutinbackend.entity.Circonscription;
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
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class CirconscriptionRepository implements JpaRepository<Circonscription, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Recherche la circonscription par son code
     *
     * @param code le code de la circonscription
     * @return la circonscription
     */
    public Optional<Circonscription> rechercherParCode(String code) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Circonscription> query = builder.createQuery(Circonscription.class);
        Root<Circonscription> root = query.from(Circonscription.class);

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
     * Liste toutes les circonscriptions
     * @return la liste des circonscriptions
     */
    public List<Circonscription> listerEnList() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();

        CriteriaQuery<Circonscription> query = criteriaBuilder.createQuery(Circonscription.class);
        Root<Circonscription> root = query.from(Circonscription.class);
        query.select(root);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Circonscription> findAll() {
        return null;
    }

    @Override
    public List<Circonscription> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Circonscription> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Circonscription> findAllById(Iterable<Long> longs) {
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
    public void delete(Circonscription entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Circonscription> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Circonscription> S save(S entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }
    }

    @Override
    public <S extends Circonscription> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Circonscription> findById(Long aLong) {
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
    public <S extends Circonscription> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Circonscription> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Circonscription> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Circonscription getOne(Long aLong) {
        return null;
    }

    @Override
    public Circonscription getById(Long aLong) {
        return null;
    }

    @Override
    public Circonscription getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Circonscription> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Circonscription> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Circonscription> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Circonscription> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Circonscription> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Circonscription> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Circonscription, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
