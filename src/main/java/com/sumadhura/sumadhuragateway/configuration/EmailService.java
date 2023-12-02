package com.sumadhura.sumadhuragateway.configuration;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineFactory;



@Configuration
public class EmailService {

	@Value("${HOST_EMAIL}")
	private String HOST_EMAIL;
	
	@Value("${HOST_EMAIL_PASSWORD}")
	private String HOST_EMAIL_PASSWORD;
	
	@Value("${HOST}")
	private String HOST;
	
	@Value("${PORT}")
	private String PORT;
	
	private final static Logger logger = Logger.getLogger(EmailService.class);
	
	@Bean(name = "mailsenderObj")
	//@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)  prototype
 //	@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
	public JavaMailSender getMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		logger.info("****** The control is inside the getMailSender *******");
		// Using gmail.
		mailSender.setHost(HOST);
		mailSender.setPort(Integer.valueOf(PORT));
		mailSender.setUsername(HOST_EMAIL);
		mailSender.setPassword(HOST_EMAIL_PASSWORD);
		
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.starttls.enable", "true");
		javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.transport.protocol", "smtp");
		javaMailProperties.put("mail.debug", "false");
		javaMailProperties.put("mail.smtp.socketFactory.port",Integer.valueOf(PORT));
		javaMailProperties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		javaMailProperties.put("mail.smtp.socketFactory.fallback", "false");
		mailSender.setJavaMailProperties(javaMailProperties);
		return mailSender;
	}
	
		
	/*
	 * Velocity configuration.
	 */
	@Bean
	public VelocityEngine getVelocityEngine() throws VelocityException, IOException {
		VelocityEngineFactory factory = new VelocityEngineFactory();
		Properties props = new Properties();
		props.put("resource.loader", "class");
		props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

		factory.setVelocityProperties(props);
		return factory.createVelocityEngine();
	}

}
