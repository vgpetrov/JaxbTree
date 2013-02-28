package ru.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ru.entities.Property;

@Stateless
public class PropertyFacade extends AbstractFacade<Property> implements PropertyFacadeLocal {
    @PersistenceContext(unitName = "FileUploader_WARPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PropertyFacade() {
        super(Property.class);
    }
    
}
