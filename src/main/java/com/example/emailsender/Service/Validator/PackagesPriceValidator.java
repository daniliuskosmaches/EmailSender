package com.example.emailsender.Service.Validator;

public interface PackagesPriceValidator {
String getType();
boolean isValid(String price);
String getMessage();

}
