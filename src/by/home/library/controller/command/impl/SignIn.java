package by.home.library.controller.command.impl;

import by.home.library.bean.User;
import by.home.library.controller.command.Command;
import by.home.library.controller.command.CommandName;
import by.home.library.controller.command.MetaCommand;
import by.home.library.service.ClientService;
import by.home.library.service.exception.ServiceException;
import by.home.library.service.provider.ServiceProvider;

public class SignIn extends MetaCommand implements Command{
		
	@Override
	public String execute(String request) {
		String login = null;
		String password = null;
				
		User activeUser = null;
		String response = null;
		
		// sign_in login=admin;password=mypassword1
		
		// удаляем комманду из запроса, оставляем только параметры
		request = request.substring(request.indexOf(REQUEST_COMMAND_DELIMETER) + 1);
		
		// get parameters from request and initialize variables
					
		if (super.countWords(request, REQUEST_PARAM_DELIMETER) != 2) {
			response = CODE_ERROR_SYNTAX + RESPONSE_CODE_DELIMETER + "Error in " + CommandName.SIGN_IN + " command syntax!";
			
			return response;
		} else {
			login = super.getValue("login", request, REQUEST_PARAM_DELIMETER);
			password = super.getValue("password", request, REQUEST_PARAM_DELIMETER);
		}
				
		ServiceProvider serviceProvider = ServiceProvider.getInstance();
		ClientService clientService = serviceProvider.getClientService();
		
		try {
			activeUser = clientService.signIn(login, password);
			
			if ((activeUser != null) && (activeUser.getStatus() == 1)) {
				response = CODE_SUCCESS + RESPONSE_CODE_DELIMETER + activeUser.getId() + RESPONSE_INFO_DELIMETER + 
						activeUser.getName() + RESPONSE_INFO_DELIMETER + activeUser.getRole();
			} else {
				// пользователь не найден, или пользователь деактивирован
				response = CODE_ERROR_NODATA + RESPONSE_CODE_DELIMETER + "Incorrect login or password!";
			}
						 
		} catch (ServiceException e) {
			// write log
			//e.printStackTrace();  // нужно ли выводить?
			response = CODE_ERROR_INTERNAL + RESPONSE_CODE_DELIMETER + "Error occured during login procedure!";
		}
		
		return response;
	}

}
