package by.gradomski.apartments.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

public class SessionCreator {
    public static Session createSession(Properties properties){
        String login = properties.getProperty("mail.user.name");
        String password = properties.getProperty("mail.user.password");
        return Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(login, password);
            }
        });
    }
}
