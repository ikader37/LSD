/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP-FOLIO
 */
@Entity
@Table(name = "horaire")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Horaire.findAll", query = "SELECT h FROM Horaire h")
    , @NamedQuery(name = "Horaire.findByIdhoraire", query = "SELECT h FROM Horaire h WHERE h.idhoraire = :idhoraire")
    , @NamedQuery(name = "Horaire.findByLibellehoraire", query = "SELECT h FROM Horaire h WHERE h.libellehoraire = :libellehoraire")})
public class Horaire implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idhoraire")
    private Integer idhoraire;
    @Size(max = 254)
    @Column(name = "libellehoraire")
    private String libellehoraire;

    public Horaire() {
    }

    public Horaire(Integer idhoraire) {
        this.idhoraire = idhoraire;
    }

    public Integer getIdhoraire() {
        return idhoraire;
    }

    public void setIdhoraire(Integer idhoraire) {
        this.idhoraire = idhoraire;
    }

    public String getLibellehoraire() {
        return libellehoraire;
    }

    public void setLibellehoraire(String libellehoraire) {
        this.libellehoraire = libellehoraire;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idhoraire != null ? idhoraire.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Horaire)) {
            return false;
        }
        Horaire other = (Horaire) object;
        if ((this.idhoraire == null && other.idhoraire != null) || (this.idhoraire != null && !this.idhoraire.equals(other.idhoraire))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Horaire[ idhoraire=" + idhoraire + " ]";
    }
    
}
