package kg.itschool.crm.Dao.InterfaceDao;

import kg.itschool.crm.Dao.Model.Course;

public interface CourseDao extends CrudDao<Course> {

    Course findById(Long id);

    void findById(long id);
}



