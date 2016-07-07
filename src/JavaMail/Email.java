package JavaMail;


import java.util.ArrayList;


/**
 * Class responsible for creating all the attributes
 * that will be needed to create and send an email
 * @author marcelly.paula
 *
 */

public class Email {

	private String subjectMail; //mail subject
	private String bodyMail; //email body, where this text message
	private ArrayList<String>  toMailsUsers = new ArrayList<String>(); //email list and names of recipients

	
	public String getSubjectMail() {
		return subjectMail;
	}
	public void setSubjectMail(String subjectMail) {
		this.subjectMail = subjectMail;
	}
	public String getBodyMail() {
		return bodyMail;
	}
	public void setBodyMail(String bodyMail) {
		this.bodyMail = bodyMail;
	}
	public void addMailsUsers(String email) {
		this.toMailsUsers.add(email);
	}
	public ArrayList<String> getToMailsUsers(){
		return this.toMailsUsers;
	}
}

