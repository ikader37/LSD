/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import java.util.*;
import entities.*;
import java.io.Serializable;
import java.sql.SQLException;
import utilities.*;
import models.*;
import javax.annotation.*;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.*;
import org.primefaces.event.RowEditEvent;



/**
 *
 * @author ilboudo
 */
@Named("SeanceController")
@SessionScoped
public class SeanceController extends AbstractBean implements Serializable {
    
    
    private Seance seance=new Seance();
    private List<Seance> seanceList=new ArrayList<>();
    private Seance selectedSeance=new Seance();
    private Date heuredeb;
    private Date heurefin;
    private JSFUtils jsfu=new JSFUtils();
    private List<String> idSelectedGroupeId=new ArrayList<>();
    
    
    
    @Inject
    SeanceFacade seanceFacade;
    @Inject
    GroupeFacade groupeFacade;
    
    @PostConstruct
    public void init(){
        seanceList=seanceFacade.findAll();
       
    }

    public Seance getSeance() {
        return seance;
    }

    public void setSeance(Seance seance) {
        this.seance = seance;
    }

    public List<Seance> getSeanceList() {
        return seanceList;
    }

    public void setSeanceList(List<Seance> seanceList) {
        this.seanceList = seanceList;
    }

    public Seance getSelectedSeance() {
        return selectedSeance;
    }

    public void setSelectedSeance(Seance selectedSeance) {
        this.selectedSeance = selectedSeance;
    }

    public Date getHeuredeb() {
        return heuredeb;
    }

    public void setHeuredeb(Date heuredeb) {
        this.heuredeb = heuredeb;
    }

    public Date getHeurefin() {
        return heurefin;
    }

    public void setHeurefin(Date heurefin) {
        this.heurefin = heurefin;
    }

    public List<String> getIdSelectedGroupeId() {
        return idSelectedGroupeId;
    }

    public void setIdSelectedGroupeId(List<String> idSelectedGroupeId) {
        this.idSelectedGroupeId = idSelectedGroupeId;
    }
    
    
    
    
    
    public void enregistrerSeance(){
        
        seance.setHeuredebut(heuredeb.getHours()+":"+heuredeb.getMinutes());
        seance.setHeurefin(heurefin.getHours()+":"+heurefin.getMinutes());
        try {
           
             System.out.println("TAILLE:"+idSelectedGroupeId.size());
             List<Groupe> lgp=new ArrayList<>();
             
             List<Seance> ls=new ArrayList<>();
             ls.add(seance);
             for(int i=0;i<idSelectedGroupeId.size();i++){
                 Groupe g=groupeFacade.find(new Integer(idSelectedGroupeId.get(i)));
                 lgp.add(g);
                //groupeFacade.enregistrerGroupeSeance(g, seance);
             }
             seance.setGroupeList(lgp);
             seanceFacade.create(seance);
            
//            for(Integer p:idSelectedGroupeId){
//                Groupe g=groupeFacade.find(p);
//                
//                List<Seance> seanc=new   ArrayList<>();
//                seanc.add(seance);
//                g.setSeanceList(seanc);
//             //   groupeFacade.edit(g);
//            }
//            
            jsfu.sendInfoMessage("Enregistrement reussit!!");
            heuredeb=null;
            heurefin=null;
            seance=new Seance();
            idSelectedGroupeId=new ArrayList<>();
            
        } catch (Exception e) {
            System.out.println("MESSAGE :"+e.getLocalizedMessage());
            jsfu.sendErrorMessage("Enregistrement d'une seance a echoué.");
        }
        init();
    }
    
    public void supprimerSeance(){
        
        try {
            seanceFacade.remove(selectedSeance);
            jsfu.sendInfoMessage("Suppression reussit!!!");
            selectedSeance=new Seance();
        } catch (Exception e) {
            jsfu.sendErrorMessage("Suppression echouee!!");
        }
        
        init();
    }
    
    public void editSeance(RowEditEvent event){
        Seance sea=(Seance)event.getObject();
        try {
            seanceFacade.edit(sea);
            jsfu.sendInfoMessage("Edition reussit!!");
        } catch (Exception e) {
            jsfu.sendErrorMessage("Edition echouee!!");
        }
        init();
    }
    
    public void cancelEdit(){
        jsfu.sendInfoMessage("Operation annulee!!");
    }
}
