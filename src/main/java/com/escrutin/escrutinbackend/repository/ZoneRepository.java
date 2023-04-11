package com.escrutin.escrutinbackend.repository;

import com.escrutin.escrutinbackend.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {

    Optional<Zone> findByCode(String code);

    @Query(value = "select * from zone z join type_zone tz on z.type_zone_id = tz.id where tz.code =:codeType", nativeQuery = true)
    List<Zone> listerEnList(String codeType);
}
