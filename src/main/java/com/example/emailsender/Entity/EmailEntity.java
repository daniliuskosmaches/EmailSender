package com.example.emailsender.Entity;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;


public class EmailEntity {

    @NotBlank(message = "Имя обязательно")
    @Size(max = 50, message = "Имя слишком длинное")
    private String name;

    @NotBlank(message = "Телефон обязателен")
    @Pattern("")
    private String phone;

    @jakarta.validation.constraints.NotNull(message = "Дата мероприятия обязательна")
    private LocalDate eventDate;

    @NotNull
    private LocalDate childBirthday;
    private String message;
    public  EmailEntity(String name, String phone, LocalDate eventDate, LocalDate childBirthday) {
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public String getPhone(){
        return phone;

    }
    public LocalDate getEventDate(){
        return eventDate;
    }
    public LocalDate getChildBirthday(){
        return childBirthday;
    }

    public String getMessage() {
        return message;
    }



}
