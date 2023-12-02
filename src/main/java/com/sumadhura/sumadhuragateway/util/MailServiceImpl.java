package com.sumadhura.sumadhuragateway.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.sumadhura.sumadhuragateway.dto.Email;



@SuppressWarnings("deprecation")
@Service("mailService")
public class MailServiceImpl implements MailService{

	
	@Autowired
	@Qualifier("mailsenderObj")
	JavaMailSender mailSender;
	
	
	@Autowired
	VelocityEngine velocityEngine;
	
	@Value("${ERROR_MAIL}")
	private String ERROR_MAIL;

	
	
	private final static Logger logger = Logger.getLogger(MailServiceImpl.class);
	
	/*@Autowired
	Configuration freemarkerConfiguration;*/
	

	@Override
	public void sendEmail(Email email) {
		logger.info("***** The control is inside the sendMail method in MailserviceImpl ******");	
		MimeMessagePreparator preparator = getMessagePreparator(email);
		
		try {
            mailSender.send(preparator);
            System.out.println("Message has been sent.............................");
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
	}
	
	private MimeMessagePreparator getMessagePreparator(final Email email){
		
		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {
            	MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
 
               	helper.setSubject(email.getSubject());
               	helper.setTo(email.getToMail());
     
               	Map<String, Object> model = new HashMap<String, Object>();
                model.put("email", email);
                
            	String text = geVelocityTemplateContent(model);
            //	String text = geFreeMarkerTemplateContent(model);
            //    System.out.println("Template content : "+text);

                // use the true flag to indicate you need a multipart message
            	helper.setText(text, true);

            	//Additionally, let's add a resource as an attachment as well.
            	if(email.isStatus()) {
                	//Additionally, let's add a resource as an attachment as well.
                //	helper.addAttachment("dhoni_dashboard.jpg", new ClassPathResource("images/dhoni_dashboard.jpg"));
            		

                	helper.addAttachment("Sumadhura_Project_Brochures.pdf", new ClassPathResource("images//Sumadhura_Project_Brochures.pdf"));
                	
                //	helper.addAttachment("Eden-Garden-Brochure.pdf", new ClassPathResource("images//Eden-Garden-_-Brochure.pdf"));

               // 	helper.addAttachment("Nandanam-Brochure.pdf", new ClassPathResource("images//Nandanam-Brochure.pdf"));
                	
                }
            }
        };
        return preparator;
	}
	
	
	public String geVelocityTemplateContent(Map<String, Object> model){
		StringBuffer content = new StringBuffer();
		try{
			content.append(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,((Email)model.get("email")).getTemplateName(), model));
			return content.toString();
		}catch(Exception e){
			System.out.println("Exception occured while processing velocity template:"+e.getMessage());
		}
	      return "";
	}

	public void sendErrorMail(final Exception ex) {
		logger.info("***** The control is inside the sendErrorMail method in MailserviceImpl ******");
		List<Email> emailList = new ArrayList<Email>();
		String[] errorAdmins = new String[2];
		errorAdmins[0] = "teamamaravadhis@gmail.com" ;
		errorAdmins[1] = "teamamaravadhis@gmail.com" ;
		for(String admin :errorAdmins ) {
			Email email = new Email();
			email.setSubject(" Production exception information");
			email.setTemplateName("/vmtemplates/velocity_ErrormailTemplate.vm");
			email.setToMail(admin);
			email.setException(ex);
			emailList.add(email);
		}
		
		for(Email email : emailList) {
			sendEmail(email);
		}
		
	}
	
	


}
