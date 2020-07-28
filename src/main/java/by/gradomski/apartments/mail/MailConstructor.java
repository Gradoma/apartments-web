package by.gradomski.apartments.mail;

public class MailConstructor {
    private static final String NEW_ADMIN_TEXT_1 = "<b>Welcome</b><br><p>Your account parameters: </p><br>";
    private static final String LOGIN = "login";
    private static final String NEW_ADMIN_TEXT_2 = "<p>To complete your registration - click the link below</p>";
    private static final String PASSWORD = "password";
    private static final String CONFIRMATION_LINK = "<a href=http://localhost:8080/apartments_web_war/control?command=confirm_email&login=";
    private static final String BAN_TEXT_1 = "<b>Dear ";
    private static final String BAN_TEXT_2 = "<br><p>Your </p>";
    private static final String BAN_TEXT_3 = " was banned </p>";

    public static String newAdminMail(String loginParameter, String passwordParameter){
        StringBuilder builder = new StringBuilder();
        builder.append(NEW_ADMIN_TEXT_1);
        builder.append(LOGIN);
        builder.append(": ");
        builder.append(loginParameter);
        builder.append("<br>");
        builder.append(PASSWORD);
        builder.append(": ");
        builder.append(passwordParameter);
        builder.append("<br>");
        builder.append(NEW_ADMIN_TEXT_2);
        builder.append("<br>");
        builder.append(CONFIRMATION_LINK);
        builder.append(loginParameter);
        builder.append(">Confirm your email</a>");
        return builder.toString();
    }

    public static String banMail(String loginParameter, String banSubject, String description){
        StringBuilder builder = new StringBuilder();
        builder.append(BAN_TEXT_1);
        builder.append(loginParameter);
        builder.append(BAN_TEXT_2);
        builder.append(banSubject);
        builder.append(" (");
        builder.append(description);
        builder.append(")");
        builder.append(BAN_TEXT_3);
        return builder.toString();
    }
}
