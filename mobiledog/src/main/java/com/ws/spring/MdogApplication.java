package com.ws.spring;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

import com.ws.spring.email.service.EmailServiceImpl;

@SpringBootApplication
@EnableJpaAuditing
@Configuration
@EnableJdbcHttpSession
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@Resource(name="jdbc/walkindbDS", type=javax.sql.DataSource.class,  lookup="jdbc/walkindbDS")
public class MdogApplication extends SpringBootServletInitializer implements ApplicationRunner {

	Logger logger = LogManager.getLogger(this.getClass().getName());

	@Autowired
	EmailServiceImpl emailServiceImpl;

	public static void main(String[] args) {
		SpringApplication.run(MdogApplication.class, args);
	}

	@Bean
	public JndiDataSourceLookup jndDataSource() {
		JndiDataSourceLookup dataSource = null;
		try {
			JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
			dataSourceLookup.getDataSource("java:comp/env/jdbc/walkindbDS");
			//JndiTemplate jndi = new JndiTemplate();
			//dataSource = jndi.lookup("java:comp/env/jdbc/walkindbDS", DataSource.class);
			logger.info("JDNI Datasource Connection success : jdbc/walkindbDS");
		} catch (Exception e) {
			logger.error("NamingException for java:comp/env/jdbc/yourname", e);
		}
		return dataSource;
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
		try {
			// emailServiceImpl.sendMail("paramanagowda.patil@gmail.com", "Test Mdog App
			// Mail", "Test Mdog App Mail");

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
