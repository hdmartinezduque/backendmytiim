package co.com.template.utils;

import co.com.template.Repositories.entities.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import javax.mail.MessagingException;
import java.util.Map;

@Component
public class EmailServiceImpl {

    @Value("${spring.mail.host}")
    public String emailHost;

    @Value("${spring.mail.port}")
    public Integer emailPort;
    @Value("${spring.mail.username}")
    public String emailFrom;

    @Value("${spring.mail.password}")
    public String emailPassword;

    private TemplateEngine templateEngine;
    private Comment comment;

    @Autowired
    public EmailServiceImpl(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendMail( Map<String, Object> data, String emailTo, String subject, String nameTemplate ) throws MessagingException{
        Context context = new Context();
        context.setVariables(data);
        String process;
        process = templateEngine.process(nameTemplate, context);
        javax.mail.internet.MimeMessage mimeMessage = Util.getJavaMailSender(emailHost, emailPort, emailFrom, emailPassword).createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject(subject);
        helper.setText(process, true);
        helper.setTo(emailTo);
        helper.setFrom(emailFrom);
        Util.getJavaMailSender(emailHost, emailPort, emailFrom, emailPassword).send(mimeMessage);
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        Util.getJavaMailSender(emailHost, emailPort, emailFrom, emailPassword).send(message);
    }
}
