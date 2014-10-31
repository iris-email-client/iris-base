/*
 * ConsoleSendMessageCommand.java
 * ---------------------------------
 *  version: 0.0.1
 *  date: Sep 8, 2014
 *  author: rbonifacio
 *  list of changes: (none) 
 */
package br.unb.cic.iris.command.console;

import static br.unb.cic.iris.i18n.Message.message;

import java.util.Scanner;

import br.unb.cic.iris.command.SendMessageCommand;
import br.unb.cic.iris.core.SystemFacade;
import br.unb.cic.iris.core.model.EmailMessage;

/**
 * 
 * @author rbonifacio
 */
public class ConsoleSendMessageCommand extends SendMessageCommand {
	public static final String COMMAND_SEND = "send";

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.unb.cic.iris.command.MailCommand#explain()
	 */
	@Override
	public void explain() {
		System.out.printf("(%s) - %s %n", COMMAND_SEND, message(EXPLAIN_I18n));
	}

	@Override
	protected EmailMessage createMessage() {
		Scanner sc = new Scanner(System.in);

		// String from = read(sc, "command.send.label.from");
		String from = SystemFacade.instance().getProvider().getUsername();
		System.out.printf("%s: %s%n", message("command.send.label.from"), from);

		String to = read(sc, "command.send.label.to");
		String subject = read(sc, "command.send.label.subject");
		String content = read(sc, "command.send.label.content");

		return new EmailMessage(from, to, subject, content);
	}

	private static String read(Scanner sc, String question) {
		askQuestion(question);
		return sc.nextLine();
	}

	private static void askQuestion(String message) {
		System.out.printf("%s: ", message(message));
	}

	@Override
	public String getCommandName() {
		return COMMAND_SEND;
	}

}
