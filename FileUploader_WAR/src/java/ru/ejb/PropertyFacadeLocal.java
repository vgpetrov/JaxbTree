package ru.ejb;

import javax.ejb.Local;
import ru.entities.Property;

@Local
public interface PropertyFacadeLocal {

    void create(Property property);

    void edit(Property property);

    void remove(Property property);

    Property find(Object id);

    int count();
    
}
