package co.com.template.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@Log4j2
@EnableAsync
public class EmailServiceImpl {

    @Value("${spring.mail.host}")
    public String emailHost;

    @Value("${spring.mail.port}")
    public Integer emailPort;
    @Value("${spring.mail.username}")
    public String emailFrom;

    @Value("${spring.mail.password}")
    public String emailPassword;

    private final TemplateEngine templateEngine;

    @Autowired
    public EmailServiceImpl(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendMail( Map<String, Object> data, String emailTo, String subject, String nameTemplate, List<String> bccList ){
        try {
            Context context = new Context();
            context.setVariables(data);
            String process;
            process = templateEngine.process(nameTemplate, context);
            JavaMailSender mailSender = Util.getJavaMailSender(emailHost, emailPort, emailFrom, emailPassword);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setSubject(subject);
            helper.setText(process, true);
            if(Objects.nonNull(emailTo))
                helper.setTo(emailTo);
            helper.setFrom(emailFrom);
            if(Objects.nonNull(bccList)){
                for (String bcc : bccList) {
                    helper.addBcc(bcc);
                }
            }
            mailSender.send(mimeMessage);
        }catch(Exception err){
            log.error(err.getMessage(), err);
        }
    }
}
