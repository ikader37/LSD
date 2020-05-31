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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
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
@Table(name = "unitedialyse")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Unitedialyse.findAll", query = "SELECT u FROM Unitedialyse u")
    , @NamedQuery(name = "Unitedialyse.findByIdunite", query = "SELECT u FROM Unitedialyse u WHERE u.idunite = :idunite")
    , @NamedQuery(name = "Unitedialyse.findByLibelleunite", query = "SELECT u FROM Unitedialyse u WHERE u.libelleunite = :libelleunite")
    , @NamedQuery(name = "Unitedialyse.findByNombreposte", query = "SELECT u FROM Unitedialyse u WHERE u.nombreposte = :nombreposte")
    , @NamedQuery(name = "Unitedialyse.findByCategorie", query = "SELECT u FROM Unitedialyse u WHERE u.categorie = :categorie")})
@SequenceGenerator(name = Unitedialyse.seqName, sequenceName = Unitedialyse.seqName,initialValue = 1,allocationSize = 1)
public class Unitedialyse implements Serializable {

    
    public static final String seqName="unitedialysesequence";
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator =Fonction.seqName )
    @Column(name = "idunite")
    private Integer idunite;
    @Size(max = 254)
    @Column(name = "libelleunite")
    private String libelleunite;
    @Column(name = "nombreposte")
    private Integer nombreposte;
    @Size(max = 254)
    @Column(name = "categorie")
    private String categorie;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idunite")
    private List<Poste> posteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idunite")
    private List<Seance> seanceList;

    public Unitedialyse() {
    }

    public Unitedialyse(Integer idunite) {
        this.idunite = idunite;
    }

    public Integer getIdunite() {
        return idunite;
    }

    public void setIdunite(Integer idunite) {
        this.idunite = idunite;
    }

    public String getLibelleunite() {
        return libelleunite;
    }

    public void setLibelleunite(String libelleunite) {
        this.libelleunite = libelleunite;
    }

    public Integer getNombreposte() {
        return nombreposte;
    }

    public void setNombreposte(Integer nombreposte) {
        this.nombreposte = nombreposte;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    @XmlTransient
    public List<Poste> getPosteList() {
        return posteList;
    }

    public void setPosteList(List<Poste> posteList) {
        this.posteList = posteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idunite != null ? idunite.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Unitedialyse)) {
            return false;
        }
        Unitedialyse other = (Unitedialyse) object;
        if ((this.idunite == null && other.idunite != null) || (this.idunite != null && !this.idunite.equals(other.idunite))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Unitedialyse[ idunite=" + idunite + " ]";
    }

    public List<Seance> getSeanceList() {
        return seanceList;
    }

    public void setSeanceList(List<Seance> seanceList) {
        this.seanceList = seanceList;
    }
    
}
