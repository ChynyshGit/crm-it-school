package kg.itschool.crm.Dao.DaoImpl;

import kg.itschool.crm.Dao.InterfaceDao.StudentDao;
import kg.itschool.crm.Dao.Model.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentDaoImpl implements StudentDao {
    public StudentDaoImpl() {

        Connection connection = null;
        Statement statement = null;

        try {
            System.out.println("Connecting to database...");
            connection = getConnection();
            System.out.println("Connection succeeded.");

            String ddlQuery = "CREATE TABLE IF NOT EXISTS tb_student(" +
                    "id           BIGSERIAL       PRIMARY KEY, " +
                    "first_name   VARCHAR(50)  NOT NULL, " +
                    "last_name    VARCHAR(50)  NOT NULL, " +
                    "email        VARCHAR(100) NOT NULL UNIQUE, " +
                    "phone_number CHAR(13)     NOT NULL, " +
                    "dob          DATE         NOT NULL CHECK(dob < NOW()), " +
                    "status       BOOLEAN DEFAULT TRUE NOT NULL, " +
                    "date_created TIMESTAMP    NOT NULL DEFAULT NOW() " +
                    ")";

            statement = connection.createStatement();
            statement.execute(ddlQuery);

            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(statement);
            close(connection);
        }
    }

    public void save() {

        Connection connection = null;
        Statement statement = null;

        try {
            System.out.println("Connecting to database...");
            connection = getConnection();
            System.out.println("Connection succeeded.");

            String dmlQuery = "INSERT INTO tb_student(first_name, last_name, phone_number, email, dob, status) " +
                    "VALUES(?, ?, ?, ?, ?, 'true')";

            statement = connection.createStatement();
            statement.execute(dmlQuery);

            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(statement);
            close(connection);
        }
    }
    @Override
    public void findById(long id) {

    }

    @Override
    public Student save(Student student) {
        return null;
    }

    @Override
    public Student findVyId(Long id) {
        return null;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return StudentDao.super.getConnection();
    }
}









