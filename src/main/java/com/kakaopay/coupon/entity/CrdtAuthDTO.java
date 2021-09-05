package com.kakaopay.coupon.entity;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@NoArgsConstructor
//@RequiredArgsConstructor
public class CrdtAuthDTO {

    @NotNull(message="9001|cardNo is not null")
    @Size(max=24, message="9001|cardNo size must be less than 24 charactors")
    private String cardNo;

    @NotNull(message="9001|expDate is not null")
    @Size(min=4, max=4, message="9001|expDate size must be 4 charactors")
    private String expDate;

    @NotBlank(message="9001|cvc is not null")
    @Size(min=3, max=3, message="9001|cvc must be 3 characters")
    private String cvc;

    @NotBlank(message="9001|amtTot is not null")
    @Size(max=12, message="9001|amtTot size must be less than 12 charactors")
    private String amtTot;

    @Size(max=12, message="9001|amtTax size must be less than 12 charactors")
    private String amtTax;

    @NotBlank(message="9001|insMon is not null")
    @Size(min=2, max=2, message="9001|insMon size must be 2 charactors")
    private String insMon;

}
