package br.unb.cic.iris.core;

/**
 * A manager class for address book. 
 * 
 * @author rbonifacio
 */
public class AddressBook {

	/**
	 * Register a new tuple nick name -> email address 
	 * @param nick 
	 * @param address
	 */
	public void register(String nick, String address) {
		throw new RuntimeException("not implemented yet");
	}
	
	/**
	 * @param nick
	 * @return the email address related to the nick name
	 */
	public String getAddress(String nick) {
		throw new RuntimeException("not implemented yet");
	}
	
	/**
	 * Unregister the address book entry related to 
	 * the <i>nick</i> name. 
	 * 
	 * @param nick
	 */
	public void unregister(String nick) {
		throw new RuntimeException("not implemented yet");
	}
	
}
