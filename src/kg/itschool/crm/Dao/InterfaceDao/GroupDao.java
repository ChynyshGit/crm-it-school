package kg.itschool.crm.Dao.InterfaceDao;

import kg.itschool.crm.Dao.Model.Group;

public interface GroupDao extends CrudDao<Group> {
    Group findById(Long id);

    void findById(long id);
}


