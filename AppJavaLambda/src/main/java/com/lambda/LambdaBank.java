package com.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class LambdaBank implements RequestHandler<BankRequest, BankResponse> {


    @Override
    public BankResponse handleRequest(BankRequest bankRequest, Context context) {
        context.getLogger().log("Recibiendo la Petición: " + context.getLogGroupName());

        //Constante que representa la precisión de los calculos matematicos. (34 Decimales)
        MathContext oMathContext = MathContext.DECIMAL128;

        BigDecimal oMonto = bankRequest
                .getMonto()
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal oTasaMensual = bankRequest
                .getTasa()
                .setScale(2, RoundingMode.HALF_UP)
                .divide(new BigDecimal(100), oMathContext);

        BigDecimal oTazaMensualConCuenta = bankRequest
                .getTasa()
                .subtract(BigDecimal.valueOf(0.2), oMathContext)
                .setScale(2, RoundingMode.HALF_UP)
                .divide(new BigDecimal(100), oMathContext);

        Integer oPlazo = bankRequest.getPlazo();

        BigDecimal oPagoMensual = calcularCuota(oMonto, oTasaMensual, oPlazo, oMathContext);

        BigDecimal oPagoMensualConCuenta = calcularCuota(oMonto, oTazaMensualConCuenta, oPlazo, oMathContext);

        BankResponse oResponse = new BankResponse();
        oResponse.setCuota(oPagoMensual);
        oResponse.setTasa(oTasaMensual);
        oResponse.setPlazo(oPlazo);

        oResponse.setCuotaConMCuenta(oPagoMensualConCuenta);
        oResponse.setTasaConMCuenta(oTazaMensualConCuenta);
        oResponse.setPlazoConMCuenta(oPlazo);

        return oResponse;
    }

    public BigDecimal calcularCuota(BigDecimal prMonto, BigDecimal prTasa, Integer prPlazo, MathContext prMathContext){

        BigDecimal unoMasTasa = prTasa.add(BigDecimal.ONE, prMathContext);

        BigDecimal unoMasTasaToN = unoMasTasa.pow(prPlazo, prMathContext);

        BigDecimal unoMasTasaConNNegativa = BigDecimal.ONE.divide(unoMasTasaToN, prMathContext);

        BigDecimal numerador = prMonto.multiply(prTasa, prMathContext);
        BigDecimal denominator = BigDecimal.ONE.subtract(unoMasTasaConNNegativa,prMathContext);

        BigDecimal cuotaMensual = numerador.divide(denominator, prMathContext);

        cuotaMensual = cuotaMensual.setScale(2, RoundingMode.HALF_UP);

        return cuotaMensual;
    }

}