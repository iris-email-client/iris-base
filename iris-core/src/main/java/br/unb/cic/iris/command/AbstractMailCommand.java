package br.unb.cic.iris.command;

public abstract class AbstractMailCommand implements MailCommand {
	protected String[] parameters;

	@Override
	public void setParameters(String... parameters) {
		this.parameters = parameters;
	}

	protected boolean validParameters() {
		return parameters != null && parameters.length > 0;
	}

}
