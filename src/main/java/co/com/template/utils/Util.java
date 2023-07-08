package co.com.template.utils;


import co.com.template.security.WebSecurityConfig;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Properties;


public class Util {


       public static String convertToDateTimeFormatted(LocalDate localDate, String format ){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
           return localDate.format(dateTimeFormatter);
    }

    public static String convertToDateTimeHourFormatted(LocalDateTime localDateTime, String format ){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(dateTimeFormatter.localizedBy(Locale.US));
    }



    public static JavaMailSender getJavaMailSender(String emailHost, Integer emailPort, String emailFrom, String emailPassword) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailHost);
        mailSender.setPort(emailPort);
        mailSender.setUsername(emailFrom);
        mailSender.setPassword(emailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");


        return mailSender;
    }
    public static PasswordEncoder getEncoder(){
        WebSecurityConfig ws= new WebSecurityConfig();
        return ws.passwordEncoder();
    }

}
