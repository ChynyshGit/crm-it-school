package kg.itschool.crm.Dao.InterfaceDao;

import kg.itschool.crm.Dao.Model.Student;

public interface StudentDao extends CrudDao<Student> {

    void findById(long id);
}

