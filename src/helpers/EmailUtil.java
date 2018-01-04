/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;


import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Authenticator;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EmailUtil{  

   
    public static void sendEmail(String email, String text){
        final String username = "hemanth1919thota@gmail.com";
        final String password = "Nandu@mylove143";
        String[] to = {email};
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        Session session=Session.getInstance(props,new Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(username, password);
            }
        });
        try {

            Message message = new MimeMessage(session);
             InternetAddress me = new InternetAddress("EShoppers@uncc.edu");
                try {
                    me.setPersonal("EShoppers");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                message.setFrom(me);
            for (int i = 0; i < to.length; i++) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
            }
            message.setSubject("Account Creation Confirmation");
            message.setText(text);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

