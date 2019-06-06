package com.ws.spring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.ws.spring.email.service.EmailServiceImpl;
import com.ws.spring.sms.service.SendSms;

@SpringBootApplication
@EnableJpaAuditing
@Configuration
public class MdogApplication extends SpringBootServletInitializer implements ApplicationRunner {

	Logger logger = LogManager.getLogger(this.getClass().getName());

	@Autowired
	EmailServiceImpl emailServiceImpl;

	@Autowired
	SendSms sendSms;

	public static void main(String[] args) {
		SpringApplication.run(MdogApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MdogApplication.class);
	}

	@Override
	public void run(ApplicationArguments applicationArguments) {
		logger.debug("Debugging log");
		logger.info("Info log");
		logger.warn("Hey, This is a warning!");
		logger.error("Oops! We have an Error. OK");
		logger.fatal("Damn! Fatal error. Please fix me.");
		// emailServiceImpl.sendMail("paramanagowda.patil@gmail.com", "Test Mdog App
		// Mail", "Test Mdog App Mail");
		try {
			sendSms.sendSMs();
		} catch (Exception e) {
			logger.error("SendSms got an error : {},", e.getMessage(), e);
		}
	}

	/*
	 * @Bean public JavaMailSender getJavaMailSender() { JavaMailSenderImpl
	 * mailSender = new JavaMailSenderImpl(); mailSender.setHost("smtp.gmail.com");
	 * mailSender.setPort(587);
	 * 
	 * mailSender.setUsername("paramanagowda.patil@gmail.com");
	 * mailSender.setPassword("letsdoit@333");
	 * 
	 * Properties props = mailSender.getJavaMailProperties();
	 * props.put("mail.transport.protocol", "smtp"); props.put("mail.smtp.auth",
	 * "true"); props.put("mail.smtp.starttls.enable", "true");
	 * props.put("mail.debug", "true");
	 * 
	 * return mailSender; }
	 */
}
