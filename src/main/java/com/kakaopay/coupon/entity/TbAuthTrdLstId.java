package com.kakaopay.coupon.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
//@NoArgsConstructor
public class TbAuthTrdLstId implements Serializable {

    @Id
    private String trdDate;

    @Id
    private String trdTime;

    @Id
    private String trdUniKey;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TbAuthTrdLstId that = (TbAuthTrdLstId) o;
        return Objects.equals(trdDate, that.trdDate);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
