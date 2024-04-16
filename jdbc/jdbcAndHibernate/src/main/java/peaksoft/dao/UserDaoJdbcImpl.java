package peaksoft.dao;

import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import peaksoft.model.User;
import peaksoft.util.Util;

import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbcImpl implements UserDao {

    public UserDaoJdbcImpl() {

    }

    public void createUsersTable() {
        SessionFactory sessionFactory = Util.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS users " +
                    "(id SERIAL PRIMARY KEY, name VARCHAR(50), " +
                    "lastName VARCHAR(50), age INT)").executeUpdate();
            transaction.commit();
            System.out.println("Таблица пользователей успешно создана.");
        } catch (Exception e) {
            System.out.println("Ошибка при создании таблицы пользователей: " + e.getMessage());
        }
    }

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

    public void saveUser(String name, String lastName, byte age) {
        SessionFactory sessionFactory = Util.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(getAllUsers());
            session.getTransaction().commit();
            session.close();
            sessionFactory.close();
            System.out.println("ПОЛЬЗОВАТЕЛЬ успешно сохранен!!!");
        } catch (SessionException e) {
            System.out.println(e.getMessage());
        }
    }

    public User removeUserById(long id) {
        User user = new User();
        try {
            SessionFactory sessionFactory = Util.getSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            user = session.get(User.class, id);
            session.getTransaction().commit();
            session.close();
        } catch (SessionException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (SessionFactory sessionFactory = Util.getSessionFactory()) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            users = session.createQuery("FROM User", User.class).getResultList();
            session.getTransaction().commit();
            session.close();
            System.out.println(" список всех пользователей");
        } catch (SessionException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    public void cleanUsersTable() {
        SessionFactory sessionFactory = Util.getSessionFactory();
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            int rowsAffected = session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Удалено записей: " + rowsAffected);
        } catch (Exception e) {
            System.out.println("Ошибка при очистке таблицы пользователей: " + e.getMessage());
        }
    }
}