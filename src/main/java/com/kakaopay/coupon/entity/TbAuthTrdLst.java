package com.kakaopay.coupon.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

//@DynamicUpdate
@Getter
@Setter
@RequiredArgsConstructor
@Builder // 빌더를 사용할 수 있게 함
@AllArgsConstructor
//@NoArgsConstructor
@Entity
@ToString
@Table(name="TB_AUTH_TRD_LST")
@IdClass(TbAuthTrdLstId.class)
public class TbAuthTrdLst {

    @Id
    @Column(name = "TRD_DATE", nullable = false, length = 8)
    private String trdDate;

    @Id
    @Column(name = "TRD_TIME", nullable = false, length = 6)
    private String trdTime;

    @Id
    @Column(name = "TRD_UNI_KEY", nullable = false, length = 20)
    private String trdUniKey;

    @Column(name = "TRD_TYPE", length = 4)
    private String trdType;

    @Column(name = "MASK_CARD_NO", length = 24)
    private String maskCardNo;

    @Column(name = "ENC_CARD_NO", length = 64)
    private String encCardNo;

    @Column(name = "AMT_TOT")
    private Long amtTot;

    @Column(name = "AMT_TAX")
    private Long amtTax;

    @Column(name = "EXP_DATE", length = 4)
    private String expDate;

    @Column(name = "INS_MON", length = 2)
    private String insMon;

    @Column(name = "CAN_TRD_UNI_KEY", length = 20)
    private String canTrdUniKey;

    @Column(name = "ORG_TRD_UNI_KEY", length = 20)
    private String orgTrdUniKey;

    @Column(name = "RESP_CD", length = 4)
    private String respCd;

    @Column(name = "DB_INS_DATE")
    private Instant dbInsDate;

    @Column(name = "DB_INS_ID", length = 16)
    private String dbInsId;

    @Column(name = "DB_INS_TRD_UNI_KEY", length = 20)
    private String dbInsTrdUniKey;

    @Column(name = "DB_UPD_DATE")
    private Instant dbUpdDate;

    @Column(name = "DB_UPD_ID", length = 16)
    private String dbUpdId;

    @Column(name = "DB_UPD_TRD_UNI_KEY", length = 20)
    private String dbUpdTrdUniKey;

    @Column(name = "ISS_SEND_DATA", length = 512)
    private String issSendData;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TbAuthTrdLst that = (TbAuthTrdLst) o;
        return Objects.equals(trdDate, that.trdDate)
                && Objects.equals(trdTime, that.trdTime)
                && Objects.equals(trdUniKey, that.trdUniKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trdDate, trdTime, trdUniKey);
    }
}

/*
UPDATE TB_PRDT_MST AA
   SET AA.TOTAL_INVESTING_AMOUNT += {addAmount}
     , AA.TOTAL_USER_CNT += {addCnt}
 WHERE AA.PRODUCT_ID = {prodId}
   AND AA.TOTAL_INVESTING_AMOUNT <= AA.TOTAL_INVESTING_AMOUNT + {addAmount}
;

UPDATE TB_USER_MST AA
   SET AA.TOTAL_INVESTING_AMOUNT += {addAmount}
 WHERE AA.PRODUCT_ID = {prodId}
   AND AA.USER_ID = {userId}
 */
