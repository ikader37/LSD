/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ilboudo
 */
@Entity
@Table(name = "branchement")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Branchement.findAll", query = "SELECT b FROM Branchement b")
    , @NamedQuery(name = "Branchement.findByLibellebranchement", query = "SELECT b FROM Branchement b WHERE b.libellebranchement = :libellebranchement")
    , @NamedQuery(name = "Branchement.findByDatebranch", query = "SELECT b FROM Branchement b WHERE b.datebranch = :datebranch")
    , @NamedQuery(name = "Branchement.findByEtatbranch", query = "SELECT b FROM Branchement b WHERE b.etatbranch = :etatbranch")
    , @NamedQuery(name = "Branchement.findByCommentairemed", query = "SELECT b FROM Branchement b WHERE b.commentairemed = :commentairemed")
    , @NamedQuery(name = "Branchement.findByPrescription", query = "SELECT b FROM Branchement b WHERE b.prescription = :prescription")
    , @NamedQuery(name = "Branchement.findByHospitalise", query = "SELECT b FROM Branchement b WHERE b.hospitalise = :hospitalise")
    , @NamedQuery(name = "Branchement.findByHeuredebut", query = "SELECT b FROM Branchement b WHERE b.heuredebut = :heuredebut")
    , @NamedQuery(name = "Branchement.findByHeurefin", query = "SELECT b FROM Branchement b WHERE b.heurefin = :heurefin")
    , @NamedQuery(name = "Branchement.findByIdbranchement", query = "SELECT b FROM Branchement b WHERE b.idbranchement = :idbranchement")})
@SequenceGenerator(name = Branchement.seqName, sequenceName = Branchement.seqName,initialValue = 1,allocationSize = 1)
public class Branchement implements Serializable {

    public static final String seqName="branchementsequence";
    
    
    private static final long serialVersionUID = 1L;
    @Column(name = "libellebranchement")
    private String libellebranchement;
    @Column(name = "datebranch")
    @Temporal(TemporalType.DATE)
    private Date datebranch;
    @Column(name = "etatbranch")
    private String etatbranch;
    @Column(name = "commentairemed")
    private String commentairemed;
    @Column(name = "prescription")
    private String prescription;
    @Column(name = "hospitalise")
    private Boolean hospitalise;
    @Column(name = "heuredebut")
    private String heuredebut;
    @Column(name = "heurefin")
    private String heurefin;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator =Branchement.seqName )
    @Column(name = "idbranchement")
    private Integer idbranchement;
    @JoinColumn(name = "idhoraire", referencedColumnName = "idhoraire")
    @ManyToOne(optional = false)
    private Horaire idhoraire;
    @JoinColumn(name = "idpatient", referencedColumnName = "idpatient")
    @ManyToOne
    private Patient idpatient;
    @JoinColumn(name = "idposte", referencedColumnName = "idposte")
    @ManyToOne
    private Poste idposte;
    @JoinColumn(name = "idseance", referencedColumnName = "idseance")
    @ManyToOne(optional = false)
    private Seance idseance;
    @Column(name = "fg_passe")
    private String fg_passe;
    

    public Branchement() {
        this.hospitalise = new Boolean(false);
    }

    public Branchement(Integer idbranchement) {
        this.hospitalise = new Boolean(false);
        this.idbranchement = idbranchement;
    }

    public String getLibellebranchement() {
        return libellebranchement;
    }

    public void setLibellebranchement(String libellebranchement) {
        this.libellebranchement = libellebranchement;
    }

    public Date getDatebranch() {
        return datebranch;
    }

    public void setDatebranch(Date datebranch) {
        this.datebranch = datebranch;
    }

    public String getEtatbranch() {
        return etatbranch;
    }

    public void setEtatbranch(String etatbranch) {
        this.etatbranch = etatbranch;
    }

    public String getCommentairemed() {
        return commentairemed;
    }

    public void setCommentairemed(String commentairemed) {
        this.commentairemed = commentairemed;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public Boolean getHospitalise() {
        return hospitalise;
    }

    public void setHospitalise(Boolean hospitalise) {
        this.hospitalise = hospitalise;
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

    public Integer getIdbranchement() {
        return idbranchement;
    }

    public void setIdbranchement(Integer idbranchement) {
        this.idbranchement = idbranchement;
    }

    public Horaire getIdhoraire() {
        return idhoraire;
    }

    public void setIdhoraire(Horaire idhoraire) {
        this.idhoraire = idhoraire;
    }

    public Patient getIdpatient() {
        return idpatient;
    }

    public void setIdpatient(Patient idpatient) {
        this.idpatient = idpatient;
    }

    public Poste getIdposte() {
        return idposte;
    }

    public void setIdposte(Poste idposte) {
        this.idposte = idposte;
    }

    public Seance getIdseance() {
        return idseance;
    }

    public void setIdseance(Seance idseance) {
        this.idseance = idseance;
    }

    public String getFg_passe() {
        return fg_passe;
    }

    public void setFg_passe(String fg_passe) {
        this.fg_passe = fg_passe;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idbranchement != null ? idbranchement.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Branchement)) {
            return false;
        }
        Branchement other = (Branchement) object;
        if ((this.idbranchement == null && other.idbranchement != null) || (this.idbranchement != null && !this.idbranchement.equals(other.idbranchement))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication6.gg.Branchement[ idbranchement=" + idbranchement + " ]";
    }
    
}
