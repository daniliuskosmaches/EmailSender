package com.example.emailsender.Service.Validator;

import java.math.BigDecimal;

public class StandartPackageValidator implements PackagesPriceValidator{
private static final BigDecimal incomingPrice = new BigDecimal("35000");
    @Override
    public String getType() {
        return "Стандарт";
    }

    @Override
    public boolean isValid(String price) {

        return new BigDecimal(price).compareTo(incomingPrice) >= 0;
    }

    @Override
    public String getMessage() {
        return "Стандарт стоит от 35 000 рублей";
    }
}
