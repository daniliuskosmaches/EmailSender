package com.example.emailsender.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Pattern;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@Getter
@Setter
public class EmailDTO {
    @NotNull
    private String PackageType;

    @NotBlank(message = "Имя обязательно")
    @Size(max = 50, message = "Имя слишком длинное")
    private String name;

    @NotBlank(message = "Телефон обязателен")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Неверный формат телефона")
    private String phone;

    @jakarta.validation.constraints.NotNull(message = "Дата мероприятия обязательна")
    private LocalDate eventDate;

    @NotNull
    private LocalDate childBirthday;
    private String message;




}
