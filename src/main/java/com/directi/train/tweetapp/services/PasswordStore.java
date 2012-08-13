package com.directi.train.tweetapp.services;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: sitesh
 * Date: 6/8/12
 * Time: 8:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class PasswordStore {

    public static void sendPassword(String receiver, String pwd) {
        final String username = "noreply.twimini@gmail.com";
        final String password = "noreply.twimini";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session mailSession = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            message.setSubject("Twimini: Password Update");
            message.setText("Howdy!,\n\n" + "Your Password: " + pwd);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String SHA(String password) {
        MessageDigest md = null;
        byte [] passwordBytes = password.getBytes();
        try {
            md = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(passwordBytes,0,passwordBytes.length);
        byte[] encodedPassword = md.digest();
        return toHexString(encodedPassword);
    }

    public static String toHexString(byte[] buf) {
        char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'A', 'B', 'C', 'D', 'E', 'F' };

        StringBuffer strBuf = new StringBuffer(buf.length * 2);
        for (int i = 0; i < buf.length; i++) {
            strBuf.append(hexChar[(buf[i] & 0xf0) >>> 4]);
            strBuf.append(':');
            strBuf.append(hexChar[buf[i] & 0x0f]);
        }
        return strBuf.toString();
    }

}
