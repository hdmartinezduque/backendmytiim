package co.com.template.utils;


import co.com.template.Repositories.CommentRepository;
import co.com.template.Repositories.entities.Comment;
import co.com.template.Repositories.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
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


    public String sendMail(User userFrom, User userTo, Comment comment) throws MessagingException{
        Context context = new Context();
        Map<String, Object> data = new HashMap<>();
        data.put(Constants.EMAIL_NAME, userFrom.getUserName());
        data.put(Constants.EMAIL_DATE, Util.convertToDateTimeHourFormatted(comment.getCommentDate(),Constants.DATETIME_FORMAT));
        data.put(Constants.EMAIL_DESCRIBE, comment.getCommentDescribe());

        context.setVariables(data);
        String process;
        process = templateEngine.process(Constants.INDEX_TEMPLATE, context);
        javax.mail.internet.MimeMessage mimeMessage = Util.getJavaMailSender(emailHost, emailPort, emailFrom, emailPassword).createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject(Constants.SUBJECT_MESSAGE + userFrom.getUserName());
        helper.setText(process, true);
        helper.setTo(userTo.getUserEmail());
        helper.setFrom(emailFrom);
        Util.getJavaMailSender(emailHost, emailPort, emailFrom, emailPassword).send(mimeMessage);
        return "Sent";
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
