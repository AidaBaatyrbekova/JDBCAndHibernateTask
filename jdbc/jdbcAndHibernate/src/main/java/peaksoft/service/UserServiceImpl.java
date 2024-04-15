package peaksoft.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import peaksoft.dao.UserDaoJdbcImpl;
import peaksoft.model.User;

import java.util.List;

@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final UserDaoJdbcImpl HIBERNATE_DAO=new UserDaoJdbcImpl();


    public void createUsersTable() {

    }

   public void dropUsersTable() {

   }

    public void saveUser(String name, String lastName, byte age) {
        HIBERNATE_DAO.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {

    }

    public List<User> getAllUsers() {
        return null;
    }

    public void cleanUsersTable() {
    }
}
