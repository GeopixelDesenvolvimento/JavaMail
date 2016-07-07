package JavaMail;

 /**
  * Class responsible for reporting all data related to sending email.
  * @author marcelly.paula
  *
  */

public class SendMailTest {

	public static void main(String[] args) throws Exception {
	
		Email email = new Email();

		//email.addMailsUsers("dvd.pansardis@gmail.com");
		email.addMailsUsers("dpsmarcelly@gmail.com");
		//email.addMailsUsers("alexandre.penteado@geopx.com.br");
		
		email.setSubjectMail("JavaMail test application");
		email.setBodyMail("mail sending test.");
		
		MailServer ms = new MailServer("Email.xml", "teste@geopx.com.br", "geopixel1234");
		
		if(ms.SendMail(email)){
			System.out.print("Email successfully sent!");
		}else{
			System.out.print(">> Error: Send Message!");
		}

	}
}
