package com.kakaopay.invest.repository;

import com.kakaopay.invest.entity.TbCustMst;
import com.kakaopay.invest.entity.TbCustMstId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface TbCustMstRepository extends JpaRepository<TbCustMst, TbCustMstId> {

    public List<TbCustMst> findByCustIdAndStatCd(String custId, String statCd);
    public Optional<TbCustMst> findByCustIdAndPrdtId(String custId, String statCd);
}