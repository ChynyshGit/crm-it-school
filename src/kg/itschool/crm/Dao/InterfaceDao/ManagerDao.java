package kg.itschool.crm.Dao.InterfaceDao;

import kg.itschool.crm.Dao.Model.Manager;

public interface ManagerDao extends CrudDao<Manager> {

    Manager findById(Long id);
}