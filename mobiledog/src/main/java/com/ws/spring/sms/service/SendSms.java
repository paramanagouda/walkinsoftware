package com.ws.spring.sms.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class SendSms {

	Logger logger = LogManager.getLogger(this.getClass().getName());

	public void sendSmstoUser(String smsMainUrl, String mobileNumber) {
		// Prepare Url
		URLConnection myURLConnection = null;
		URL myURL = null;
		BufferedReader reader = null;
		try {
			// prepare connection
			myURL = new URL(smsMainUrl);
			myURLConnection = myURL.openConnection();
			myURLConnection.connect();
			reader = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
			// reading response
			String response;
			while ((response = reader.readLine()) != null)
				// print response
				logger.info("Send sms to user mobile : {} and response is : {}", mobileNumber, response);

			// finally close connection
			reader.close();
		} catch (IOException e) {
			logger.error("Error occured while Sending sms to user mobile : {} and error is : {}", mobileNumber,
					e.getMessage(), e);
		}
	}
}
