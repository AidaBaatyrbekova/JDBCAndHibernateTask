package peaksoft.dao;

import org.hibernate.*;
import peaksoft.model.User;
import peaksoft.util.Util;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        SessionFactory sessionFactory = Util.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            transaction.commit();
            System.out.println("Таблица пользователей успешно создана.");
        } catch (Exception e) {
            System.out.println("Ошибка при создании таблицы пользователей: " + e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            SessionFactory sessionFactory = Util.getSessionFactory();
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                session.createQuery("DROP Users", User.class).executeUpdate();
                System.out.println("Таблица пользователей успешно удалена.");
                session.getTransaction().commit();
            }
        } catch (HibernateException e) {
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
            User user = session.get(User.class, id);
            if (user != null) {
                session.beginTransaction();
                session.delete(user);
                session.getTransaction().commit();
                System.out.println("Пользователь успешно удален: " + user.getName() + " " + user.getLastName());
                return user;
            } else {
                System.out.println("Пользователь с идентификатором " + id + " не найден.");
            }
        } catch (HibernateException e) {
            System.out.println("Ошибка при удалении пользователя: " + e.getMessage());
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
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
            System.out.println("Таблица пользователей успешно очищена.");
        } catch (Exception e) {
            System.out.println("Ошибка при очистке таблицы пользователей: " + e.getMessage());
        }
    }
}
