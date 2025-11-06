package com.example.emailsender.Service;



import com.example.emailsender.DTO.EmailDTO;
import com.example.emailsender.Error.ApplicationException;
import com.example.emailsender.Service.Validator.PriceValidationService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class EmailService {

    // 2. Внедрение через конструктор (Spring предоставит экземпляр)


    private final JavaMailSender mailSender;
    private final PriceValidationService priceValidationService;


    public EmailService(PriceValidationService priceValidationService, JavaMailSender mailSender) {
        this.priceValidationService = priceValidationService;

        this.mailSender = mailSender;
    }

    public void SendEmail(EmailDTO emailEntity) {
        // Сначала выполняем валидацию (вызываем нестатический метод)
        // Если цена не пройдет проверку, будет выброшено исключение PriceValidationException
      
        // Если исключения нет, значит, цена валидна, и мы продолжаем отправку:
        System.out.println("Цена валидна. Отправляем сообщение получателю: " + recipient);

        if (emailEntity == null) {
            throw new IllegalArgumentException("Заявка не может быть пуста");
        }
        try {
            // !!! ВАЖНО: нужно добавить packageType и price в EmailDTO, чтобы их получить
            String packageType = emailEntity.getPackageType();
            BigDecimal price = emailEntity.getPrice();

            priceValidationService.validatePackagePrice(packageType, price);

            log.info("Цена для пакета {} успешно прошла валидацию.", packageType);

        } catch (Exception e) {
            log.error("Валидация цены провалилась: {}", e.getMessage());
            // Если валидация провалилась, бросаем исключение и останавливаем отправку.
            throw e;
        }

        try {

            log.info("Цена для пакета {} успешно прошла валидацию.", packageType);
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo("EMAIL_TO"); // <-- получатель (можно заменить на emailEntity.getEmail())
            msg.setText("Новая заявка от " + emailEntity.getName());
            String emailText = String.format(
                    "Новая заявка:\n\n" +
                            "Имя: %s\n" +
                            "Телефон: %s\n" +
                            "Дата мероприятия: %s\n" +
                            "День рождения ребенка: %s\n" +
                            "Сообщение: %s",
                    emailEntity.getName(),
                    emailEntity.getPhone(),
                    emailEntity.getEventDate(),
                    emailEntity.getChildBirthday(),
                    emailEntity.getMessage() != null ? emailEntity.getMessage() : "Нет"
            );



            mailSender.send(msg);
            log.info("Письмо успешно отправлено клиенту {}", "client@example.com");

        } catch (MailSendException e) {
            log.error("Не удалось отправить письмо: {}", e.getMessage());
            throw new ApplicationException("Ошибка при отправке письма", e);
        } catch (Exception e) {
            log.error("Неизвестная ошибка при отправке письма", e);
            throw new ApplicationException("Ошибка почты", e);
        }
    }
}
