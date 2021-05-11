package by.home.library.dao;

import by.home.library.bean.User;
import by.home.library.dao.exception.DAOException;

public interface UserDAO {
	User signIn(String login, String password) throws DAOException;
	void register(User user) throws DAOException;
	boolean isUniqueUserInfo(String checkValue, int pos) throws DAOException;
}
