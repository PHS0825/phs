package com.yosebmon.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yosebmon.test.entity.MemberEntity;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

}

/*
    @Query(value = "select * from failed_message s " +
            "where s.seq >= :#{#startSeq} " +
            "and s.seq < :#{#endSeq} " +
            "and s.status = :#{#status} " +
            "and s.created_date >= :#{#startDateTime} " +
            "and s.created_date <= :#{#endDateTime} " +
            "order by s.created_date"
            , nativeQuery = true)
    Page<FailedMessage> findBySeqAndStatus(@Param("startSeq") Long startSeq,
                                           @Param("endSeq") Long endSeq,
                                           @Param("status") String status,
                                           @Param("startDateTime") LocalDateTime startDateTime,
                                           @Param("endDateTime") LocalDateTime endDateTime,
                                           Pageable pageable);
 */