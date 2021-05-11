package by.home.library.dao.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import by.home.library.bean.User;
import by.home.library.dao.UserDAO;
import by.home.library.dao.exception.DAOException;

public class FileUserDAO implements UserDAO {

	private static final String USER_FILE_SOURCE = "resources/Users.csv";
	private static final String DELIMETER = ";";

	@Override
	public User signIn(String login, String password) throws DAOException {
		// считываем файл с логинами и проверяем корректность логина пароля
		File userDataSource = new File(USER_FILE_SOURCE);
		BufferedReader reader = null;
		User activeUser = null;

		try {
			reader = new BufferedReader(new FileReader(userDataSource));
			String userData = null;

			while ((userData = reader.readLine()) != null) {
				if (findAuthMatches(userData, login, password)) {
					activeUser = parseUserData(userData);
					break;
				}
			}

		} catch (IOException e) {
			throw new DAOException(e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new DAOException(e);
				}
			}
		}

		return activeUser;
	}

	@Override
	public void register(User user) throws DAOException {
		// регистрация нового пользователя

		String userDataString = createUserDataString(user);

		BufferedWriter bufferedWriter = null;

		try {
			bufferedWriter = new BufferedWriter(new FileWriter(USER_FILE_SOURCE, true));
			bufferedWriter.write("\r\n");
			bufferedWriter.write(userDataString);

		} catch (IOException e) {
			throw new DAOException(e);
		} finally {
			if (bufferedWriter != null) {
				try {
					bufferedWriter.close();
				} catch (IOException e) {
					throw new DAOException(e);
				}
			}
		}
	}

	@Override
	public boolean isUniqueUserInfo(String checkValue, int pos) throws DAOException {
		// проверка id пользователя на уникальность

		File userDataSource = new File(USER_FILE_SOURCE);
		BufferedReader reader = null;		
		boolean isUnique = true;
		String[] parsedData;
		
		try {
			reader = new BufferedReader(new FileReader(userDataSource));
			String userData = null;

			while ((userData = reader.readLine()) != null) {
				
				parsedData = userData.split(DELIMETER);
				
				if (parsedData[pos].equals(checkValue)) {				
					isUnique = false;
					break;
				}
			}		
		} catch (IOException e) {
			throw new DAOException(e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new DAOException(e);
				}
			}
		}

		return isUnique;
	}

	private boolean findAuthMatches(String userData, String login, String password) {
		String[] parsedUserData = userData.split(DELIMETER);

		if (parsedUserData[1].equals(login) && parsedUserData[2].equals(password)) {
			return true;
		} else
			return false;
	}

	private User parseUserData(String userData) throws DAOException {
		User parsedUser = null;
		int userId = -1;
		int userStatus = 0;
		String userRole = null;

		String[] parsedString = userData.split(DELIMETER);

		if (parsedString.length != 6) {
			throw new DAOException("Incorrect User file structure!");
		}

		if (checkIntString(parsedString[0]) && checkStatus(parsedString[5]) && checkRole(parsedString[4])) {
			userId = Integer.parseInt(parsedString[0]);
			userStatus = Integer.parseInt(parsedString[5]);
			userRole = parsedString[4];
		} else {
			throw new DAOException("Incorrect User file data!");
		}

		parsedUser = new User(userId, parsedString[1], parsedString[2], parsedString[3], userRole, userStatus);

		return parsedUser;
	}

	private boolean checkIntString(String string) {
		try {
			Integer.parseInt(string);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private boolean checkRole(String role) {
		if (role.equals("admin") || role.equals("user") || role.equals("child")) {
			return true;
		} else
			return false;
	}

	private boolean checkStatus(String status) {
		if (status.equals("0") || status.equals("1")) {
			return true;
		} else
			return false;
	}

	private String createUserDataString(User user) {
				
		StringBuilder strBuilder = new StringBuilder();

		strBuilder.append(user.getId());
		strBuilder.append(DELIMETER);
		strBuilder.append(user.getLogin());
		strBuilder.append(DELIMETER);
		strBuilder.append(user.getPassword());
		strBuilder.append(DELIMETER);
		strBuilder.append(user.getName());
		strBuilder.append(DELIMETER);
		strBuilder.append(user.getRole());
		strBuilder.append(DELIMETER);
		strBuilder.append(user.getStatus());
		
		return strBuilder.toString();

	}
}
