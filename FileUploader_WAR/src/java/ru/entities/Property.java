package ru.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(catalog = "", schema = "APP")
@XmlRootElement
public class Property implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long id;
    
    @Size(max = 256)
    private String attribute;
    
    @ManyToOne
    @JoinColumn(name="PARENT_ID")
    private Property parentPproperty;

    @OneToMany(mappedBy="parentPproperty", cascade=CascadeType.ALL)
    private List<Property> property;

    public Property() {
    }

    public Property(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @XmlTransient
    public void setId(Long id) {
        this.id = id;
    }

    public String getAttribute() {
        return attribute;
    }

    @XmlAttribute(name="name")
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public Property getParentPproperty() {
        return parentPproperty;
    }

    @XmlTransient
    public void setParentPproperty(Property parentPproperty) {
        this.parentPproperty = parentPproperty;
    }

    public List<Property> getProperty() {
        return property;
    }

    @XmlElement
    public void setProperty(List<Property> property) {
        this.property = property;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Property)) {
            return false;
        }
        Property other = (Property) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

//    @Override
//    public String toString() {
//        return "Property{" + "id=" + id + ", attribute=" + attribute + ", parentPproperty=" + parentPproperty + ", property=" + property + '}';
//    }
    
}
