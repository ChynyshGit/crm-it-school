package kg.itschool.crm.Dao.InterfaceDao;

import kg.itschool.crm.Dao.Model.Address;

public interface AddressDao extends CrudDao<Address> {

    Address findById(long id);
}


