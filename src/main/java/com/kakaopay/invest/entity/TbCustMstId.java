package com.kakaopay.invest.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

//@DynamicUpdate
@Getter
@Setter
@RequiredArgsConstructor
@Builder // 빌더를 사용할 수 있게 함
@AllArgsConstructor
//@NoArgsConstructor
@Entity
@ToString
@Table(name="TB_CUST_MST")
public class TbCustMstId implements Serializable {

    @Id
    private String custId;

    @Id
    private String prdtId;

}


