package by.home.library.service;

import by.home.library.bean.User;
import by.home.library.service.exception.ServiceException;

public interface ClientService {
	User signIn(String login, String password) throws ServiceException;
	void signOut(String login) throws ServiceException;
	void registration(User user) throws ServiceException;
	int getNewUserId() throws ServiceException;
	boolean isUniqueUserInfo(String checkValue, int pos) throws ServiceException;
}
