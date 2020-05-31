/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP-FOLIO
 */
@Entity
@Table(name = "patient")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Patient.findAll", query = "SELECT p FROM Patient p")
    , @NamedQuery(name = "Patient.findByIdpatient", query = "SELECT p FROM Patient p WHERE p.idpatient = :idpatient")
    , @NamedQuery(name = "Patient.findByNompatient", query = "SELECT p FROM Patient p WHERE p.nompatient = :nompatient")
    , @NamedQuery(name = "Patient.findByPrenompatient", query = "SELECT p FROM Patient p WHERE p.prenompatient = :prenompatient")
    , @NamedQuery(name = "Patient.findByAdresse", query = "SELECT p FROM Patient p WHERE p.adresse = :adresse")
    , @NamedQuery(name = "Patient.findByTelephone1", query = "SELECT p FROM Patient p WHERE p.telephone1 = :telephone1")
    , @NamedQuery(name = "Patient.findByTelephone2", query = "SELECT p FROM Patient p WHERE p.telephone2 = :telephone2")
    , @NamedQuery(name = "Patient.findByAccompagnant", query = "SELECT p FROM Patient p WHERE p.accompagnant = :accompagnant")
    , @NamedQuery(name = "Patient.findByTelephonaccompagnant", query = "SELECT p FROM Patient p WHERE p.telephonaccompagnant = :telephonaccompagnant")
    , @NamedQuery(name = "Patient.findByAntecedent", query = "SELECT p FROM Patient p WHERE p.antecedent = :antecedent")
    , @NamedQuery(name = "Patient.findByDateentree", query = "SELECT p FROM Patient p WHERE p.dateentree = :dateentree")
    , @NamedQuery(name = "Patient.findByNbreseance", query = "SELECT p FROM Patient p WHERE p.nbreseance = :nbreseance")
    , @NamedQuery(name = "Patient.findByEtatsante", query = "SELECT p FROM Patient p WHERE p.etatsante = :etatsante")
    , @NamedQuery(name = "Patient.findByContraintefonction", query = "SELECT p FROM Patient p WHERE p.contraintefonction = :contraintefonction")})

@SequenceGenerator(name = Patient.seqName, sequenceName = Patient.seqName, initialValue = 1, allocationSize = 1)
public class Patient implements Serializable {

    public static final String seqName = "patientsequence";

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = seqName)
    @Column(name = "idpatient")
    private Integer idpatient;
    @Size(max = 254)
    @Column(name = "nompatient")
    private String nompatient;
    @Size(max = 254)
    @Column(name = "prenompatient")
    private String prenompatient;
    @Size(max = 254)
    @Column(name = "adresse")
    private String adresse;
    @Size(max = 254)
    @Column(name = "telephone1")
    private String telephone1;
    @Size(max = 254)
    @Column(name = "telephone2")
    private String telephone2;
    @Size(max = 254)
    @Column(name = "accompagnant")
    private String accompagnant;
    @Size(max = 254)
    @Column(name = "telephonaccompagnant")
    private String telephonaccompagnant;
    @Size(max = 254)
    @Column(name = "antecedent")
    private String antecedent;
    @Column(name = "dateentree")
    @Temporal(TemporalType.DATE)
    private Date dateentree;
    @Column(name = "datesortie")
    @Temporal(TemporalType.DATE)
    private Date datesortie;
    @Column(name = "nbreseance")
    private Integer nbreseance;
    @Size(max = 254)
    @Column(name = "etatsante")
    private String etatsante;
    @Size(max = 254)
    @Column(name = "contraintefonction")
    private String contraintefonction;
    @JoinColumn(name = "idfonction", referencedColumnName = "idfonction")
    @ManyToOne
    private Fonction idfonction;

    @JoinColumn(name = "idlocalite", referencedColumnName = "idlocalite")
    @ManyToOne
    private Localite idlocalite;

    @OneToMany(mappedBy = "idpatient")
    private List<Branchement> branchementCollection;
    @Column(name = "sexe")
    private String sexe;
    @JoinTable(name = "seancepatient", joinColumns = {
        @JoinColumn(name = "idpatient", referencedColumnName = "idpatient")}, inverseJoinColumns = {
        @JoinColumn(name = "idseance", referencedColumnName = "idseance")})
    @ManyToMany
    private List<Seance> seanceList;
    @JoinColumn(name = "idgroupe", referencedColumnName = "idgroupe")
    @ManyToOne
    private Groupe idgroupe;
    @Column(name = "cause_sortie")
    private String cause_sortie;
     @Column(name = "fg_sortie")
    private Boolean fgSortie;
    
     
     
    public Patient() {
        fgSortie=Boolean.FALSE;
        idfonction = new Fonction();
        idgroupe=new Groupe();
        idlocalite = new Localite();

    }

    public Patient(Integer idpatient) {
        this.idpatient = idpatient;
    }

    public Integer getIdpatient() {
        return idpatient;
    }

    public void setIdpatient(Integer idpatient) {
        this.idpatient = idpatient;
    }

    public String getNompatient() {
        return nompatient;
    }

    public void setNompatient(String nompatient) {
        this.nompatient = nompatient;
    }

    public String getPrenompatient() {
        return prenompatient;
    }

    public void setPrenompatient(String prenompatient) {
        this.prenompatient = prenompatient;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone1() {
        return telephone1;
    }

    public void setTelephone1(String telephone1) {
        this.telephone1 = telephone1;
    }

    public String getTelephone2() {
        return telephone2;
    }

    public void setTelephone2(String telephone2) {
        this.telephone2 = telephone2;
    }

    public String getAccompagnant() {
        return accompagnant;
    }

    public void setAccompagnant(String accompagnant) {
        this.accompagnant = accompagnant;
    }

    public String getTelephonaccompagnant() {
        return telephonaccompagnant;
    }

    public void setTelephonaccompagnant(String telephonaccompagnant) {
        this.telephonaccompagnant = telephonaccompagnant;
    }

    public String getAntecedent() {
        return antecedent;
    }

    public void setAntecedent(String antecedent) {
        this.antecedent = antecedent;
    }

    public Date getDateentree() {
        return dateentree;
    }

    public void setDateentree(Date dateentree) {
        this.dateentree = dateentree;
    }

    public Integer getNbreseance() {
        return nbreseance;
    }

    public void setNbreseance(Integer nbreseance) {
        this.nbreseance = nbreseance;
    }

    public String getEtatsante() {
        return etatsante;
    }

    public void setEtatsante(String etatsante) {
        this.etatsante = etatsante;
    }

    public String getContraintefonction() {
        return contraintefonction;
    }

    public void setContraintefonction(String contraintefonction) {
        this.contraintefonction = contraintefonction;
    }

    public Fonction getIdfonction() {
        return idfonction;
    }

    public void setIdfonction(Fonction idfonction) {
        this.idfonction = idfonction;
    }

    public Localite getIdlocalite() {
        return idlocalite;
    }

    public void setIdlocalite(Localite idlocalite) {
        this.idlocalite = idlocalite;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public Groupe getIdgroupe() {
        return idgroupe;
    }

    public void setIdgroupe(Groupe idgroupe) {
        this.idgroupe = idgroupe;
    }

    public Date getDatesortie() {
        return datesortie;
    }

    public void setDatesortie(Date datesortie) {
        this.datesortie = datesortie;
    }

    public String getCause_sortie() {
        return cause_sortie;
    }

    public void setCause_sortie(String cause_sortie) {
        this.cause_sortie = cause_sortie;
    }

    public Boolean getFgSortie() {
        return fgSortie;
    }

    public void setFgSortie(Boolean fgSortie) {
        this.fgSortie = fgSortie;
    }
    
    

    @XmlTransient
    public List<Seance> getSeanceList() {
        return seanceList;
    }

    public void setSeanceList(List<Seance> seanceList) {
        this.seanceList = seanceList;
    }

    @XmlTransient
    public List<Branchement> getBranchementCollection() {
        return branchementCollection;
    }

    public void setBranchementCollection(List<Branchement> branchementCollection) {
        this.branchementCollection = branchementCollection;
    }
//    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpatient != null ? idpatient.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Patient)) {
            return false;
        }
        Patient other = (Patient) object;
        if ((this.idpatient == null && other.idpatient != null) || (this.idpatient != null && !this.idpatient.equals(other.idpatient))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Patient[ idpatient=" + idpatient + " ]";
    }

}
