package currencyExchange.email;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class SendEmail {
    public static void sendEmail(String receiver) {
        String from = "glibrary@op.pl";
        String password = "Glibrary.123";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.op.pl");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(properties, new MyAuthenticator(from, password));

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            message.setSubject("Tytuł maila wysłanego z kantoru");
            MimeBodyPart textBody = new MimeBodyPart();
            textBody.setText("Witaj\nTytuł maila wysłanego z kantoru!\nPozdrawiam");
            MimeBodyPart attachmentBody = new MimeBodyPart();
            String filename = "file.txt";
            DataSource source = new FileDataSource(filename);
            attachmentBody.setDataHandler(new DataHandler(source));
            attachmentBody.setFileName(filename);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textBody);
            multipart.addBodyPart(attachmentBody);
            message.setContent(multipart);
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
