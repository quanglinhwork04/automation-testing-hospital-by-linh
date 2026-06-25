package com.booking_care.model;

import lombok.Data;

import javax.mail.internet.InternetAddress;
import java.util.Map;

@Data
public class Email {
    String to;
    InternetAddress from;
    String subject;
    String text;
    String template;
    Map<String, Object> properties;
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public InternetAddress getFrom() {
		return from;
	}
	public void setFrom(InternetAddress from) {
		this.from = from;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public Map<String, Object> getProperties() {
		return properties;
	}
	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}
    
}