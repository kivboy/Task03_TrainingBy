package by.home.library.controller.command.impl;

import by.home.library.controller.command.Command;
import by.home.library.controller.command.MetaCommand;

public class WrongRequest extends MetaCommand implements Command {

	@Override
	public String execute(String request) {		
		return CODE_ERROR_SYNTAX + RESPONSE_CODE_DELIMETER + "Wrong request!";
	}

}
