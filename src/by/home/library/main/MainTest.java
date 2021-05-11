package by.home.library.main;

import by.home.library.controller.Controller;

public class MainTest {

	public static void main(String[] args) {
		String activeUserId = null;
		String activeUserRole = null;
		String activeUserName = null;
		String response = null;
		String[] parsedString;
		final String DATA_DELIMETER = ";";

		Controller controller = new Controller();

		response = controller.executeTask("sign_in login=admin;password=mypassword1");

		if (response.startsWith("200") == true) {
			// успешный логин вернуло id пользователя
			response = response.substring(response.indexOf(' ') + 1);

			parsedString = response.split(DATA_DELIMETER);
			activeUserId = parsedString[0];
			activeUserName = parsedString[1];
			activeUserRole = parsedString[2];

			System.out.println("Hello " + activeUserName);

		} else if (response.startsWith("400") == true) {
			// ошибка входа
			System.out.println("No such user or password!");
		} else {
			// возвращаем 300 (ошибка синтакса) или 500 (ошибка выполнения) и описание
			// ошибки
			System.out.println(response.substring(response.indexOf(' ') + 1));
		}

		// регистрация пользователя
		if ((activeUserId != null) && (activeUserRole.equals("admin"))) {
			response = controller.executeTask("register login=test;password=test;name=QQQ;role=admin");

			if (response.startsWith("200") == true) {
				// пользователь зарегистрирован
				System.out.println("User registred successfully!");
			} else {
				// возвращаем 300 (ошибка синтакса) или 500 (ошибка выполнения) и описание
				// ошибки
				System.out.println(response.substring(response.indexOf(' ') + 1));
			}

		}

		// добавление новой книги

		int newBookId = -1;

		if (activeUserId != null) {
			response = controller
					.executeTask("add_book title=my test;author=new author;year=2021;genre=стихи;notchild=false");

			if (response.startsWith("200") == true) {
				// книга добавлена успешно
				System.out.println("Book added successfully!");

				newBookId = Integer.parseInt(response.substring(response.indexOf(' ') + 1));

			} else {
				// возвращаем 300 (ошибка синтакса) или 500 (ошибка выполнения) и описание
				// ошибки
				System.out.println(response.substring(response.indexOf(' ') + 1));
			}

		}

		if (newBookId != -1) {
			response = controller.executeTask("edit_book id=" + newBookId + ";title=new test;author=author111");

			if (response.startsWith("200") == true) {
				// книга изменена успешно
				System.out.println("Book edit successfully!");
			} else {
				// возвращаем 300 (ошибка синтакса) или 400 (плохие данные) или 500 (ошибка
				// выполнения) и описание
				// ошибки
				System.out.println(response.substring(response.indexOf(' ') + 1));
			}
		}

		if (activeUserRole != null) {
			response = controller.executeTask("search_book title=my;role=" + activeUserRole);

			if (response.startsWith("200") == true) {
				// книга добавлена успешно
				System.out.println("Search book completed successfully!");

				System.out.println(response.substring(response.indexOf(' ') + 1));

			} else {
				// возвращаем 300 (ошибка синтакса) или 500 (ошибка выполнения) и описание
				// ошибки
				System.out.println(response.substring(response.indexOf(' ') + 1));
			}

		}

		System.out.println("End!");
	}

}
