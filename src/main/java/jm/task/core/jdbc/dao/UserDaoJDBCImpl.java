package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS taskjdbc.users" +
                    "(id mediumint not null auto_increment," +
                    "name VARCHAR (70), " +
                    "lastname VARCHAR (70), " +
                    "age tinyint, " +
                    "PRIMARY KEY (id))");
            System.out.println("Таблица создана");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("Drop table if exists taskjdbc.users");
            System.out.println("Таблица удалена");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO taskjdbc.users(name, lastname, age) VALUES(?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Statement statement = connection.createStatement()) {
            String sql = "DELETE FROM taskjdbc.users where id";
            statement.executeUpdate(sql);
            System.out.println("User удален");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        String sql = "SELECT id, name, lastName, age from taskjdbc.users";

        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));
                allUsers.add(user);
                connection.commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return allUsers;
    }




    public void cleanUsersTable() {
        String sql = "DELETE FROM taskjdbc.users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица очищена");
//            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось очистить");
        }
    }
}
