package peaksoft.dao;

import org.hibernate.*;
import peaksoft.model.User;
import peaksoft.util.Util;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }@Override
    public void createUsersTable() {
        SessionFactory sessionFactory = Util.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS users " +
                    "(id SERIAL PRIMARY KEY, name VARCHAR(50), lastName VARCHAR(50), age INT)").executeUpdate();
            transaction.commit();
            System.out.println("Таблица пользователей успешно создана.");
        } catch (Exception e) {
            System.out.println("Ошибка при создании таблицы пользователей: " + e.getMessage());
        }
    }

@Override
public void dropUsersTable() {
    SessionFactory sessionFactory = Util.getSessionFactory();
    try (Session session = sessionFactory.openSession()) {
        Transaction transaction = session.beginTransaction();
        session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
        System.out.println("Таблица пользователей успешно удалена.");
        transaction.commit();
    } catch (Exception e) {
        System.out.println("Ошибка при удалении таблицы пользователей: " + e.getMessage());
    }
}
@Override
public void saveUser(String name, String lastName, byte age) {
    try (SessionFactory sessionFactory = Util.getSessionFactory()) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);
        session.persist(user);
        session.getTransaction().commit();
        System.out.println("ПОЛЬЗОВАТЕЛЬ успешно сохранен!!!");
    } catch (HibernateException e) {
        System.out.println("Ошибка при сохранении пользователя: " + e.getMessage());
    }
}
@Override
public User removeUserById(long id) {
    try {
        SessionFactory sessionFactory = Util.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = session.get(User.class, id);
        if (user != null) {
            session.delete(user);
            System.out.println("Пользователь успешно удален: " + user.getName() + " " + user.getLastName());
        } else {
            System.out.println("Пользователь с идентификатором " + id + " не найден.");
        }
        session.getTransaction().commit();
        session.close();
    } catch (SessionException e) {
        System.out.println(e.getMessage());
    }
    return null;
}
@Override
public List<User> getAllUsers() {
    List<User> users = new ArrayList<>();
    try {
        SessionFactory sessionFactory = Util.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        users = session.createQuery("FROM User", User.class).getResultList();
        session.getTransaction().commit();
        session.close();
        System.out.println(" список всех пользователей");
    } catch (HibernateException e) {
        System.out.println("Error getting all users: " + e.getMessage());
    }
    return users;
}

@Override
public void cleanUsersTable() {
    try (Session session = Util.getSessionFactory().openSession()) {
        Transaction transaction = session.beginTransaction();
        int rowsAffected = session.createQuery("DELETE FROM User").executeUpdate();
        System.out.println("Удалено записей: " + rowsAffected);
        transaction.commit();
    } catch (HibernateException e) {
        System.out.println(e.getMessage());
    }
}
}
