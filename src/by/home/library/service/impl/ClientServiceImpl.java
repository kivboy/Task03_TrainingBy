package by.home.library.service.impl;

import by.home.library.bean.User;
import by.home.library.dao.UserDAO;
import by.home.library.dao.exception.DAOException;
import by.home.library.dao.provider.DAOProvider;
import by.home.library.service.ClientService;
import by.home.library.service.exception.ServiceException;

public class ClientServiceImpl implements ClientService {

	@Override
	public User signIn(String login, String password) throws ServiceException {
		User activeUser = null;

		// проверяем параметры
		if (login == null || login.isEmpty()) {
			throw new ServiceException("Incorrect login!");
		}

		// реализуем функционал логинации пользователя в системе
		try {
			DAOProvider daoObjProvider = DAOProvider.getInstance();
			UserDAO userDAO = daoObjProvider.getUserDAO();

			activeUser = userDAO.signIn(login, password);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		return activeUser;
	}

	@Override
	public void signOut(String login) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void registration(User user) throws ServiceException {
		// реализуем функционал регистрации нового пользователя
		try {
			DAOProvider daoObjProvider = DAOProvider.getInstance();
			UserDAO userDAO = daoObjProvider.getUserDAO();

			userDAO.register(user);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int getNewUserId() throws ServiceException {
		// получение нового уникального id для пользователя
		int newUserId;
		
		try {
			DAOProvider daoObjProvider = DAOProvider.getInstance();
			UserDAO userDAO = daoObjProvider.getUserDAO();
			
			// можно переписать на работу с файлом
			// iterate new user id and check if it unique 
			// max 1000 users
			
			for (newUserId= 1; newUserId < 1000; newUserId++) {
				if (userDAO.isUniqueUserInfo(String.valueOf(newUserId), 0)) {
					break;
				}
			}			
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
		return newUserId;
	}

	@Override
	public boolean isUniqueUserInfo(String checkValue, int pos) throws ServiceException {
		// Проверка на уникальность требуемых данных
		boolean isUnique = false;
		
		try {
			DAOProvider daoObjProvider = DAOProvider.getInstance();
			UserDAO userDAO = daoObjProvider.getUserDAO();			
			isUnique = userDAO.isUniqueUserInfo(checkValue, pos);		
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
		return isUnique;
	}	
}
