package kg.itschool.crm.Dao.DaoImpl;

import kg.itschool.crm.Dao.DaoUtil.Log;
import kg.itschool.crm.Dao.InterfaceDao.CourseDao;
import java.sql.*;
import kg.itschool.crm.Dao.Model.Course;

public class CourseDaoImpl implements CourseDao {

    public CourseDaoImpl() {

        Connection connection = null;
        Statement statement = null;

        try {
            Log.info(this.getClass().getSimpleName(), Connection.class.getSimpleName(), "Establishing connection");
            connection = getConnection();
            Log.info(this.getClass().getSimpleName(), connection.getClass().getSimpleName(), "Connection established");

            String ddlQuery = "CREATE TABLE IF NOT EXISTS tb_course(" +
                    "id           BIGSERIAL, " +
                    "name         VARCHAR(50)  NOT NULL, " +
                    "price        MONEY        NOT NULL CHECK(price > MONEY(0)), " +
                    "course_format  VARCHAR      NOT NULL,  " +
                    "date_created TIMESTAMP    NOT NULL DEFAULT NOW() " +
                    "course_format_id BIGINT   NOT NULL, " +
                    "" +
                    "CONSTRAINT pk_course_id   PRIMARY KEY(id), " +
                    "CONSTRAINT fk_course_format_id FOREIGN KEY (course_format_id) " +
                    "   REFERENCES tb_course_format(id)" +
                    ");";

            System.out.println("Creating statement...");
            statement = connection.createStatement();
            System.out.println("Executing create table statement...");
            statement.execute(ddlQuery);


        } catch (SQLException e) {
            Log.error(this.getClass().getSimpleName(), e.getStackTrace()[0].getClass().getSimpleName(), e.getMessage());
            e.printStackTrace();
        } finally {
            close(statement);
            close(connection);
        }
    }

    public Course save(Course course) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Course savedCourse = null;

        try {
            System.out.println("Connecting to database...");
            connection = getConnection();
            System.out.println("Connection succeeded.");

            String createQuery = "INSERT INTO tb_course(name, price, date_created, course_format_id) " +
                    "VALUES(?, MONEY(?), ?, ?)";

            String readQuery = "SELECT * FROM tb_course ORDER BY id DESC LIMIT 1";

            preparedStatement = connection.prepareStatement(createQuery);
            preparedStatement.setString(1, course.getName());
            preparedStatement.setDouble(2, Double.parseDouble(course.getPrice() + ""));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(course.getDateCreated()));
            preparedStatement.setLong(4, course.getId());


            preparedStatement.execute();
            close(preparedStatement);

            preparedStatement = connection.prepareStatement(readQuery);
            resultSet = preparedStatement.executeQuery();

            resultSet.next();

            savedCourse = new Course();
            savedCourse.setId(resultSet.getLong("id"));
            savedCourse.setName(resultSet.getString("name"));
            savedCourse.setPrice(Double.valueOf(resultSet.getString("price").replaceAll("[^\\d\\.]+", "")));
            savedCourse.setDuration(resultSet.getInt("duration"));
            savedCourse.setDateCreated(resultSet.getTimestamp("date_created").toLocalDateTime());

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return savedCourse;
    }

}



