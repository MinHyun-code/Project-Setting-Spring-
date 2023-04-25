package sample.prac.common.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import org.springframework.beans.factory.annotation.Value;

public class MailAuth extends Authenticator{

	PasswordAuthentication pa;
	
    @Value("${smtp.id}")
    private String mailId;
    @Value("${smtp.pw}")
    private String mailPw;
    @Value("${smtp.host}")
    private String mailHost;

	
    public MailAuth() {
    	System.out.println(mailId);
        String mail_id = "mhan@bsgglobal.com";
        String mail_pw = "";
        pa = new PasswordAuthentication(mail_id, mail_pw);
    }

    public PasswordAuthentication getPasswordAuthentication() {
        return pa;

    }

}
