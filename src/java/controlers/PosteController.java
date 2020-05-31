/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import java.util.*;
import entities.*;
import java.io.Serializable;
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
@Named("PosteController")
@SessionScoped
public class PosteController extends AbstractBean implements Serializable {
    
    private Poste poste=new Poste();
    private Poste selectedPoste=new Poste();
    private List<Poste> posteList=new ArrayList<>();
    private JSFUtils jsfu=new JSFUtils();
    
    
    @Inject
    PosteFacade posteFacade;
    
    @PostConstruct
    public void init(){
        posteList=posteFacade.findAll();
    }

    public Poste getPoste() {
        return poste;
    }

    public void setPoste(Poste poste) {
        this.poste = poste;
    }

    public Poste getSelectedPoste() {
        return selectedPoste;
    }

    public void setSelectedPoste(Poste selectedPoste) {
        this.selectedPoste = selectedPoste;
    }

    public List<Poste> getPosteList() {
        return posteList;
    }

    public void setPosteList(List<Poste> posteList) {
        this.posteList = posteList;
    }
    
    
    
    
    
    public void enregistrerPost(){
        try {
            posteFacade.create(poste);
            poste=new Poste();
            jsfu.sendInfoMessage("Enregistrement reussit!!!");
        } catch (Exception e) {
            jsfu.sendErrorMessage("Enregistrement echouee!!");
        }
        init();
    }
    
    public void supprimerPoste(){
        
        try {
            posteFacade.remove(selectedPoste);
            selectedPoste=new Poste();
            jsfu.sendInfoMessage("Suppression reussit!!");
            
        } catch (Exception e) {
            
            jsfu.sendErrorMessage("Suppression echouee");
        }
        init();
    }
    
    public void editPoste(RowEditEvent event){
        Poste pste=(Poste)event.getObject();
        try {
            posteFacade.edit(pste);
            System.out.print("  "+pste.getIdunite().getLibelleunite());
            jsfu.sendInfoMessage("Edition reussit!!!");
        } catch (Exception e) {
            jsfu.sendErrorMessage("Edition echouee!!");
        }
        init();
    }
    
    public void cancelEdit(){
        jsfu.sendInfoMessage("Operation echouee!!!");
    }
    
}
