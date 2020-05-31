/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "fonction")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fonction.findAll", query = "SELECT f FROM Fonction f")
    , @NamedQuery(name = "Fonction.findByIdfonction", query = "SELECT f FROM Fonction f WHERE f.idfonction = :idfonction")
    , @NamedQuery(name = "Fonction.findByLibellefonction", query = "SELECT f FROM Fonction f WHERE f.libellefonction = :libellefonction")})
@SequenceGenerator(name = Fonction.seqName, sequenceName = Fonction.seqName,initialValue = 1,allocationSize = 1)
public class Fonction implements Serializable {

    public static final String seqName="fonctionsequence";
   
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator =Fonction.seqName )
    @Column(name = "idfonction")
    private Integer idfonction;
    @Size(max = 254)
    @Column(name = "libellefonction")
    private String libellefonction;
    @OneToMany(mappedBy = "idfonction")
    private List<Patient> patientList;

    public Fonction() {
    }

    public Fonction(Integer idfonction) {
        this.idfonction = idfonction;
    }

    public Integer getIdfonction() {
        return idfonction;
    }

    public void setIdfonction(Integer idfonction) {
        this.idfonction = idfonction;
    }

    public String getLibellefonction() {
        return libellefonction;
    }

    public void setLibellefonction(String libellefonction) {
        this.libellefonction = libellefonction;
    }

    @XmlTransient
    public List<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idfonction != null ? idfonction.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fonction)) {
            return false;
        }
        Fonction other = (Fonction) object;
        if ((this.idfonction == null && other.idfonction != null) || (this.idfonction != null && !this.idfonction.equals(other.idfonction))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Fonction[ idfonction=" + idfonction + " ]";
    }
    
}
