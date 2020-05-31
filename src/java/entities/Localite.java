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
@Table(name = "localite")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Localite.findAll", query = "SELECT l FROM Localite l")
    , @NamedQuery(name = "Localite.findByIdlocalite", query = "SELECT l FROM Localite l WHERE l.idlocalite = :idlocalite")
    , @NamedQuery(name = "Localite.findByLibellelocalite", query = "SELECT l FROM Localite l WHERE l.libellelocalite = :libellelocalite")
    , @NamedQuery(name = "Localite.findByRegion", query = "SELECT l FROM Localite l WHERE l.region = :region")})
@SequenceGenerator(name = Localite.seqName,sequenceName =Localite.seqName,initialValue = 1,allocationSize = 1 )
public class Localite implements Serializable {

    
    public static final String seqName="localitesequence";
    
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = seqName)
    @Column(name = "idlocalite")
    private Integer idlocalite;
    @Size(max = 254)
    @Column(name = "libellelocalite")
    private String libellelocalite;
    @Size(max = 254)
    @Column(name = "region")
    private String region;
    @JoinColumn(name = "idpays", referencedColumnName = "idpays")
    @ManyToOne(optional = true)
    private Pays idpays;
    @OneToMany(mappedBy = "idlocalite")
    private List<Patient> patientList;

    public Localite() {
    }

    public Localite(Integer idlocalite) {
        this.idlocalite = idlocalite;
    }

    public Integer getIdlocalite() {
        return idlocalite;
    }

    public void setIdlocalite(Integer idlocalite) {
        this.idlocalite = idlocalite;
    }

    public String getLibellelocalite() {
        return libellelocalite;
    }

    public void setLibellelocalite(String libellelocalite) {
        this.libellelocalite = libellelocalite;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Pays getIdpays() {
        return idpays;
    }

    public void setIdpays(Pays idpays) {
        this.idpays = idpays;
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
        hash += (idlocalite != null ? idlocalite.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Localite)) {
            return false;
        }
        Localite other = (Localite) object;
        if ((this.idlocalite == null && other.idlocalite != null) || (this.idlocalite != null && !this.idlocalite.equals(other.idlocalite))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Localite[ idlocalite=" + idlocalite + " ]";
    }
    
}
