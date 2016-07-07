package JavaMail;


import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.sun.mail.util.MailSSLSocketFactory;


/**
 * Class responsible for receiving
 * Settings, create the necessary objects
 * For email and then send it.
 * It is this class that we will work with the reading of the XML file
 * Containing the parameters of protol, port, server, and User and Password
 * Responsible for sending e-mails with the JavaMail API
 * @author marcelly.paula
 *
 */

public class MailServer {
	
	private Protocoll protocoll;
	private boolean auth = true;
	private final static boolean debug = true;
	private NodeList nlProtocol;
	private NodeList nlServer; 
	private NodeList nlPort;
	String Protocol;
	String Server;
	String Port;
	String User;
	String Password;
	
	public MailServer(String pathXml, String user, String pass){
		ReadXml (pathXml);
		User = user;
		Password = pass;
	}
	/**
	 * Method responsible for making reading
	 * Xml file and capture the values of tags
	 * @param pathXml
	 * @return
	 */
	private boolean ReadXml (String pathXml){
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder Builder;
			Builder = dbFactory.newDocumentBuilder();
			Document document = Builder.parse(new File(pathXml));
			document.getDocumentElement().normalize();
			
			nlProtocol = document.getElementsByTagName("protocol");
			Protocol = nlProtocol.item(0).getTextContent();
		
			nlServer = document.getElementsByTagName("server");
			Server = nlServer.item(0).getTextContent();
			
			nlPort = document.getElementsByTagName("port");
			Port = nlPort.item(0).getTextContent();
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	/**
	 * responsible method necessary properties for sending email
	 * @param email
	 * @return
	 * @throws IOException
	 */
	public boolean SendMail(Email email) throws IOException {

		try { 
			Properties props = new Properties();
			
		    props.put("mail.transport.protocol",Protocol); 
		    props.put("mail.smtp.host", Server);
		    props.put("mail.smtp.port", Port); 
		   // props.put("mail.smtp.timeout", 10000); //Debug
		    props.put("mail.smtp.starttls.enable", "true");
		    
			if ((protocoll == Protocoll.STMP) && (protocoll == Protocoll.SMTPS)) {
				props.put("mail.smtp.ssl.enable", true);
			}
			else if (protocoll == Protocoll.TLS) {
				props.put("mail.smtp.starttls.enable", true);

			}
		    
		    /**
		     * SSL security check
		     */
			MailSSLSocketFactory sf = null;
		
			sf = new MailSSLSocketFactory(); //Initializes a new SSL connection
				
			sf.setTrustAllHosts(true); //host reliability check
			props.put("mail.smtp.ssl.socketFactory", sf); // Method responsible for creating the SMTP connection layer
			
			/**
			 * responsible method for verifying authentication sending email
			 */
			 Authenticator authenticator = null;
		        if (auth) {
		            props.put("mail.smtp.auth", true);
		            authenticator = new Authenticator() {
		                private PasswordAuthentication pa = new PasswordAuthentication(User, Password);
		                @Override
		                public PasswordAuthentication getPasswordAuthentication() {
		                    return pa;
		                }
		            };
		        }

		        Session session = Session.getInstance(props, authenticator);
		    session.setDebug(debug);
			/**
			 * responsible method for assembling the destination email
			 */
		
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(User));
			
			ArrayList<String> emails = email.getToMailsUsers();
			for (int index = 0; index < emails.size(); index++){
				message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress (emails.get(index)));
			}
	 
	        
			message.setSubject(email.getSubjectMail()); // Set the email subject 
			message.setText(email.getBodyMail()); // set the content/body of the email
			
			Transport tr = session.getTransport(Protocol);
			tr.connect(Server, User, Password);
			tr.sendMessage(message, message.getAllRecipients());
			message.saveChanges();
			
			tr.close();
			System.out.println("Email successfully sent!");

		} catch (MessagingException | GeneralSecurityException e) {
			System.out.println(">> Error: Send Message!");
			e.printStackTrace();
		}
	    
		return true;	
	}
}

