package br.unb.cic.iris.command.console;

import java.security.InvalidParameterException;

import br.unb.cic.iris.command.AbstractMailCommand;
import br.unb.cic.iris.core.SystemFacade;
import br.unb.cic.iris.core.exception.EmailException;
import br.unb.cic.iris.core.exception.EmailUncheckedException;
import br.unb.cic.iris.util.EmailValidator;

public class AddressBookConsoleCommand extends AbstractMailCommand {

	@Override
	public void explain() {
		System.out.println("(ab list) - show the address book entries");
		System.out.println("(ab add <name> <email>) - add an address book entry (name=email)");
		System.out.println("(ab del <name> - delete an address book entry");
	}

	@Override
	public String getCommandName() {
		return "ab";
	}

	@Override
	protected void handleExecute() throws EmailException {
		switch(parameters[0]) {
			case "list" : break;
			case "add"  : add(parameters);break;
		
		}
	}
	
	private void add(String[] parameters) throws EmailException {
		if(parameters.length == 3) {
			String name = parameters[1];
			String email = parameters[2];
			if(!EmailValidator.validate(email)) {
				throw new EmailUncheckedException("invalid email");
			}
			SystemFacade.instance().addAddressBookEntry(name, email);
		}
		else {
			throw new InvalidParameterException();
		}
	}
	
	

}
