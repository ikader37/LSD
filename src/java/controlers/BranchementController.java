/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import entities.*;
import java.io.IOException;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.inject.Named;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Default;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import models.*;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.event.RowEditEvent;
import utilities.JSFUtils;
import utilities.SingletonConnection;

/**
 *
 * @author ilboudo
 */
@SessionScoped
@Named("BranchementController")
public class BranchementController extends AbstractBean implements Serializable {

    private Branchement branchement = new Branchement();
    private Groupe groupe = new Groupe();
    private List<Seance> seanceList = new ArrayList<>();
    private List<Branchement> branchementReporter = new ArrayList<>();
    private List<Branchement> branchementNonReporter = new ArrayList<>();

    private Seance seance = new Seance();
    private Date hdeb;
    private Date hfin;
    private Date datefinbr;

    private boolean impPoste = false;//=new ArrayList<>();

    private boolean impDialise = false;

    private List<Branchement> branchementList = new ArrayList<>();// Branchement();

    private List<Branchement> branchementAdd = new ArrayList<>();
    private List<Object[]> branchementListByDate = new ArrayList<>();
    private Object[] branchementSelected = new Object[2];
    private List<Branchement> detailBranchement = new ArrayList<>();
    private List<Branchement> branchementNonValide = new ArrayList<>();
    private Patient selectedPatientMan = new Patient();//Utiliser pour les branchements manuels
    private Branchement branchementManu = new Branchement();//Ytiliser pour un branchement manuel
    private Branchement branchementAValider = new Branchement();

    private List<Branchement> branchementValider = new ArrayList<>();
    private List<Patient> branchementHospitaliser = new ArrayList<>();
    private Branchement branchementReprogrammer ;//= new Branchement();

    private List<Patient> patientAdd = new ArrayList<>();
    private JSFUtils jsfu = new JSFUtils();
    private String commentaire;
    private String prescription;
    private boolean hospitaliser;
    
    private List<Patient> historiqueBranchement=new ArrayList<>();
    

    public Seance getSeance() {
        return seance;
    }

    public Date getHdeb() {
        return hdeb;
    }

    public void setHdeb(Date hdeb) {
        this.hdeb = hdeb;
    }

    public Date getHfin() {
        return hfin;
    }

    public void setHfin(Date hfin) {
        this.hfin = hfin;
    }

    public void setSeance(Seance seance) {
        this.seance = seance;

    }

    private Unitedialyse unitedialyse = new Unitedialyse();

    public Unitedialyse getUnitedialyse() {
        return unitedialyse;
    }

    public void setUnitedialyse(Unitedialyse unitedialyse) {
        this.unitedialyse = unitedialyse;
    }

    public Branchement getBranchement() {
        return branchement;
    }

    public void setBranchement(Branchement branchement) {
        this.branchement = branchement;
    }

    public boolean isImpPoste() {
        return impPoste;
    }

    public void setImpPoste(boolean impPoste) {
        this.impPoste = impPoste;
    }

    public boolean isImpDialise() {
        return impDialise;
    }

    public void setImpDialise(boolean impDialise) {
        this.impDialise = impDialise;
    }

    public List<Object[]> getBranchementListByDate() {
        return branchementListByDate;
    }

    public void setBranchementListByDate(List<Object[]> branchementListByDate) {
        this.branchementListByDate = branchementListByDate;
    }

    public Object[] getBranchementSelected() {
        return branchementSelected;
    }

    public void setBranchementSelected(Object[] branchementSelected) {
        this.branchementSelected = branchementSelected;
    }

    public List<Branchement> getDetailBranchement() {
        return detailBranchement;
    }

    public void setDetailBranchement(List<Branchement> detailBranchement) {
        this.detailBranchement = detailBranchement;
    }

    public List<Branchement> getBranchementNonValide() {
        return branchementNonValide;
    }

    public void setBranchementNonValide(List<Branchement> branchementNonValide) {
        this.branchementNonValide = branchementNonValide;
    }

    public Branchement getBranchementAValider() {
        return branchementAValider;
    }

    public void setBranchementAValider(Branchement branchementAValider) {
        this.branchementAValider = branchementAValider;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public boolean isHospitaliser() {
        return hospitaliser;
    }

    public void setHospitaliser(boolean hospitaliser) {
        this.hospitaliser = hospitaliser;
    }

    public List<Branchement> getBranchementValider() {
        return branchementValider;
    }

    public void setBranchementValider(List<Branchement> branchementValider) {
        this.branchementValider = branchementValider;
    }

    public List<Patient> getBranchementHospitaliser() {
        return branchementHospitaliser;
    }

    public void setBranchementHospitaliser(List<Patient> branchementHospitaliser) {
        this.branchementHospitaliser = branchementHospitaliser;
    }

    public Date getDatefinbr() {
        return datefinbr;
    }

    public void setDatefinbr(Date datefinbr) {
        this.datefinbr = datefinbr;
    }

    public List<Branchement> getBranchementReporter() {
        return branchementReporter;
    }

    public void setBranchementReporter(List<Branchement> branchementReporter) {
        this.branchementReporter = branchementReporter;
    }

    public List<Branchement> getBranchementNonReporter() {
        return branchementNonReporter;
    }

    public void setBranchementNonReporter(List<Branchement> branchementNonReporter) {
        this.branchementNonReporter = branchementNonReporter;
    }

    public Branchement getBranchementReprogrammer() {
        return branchementReprogrammer;
    }

    public void setBranchementReprogrammer(Branchement branchementReprogrammer) {
        this.branchementReprogrammer = branchementReprogrammer;
    }

    public Patient getSelectedPatientMan() {
        return selectedPatientMan;
    }

    public void setSelectedPatientMan(Patient selectedPatientMan) {
        this.selectedPatientMan = selectedPatientMan;
    }

    public Branchement getBranchementManu() {
        return branchementManu;
    }

    public void setBranchementManu(Branchement branchementManu) {
        this.branchementManu = branchementManu;
    }

    public List<Patient> getHistoriqueBranchement() {
        return historiqueBranchement;
    }

    public void setHistoriqueBranchement(List<Patient> historiqueBranchement) {
        this.historiqueBranchement = historiqueBranchement;
    }
    
    
    

    @Inject
    GroupeFacade groupeFacade;
    @Inject
    PosteFacade posteFacade;
    @Inject
    UnitedialyseFacade unitedialyseFacade;
    @Inject
    BranchementFacade branchementFacade;

    //BranchementFacae branchementFacae;
    @PostConstruct
    public void init() {
        // branchementFacade.getBranchementGrpeByDateLib();
//        System.out.println(""+branchementFacade.getBranchementGrpeByDateLib().toString());
//
//        List<Object[]> brl=branchementFacade.getBranchementGrpeByDateLib();

        branchementListByDate = branchementFacade.getBranchementGrpeByDateLib();
        branchementNonValide = branchementFacade.listBranchementNonValide();
        branchementHospitaliser = branchementFacade.listDesHospitaliser();
        branchementValider = branchementFacade.listBranchementValide();

        branchementNonReporter = branchementFacade.listBranchementNonReport();
        
        branchementReporter = branchementFacade.listBranchementReport();
        
        historiqueBranchement=branchementFacade.historiqueBranchement();
        
    }

    public List<Seance> getSeanceList() {
        return seanceList;
    }

    public void setSeanceList(List<Seance> seanceList) {
        this.seanceList = seanceList;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public void changeSeance() {
        if (groupe != null) {
            System.out.println("GROUPE :" + groupe);
        }

        groupe = groupeFacade.find(groupe.getIdgroupe());
        // System.out.println
        System.out.println("GROUPE 3 :" + groupe);

        System.out.println("GROUPE 2 :" + groupe.getLibellegroupe());
        System.out.println(groupe.getSeanceList().size() + " SIZE");
        seanceList = groupe.getSeanceList();
    }

    public void ff() {
        System.out.println("EEGFGFFF\n");
    }

    public void programme() {

        //Cette variable contienra les patients quil faut programmer
        /*List<Patient> patientProg=new ArrayList<>();
        
        //Recuperons les patient du groupe
        List<Patient> patientGrpe=groupe.getPatientList();
        //Recuperons les postes de lunite choisie
        unitedialyse=unitedialyseFacade.find(unitedialyse.getIdunite());
        
        List<Poste> posteUnite=unitedialyse.getPosteList();
        //Cherchons les patients qui npont pas ete programmer pour ce jpour
        System.out.println("SIZE :"+posteUnite.size());
        //Verifions si des branchement existent pour ce meme jour
       
       Connection conn= SingletonConnection.getConnecter();
       
       //recuperons les branchements qui ont ete effectuee a la ate choisie
       
       try{
           Statement st=conn.createStatement();
           branchementList=branchementFacade.findAll();
           SimpleDateFormat sdF=new SimpleDateFormat("dd-MM-yyyy");
//           Date dh1=sdF.parse(branchement.getHeuredebut());
//           Date dh2=sdF.parse(branchement.getHeurefin());
//           SimpleDateFormat sdF2=new SimpleDateFormat("dd/MM/yyyy");
//           Date dprog=sdF2.parse(branchement.getDatebranch());
           //branchement.setDatebranch(sdF.parse(branchement.getDatebranch().toString()));
//            branchement.setHeuredebut(hdeb.getHours()+":"+hdeb.getMinutes()+" min");
//            branchement.setHeurefin(hfin.getHours()+":"+hfin.getMinutes()+" min");
           //Pour 
           System.out.println("FORM");
           if(branchementList.size()>0){
               for(int i=0;i<patientGrpe.size();i++){
                for(int j=0;j<branchementList.size();j++){
                    if(patientGrpe.get(i).getIdpatient()==branchementList.get(j).getIdpatient().getIdpatient() && branchementList.get(j).getDatebranch().equals(branchement.getDatebranch())){
                        System.out.println("PARCOURS ");
                    }else{
                        patientProg.add(patientGrpe.get(j));
                        System.out.println("PARCOURS 2");
                    }
                }
             }
           }else{
               
               patientProg=patientGrpe;
               System.out.println("PARCOURS 3");
           }
           
           
           //Maintenant que nous avons la liste es patient a etre programmer, nous allons les programmer;
           //Verifions sil ya des patients a programmer
           if(patientProg.size()>0){
               System.out.println("PARCOURS 4");
               //Verifions si le nombre de patient ne depassse pas celui e l'unite
               if(patientProg.size()>posteUnite.size()){
                   System.out.println("PARCOURS 5");
                   boolean bb=true;
                   int j=0;
                   while(j<posteUnite.size()){
                       System.out.println("PARCOURS 6");
                       //Verifions si le poste des en bon etat
                       if(posteUnite.get(j).getEtatposte().equalsIgnoreCase("Fonctionnel")){
                           System.out.println("PARCOURS 7");
                           Branchement brr=new Branchement();
                           brr.setIdpatient(patientProg.get(j));
                           brr.setIdposte(posteUnite.get(j));
                           brr.setIdseance(seance);
                            brr.setDatebranch(branchement.getDatebranch());
                           brr.setHeuredebut(branchement.getHeuredebut());
                           brr.setHeurefin(branchement.getHeurefin());
                           System.out.println("PATIENT "+patientProg.get(j).getNompatient()+"\n");
                           branchementAdd.add(brr);
                           System.out.println("PARCOURS 8");
                       }
                       j++;
                       System.out.println("PARCOURS 9");
                   }
                   
               }else{
                   
                   int j=0;
                   while(j<patientProg.size()){
                       //Verifions si le poste des en bon etat
                       if(posteUnite.get(j).getEtatposte().equalsIgnoreCase("Bien")){
                           Branchement brr=new Branchement();
                           brr.setDatebranch(branchement.getDatebranch());
                           brr.setHeuredebut(branchement.getHeuredebut());
                           brr.setHeurefin(branchement.getHeurefin());
                           brr.setIdpatient(patientProg.get(j));
                           brr.setIdposte(posteUnite.get(j));
                            brr.setIdseance(seance);
                           branchementAdd.add(brr);
                       }
                       j++;
                   }
                   
               }
               
               int nombreEnre=0;
               
               for(int k=0;k<branchementAdd.size();k++){
                   System.out.println("ID:"+branchementAdd.get(k).getIdpatient().getIdpatient());
                  
                   try {
                       branchementFacade.create(branchementAdd.get(k));
                       nombreEnre++;

                   } catch (Exception e) {
                       
                       System.out.println("CATCH 1: "+e.toString());
                       jsfu.sendErrorMessage("Erreur!!\n Une erreur est survenue lors de lenregistrement");
                   }
                   
                 // st.executeUpdate(sql);
                   
               }
               // imprimer();
               jsfu.sendInfoMessage("Operation reussit!! \n Nous avons enregistré "+nombreEnre+" personnes ");
               
           }else{
               System.out.println("PARCOURS ELSE");
               jsfu.sendInfoMessage("Il n'y a pas de patient a programmer ce jour pour ce groupe");
               
           }
           
       }catch(Exception xe){
           
           System.out.println("CATCH 2 :"+xe.getLocalizedMessage());
           jsfu.sendErrorMessage("Erreur!\n Veuillez reessayez svp.");
       }
       
       
       //Imprimer le fichier
         */
    }

    public void imprimer() {
        String file = "listeProgramme.pdf";
        //String jasperPath="/Pages/Secreta/impression/new.jrxml";
        String jasperPath = "/Pages/Secreta/impression/patientsProgrammer.jrxml";
        HashMap<String, Object> parametre = new HashMap<>();
        String title = "patientProg";
        String chemin = "/Pages/Secreta/impression/";

        String sql = "SELECT\n"
                + "     branchement.\"idhoraire\" AS branchement_idhoraire,\n"
                + "     branchement.\"idpatient\" AS branchement_idpatient,\n"
                + "     branchement.\"idseance\" AS branchement_idseance,\n"
                + "     branchement.\"idposte\" AS branchement_idposte,\n"
                + "     branchement.\"libellebranchement\" AS branchement_libellebranchement,\n"
                + "     branchement.\"datebranch\" AS branchement_datebranch,\n"
                + "     branchement.\"etatbranch\" AS branchement_etatbranch,\n"
                + "     branchement.\"commentairemed\" AS branchement_commentairemed,\n"
                + "     branchement.\"prescription\" AS branchement_prescripti\"     branchement.\\\"hospitalise\\\" AS branchement_hospitalise,\\n\" +\n"
                + "on,\n"
                + "     branchement.\"heuredebut\" AS branchement_heuredebut,\n"
                + "     branchement.\"heurefin\" AS branchement_heurefin,\n"
                + "     branchement.\"idbranchement\" AS branchement_idbranchement,\n"
                + "     patient.\"idpatient\" AS patient_idpatient,\n"
                + "     patient.\"idgroupe\" AS patient_idgroupe,\n"
                + "     patient.\"idfonction\" AS patient_idfonction,\n"
                + "     patient.\"idlocalite\" AS patient_idlocalite,\n"
                + "     patient.\"nompatient\" AS patient_nompatient,\n"
                + "     patient.\"prenompatient\" AS patient_prenompatient,\n"
                + "     patient.\"adresse\" AS patient_adresse,\n"
                + "     patient.\"telephone1\" AS patient_telephone1,\n"
                + "     patient.\"telephone2\" AS patient_telephone2,\n"
                + "     patient.\"accompagnant\" AS patient_accompagnant,\n"
                + "     patient.\"telephonaccompagnant\" AS patient_telephonaccompagnant,\n"
                + "     patient.\"antecedent\" AS patient_antecedent,\n"
                + "     patient.\"dateentree\" AS patient_dateentree,\n"
                + "     patient.\"nbreseance\" AS patient_nbreseance,\n"
                + "     patient.\"etatsante\" AS patient_etatsante,\n"
                + "     patient.\"contraintefonction\" AS patient_contraintefonction,\n"
                + "     seance.\"idseance\" AS seance_idseance,\n"
                + "     seance.\"idjour\" AS seance_idjour,\n"
                + "     seance.\"idgroupe\" AS seance_idgroupe,\n"
                + "     seance.\"libelleseance\" AS seance_libelleseance\n"
                + "FROM\n"
                + "     \"public\".\"patient\" patient INNER JOIN \"public\".\"branchement\" branchement ON patient.\"idpatient\" = branchement.\"idpatient\"\n"
                + "     INNER JOIN \"public\".\"seance\" seance ON branchement.\"idseance\" = seance.\"idseance\"";

        try {
            report(parametre, jasperPath, sql, chemin, title, SingletonConnection.getConnecter());
        } catch (IOException ex) {
            Logger.getLogger(BranchementController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("EE :" + ex.getMessage());

        } catch (JRException ex) {
            Logger.getLogger(BranchementController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("EE2 :" + ex.getMessage());
        }
        init();
    }

    /**
     * Cette methode permet d'imprimer la liste des dialses d'aujourdhui
     */
    public void imprimerOption() {

        String file = "patientAvecPostes.pdf";
        //String jasperPath="/Pages/Secreta/impression/new.jrxml";
        String jasperPath = "/Pages/Secreta/impression/patientsAvecPoste.jrxml";
        HashMap<String, Object> parametre = new HashMap<>();
        String title = "patientProg";
        String chemin = "/Pages/Secreta/impression/";

        String sql = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("DATE :" + sdf.format(new Date()));
        //Verifions que ces la cases des postes qui a ete selectionnees et lunite nom
//        if (impPoste == true && impDialise == false) {
//            sql = "select b.datebranch, po.libelleposte,p.nompatient,p.prenompatient,p.telephone1,p.accompagnant,p.telephonaccompagnant from branchement b,patient p,poste po where po.idposte=b.idposte and b.idpatient=p.idpatient and b.datebranch= '" + sdf.format(new Date()) + "'";
//            jasperPath = "/Pages/Secreta/impression/patientsAvecPoste.jrxml";
//        }
//        //Verifions si le poste et lunite ont ete selectionnees
//        if (impPoste == true && impDialise == true) {
//            sql = "select from branchement b,patient p,poste po,unitedialyse u where po.idposte=b.idposte and b.idpatient=p.idpatient and u.idunite=po.idunite and b.datebranch='" + sdf.format(new Date()) + "'";
//            jasperPath = "/Pages/Secreta/impression/patientsAvecPosteUnite.jrxml";
//        }
//        //Unite de dialyse selectionne et pas le poste
//        if (impPoste == false && impDialise == true) {
//            sql = "select from branchement b,patient p,poste po,unitedialyse u where po.idposte=b.idposte and b.idpatient=p.idpatient and u.idunite=po.idunite and b.datebranch='" + sdf.format(new Date()) + "'";
//            jasperPath = "/Pages/Secreta/impression/patientsAvecUnite.jrxml";
//        }
        //Aunuc na ete selectionnee
       // if (impPoste == false && impDialise == false) {
            sql = "select *,row_number() over() as numero from branchement b,patient p,seance s,unitedialyse u where b.idpatient=p.idpatient and s.idunite=u.idunite and s.idseance=b.idseance and b.fg_passe='P' and b.datebranch='" + sdf.format(new Date()) + "'";
            jasperPath = "/Pages/Secreta/impression/patientsSPosSUni.jrxml";

       // }
        try {
            report(parametre, jasperPath, sql, chemin, title, SingletonConnection.getConnecter());
        } catch (IOException ex) {
            Logger.getLogger(BranchementController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("EE :" + ex.getMessage());

        } catch (JRException ex) {
            Logger.getLogger(BranchementController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("EE2 :" + ex.getMessage());
        }
        init();

    }

    /**
     * Cette methode imprime la liste des dialyses sur une periode donnees
     */
    public void imprimerPeriode() {

        String file = "patientAvecPostes.pdf";
        //String jasperPath="/Pages/Secreta/impression/new.jrxml";
        String jasperPath = "/Pages/Secreta/impression/patientsAvecPoste.jrxml";
        HashMap<String, Object> parametre = new HashMap<>();
        String title = "patientProg";
        String chemin = "/Pages/Secreta/impression/";

        String sql = "";
        System.out.println("POSTE :" + impPoste);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("DATE :" + sdf.format(new Date()));
        //Verifions que ces la cases des postes qui a ete selectionnees et lunite nom
//        if (impPoste == true && impDialise == false) {
//            System.out.println("ESSAI");
//            System.out.println("DEBUT:" + sdf.format(hdeb));
//            System.out.println("FIN :" + sdf.format(hfin));
//            sql = "select b.datebranch, po.libelleposte,p.nompatient,p.prenompatient,p.telephone1,p.accompagnant,p.telephonaccompagnant from branchement b,patient p,poste po where po.idposte=b.idposte and b.idpatient=p.idpatient and b.datebranch between '" + sdf.format(hdeb) + "' and '" + sdf.format(hfin) + "'";
//            jasperPath = "/Pages/Secreta/impression/patientsAvecPoste.jrxml";
//        }
//        //Verifions si le poste et lunite ont ete selectionnees
//        if (impPoste == true && impDialise == true) {
//            sql = "select b.datebranch, po.libelleposte,p.nompatient,p.prenompatient,u.libelleunite,p.telephone1,p.accompagnant,p.telephonaccompagnant from branchement b,patient p,poste po,unitedialyse u where po.idposte=b.idposte and b.idpatient=p.idpatient and u.idunite=po.idunite and b.datebranch between '" + sdf.format(hdeb) + "' and '" + sdf.format(hfin) + "'";
//            jasperPath = "/Pages/Secreta/impression/patientsAvecPosteUnite.jrxml";
//        }
//        //Unite de dialyse selectionne et pas le poste
//        if (impPoste == false && impDialise == true) {
//            sql = "select b.datebranch, po.libelleposte,p.nompatient,p.prenompatient,p.telephone1,p.accompagnant,p.telephonaccompagnant, u.libelleunite from branchement b,patient p,poste po,unitedialyse u where po.idposte=b.idposte and b.idpatient=p.idpatient and u.idunite=po.idunite and b.datebranch between '" + sdf.format(hdeb) + "' and '" + sdf.format(hfin) + "'";
//            jasperPath = "/Pages/Secreta/impression/patientsAvecUnite.jrxml";
//        }
        //Aunuc na ete selectionnee
        //if (impPoste == false && impDialise == false) {
            parametre.put("datedeb", sdf.format(hdeb));
            parametre.put("datefin", sdf.format(hfin));
            sql = "select *,row_number() over() as numero from branchement b,patient p,seance s,unitedialyse u where b.idpatient=p.idpatient and s.idunite=u.idunite and s.idseance=b.idseance and b.fg_passe='P' and b.datebranch between '" + sdf.format(hdeb) + "' and '" + sdf.format(hfin) + "'";
            jasperPath = "/Pages/Secreta/impression/patientsSPosSUni.jrxml";

       // }
        try {
            report(parametre, jasperPath, sql, chemin, title, SingletonConnection.getConnecter());
        } catch (IOException ex) {
            Logger.getLogger(BranchementController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("EE :" + ex.getMessage());

        } catch (JRException ex) {
            Logger.getLogger(BranchementController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("EE2 :" + ex.getMessage());
        } catch (Exception e) {
            System.out.println("SDDSD :" + e.getLocalizedMessage());
        }
        init();
    }

    public void detailBranchementClic() {

        if (branchementSelected[0] == null && branchementSelected[1] == null) {
            System.out.println("NULL");
        } else {
            this.detailBranchement = branchementFacade.getBranchementByDateAndLibelle(branchementSelected);
            System.out.println("TAILLE :" + detailBranchement.size());
        }

        System.out.println("OBJECT: " + branchementSelected[0] + " VV :" + branchementSelected[1]);
    }

    //Cette methode permettra de trouve des dialyses qui nont pas ete programmees a une date donnees
    public List<Patient> getPatientBranchemeent(Branchement br) {

        try {
            return branchementFacade.patientNonProgramm(br);
        } catch (Exception e) {
            System.out.println("Message :" + e.getMessage());
            return null;
        }
    }

    public void changerPatient(RowEditEvent event) {

        try {
            Branchement bb = (Branchement) event.getObject();
            System.out.println("IDP:" + bb.getIdpatient().getIdpatient());

            branchementFacade.mettreAjourBranchement(bb);
            // branchementFacade.edit(bb);
            jsfu.sendInfoMessage("Operation reussie!!");
        } catch (Exception e) {
            jsfu.sendErrorMessage("Opération échouée!!");
        }
        init();
    }

    public void valider(Branchement b) {
        //System.out.println("A VAL :"+branchement.getCommentairemed());
        try {
            b.setEtatbranch("Valider");
            System.out.println("A VAL :" + b.getCommentairemed() + " :" + b.getIdbranchement());
            b.setCommentairemed(commentaire);
            b.setPrescription(prescription);
            b.setHospitalise(hospitaliser);
            branchementFacade.edit(b);
            jsfu.sendInfoMessage("Opération reussit!!!");
        } catch (Exception e) {
            System.out.println("MESSAGE:" + e.toString());
            jsfu.sendErrorMessage("Opération échouée!!");
        }
        init();
    }

    public void programmeSemiAuto() {
        Connection c = SingletonConnection.getConnecter();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
           // if (seance.getIdseance() == 0) {
                CallableStatement fonc = c.prepareCall("call branchergroupe(?,?,?)");
                fonc.setInt(1, groupe.getIdgroupe());
                fonc.setDate(2, java.sql.Date.valueOf(sdf.format(branchement.getDatebranch())));
                fonc.setDate(3, java.sql.Date.valueOf(sdf.format(datefinbr)));
                fonc.execute();
                System.out.println("ok:1");
                jsfu.sendInfoMessage("Proggrammation reussie!!!");
            //} else {
//                CallableStatement fonc = c.prepareCall("call branchergroupe(?,?,?)");
//                fonc.setInt(1, groupe.getIdgroupe());
//                //fonc.setInt(2, seance.getIdseance());
//                fonc.setDate(2, java.sql.Date.valueOf(sdf.format(branchement.getDatebranch())));
//                fonc.setDate(3, java.sql.Date.valueOf(sdf.format(datefinbr)));
//                fonc.execute();
//                System.out.println("ok:2");
               // jsfu.sendInfoMessage("Proggrammation reussie!!!");
           // }

        } catch (Exception e) {
            System.out.println("Erreur :" + e);
        }
        init();
    }

    public void programmeAuto() {
        Connection c = SingletonConnection.getConnecter();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            CallableStatement fonc = c.prepareCall("call brancherauto(?,?)");

            fonc.setDate(1, java.sql.Date.valueOf(sdf.format(branchement.getDatebranch())));
            fonc.setDate(2, java.sql.Date.valueOf(sdf.format(datefinbr)));
            fonc.execute();
            System.out.println("ok:1");
            jsfu.sendInfoMessage("Proggrammation reussie!!!");
        } catch (Exception e) {
            jsfu.sendErrorMessage("Erreur veuillez reessayer plus tard");
        }
        init();
    }

    public void reprogrammerPatient() {
        try {
            
            System.out.println("Bonjour :");
            
           
            Branchement br= new Branchement();
            br=this.branchementReprogrammer;
            System.out.println("Rebrqnchement :"+this.branchementReprogrammer.getIdbranchement());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdH = new SimpleDateFormat("HH:mm");
            System.out.println(datefinbr);
            System.out.println("Heure debut"+sdH.format(hdeb).toString());
            System.out.println("Heure debut"+sdH.format(hfin).toString());
             br.setDatebranch(datefinbr);
             br.setHeuredebut(sdH.format(hdeb));
             br.setHeurefin(sdH.format(hfin));
            br.setFg_passe("P");
            branchementFacade.edit(br);
            jsfu.sendInfoMessage("Reprogrammation reussie!!");
        } catch (Exception e) {
            System.out.println("ERREUR :" + e.getLocalizedMessage());
            jsfu.sendErrorMessage("Reprogrammation échouée.Veuillez réessayer svp");
        }
        init();
    }

    public void reporterProgrammer(Branchement br) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdH = new SimpleDateFormat("HH:mm");
        try {
//             br.setDatebranch(datefinbr);
//             br.setHeuredebut(sdH.format(hdeb));
//             br.setHeurefin(sdH.format(hfin));
            br.setFg_passe("R");
            branchementFacade.edit(br);
            jsfu.sendInfoMessage("Report reussi!!");
        } catch (Exception e) {
            System.out.println("ERREUR :" + e.getLocalizedMessage());
            jsfu.sendErrorMessage("Report échoué.Veuillez réessayer svp");
        }
        init();
    }

    public void branchementManuel() {
        try {
            branchementManu.setIdpatient(selectedPatientMan);
            branchementFacade.create(branchementManu);
            jsfu.sendInfoMessage("Branchement de " + selectedPatientMan.getNompatient() + " " + selectedPatientMan.getPrenompatient() + " a été effectué avec success!!");
        } catch (Exception e) {
            System.out.println("ERREUR :" + e.getLocalizedMessage());
            jsfu.sendErrorMessage("Branchement de " + selectedPatientMan.getNompatient() + " " + selectedPatientMan.getPrenompatient() + " échoué!! Veuillez reessayer svp.");
        }
        init();
    }
    
    
}
