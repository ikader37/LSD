/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;


import entities.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.enterprise.context.*;
import javax.inject.Inject;
import javax.inject.Named;
import models.*;
import java.util.*;
import javax.annotation.PostConstruct;
import org.primefaces.event.RowEditEvent;
import utilities.JSFUtils;
import utilities.SingletonConnection;

/**
 *
 * @author ilboudo
 */


@Named("FonctionCoontroller")
@SessionScoped
public class FonctionController extends AbstractBean implements Serializable {
    
    
    private Fonction fonction=new Fonction();
    
    private Fonction selectedFonction=new Fonction();
    
    
    private List<Fonction> fonctionList=new ArrayList<>();
    private JSFUtils jsfu=new JSFUtils();
    
    
    @Inject
    FonctionFacade  fonctionFacade;
    
    @PostConstruct
    public void init(){
        fonctionList=fonctionFacade.findAll();
        
    }

    public Fonction getFonction() {
        return fonction;
    }

    public void setFonction(Fonction fonction) {
        this.fonction = fonction;
    }

    public Fonction getSelectedFonction() {
        return selectedFonction;
    }

    public void setSelectedFonction(Fonction selectedFonction) {
        this.selectedFonction = selectedFonction;
    }

    public List<Fonction> getFonctionList() {
        return fonctionList;
    }

    public void setFonctionList(List<Fonction> fonctionList) {
        this.fonctionList = fonctionList;
    }
    
    
    
    
    
    public void enregistrerFonction(){
        
        try {
            fonctionFacade.create(fonction);
            fonction=new Fonction();
            jsfu.sendInfoMessage("Enregistrement reussit!!");
            
        } catch (Exception e) {
            
            jsfu.sendErrorMessage("Enregistrement echoue!!");
            System.out.println("ERRREUR :"+e.getLocalizedMessage());
            
        }
        init();
    }
    
    public void supprimerFonction(){
        try {
            fonctionFacade.remove(selectedFonction);
            jsfu.sendInfoMessage("Suuppression reussie");
        } catch (Exception e) {
            jsfu.sendErrorMessage("Suppression echouee!!");
        }
        
        init();
    }
    
    public void editFonction(RowEditEvent event){
        
        Fonction fnc=(Fonction)event.getObject();
        try {
            fonctionFacade.edit(fnc);
            jsfu.sendInfoMessage("Edition reussie!!");
            
        } catch (Exception e) {
            
            jsfu.sendErrorMessage("Edition echouee!!");
            
        }
        init();
    }
    
    public void cancelEdit(RowEditEvent event){
        jsfu.sendErrorMessage("Operation annulee!!");
    }
    
    
     public void ff(){
       jsfu.sendInfoMessage("DGDFGDFGDFGD");
    }
     
     public void tester(){
         System.out.println("sdsdsdsdsdasds");
     }
    
}
