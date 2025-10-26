package com.example.emailsender.Service;



import com.example.emailsender.Entity.EmailEntity;
import com.example.emailsender.Error.ApplicationException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;


    public EmailService(JavaMailSender mailSender) {

        this.mailSender = mailSender;
    }

    public void SendEmail(EmailEntity emailEntity) {
        if (emailEntity == null) {
            throw new IllegalArgumentException("Заявка не может быть пуста");
        }

        try {
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
