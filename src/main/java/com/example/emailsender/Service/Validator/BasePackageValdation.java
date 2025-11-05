package com.example.emailsender.Service.Validator;

import java.math.BigDecimal;

public class BasePackageValdation implements PackagesPriceValidator{
    private static final String incomingPrice = "10000";
    @Override
    public String getType() {
        return "Базовый";
    }

    @Override
    public boolean isValid(String price) {
        return new BigDecimal(price).compareTo(new BigDecimal(incomingPrice)) >= 0;
    }

    @Override
    public String getMessage() {
        return "Базовый начинается от 10000";
    }

}
