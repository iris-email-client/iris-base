/*
 * Email
 * ---------------------------------
 *  version: 0.0.1
 *  date: Sep 6, 2014
 *  author: rbonifacio
 *  list of changes: (none) 
 */
package br.unb.cic.iris.core.model;

import java.util.Date;

/**
 * A class that represents an email message.
 * 
 * @author ExceptionHandling
 */
public class EmailMessage extends FolderContent {
	private String from;
	private String to;
	private String cc;
	private String bcc;
	private String subject;
	private String message;
	private Date date;

	public EmailMessage() {
	}

	public EmailMessage(String to, String subject, String message) {
		this(null, to, subject, message);
	}

	public EmailMessage(String from, String to, String subject, String message) {
		this(from, to, null, null, subject, message);
	}

	public EmailMessage(String from, String to, String cc, String bcc,
			String subject, String message) {
		this.from = from;
		this.to = to;
		this.cc = cc;
		this.bcc = bcc;
		this.subject = subject;
		this.message = message;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public String getCc() {
		return cc;
	}

	public String getBcc() {
		return bcc;
	}

	public String getSubject() {
		return subject;
	}

	public String getMessage() {
		return message;
	}

}
