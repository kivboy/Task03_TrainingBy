package by.home.library.controller.command.impl;

import by.home.library.bean.User;
import by.home.library.controller.command.Command;
import by.home.library.controller.command.CommandName;
import by.home.library.controller.command.MetaCommand;
import by.home.library.service.ClientService;
import by.home.library.service.exception.ServiceException;
import by.home.library.service.provider.ServiceProvider;

public class Register extends MetaCommand implements Command {

	@Override
	public String execute(String request) {
		String userLogin = null;
		String userPassword = null;
		String userName = null;
		String userRole = null;
		int userId = -1;
		
		User regUser = null;
		String response = null;
		
		// register login=test;password=test;name=QQQ;role=admin
		
		// удаляем комманду из запроса, оставляем только параметры
		request = request.substring(request.indexOf(REQUEST_COMMAND_DELIMETER) + 1);
		
		// get parameters from request and initialize variables
			
		if (super.countWords(request, REQUEST_PARAM_DELIMETER) != 4) {
			response = CODE_ERROR_SYNTAX + RESPONSE_CODE_DELIMETER + "Error in " + CommandName.REGISTER + " command syntax!";
			
			return response;
		} else {
			userLogin = super.getValue("login", request, REQUEST_PARAM_DELIMETER);
			userPassword = super.getValue("password", request, REQUEST_PARAM_DELIMETER);
			userName = super.getValue("name", request, REQUEST_PARAM_DELIMETER);					
			userRole = super.getValue("role", request, REQUEST_PARAM_DELIMETER);
		}
		
		// TODO check validness
		//if ((login == null) or login.isEmpty())
						
		ServiceProvider serviceProvider = ServiceProvider.getInstance();
		ClientService clientService = serviceProvider.getClientService();
		
		try {
			
			if (!clientService.isUniqueUserInfo(userLogin, 1)) {
				response = CODE_ERROR_NODATA + RESPONSE_CODE_DELIMETER + "Login " + userLogin + " arleady exist!";
				return response;
			}
			
			userId = clientService.getNewUserId();
			regUser = new User(userId, userLogin, userPassword, userName, userRole, 1);
			
			clientService.registration(regUser);
			response = CODE_SUCCESS + RESPONSE_CODE_DELIMETER + regUser.getId() + RESPONSE_INFO_DELIMETER + 
					regUser.getName() + RESPONSE_INFO_DELIMETER + regUser.getRole();
						 
		} catch (ServiceException e) {
			// write log
			//e.printStackTrace();  // нужно ли выводить?
			response = CODE_ERROR_INTERNAL + RESPONSE_CODE_DELIMETER + "Error occured during register procedure!";
		}
		
		return response;
	}

}
