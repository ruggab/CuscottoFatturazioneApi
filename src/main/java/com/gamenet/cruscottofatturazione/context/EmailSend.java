package com.gamenet.cruscottofatturazione.context;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gamenet.cruscottofatturazione.models.EmailMessage;
import com.gamenet.cruscottofatturazione.services.interfaces.ApplicationLogsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailSend
{
    private final ApplicationLogsService appService;
    
    private Logger log = LoggerFactory.getLogger(getClass());
    
    public void send(EmailMessage email)
    {
        // Recipient's email ID needs to be mentioned.
        String to = email.getTo();

        // Sender's email ID needs to be mentioned
        //String from = env.getProperty("mail.smtp.default.from");
        //String from = getFrom();
        String from = email.getSmtp_default_from();
        
        if(email.getFrom() != "")
        {
        	from = email.getFrom();
        }

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", email.getSmtp_host());
        properties.put("mail.smtp.port", email.getSmtp_port());
        properties.put("mail.smtp.ssl.enable", email.getSmtp_ssl_enable());

        Boolean smtp_auth = email.getSmtp_auth();
        properties.put("mail.smtp.auth", smtp_auth);
        
        Session session = Session.getDefaultInstance(properties);

        if(smtp_auth)
        {
	        // Get the Session object.// and pass username and password
	        session = Session.getInstance(properties, new Authenticator() 
	        {
	            protected PasswordAuthentication getPasswordAuthentication() {
	
	                return new PasswordAuthentication(email.getSmtp_auth_user(), email.getSmtp_auth_pwd());
	            }
	
	        });
        }

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            // message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            if (to.indexOf(',') > 0)
            {
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            }
            else
            {
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            }

            // Set Subject: header field
            message.setSubject(email.getSubject());
            
            BodyPart msgBodyPart = new MimeBodyPart();            
            if(email.getIsHtml())
            {
            	message.setContent(email.getMessage(), "text/html; charset=utf-8");
            	msgBodyPart.setContent(email.getMessage(), "text/html; charset=utf-8");
            }
            else
            {
            	message.setText(email.getMessage());
            	msgBodyPart.setText(email.getMessage());
            }
            
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(msgBodyPart);
            
            if(email.getAttachments() != null)
            {
            	if(email.getAttachments() != "")
                {
            		String[] attachList = email.getAttachments().split(";");
            		
            		for (String filepath : attachList)
            		{
            			addAttachment(multipart, filepath, email.getIsResouceAttach());
					}
                }
            }

            message.setContent(multipart);
                        
            System.out.println("sending...");
	    	this.log.info("EmailSend: send -> SENDING MESSAGE...");
	    	appService.insertLog("info", "EmailSend", "send", "SENDING MESSAGE...", "", "send");
            
            // SEND MESSAGE
            Transport.send(message);
            
	    	this.log.info("EmailSend: send -> EMAIL MESSAGE SEND");
	    	appService.insertLog("info", "EmailSend", "send", "EMAIL MESSAGE SEND", "", "send");
        }
        catch (MessagingException mex)
        {
    		String stackTrace = ExceptionUtils.getStackTrace(mex);
    		this.log.error("EmailSend: send -> " + stackTrace);
        	appService.insertLog("error", "EmailSend", "send", "Exception", stackTrace, "send");
			
            // mex.printStackTrace();
        }

    }
    
    private void addAttachment(Multipart multipart, String filepath, Boolean isResourceAttach)
    {  
        try
        {
			try 
			{
				String fileName = filepath.substring(filepath.lastIndexOf('/')+1);
				String completePath = "";
				
				if(isResourceAttach)
				{
					completePath = URLDecoder.decode(getClass().getResource(filepath).getPath(), "UTF-8");
				}
				else
				{
					completePath = filepath;
				}
			
	            DataSource source = new FileDataSource(completePath);
	            BodyPart messageBodyPart = new MimeBodyPart();      
				messageBodyPart.setDataHandler(new DataHandler(source));
		        messageBodyPart.setFileName(fileName);
		        multipart.addBodyPart(messageBodyPart);
			}
			catch (UnsupportedEncodingException e) {

	    		String stackTrace = ExceptionUtils.getStackTrace(e);
	    		this.log.error("EmailSend: addAttachment -> " + stackTrace);
	        	appService.insertLog("error", "EmailSend", "addAttachment", "Exception", stackTrace, "addAttachment");
			}
		}
        catch (MessagingException e)
        {
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("EmailSend: addAttachment -> " + stackTrace);
        	appService.insertLog("error", "EmailSend", "addAttachment", "Exception", stackTrace, "addAttachment");
		}
    }

}
