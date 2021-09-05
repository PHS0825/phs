package com.kakaopay.invest.controller;

import com.kakaopay.invest.constants.Constants;
import com.kakaopay.coupon.entity.CrdtAuthDTO;
import com.kakaopay.coupon.entity.TbAuthTrdLst;
import com.kakaopay.invest.entity.BuyPrdtDTO;
import com.kakaopay.invest.service.InvestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class InvestController {

    @Autowired
    InvestService investService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    /* 전체투자상품조회 API */
    @PostMapping("/selPrdtInfo")
    public Map<String, Object> SelectProductAll() {
        Map<String, Object> retMap = new HashMap<>();

        retMap = investService.SelectProductAll();
        return retMap;
    }

    /* 투자하기 API */
    @PostMapping("/buyPrdt")
    public Map<String, Object> BuyProduct(@RequestHeader(value = "X-USER-ID") String custId,
                                          @Valid @RequestBody BuyPrdtDTO bytprdtDto) {
        Map<String, Object> retMap = new HashMap<>();

        retMap = investService.BuyProduct(custId, bytprdtDto.getPrdtId(), bytprdtDto.getIvstAmt());
        return retMap;
    }

    /* 나의투자상품조회 API */
    @PostMapping("/selMyPrdt")
    public Map<String, Object> SelectProductByUserId(@RequestHeader(value = "X-USER-ID") String custId) {
        Map<String, Object> retMap = new HashMap<>();

        // 입력값 체크
        if ( custId.isBlank() )
        {
            retMap.put("respCd", "999");
            retMap.put("respMsg", "아이디 없음");
            return retMap;
        }
        //retMap = investService.SelectProductByUserId("PRDT0003");
        retMap = investService.SelectProductByUserId(custId);
        return retMap;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();

        Map<String, Object> retMap = new HashMap<>();

        if ( result.hasErrors() ) {
            FieldError error = result.getFieldError();
            String message[] = error.getDefaultMessage().split("\\|");
            retMap.put("respCd", message[0]);
            retMap.put("respMsg", message[1]);

            logger.debug("===============================================");
            logger.debug("getDefaultMessage[" + error.getDefaultMessage() + "]");
            logger.debug("===============================================");
        }
        return retMap;
    }

    /*
    @PostMapping("/crdtAuth")
    public Map<String, String> test(@Valid @RequestBody CrdtAuthDTO crdtAuthDTO){

        SimpleDateFormat format1 = new SimpleDateFormat("yyMMddHHmmss");
        Date time = new Date();
        String trdDate = format1.format(time).substring(0,6);
        String trdTime = format1.format(time).substring(7,6);

        String uuid = UUID.randomUUID().toString().replace("-", "");

        TbAuthTrdLst tbAuthTrdLst = new TbAuthTrdLst();
        tbAuthTrdLst.setTrdDate(trdDate);
        tbAuthTrdLst.setTrdTime(trdTime);
        tbAuthTrdLst.setTrdUniKey(uuid);
        //tbAuthTrdLst.setTrdType(Constants.TRD_TYPE_AUTH.toString());
        tbAuthTrdLst.setMaskCardNo("123");
        tbAuthTrdLst.setEncCardNo("456");

        Long amtTot = Long.parseLong(crdtAuthDTO.getAmtTot());
        tbAuthTrdLst.setAmtTot(amtTot);

        Long amtTax = Long.parseLong(crdtAuthDTO.getAmtTax());
        if ( amtTax <= 0 )
            amtTax = amtTot / 10;
        tbAuthTrdLst.setAmtTax(amtTax);

        tbAuthTrdLst.setExpDate(crdtAuthDTO.getExpDate());
        tbAuthTrdLst.setInsMon(crdtAuthDTO.getInsMon());
        tbAuthTrdLst.setRespCd(Constants.RESP_CD_DEFAULT.toString());

        Map<String, String> retMap = investService.CrdtAuth(crdtAuthDTO);
        return retMap;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();

        Map<String, String> retMap = new HashMap<>();

        if ( result.hasErrors() ) {
            FieldError error = result.getFieldError();
            String message[] = error.getDefaultMessage().split("\\|");
            retMap.put("respCd", message[0]);
            retMap.put("respMsg", message[1]);

            logger.debug("===============================================");
            logger.debug("getDefaultMessage[" + error.getDefaultMessage() + "]");
            logger.debug("===============================================");
        }
        return retMap;
    }
     */
}
