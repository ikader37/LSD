/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
@Table(name = "seance")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Seance.findAll", query = "SELECT s FROM Seance s")
    , @NamedQuery(name = "Seance.findByIdseance", query = "SELECT s FROM Seance s WHERE s.idseance = :idseance")
    , @NamedQuery(name = "Seance.findByLibelleseance", query = "SELECT s FROM Seance s WHERE s.libelleseance = :libelleseance")})
@SequenceGenerator(name = Seance.seqName, sequenceName = Seance.seqName,initialValue = 1,allocationSize = 1 )
public class Seance implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public static final String seqName="seancesequence";
    
    
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = seqName)
    @Column(name = "idseance")
    private Integer idseance;
    @Size(max = 254)
    @Column(name = "libelleseance")
    private String libelleseance;
    
    @JoinColumn(name = "idjour", referencedColumnName = "idjour")
    @ManyToOne(optional = false)
    private Jour idjour;
    @Column(name = "hdeb")
    private String heuredebut;
    @Column(name = "hfin")
    private String heurefin;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idseance")
    private List<Branchement> branchementCollection;
    @JoinColumn(name = "idunite", referencedColumnName = "idunite")
    @ManyToOne(optional = false)
    private Unitedialyse idunite;
    @JoinTable(name = "seancegroupe", joinColumns = {
        @JoinColumn(name ="idseance", referencedColumnName = "idseance" )}, inverseJoinColumns = {
        @JoinColumn(name = "idgroupe", referencedColumnName = "idgroupe")})
    @ManyToMany
    private List<Groupe> groupeList;
    @ManyToMany(mappedBy = "seanceList")
    private List<Patient> patientList;
    @Column(name="nombre_poste")
    private Integer nombre_poste;
    
    
    
    public Seance() {
        
       
        idjour=new Jour();
        idunite=new Unitedialyse();
        groupeList=new ArrayList<>();
        
    }

    public Seance(Integer idseance) {
        this.idseance = idseance;
    }

    public Integer getIdseance() {
        return idseance;
    }

    public void setIdseance(Integer idseance) {
        this.idseance = idseance;
    }

    public String getLibelleseance() {
        return libelleseance;
    }

    public void setLibelleseance(String libelleseance) {
        this.libelleseance = libelleseance;
    }
    public Jour getIdjour() {
        return idjour;
    }

    public void setIdjour(Jour idjour) {
        this.idjour = idjour;
    }

    public String getHeuredebut() {
        return heuredebut;
    }

    public void setHeuredebut(String heuredebut) {
        this.heuredebut = heuredebut;
    }

    public String getHeurefin() {
        return heurefin;
    }

    public void setHeurefin(String heurefin) {
        this.heurefin = heurefin;
    }

    public List<Groupe> getGroupeList() {
        return groupeList;
    }

    public void setGroupeList(List<Groupe> groupeList) {
        this.groupeList = groupeList;
    }

    public List<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }

    public Integer getNombre_poste() {
        return nombre_poste;
    }

    public void setNombre_poste(Integer nombre_poste) {
        this.nombre_poste = nombre_poste;
    }

    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idseance != null ? idseance.hashCode() : 0);
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
        if (!(object instanceof Seance)) {
            return false;
        }
        Seance other = (Seance) object;
        if ((this.idseance == null && other.idseance != null) || (this.idseance != null && !this.idseance.equals(other.idseance))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Seance[ idseance=" + idseance + " ]";
    }

    public Unitedialyse getIdunite() {
        return idunite;
    }

    public void setIdunite(Unitedialyse idunite) {
        this.idunite = idunite;
    }
    
}
