/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP-FOLIO
 */
@Entity
@Table(name = "pays")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pays.findAll", query = "SELECT p FROM Pays p")
    , @NamedQuery(name = "Pays.findByIdpays", query = "SELECT p FROM Pays p WHERE p.idpays = :idpays")
    , @NamedQuery(name = "Pays.findByLibellepays", query = "SELECT p FROM Pays p WHERE p.libellepays = :libellepays")})
public class Pays implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idpays")
    private Integer idpays;
    @Size(max = 254)
    @Column(name = "libellepays")
    private String libellepays;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idpays")
    private List<Localite> localiteList;

    public Pays() {
    }

    public Pays(Integer idpays) {
        this.idpays = idpays;
    }

    public Integer getIdpays() {
        return idpays;
    }

    public void setIdpays(Integer idpays) {
        this.idpays = idpays;
    }

    public String getLibellepays() {
        return libellepays;
    }

    public void setLibellepays(String libellepays) {
        this.libellepays = libellepays;
    }

    @XmlTransient
    public List<Localite> getLocaliteList() {
        return localiteList;
    }

    public void setLocaliteList(List<Localite> localiteList) {
        this.localiteList = localiteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpays != null ? idpays.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pays)) {
            return false;
        }
        Pays other = (Pays) object;
        if ((this.idpays == null && other.idpays != null) || (this.idpays != null && !this.idpays.equals(other.idpays))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Pays[ idpays=" + idpays + " ]";
    }
    
}
