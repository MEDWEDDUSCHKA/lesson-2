package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();
        System.out.println("Таблица создана");

        userService.saveUser("Ivan", "Ivanov", (byte) 25);
        System.out.println("User Ivan Ivanov создан");

        userService.saveUser("Artem", "Kirilov", (byte) 35);
        System.out.println("User Artem Kirilov создан");

        userService.saveUser("Max", "Petrov", (byte) 55);
        System.out.println("User Max Petrov создан");

        userService.saveUser("Nikita", "Vasilev", (byte) 45);
        System.out.println("User Nikita Vasilev создан");

        List<User> users = userService.getAllUsers();
        for(User user : users){

            System.out.println(user);
        }


        userService.cleanUsersTable();
        System.out.println("Таблица очищена");

        userService.dropUsersTable();
        System.out.println("Таблица удалена");
    }

}
