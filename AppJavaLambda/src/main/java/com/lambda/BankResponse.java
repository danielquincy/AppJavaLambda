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
public class BankResponse {
    private BigDecimal cuota;
    private BigDecimal tasa;
    private Integer plazo;
    private BigDecimal cuotaConMCuenta;
    private BigDecimal tasaConMCuenta;
    private Integer plazoConMCuenta;
}
