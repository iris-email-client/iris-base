package br.unb.cic.iris.mail;

import static br.unb.cic.iris.i18n.Message.message;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Store;
import javax.mail.event.FolderEvent;
import javax.mail.event.FolderListener;
import javax.mail.event.StoreEvent;
import javax.mail.event.StoreListener;
import javax.mail.search.SearchTerm;

import br.unb.cic.iris.core.exception.EmailException;
import br.unb.cic.iris.core.model.EmailMessage;
import br.unb.cic.iris.core.model.IrisFolder;

public class EmailReceiver implements StoreListener, FolderListener {
	private EmailSession session;
	private EmailProvider provider;

	public EmailReceiver(EmailProvider provider, String encoding) {
		this.provider = provider;
		session = new EmailSession(provider, encoding);
	}

	private Store store;

	public Store getStore() throws MessagingException {
		if (store == null) {
			store = createStoreAndConnect();
		}
		return store;
	}

	public Store renew() throws MessagingException {
		if (store != null) {
			store.close();
			store = null;
		}
		return getStore();
	}

	public List<IrisFolder> listFolders() throws EmailException {
		List<IrisFolder> folders = new ArrayList<>();
		try {
			Store store = getStore();
			Folder defaultFolder = store.getDefaultFolder();
			Folder[] externalFolders = defaultFolder.list();
			for (Folder f : externalFolders) {
				folders.add(new IrisFolder(f.getName()));
			}
		} catch (MessagingException e) {
			throw new EmailException(message("error.list.folder"), e);
		}
		return folders;
	}

	public List<EmailMessage> getMessages(String folderName,
			SearchTerm searchTerm) throws EmailException {
		List<EmailMessage> messages = new ArrayList<>();

		try {
			Store store = getStore();

			Folder folder = store.getFolder(folderName);
			// open folder only to read
			folder.open(Folder.READ_ONLY);

			Message messagesRetrieved[] = null;
			if (searchTerm == null) {
				// list all
				messagesRetrieved = folder.getMessages();
			} else {
				// search mail
				messagesRetrieved = folder.search(searchTerm);
			}

			int cont = 0;
			int total = messagesRetrieved.length;
			for (Message m : messagesRetrieved) {
				messages.add(convertToIrisMessage(m));

				// TODO arrumar progresso
				if (total != 0) {
					for (int i = 0; i < 15; i++) {
						System.out.print('\b');
					}
					cont++;
					System.out.print((100 * cont / total) + "% completed");
				}

			}
			System.out.println();

			// TODO deixa o store aberto mesmo?
			// store.close();
		} catch (NoSuchProviderException e) {
			// TODO fazer tratamento de excessoes (em tudo)
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return messages;
	}

	private EmailMessage convertToIrisMessage(Message m) throws IOException,
			MessagingException {
		// System.out.println("Converting to iris: "+m.getSubject());
		EmailMessage msg = new EmailMessage();
		msg.setBcc(convertAddressToString(m.getRecipients(RecipientType.BCC)));
		msg.setCc(convertAddressToString(m.getRecipients(RecipientType.CC)));
		msg.setTo(convertAddressToString(m.getRecipients(RecipientType.TO)));
		msg.setFrom(convertAddressToString(m.getFrom()));
		msg.setMessage(m.getContent().toString());
		msg.setSubject(m.getSubject());
		msg.setDate(m.getReceivedDate());
		return msg;
	}

	private String convertAddressToString(Address[] recipients) {
		StringBuilder sb = new StringBuilder("");
		if (recipients != null) {
			for (Address a : recipients) {
				sb.append(a.toString() + ", ");
			}
		}
		return sb.toString();
	}

	private Store createStoreAndConnect() throws MessagingException {
		System.out.println("Creating store ...");
		Store store = session.getSession()
				.getStore(provider.getStoreProtocol());
		store.addStoreListener(this);
		store.addConnectionListener(session);

		session.connect(store, provider.getStoreHost(), provider.getStorePort());

		return store;
	}

	@Override
	public void notification(StoreEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Notification: " + e.getMessage());
	}

	@Override
	public void folderCreated(FolderEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void folderDeleted(FolderEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void folderRenamed(FolderEvent e) {
		// TODO Auto-generated method stub

	}
}
