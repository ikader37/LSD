 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import entities.*;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import javax.enterprise.context.*;
import javax.inject.Inject;
import javax.inject.Named;
import models.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.event.RowEditEvent;
import utilities.*;

/**
 *
 * @author ilboudo
 */

@Named("patientController")
@SessionScoped
public class PatientController extends AbstractBean implements Serializable {
    
    
    private Patient patient=new Patient();
    private Groupe groupe=new Groupe();
    private List<String> idSelectedSeanceId=new ArrayList<>();
    
    private List<Patient> patientActuel=new ArrayList<>();
    private List<Patient> patientSortie=new ArrayList<>();
    
    private List<Seance> listSeanceSelect=new ArrayList<>();
    
    private List<Patient> patientList=new ArrayList<>();
    private Patient selectedPatient=new Patient();
    private JSFUtils jsfu=new JSFUtils();
   
    private Groupe groupeImp=new Groupe();
    private String etatPatient;
    private String impPost;
    private String impDia;
    
    
    
    @Inject
    PatientFacade patientFacade;
   
    @Inject
    GroupeFacade groupeFacade;
    
    @Inject
    SeanceFacade seanceFacade;
    
    
    
    @PostConstruct
    public void init(){
        
        patientList=patientFacade.findAll();
        patientActuel=patientFacade.patientListActu();
        patientSortie=patientFacade.patientListSortie();
        selectedPatient=new Patient();
        
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }

    public Patient getSelectedPatient() {
        return selectedPatient;
    }

    public void setSelectedPatient(Patient selectedPatient) {
        this.selectedPatient = selectedPatient;
    }

   

    public Groupe getGroupeImp() {
        return groupeImp;
    }

    public void setGroupeImp(Groupe groupeImp) {
        this.groupeImp = groupeImp;
    }

    public String getEtatPatient() {
        return etatPatient;
    }

    public void setEtatPatient(String etatPatient) {
        this.etatPatient = etatPatient;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public String getImpPost() {
        return impPost;
    }

    public void setImpPost(String impPost) {
        this.impPost = impPost;
    }

    public String getImpDia() {
        return impDia;
    }

    public void setImpDia(String impDia) {
        this.impDia = impDia;
    }

    public List<String> getIdSelectedSeanceId() {
        return idSelectedSeanceId;
    }

    public void setIdSelectedSeanceId(List<String> idSelectedSeanceId) {
        this.idSelectedSeanceId = idSelectedSeanceId;
    }

    public List<Seance> getListSeanceSelect() {
        return listSeanceSelect;
    }

    public void setListSeanceSelect(List<Seance> listSeanceSelect) {
        this.listSeanceSelect = listSeanceSelect;
    }

    public List<Patient> getPatientActuel() {
        return patientActuel;
    }

    public void setPatientActuel(List<Patient> patientActuel) {
        this.patientActuel = patientActuel;
    }

    public List<Patient> getPatientSortie() {
        return patientSortie;
    }

    public void setPatientSortie(List<Patient> patientSortie) {
        this.patientSortie = patientSortie;
    }
    
//    
    
    public void cheangeGroupe(){
        if(patient.getIdgroupe().getIdgroupe()!=null){
            Groupe g=groupeFacade.find(patient.getIdgroupe().getIdgroupe());
            listSeanceSelect=g.getSeanceList();
            
        }else{
            
        }
    }
    
    
    public void enregistrerPation(){
        System.out.println("ENREGISTRER");
        
        try {
            
            //
            //Verifier si le patient ne possede pas de id 
            
            
           // groupe=groupeFacade.find(patient.getIdgroupe().getIdgroupe());
            
           // if(groupe.getTaille()>groupe.getPatientList().size()){
                List<Seance> seL=new ArrayList<>();
                //patient.setFgSortie(Boolean.FALSE);
                for(String ids:idSelectedSeanceId){
                    Seance s=seanceFacade.find(new Integer(ids));
                    seL.add(s);
                }
                if(patient.getIdpatient()==null){
                    patient.setSeanceList(seL);
                     patientFacade.create(patient);
                }else{
                    patient.setSeanceList(seL);
                    patientFacade.edit(patient);
                    System.out.println("EDIT");
                }
               idSelectedSeanceId=new ArrayList<>();
                patient=new Patient();
                jsfu.sendInfoMessage("Enregistrement reussit!!!");
//            }else{
//                
//                jsfu.sendErrorMessage("La taille du groupe est atteinte.Choisir un autre groupe svp.");
//            }
//            
            
        } catch (Exception e) {
            
            jsfu.sendErrorMessage("Enregistrement echouee!!");
        }finally{
            patient.setIdpatient(null);
        }
        init();
    }
    
    
    public void supprimerPatient(){
        
        try {
            patientFacade.remove(selectedPatient);
            jsfu.sendInfoMessage("Suppression reussit!!!");
            selectedPatient=new Patient();
        } catch (Exception e) {
            jsfu.sendErrorMessage("Suppression echouee!!");
        }finally{
            
        }
        init();
    }
       public void printPdfPatientsParGroupe() throws JRException, IOException {
        String filename = "listesPatientsParGroupe.pdf";
        String jasperPath = "/Pages/Secreta/impression/patientsParGroupe.jrxml";
        HashMap<String, Object> parameters = new HashMap<>();
        Connection conn = SingletonConnection.getConnecter();
        String title = "listesPatientsParGroupe";
        String chemin = "/Pages/Secreta/impression/";
        String sql = "select p.nompatient, p.prenompatient, p.adresse,g.libellegroupe,f.libellefonction"
                + " from Patient p inner join fonction f on f.idfonction=p.idfonction inner join groupe g"
                + " on g.idgroupe=p.idgroupe where p.fg_sortie is false";
        //PDF2(parameters, jasperPath, sql, chemin, filename, conn);
        report(parameters, jasperPath, sql, chemin, title, conn);
    }
        public void printPdfPatients() throws JRException, IOException {
        String filename = "listesPatients.pdf";
        String jasperPath = "/Pages/Secreta/impression/patients.jrxml";
        HashMap<String, Object> parameters = new HashMap<>();
        Connection conn = SingletonConnection.getConnecter();
        String title = "listesPatients";
        String chemin = "/Pages/Secreta/impression/";
        String sql = "select p.nompatient, p.prenompatient, p.adresse,f.libellefonction"
                + " from Patient p inner join fonction f on f.idfonction=p.idfonction ";
        //PDF2(parameters, jasperPath, sql, chemin, filename, conn);
        report(parameters, jasperPath, sql, chemin, title, conn);
    }
        
        
    public void imprrimerPatientUnGroupe() throws IOException, JRException{
        
        if(groupeImp.getIdgroupe()!=null){
            String filename = "listesPatientsParGroupe.pdf";
        String jasperPath = "/Pages/Secreta/impression/patientsUnGroupe.jrxml";
        HashMap<String, Object> parameters = new HashMap<>();
        Connection conn = SingletonConnection.getConnecter();
        String title = "listesPatientsParGroupe";
        String chemin = "/Pages/Secreta/impression/";
        String sql = "select p.nompatient, p.prenompatient, p.adresse,g.libellegroupe,f.libellefonction"
                + " from Patient p inner join fonction f on f.idfonction=p.idfonction inner join groupe g"
                + " on g.idgroupe=p.idgroupe where g.idgroupe ="+groupeImp.getIdgroupe();
        //PDF2(parameters, jasperPath, sql, chemin, filename, conn);
        report(parameters, jasperPath, sql, chemin, title, conn);
        }else{
           jsfu.sendErrorMessage("Veuillez selectionner un groupe d'abord");
        }
        
    }
    
    
     public void imprrimerPatientChro() {
        
        
            String filename = "listesPatientsParGroupe.pdf";
        String jasperPath = "/Pages/Secreta/impression/patientsChonik.jrxml";
        HashMap<String, Object> parameters = new HashMap<>();
        
        parameters.put("chro", etatPatient);
        
        Connection conn = SingletonConnection.getConnecter();
        String title = "listesPatientsParGroupe";
        String chemin = "/Pages/Secreta/impression/";
        String sql = "select p.nompatient, p.prenompatient, p.adresse,f.libellefonction from Patient p inner join fonction f on f.idfonction=p.idfonction inner join groupe g on g.idgroupe=p.idgroupe where p.etatsante='"+etatPatient+"'";
        try {
            //PDF2(parameters, jasperPath, sql, chemin, filename, conn);
            report(parameters, jasperPath, sql, chemin, title, conn);
        } catch (IOException ex) {
            jsfu.sendErrorMessage("Une erreur est survénue!! Veuillez réessayer svp.");
            Logger.getLogger(PatientController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            jsfu.sendErrorMessage("Une erreur est survénue!! Veuillez réessayer svp.");
            Logger.getLogger(PatientController.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
    }
     
     
     public void sortirPatient(){
         try {
             selectedPatient.setFgSortie(Boolean.TRUE);
             patientFacade.edit(selectedPatient);
             jsfu.sendInfoMessage("Sortiie d'un patient effectué avec succes.");
             selectedPatient=new Patient();
         } catch (Exception e) {
             jsfu.sendErrorMessage("Sortie d'un patient échoué. Veuillez reessayer svp.");
             
         }
         init();
         
     }

}
