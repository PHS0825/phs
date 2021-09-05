package com.kakaopay.invest.entity;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BuyPrdtDTO {

    @NotNull(message="999|prdtId is not null")
    @Size(max=16, message="999|prdtId size must be less than 16 charactors")
    private String prdtId;

    @NotNull(message="999|ivstAmt is not null")
    @Min(value=1, message="999|ivstAmt is not null")
    private Long ivstAmt;

}
