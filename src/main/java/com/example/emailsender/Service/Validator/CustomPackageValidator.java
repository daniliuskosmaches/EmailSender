package com.example.emailsender.Service.Validator;

import java.math.BigDecimal;

public class CustomPackageValidator implements PackagesPriceValidator{
    private static final String incomingPrice = "15000";
    @Override
    public String getType() {
        return "Кастомный";
    }

    @Override
    public boolean isValid(String price) {


        return new BigDecimal(price).compareTo(new BigDecimal(incomingPrice)) >= 0;
    }

    @Override
    public String getMessage() {
        return "кастомный начинается от 15 000 рублей";
    }
}
