package by.gradomski.apartments.mail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class MailSender {
    private String sendToAddress;
    private String mailSubject;
    private String mailText;
    private Properties props;
    private String propertiesPath = "/prop/mail.properties";
    private static final Logger log = LogManager.getLogger();

    public MailSender(String sendToAddress, String mailSubject, String mailText){
        this.mailSubject = mailSubject;
        this.mailText = mailText;
        this.sendToAddress = sendToAddress;
        props = new Properties();
        try {
            props.load(this.getClass().getResourceAsStream(propertiesPath));
        } catch (IOException e){
            log.error("mail config file problem: " + e);
        }
    }

    public void send(){
        try {
            Message message = initMessage();
            Transport.send(message);
        } catch (AddressException e){
            log.error("incorrect address " + sendToAddress + " : " + e);
        } catch (MessagingException e){
            log.error("error generating or sending mail" + e);
        }
    }

    private MimeMessage initMessage() throws MessagingException {
        String login = props.getProperty("mail.user.name");
        String password = props.getProperty("mail.user.password");
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(login, password);
            }
        });
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(login));
        message.setSubject(mailSubject);
        message.setContent(mailText, "text/html");
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendToAddress));
        return message;
    }
}
