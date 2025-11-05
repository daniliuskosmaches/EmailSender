package com.example.emailsender.Service.Validator;
import com.example.emailsender.Error.PriceValidationException;
import com.example.emailsender.Service.Validator.PackagesPriceValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PriceValidationService {
    private final Map<String, PackagesPriceValidator> validators;

    // Spring автоматически находит все классы, реализующие PackagePriceValidator, и передает их сюда
    public PriceValidationService(List<PackagesPriceValidator> validatorList) {
        this.validators = validatorList.stream()
                // Создаем Map: ключ - результат .getType(), значение - сам объект-валидатор
                .collect(Collectors.toMap(
                        v -> v.getType().toLowerCase(),
                        Function.identity()
                ));
    }
    public static void validatePackagePrice(String packageType, BigDecimal price) {

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
