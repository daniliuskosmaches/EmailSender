package com.example.emailsender.Service;



import com.example.emailsender.Entity.EmailEntity;
import com.example.emailsender.Error.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j





public class EmailService {

    private final JavaMailSender mailSender;

    private static final List<String> BAD_WORDS = List.of(
            "дурак", "идиот", "тупой", "мат1", "мат2", "мат3"
    );

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }



    public void SendEmail(EmailEntity emailEntity) {

        if (emailEntity == null) {
            throw new IllegalArgumentException("Заявка не может быть пуста");
        }

        // 🧹 Проверка на плохие слова
        if (containsBadWords(emailEntity)) {
            throw new IllegalArgumentException("Сообщение содержит запрещённые слова");
        }

        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo("client@example.com");
            msg.setSubject("Новая заявка от " + emailEntity.getName());
            msg.setText(emailEntity.toString());

            mailSender.send(msg);
            log.info("✅ Письмо успешно отправлено клиенту {}", "client@example.com");

        } catch (MailSendException e) {
            log.error("Не удалось отправить письмо: {}", e.getMessage());
            throw new ApplicationException("Ошибка при отправке письма", e);
        } catch (Exception e) {
            log.error("Неизвестная ошибка при отправке письма", e);
            throw new ApplicationException("Ошибка почты", e);
        }
    }

    private boolean containsBadWords(EmailEntity emailEntity) {
        String text = (emailEntity.getMessage() + " " + emailEntity.getName()).toLowerCase();
        return BAD_WORDS.stream().anyMatch(text::contains);
    }
}
