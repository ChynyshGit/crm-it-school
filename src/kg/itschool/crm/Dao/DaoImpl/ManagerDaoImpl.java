package kg.itschool.crm.Dao.DaoImpl;

import kg.itschool.crm.Dao.InterfaceDao.ManagerDao;
import java.sql.*;
import kg.itschool.crm.Dao.Model.Manager;

public class ManagerDaoImpl implements ManagerDao {

    public ManagerDaoImpl() {

        Connection connection = null;
        Statement statement = null;

        try {
            // api:driver://host:port/database_name
            System.out.println("Connecting to database...");
            connection = getConnection();
            System.out.println("Connection succeeded.");

            String ddlQuery = "CREATE TABLE IF NOT EXISTS tb_managers(" +
                    "id           BIGSERIAL, " +
                    "first_name   VARCHAR(50)  NOT NULL, " +
                    "last_name    VARCHAR(50)  NOT NULL, " +
                    "email        VARCHAR(100) NOT NULL UNIQUE, " +
                    "phone_number CHAR(13)     NOT NULL, " +
                    "salary       MONEY        NOT NULL, " +
                    "dob          DATE         NOT NULL CHECK(dob < NOW()), " +
                    "date_created TIMESTAMP    NOT NULL DEFAULT NOW(), " +
                    "" +
                    "CONSTRAINT pk_manager_id PRIMARY KEY(id), " +
                    "CONSTRAINT chk_manager_salary CHECK (salary > MONEY(0))," +
                    "CONSTRAINT chk_manager_first_name CHECK(LENGTH(first_name) > 2));";

            System.out.println("Creating statement...");
            statement = connection.createStatement();
            System.out.println("Executing create table statement...");
            statement.execute(ddlQuery);

        } catch (SQLException e) {
            System.out.println("Some error occurred");
            e.printStackTrace();
        } finally {
            close(statement);
            close(connection);
        }

    }

    @Override
    public Manager save(Manager manager) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Manager savedManager = null;

        try {
            System.out.println("Connecting to database...");
            connection = getConnection();
            System.out.println("Connection succeeded.");

            String createQuery = "INSERT INTO tb_managers(first_name, last_name, email, phone_number, salary, dob, date_created) " +
                    "VALUES(?, ?, ?, ?, MONEY(?), ?, ?)";

            String readQuery = "SELECT * FROM tb_managers ORDER BY id DESC LIMIT 1";

            preparedStatement = connection.prepareStatement(createQuery);
            preparedStatement.setString(1, manager.getFirstName());
            preparedStatement.setString(2, manager.getLastName());
            preparedStatement.setString(3, manager.getEmail());
            preparedStatement.setString(4, manager.getPhoneNumber());
            preparedStatement.setString(4, manager.getSalary() + "");
            preparedStatement.setDate(5, Date.valueOf(manager.getDob()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(manager.getDateCreated()));

            preparedStatement.execute();
            close(preparedStatement);

            preparedStatement = connection.prepareStatement(readQuery);
            resultSet = preparedStatement.executeQuery();

            resultSet.next();

            savedManager = new Manager();
            savedManager.setId(resultSet.getLong("id"));
            savedManager.setFirstName(resultSet.getString("first_name"));
            savedManager.setLastName(resultSet.getString("last_name"));
            savedManager.setEmail(resultSet.getString("email"));
            savedManager.setPhoneNumber(resultSet.getString("phone_number"));
            savedManager.setSalary(Double.valueOf(resultSet.getString("salary").replaceAll("[^\\d\\.]+", "")));
            savedManager.setDob(resultSet.getDate("dob").toLocalDate());
            savedManager.setDateCreated(resultSet.getTimestamp("date_created").toLocalDateTime());

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return savedManager;
    }

    @Override
    public Manager findVyId(Long id) {
        return null;
    }

    @Override
    public Manager findById(Long id) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Manager manager = null;

        try {
            connection = getConnection();

            String readQuery = "SELECT * FROM tb_managers WHERE id = ?";

            preparedStatement = connection.prepareStatement(readQuery);
            preparedStatement.setLong(1, id);

            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            manager = new Manager();
            manager.setId(resultSet.getLong("id"));
            manager.setFirstName(resultSet.getString("first_name"));
            manager.setLastName(resultSet.getString("last_name"));
            manager.setEmail(resultSet.getString("email"));
            manager.setPhoneNumber(resultSet.getString("phone_number"));
            manager.setSalary(Double.valueOf(resultSet.getString("salary").replaceAll("[^\\d\\.]+", "")));
            manager.setDob(resultSet.getDate("dob").toLocalDate());
            manager.setDateCreated(resultSet.getTimestamp("date_created").toLocalDateTime());


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return manager;
    }
}
