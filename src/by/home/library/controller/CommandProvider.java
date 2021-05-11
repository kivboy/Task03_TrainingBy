package by.home.library.controller;

import by.home.library.controller.command.Command;
import by.home.library.controller.command.CommandName;
import by.home.library.controller.command.impl.AddBook;
import by.home.library.controller.command.impl.DeleteBook;
import by.home.library.controller.command.impl.EditBook;
import by.home.library.controller.command.impl.Register;
import by.home.library.controller.command.impl.SearchBook;
import by.home.library.controller.command.impl.SignIn;
import by.home.library.controller.command.impl.WrongRequest;

import java.util.HashMap;
import java.util.Map;

final class CommandProvider {
	private final Map<CommandName, Command> repository = new HashMap<>();
	
	CommandProvider() {
		repository.put(CommandName.SIGN_IN, new SignIn());
		repository.put(CommandName.REGISTER, new Register());
		repository.put(CommandName.ADD_BOOK, new AddBook());
		repository.put(CommandName.EDIT_BOOK, new EditBook());
		repository.put(CommandName.DELETE_BOOK, new DeleteBook());
		repository.put(CommandName.SEARCH_BOOK, new SearchBook());
		repository.put(CommandName.WRONG_REQUEST, new WrongRequest());
	}
	
	Command getCommand(String name) {
		CommandName commandName = null;
		Command command = null;
		
		try {
			commandName = CommandName.valueOf(name.toUpperCase());
			command = repository.get(commandName);
		} catch (IllegalArgumentException | NullPointerException e) {
			// write log
			command = repository.get(CommandName.WRONG_REQUEST);
		}
		
		return command;
	}
}
