/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "poste")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Poste.findAll", query = "SELECT p FROM Poste p")
    , @NamedQuery(name = "Poste.findByIdposte", query = "SELECT p FROM Poste p WHERE p.idposte = :idposte")
    , @NamedQuery(name = "Poste.findByLibelleposte", query = "SELECT p FROM Poste p WHERE p.libelleposte = :libelleposte")
    , @NamedQuery(name = "Poste.findByEtatposte", query = "SELECT p FROM Poste p WHERE p.etatposte = :etatposte")
    , @NamedQuery(name = "Poste.findByRefconstructeur", query = "SELECT p FROM Poste p WHERE p.refconstructeur = :refconstructeur")})

@SequenceGenerator(name = Poste.seqName, sequenceName = Poste.seqName,initialValue = 1,allocationSize = 1 )
public class Poste implements Serializable {

    
    
    public static final String seqName="postesequence";
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = seqName)
    @Column(name = "idposte")
    private Integer idposte;
    @Size(max = 254)
    @Column(name = "libelleposte")
    private String libelleposte;
    @Size(max = 254)
    @Column(name = "etatposte")
    private String etatposte;
    @Size(max = 254)
    @Column(name = "refconstructeur")
    private String refconstructeur;
    @JoinColumn(name = "idunite", referencedColumnName = "idunite")
    @ManyToOne(optional = false)
    private Unitedialyse idunite;
    @OneToMany(mappedBy = "idposte")
    private List<Branchement> branchementCollection;

    public Poste() {
        idunite=new Unitedialyse();
    }

    public Poste(Integer idposte) {
        this.idposte = idposte;
    }

    public Integer getIdposte() {
        return idposte;
    }

    public void setIdposte(Integer idposte) {
        this.idposte = idposte;
    }

    public String getLibelleposte() {
        return libelleposte;
    }

    public void setLibelleposte(String libelleposte) {
        this.libelleposte = libelleposte;
    }

    public String getEtatposte() {
        return etatposte;
    }

    public void setEtatposte(String etatposte) {
        this.etatposte = etatposte;
    }

    public String getRefconstructeur() {
        return refconstructeur;
    }

    public void setRefconstructeur(String refconstructeur) {
        this.refconstructeur = refconstructeur;
    }

    public Unitedialyse getIdunite() {
        return idunite;
    }

    public void setIdunite(Unitedialyse idunite) {
        this.idunite = idunite;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idposte != null ? idposte.hashCode() : 0);
        return hash;
    }
    
    @XmlTransient
    public List<Branchement> getBranchementCollection() {
        return branchementCollection;
    }

    public void setBranchementCollection(List<Branchement> branchementCollection) {
        this.branchementCollection = branchementCollection;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Poste)) {
            return false;
        }
        Poste other = (Poste) object;
        if ((this.idposte == null && other.idposte != null) || (this.idposte != null && !this.idposte.equals(other.idposte))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Poste[ idposte=" + idposte + " ]";
    }
    
}
