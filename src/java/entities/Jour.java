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
@Table(name = "jour")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jour.findAll", query = "SELECT j FROM Jour j")
    , @NamedQuery(name = "Jour.findByIdjour", query = "SELECT j FROM Jour j WHERE j.idjour = :idjour")
    , @NamedQuery(name = "Jour.findByLibellejour", query = "SELECT j FROM Jour j WHERE j.libellejour = :libellejour")})
public class Jour implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idjour")
    private Integer idjour;
    @Size(max = 254)
    @Column(name = "libellejour")
    private String libellejour;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idjour")
    private List<Seance> seanceList;

    public Jour() {
    }

    public Jour(Integer idjour) {
        this.idjour = idjour;
    }

    public Integer getIdjour() {
        return idjour;
    }

    public void setIdjour(Integer idjour) {
        this.idjour = idjour;
    }

    public String getLibellejour() {
        return libellejour;
    }

    public void setLibellejour(String libellejour) {
        this.libellejour = libellejour;
    }

    @XmlTransient
    public List<Seance> getSeanceList() {
        return seanceList;
    }

    public void setSeanceList(List<Seance> seanceList) {
        this.seanceList = seanceList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idjour != null ? idjour.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jour)) {
            return false;
        }
        Jour other = (Jour) object;
        if ((this.idjour == null && other.idjour != null) || (this.idjour != null && !this.idjour.equals(other.idjour))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Jour[ idjour=" + idjour + " ]";
    }
    
}
