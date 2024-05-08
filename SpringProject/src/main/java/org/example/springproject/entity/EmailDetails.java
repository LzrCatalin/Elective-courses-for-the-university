package org.example.springproject.entity;

public class EmailDetails {
	private String recipient;
	private String msgBody;
	private String subject;
	private String attachment;

	public EmailDetails() {}

	public EmailDetails(String recipient, String msgBody, String subject, String attachment) {
		this.recipient = recipient;
		this.msgBody = msgBody;
		this.subject = subject;
		this.attachment = attachment;
	}

	public EmailDetails(String recipient, String msgBody, String subject) {
		this.recipient = recipient;
		this.msgBody = msgBody;
		this.subject = subject;
	}

	public EmailDetails(String recipient) {
		this.recipient = recipient;
	}

	public String getRecipient() {
		return recipient;
	}

	public String getMsgBody() {
		return msgBody;
	}

	public String getSubject() {
		return subject;
	}

	public String getAttachment() {
		return attachment;
	}
}
