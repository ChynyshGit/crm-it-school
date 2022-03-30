package kg.itschool.crm.Dao.InterfaceDao;

import kg.itschool.crm.Dao.Model.Mentor;

public interface MentorDao extends CrudDao<Mentor> {

    void findById(long id);
}

