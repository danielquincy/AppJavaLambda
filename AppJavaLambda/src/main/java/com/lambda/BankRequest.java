package com.lambda;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BankRequest {
    private BigDecimal monto;
    private Integer plazo;
    private BigDecimal tasa;

}
