package com.example.emailsender.Service.Validator;
import com.example.emailsender.Error.PriceValidationException;
import com.example.emailsender.Service.Validator.PackagesPriceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
@Service
public class PriceValidationService {

    private final Map<String, PackagesPriceValidator> validators;


    // Spring автоматически находит все классы, реализующие PackagePriceValidator, и передает их сюда
    public PriceValidationService(List<PackagesPriceValidator> validatorList) {
     this.validators = validatorList.stream().collect(Collectors.toMap(PackagesPriceValidator::getType, Function.identity()));
    }


    public void validatePackagePrice(String packageType, BigDecimal price) {

        // 1. Находим нужный валидатор по типу пакета
        PackagesPriceValidator validator = validators.get(packageType.toLowerCase());

        if (validator == null) {
            // Если пришел неизвестный тип пакета
            throw new IllegalArgumentException("Unknown package type: " + packageType);
        }

        // 2. Вызываем метод валидации на найденном объекте
        if (!validator.isValid(String.valueOf(price))) {
            // 3. Если цена не прошла проверку (например, 1 рубль), бросаем исключение
            throw new PriceValidationException(validator.getMessage());
        }

        // Если метод завершился без исключений, значит, цена точна и валидна.
    }
}
