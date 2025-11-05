package com.example.emailsender.Service.Validator;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class PremiumPackageValidator implements PackagesPriceValidator {
private static final BigDecimal incomingPrice= new BigDecimal("55000");
    @Override
    public String getType() {
        return "Премиум";
    }

    @Override
    public boolean isValid(@NotNull String price) {
        return new BigDecimal(price).compareTo(incomingPrice) >= 0;
    }

    @Override
    public String getMessage() {
        return "цена Премиум вакансии от 55 000 рублей";
    }
}
