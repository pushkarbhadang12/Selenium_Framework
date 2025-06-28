package utils;

import java.io.File;
import java.util.Properties;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

public class EmailUtils {
	
	public static void sendTestReport(String reportPath) {
		
		final String senderEmail = ConfigReader.readConfigValue("senderEmail");
		final String appPassword = ConfigReader.readConfigValue("appPassword");
		final String recipientEmail = ConfigReader.readConfigValue("recipientEmail");
		
		//SMTP Server Properties
		Properties prop = new Properties();
		prop.put("mail.smtp.auth","true");
		prop.put("mail.smtp.host","smtp.gmail.com");
		prop.put("mail.smtp.starttls.enable","true");
		prop.put("mail.smtp.port","587");
		
		//Create a session with Authentication
		Session session = Session.getInstance(prop, new Authenticator() {			
			protected PasswordAuthentication getPasswordAuthentication() {				
				return new PasswordAuthentication(senderEmail, appPassword);
			}
		});
		session.setDebug(true);
		
		try {
			//Create email message
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(senderEmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
			message.setSubject("Test Automation Report Email");
			message.setText("This is a Test Automation Report Email from Selenium Framework");
			
			//Email body
			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setText("Hello \n\n This is a Test Automation Report Email from Selenium Framework \n\n Regards,\nQA Team");
			
			//Email Attachment
			MimeBodyPart attachmentPart = new MimeBodyPart();			
			attachmentPart.attachFile(new File(reportPath));
			
			//Combine Email body and attachment 
			MimeMultipart multiPart = new MimeMultipart();
			multiPart.addBodyPart(textPart);
			multiPart.addBodyPart(attachmentPart);
			message.setContent(multiPart);
			
			//send email
			Transport.send(message);
			System.out.println("Email send successfully");
			
		} catch (Exception e) {
			System.out.println("Unable to send email. Getting exception as "+e.getMessage());
		}
	}

}
