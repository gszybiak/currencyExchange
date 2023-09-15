package currencyExchange.email;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static currencyExchange.helpers.PropertiesHelper.loadPropertiesConnection;

public class SendEmail {
    private static String from;
    private static String password;

    public static boolean sendEmail(String receiver, String contents) {
        try {
        Properties propertiesEmail = loadPropertiesConnection();

        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.op.pl");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(properties, new MyAuthenticator(propertiesEmail.getProperty("email.from"),
                propertiesEmail.getProperty("email.password")));

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            message.setSubject("Exchange rate statistics");
            message.setText("Good morning\nPlease read the details:\n" + contents + "\nRegards!");
            Transport.send(message);
            return true;

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
