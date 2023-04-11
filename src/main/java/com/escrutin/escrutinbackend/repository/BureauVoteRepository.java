package com.escrutin.escrutinbackend.repository;

import com.escrutin.escrutinbackend.entity.BureauVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BureauVoteRepository extends JpaRepository<BureauVote, Long> {

    @Query(value = "select distinct bv.* from bureau_vote bv join lieu_vote lv on lv.id = bv.lieu_vote_id", nativeQuery = true)
    List<BureauVote> lister();

    @Query(value = "select bv.* from bureau_vote bv join lieu_vote lv on bv.lieu_vote_id = lv.id join commission_locale cl on cl.id = lv.commission_locale_id where cl.code = :codeCommissionLocale limit 1", nativeQuery = true)
    BureauVote recupererParCommissionLocale(@Param("codeCommissionLocale") String codeCommissionLocale);
}
