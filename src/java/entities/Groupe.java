/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.inject.Default;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "groupe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Groupe.findAll", query = "SELECT g FROM Groupe g")
    , @NamedQuery(name = "Groupe.findByIdgroupe", query = "SELECT g FROM Groupe g WHERE g.idgroupe = :idgroupe")
    , @NamedQuery(name = "Groupe.findByLibellegroupe", query = "SELECT g FROM Groupe g WHERE g.libellegroupe = :libellegroupe")
    , @NamedQuery(name = "Groupe.findByNbseancesem", query = "SELECT g FROM Groupe g WHERE g.nbseancesem = :nbseancesem")
    , @NamedQuery(name = "Groupe.findByTaille", query = "SELECT g FROM Groupe g WHERE g.taille = :taille")})

//@SequenceGenerator(name = Groupe.seqName, sequenceName = Groupe.seqName)
public class Groupe implements Serializable {

    
    public static final String seqName="groupeSequence";
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idgroupe")
    private Integer idgroupe;
    @Size(max = 254)
    @Column(name = "libellegroupe")
    private String libellegroupe;
    @Column(name = "nbseancesem")
    private Integer nbseancesem;
    @Column(name = "taille")
    private Integer taille;
    @Column(name = "fg_fixe")
   
    private boolean fixe;
    @Column(name = "intval_jr")
    private Integer intval_jour;
    
    @ManyToMany(mappedBy = "groupeList")
    private List<Seance> seanceList;
     @OneToMany(mappedBy = "idgroupe")
    private List<Patient> patientList;

    public Groupe() {
        //fixe=false;
    }

    public Groupe(Integer idgroupe) {
        this.idgroupe = idgroupe;
    }

    public Integer getIdgroupe() {
        return idgroupe;
    }

    public void setIdgroupe(Integer idgroupe) {
        this.idgroupe = idgroupe;
    }

    public String getLibellegroupe() {
        return libellegroupe;
    }

    public void setLibellegroupe(String libellegroupe) {
        this.libellegroupe = libellegroupe;
    }

    public Integer getNbseancesem() {
        return nbseancesem;
    }

    public void setNbseancesem(Integer nbseancesem) {
        this.nbseancesem = nbseancesem;
    }

    public Integer getTaille() {
        return taille;
    }

    public void setTaille(Integer taille) {
        this.taille = taille;
    }

    public boolean isFixe() {
        return fixe;
    }

    public void setFixe(boolean fixe) {
        this.fixe = fixe;
    }

    public Integer getIntval_jour() {
        return intval_jour;
    }

    public void setIntval_jour(Integer intval_jour) {
        this.intval_jour = intval_jour;
    }

    public List<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
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
        hash += (idgroupe != null ? idgroupe.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Groupe)) {
            return false;
        }
        Groupe other = (Groupe) object;
        if ((this.idgroupe == null && other.idgroupe != null) || (this.idgroupe != null && !this.idgroupe.equals(other.idgroupe))) {
            return false;
        }
        return true;
    }

//    @Override
//    public String toString() {
//        return "entities.Groupe[ idgroupe=" + idgroupe + " ]";
//    }
    
}
