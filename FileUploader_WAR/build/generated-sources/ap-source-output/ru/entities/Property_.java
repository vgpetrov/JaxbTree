package ru.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import ru.entities.Property;

@Generated(value="EclipseLink-2.4.1.v20121003-rNA", date="2013-02-15T03:20:29")
@StaticMetamodel(Property.class)
public class Property_ { 

    public static volatile SingularAttribute<Property, Long> id;
    public static volatile SingularAttribute<Property, Property> parentPproperty;
    public static volatile SingularAttribute<Property, String> attribute;
    public static volatile ListAttribute<Property, Property> property;

}