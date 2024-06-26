package peaksoft;

import peaksoft.dao.UserDaoHibernateImpl;
import peaksoft.model.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();
        userDaoHibernate.createUsersTable();

        List<User> users = userDaoHibernate.getAllUsers();

        System.out.println("User Muhammet Karimov (age 4) был сохранен.");

        System.out.println("очистки таблицы пользователей");
        userDaoHibernate.cleanUsersTable();

        System.out.println("Пользователь успешно удален:");
        long userIdToRemove = 1;
        userDaoHibernate.removeUserById(userIdToRemove);

        System.out.println("удаления таблицы пользователей");
        userDaoHibernate.dropUsersTable();
    }

}




