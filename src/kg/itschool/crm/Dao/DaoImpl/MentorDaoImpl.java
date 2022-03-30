package kg.itschool.crm.Dao.DaoImpl;

import kg.itschool.crm.Dao.InterfaceDao.MentorDao;
import kg.itschool.crm.Dao.Model.Mentor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MentorDaoImpl implements MentorDao {

    public MentorDaoImpl() {

        Connection connection = null;
        Statement statement = null;

        try {
            System.out.println("Connecting to database...");
            connection = getConnection();
            System.out.println("Connection succeeded.");

            String ddlQuery = "CREATE TABLE IF NOT EXISTS tb_mentor(" +
                    "id           BIGSERIAL    PRIMARY KEY, " +
                    "first_name   VARCHAR(50)  NOT NULL, " +
                    "last_name    VARCHAR(50)  NOT NULL, " +
                    "email        VARCHAR(100) NOT NULL UNIQUE, " +
                    "phone_number CHAR(13)     NOT NULL, " +
                    "salary       MONEY        NOT NULL CHECK(salary > MONEY(0)), " +
                    "dob          DATE         NOT NULL CHECK(dob < NOW()), " +
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

            String dmlQuery = "INSERT INTO tb_mentor(first_name, last_name, phone_number, email, dob, salary) " +
                    "VALUES(?, ?, ?, ?, ?, MONEY(?))";


            statement = connection.createStatement();
            statement.execute(dmlQuery);

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
    public Mentor save(Mentor mentor) {
        return null;
    }

    @Override
    public Mentor findVyId(Long id) {
        return null;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return MentorDao.super.getConnection();
    }
}