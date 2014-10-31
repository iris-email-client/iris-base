package br.unb.cic.iris.command.console;

import static br.unb.cic.iris.i18n.Message.message;

import java.util.List;

import br.unb.cic.iris.command.AbstractMailCommand;
import br.unb.cic.iris.core.SystemFacade;
import br.unb.cic.iris.core.exception.EmailException;
import br.unb.cic.iris.core.model.IrisFolder;

public class ConsoleListFoldersCommand extends AbstractMailCommand {
	static final String COMMAND_NAME = "ls";

	@Override
	public void explain() {
		System.out.printf("(%s) - %s %n", COMMAND_NAME,
				message("command.list.folders.explain"));
	}

	@Override
	public void execute() {
		try {
			List<IrisFolder> irisFolders = SystemFacade.instance()
					.listFolders();
			irisFolders.forEach(folder -> System.out.printf(" + %s%n",
					folder.getName()));
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

}
