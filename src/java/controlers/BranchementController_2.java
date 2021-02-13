/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import entities.Branchement;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import models.BranchementFacade;
import utilities.JSFUtils;

/**
 *
 * @author HP
 */
@SessionScoped
@Named("BranchementController_2")
public class BranchementController_2 extends AbstractBean implements Serializable {
    private List<Branchement> branchementReporter = new ArrayList<>();
     private Date hdeb;
    private Date hfin;
    private Date datefinbr;
    
    private List<Branchement> branchementNonReporter = new ArrayList<>();
     private Branchement branchementReprogrammer ;
     private JSFUtils jsfu = new JSFUtils();

    public List<Branchement> getBranchementReporter() {
        return branchementReporter;
    }

    public void setBranchementReporter(List<Branchement> branchementReporter) {
        this.branchementReporter = branchementReporter;
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

    public Date getDatefinbr() {
        return datefinbr;
    }

    public void setDatefinbr(Date datefinbr) {
        this.datefinbr = datefinbr;
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
     
     
     
     
     
    @Inject
    BranchementFacade branchementFacade;
    
    @PostConstruct
    public void init(){
        branchementNonReporter = branchementFacade.listBranchementNonReport();
        
        branchementReporter = branchementFacade.listBranchementReport();
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
    
}
