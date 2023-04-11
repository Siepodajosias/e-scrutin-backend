package com.escrutin.escrutinbackend.repository;

import com.escrutin.escrutinbackend.entity.Election;
import com.escrutin.escrutinbackend.entity.JoinResultatCandidatElection;
import com.escrutin.escrutinbackend.entity.Zone;
import com.escrutin.escrutinbackend.enums.TourScrutin;
import com.escrutin.escrutinbackend.enums.TypeScrutin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import com.escrutin.escrutinbackend.entity.*;
import com.escrutin.escrutinbackend.enums.TourScrutin;
import com.escrutin.escrutinbackend.enums.TypeScrutin;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public interface ElectionRepository extends JpaRepository<Election, Long> {

    Optional<Election> findByAnneeAndTypeAndTour(long annee, TypeScrutin typeScrutin, TourScrutin tourScrutin);
//    @Query("SELECT s FROM Election s join s.circonscription c WHERE s.annee =:annee and s.type =:type and s.tour =:tourScrutin and c.id =:circonscriptionId")
//    Election recupererScrutin(Long annee, Long circonscriptionId, TypeScrutin type, TourScrutin tourScrutin);

    boolean existsByAnneeAndType(long annee, TypeScrutin type);

    boolean existsByAnneeAndTypeAndTour(long annee, TypeScrutin type, TourScrutin tour);
}
