package br.unb.cic.iris.mail;

import java.util.List;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;

import br.unb.cic.iris.core.exception.EmailException;
import br.unb.cic.iris.core.model.EmailMessage;
import br.unb.cic.iris.core.model.IrisFolder;

public interface IEmailClient {

	/**
	 * Send an email message
	 * 
	 * @param message
	 * @throws EmailException
	 */
	public void send(EmailMessage message) throws EmailException;

	public List<IrisFolder> listFolders() throws EmailException;

	public List<EmailMessage> getMessages(String folder, SearchTerm searchTerm)
			throws EmailException;

	// public TransportStrategy getTransportStrategy();
	public List<String> validateEmailMessage(EmailMessage message);

	/**
	 * Retrieves all messages from a given seqnum
	 * 
	 * @param seqnum
	 * @return
	 * @throws EmailException
	 */
	public List<EmailMessage> getMessages(String folder, int seqnum) throws EmailException;

	public List<EmailMessage> getMessages(String folder, int begin, int end) throws EmailException;
	
	
	
	
	
	
	// TODO terminar metodos abaixo
	default public void searchEmailBySubject(javax.mail.Folder folder,
			String subject) throws MessagingException {
		// creates a search criterion
		@SuppressWarnings("serial")
		SearchTerm searchCondition = new SearchTerm() {
			@Override
			public boolean match(Message message) {
				try {
					if (message.getSubject().contains(subject)) {
						return true;
					}
				} catch (MessagingException ex) {
					ex.printStackTrace();
				}
				return false;
			}
		};

		// performs search through the folder
		Message[] foundMessages = folder.search(searchCondition);
		// TODO ............
	}

	default public void showUnreadMails(javax.mail.Folder inbox) {
		try {
			FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
			Message msg[] = inbox.search(ft);
			System.out.println("MAILS: " + msg.length);
			for (Message message : msg) {
				try {
					System.out.println("DATE: "
							+ message.getSentDate().toString());
					System.out.println("FROM: "
							+ message.getFrom()[0].toString());
					System.out.println("SUBJECT: "
							+ message.getSubject().toString());
					System.out.println("CONTENT: "
							+ message.getContent().toString());
					System.out
							.println("******************************************");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("No Information");
				}
			}
		} catch (MessagingException e) {
			System.out.println(e.toString());
		}
	}

	default public void showAllMails(javax.mail.Folder inbox) {
		try {
			Message msg[] = inbox.getMessages();
			System.out.println("MAILS: " + msg.length);
			for (Message message : msg) {
				try {
					System.out.println("DATE: "
							+ message.getSentDate().toString());
					System.out.println("FROM: "
							+ message.getFrom()[0].toString());
					System.out.println("SUBJECT: "
							+ message.getSubject().toString());
					System.out.println("CONTENT: "
							+ message.getContent().toString());
					System.out
							.println("******************************************");
				} catch (Exception e) {
					System.out.println("No Information");
				}
			}
		} catch (MessagingException e) {
			System.out.println(e.toString());
		}
	}
}
