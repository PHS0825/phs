package com.kakaopay.invest.service;

import com.kakaopay.invest.constants.Constants;
import com.kakaopay.invest.entity.TbCustMst;
import com.kakaopay.invest.entity.TbPrdtMst;
import com.kakaopay.invest.repository.TbPrdtMstRepository;
import com.kakaopay.invest.repository.TbCustMstRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class InvestService {

    @Autowired
    private TbPrdtMstRepository tbPrdtMstRepo;

    @Autowired
    private TbCustMstRepository tbCustMstRepo;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public Map<String, Object> SelectProductAll() {
        Map<String, Object> retMap = new HashMap();

        Date nowTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        /* 전체 상품을 가져온다. */
        List<TbPrdtMst> tbPrdtMst = tbPrdtMstRepo.findByStartedAtLessThanEqualAndFinishedAtGreaterThanEqualAndStatCdOrderByPrdtIdAsc(nowTime, nowTime, Constants.PRDT_MST_STAT_CD_OPEN.toString());

        // 상품 건수 체크
        logger.debug("tbPrdtMst : " + tbPrdtMst.size());
        if ( tbPrdtMst.size() <= 0 )
        {
            retMap.put("respCd", "999");
            retMap.put("respMsg", "데이터 없음");
            return retMap;
        }

        List prdtList = new ArrayList<Object>();
        tbPrdtMst.forEach(s->{
            logger.debug("tbPrdtMst : " + s.toString());
            Map<String, Object> prdtItem = new HashMap();

            // 상품모집상태
            if ( s.getAmtTot() > s.getAmtCur() )
                prdtItem.put("prdtStat", "모집중");
            else
                prdtItem.put("prdtStat", "모집완료");
            prdtItem.put("prdtId", s.getPrdtId());  // 상품ID
            prdtItem.put("prdtNm", s.getPrdtNm());  // 상품명
            prdtItem.put("totAmt", s.getAmtTot());   // 총 모집금액
            prdtItem.put("curAmt", s.getAmtCur());   // 현재 모집금액
            prdtItem.put("curUser", s.getUserCur()); // 현재 투자자 수
            prdtItem.put("startedAt", dateFormat.format(s.getStartedAt())); // 상품모집 시작일시
            prdtItem.put("finishedAt", dateFormat.format(s.getFinishedAt()));   // 상품모집 종료일시
            prdtList.add(prdtItem);
        });
        retMap.put("prdtList", prdtList);
        retMap.put("respCd", "000");
        retMap.put("respMsg", "정상완료");
        return retMap;
    }


    public Map<String, Object> BuyProduct(String custId, String prdtId, long ivstAmt) {
        Map<String, Object> retMap = new HashMap();

        Date nowTime = new Date();

        // 거래내역 insert

        // 이미 투자한 상품인지 확인
        Optional<TbCustMst> custItem = tbCustMstRepo.findByCustIdAndPrdtId(custId, prdtId);
        if ( !custItem.isEmpty() )
        {
            // 이미 투자한 상품
            retMap.put("respCd", "999");
            retMap.put("respMsg", "이미 투자한 상품");
            return retMap;
        }

        // 상품 가져오기
        Optional<TbPrdtMst> prdtItem = tbPrdtMstRepo.findById(prdtId);
        logger.debug("tbPrdtMst : " + prdtItem.toString());
        if ( prdtItem.isEmpty() )
        {
            // 상품 없음
            retMap.put("respCd", "999");
            retMap.put("respMsg", "없는 상품");
            return retMap;
        }
        TbPrdtMst buyItem = prdtItem.get();
        logger.debug("buyItem : " + buyItem.toString());

        // 투자 가능한지 확인 로직 START

        // 상품 상태 확인
        if ( ! buyItem.getStatCd().equals(Constants.PRDT_MST_STAT_CD_OPEN.toString()) )
        {
            // 상품 상태 오류
            retMap.put("respCd", "999");
            retMap.put("respMsg", "없는 상품");
            return retMap;
        }
        // 시작일자 확인
        int compStartdAt = buyItem.getStartedAt().compareTo(nowTime);
        if ( compStartdAt > 0 )
        {
            retMap.put("respCd", "999");
            retMap.put("respMsg", "시작일자 오류");
            return retMap;
        }

        // 종료일자 확인
        int compFinishedAt = buyItem.getFinishedAt().compareTo(nowTime);
        if ( compFinishedAt < 0 )
        {
            retMap.put("respCd", "999");
            retMap.put("respMsg", "종료일자 오류");
            return retMap;
        }

        // 금액 확인
        if ( buyItem.getAmtCur() + ivstAmt >= buyItem.getAmtTot() )
        {
            // 투자 금액 초과
            retMap.put("respCd", "999");
            retMap.put("respMsg", "상품 투자가능금액 초과");
            return retMap;
        }

        // 상품 투자금 update
        // PHS 개발해야함...

        // 유저 테이블 insert
        TbCustMst insCustItem = new TbCustMst();
        insCustItem.setPrdtId(prdtId);
        insCustItem.setCustId(custId);
        insCustItem.setStatCd(Constants.USER_MST_STAT_CD_OPEN.toString());
        insCustItem.setAmtCur(ivstAmt);
        insCustItem.setStartedAt(nowTime);
        // PHS 정상여부 확인 필요
        tbCustMstRepo.save(insCustItem);

        // 거래내역 update

        retMap.put("respCd", "000");
        retMap.put("respMsg", "정상완료");
        return retMap;
    }


    public Map<String, Object> SelectProductByUserId(String custId) {
        Map<String, Object> retMap = new HashMap();

        Date nowTime = new Date();

        // 사용자 투자상품 가져오기
        List<TbCustMst> tbCustMst = tbCustMstRepo.findByCustIdAndStatCd(custId, Constants.USER_MST_STAT_CD_OPEN.toString());
        logger.debug("TbCustMst : " + tbCustMst.size());
        
        // 투자한 상품 없음
        if ( tbCustMst.size() < 1 )
        {
            retMap.put("respCd", "999");
            retMap.put("respMsg", "투자상품없음");
            return retMap;
        }

        List prdtList = new ArrayList<Object>();
        tbCustMst.forEach(s->{
            logger.debug("tbCustMst : " + s.toString());

            Map<String, String> prdtItem = new HashMap();

            prdtItem.put("prdtId", s.getPrdtId());
            // PHS 상품 마스터랑 조인해야함.
            //prdtItem.put("prdtNm", s.getPrdtNm());
            //prdtItem.put("totAmt", s.getAmtTot().toString());
            prdtItem.put("curAmt", s.getAmtCur().toString());
            prdtItem.put("startedAt", s.getStartedAt().toString());
            prdtList.add(prdtItem);
        });
        retMap.put("prdtList", prdtList);
        retMap.put("respCd", "000");
        retMap.put("respMsg", "정상완료");
        return retMap;
    }
}
