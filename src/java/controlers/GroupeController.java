/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import entities.*;
import java.io.Serializable;
import javax.enterprise.context.*;
import javax.inject.Inject;
import javax.inject.Named;
import models.*;
import java.util.*;
import javax.annotation.PostConstruct;
import org.primefaces.event.RowEditEvent;
import utilities.JSFUtils;

/**
 *
 * @author ilboudo
 */
@Named(value = "GroupeController")
@SessionScoped
public class GroupeController extends AbstractBean implements Serializable {
    
    
    
    @Inject
    GroupeFacade groupeFacade;
    
    JSFUtils jsfu=new JSFUtils();
    
    private Groupe groupe=new Groupe();
    
    private Groupe groupeSelected=new Groupe();
    
    private List<Groupe> groupeList=new ArrayList<>();

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public List<Groupe> getGroupeList() {
        return groupeList;
    }

    public void setGroupeList(List<Groupe> groupeList) {
        this.groupeList = groupeList;
    }

    public Groupe getGroupeSelected() {
        return groupeSelected;
    }

    public void setGroupeSelected(Groupe groupeSelected) {
        this.groupeSelected = groupeSelected;
    }
    
    
    
    @PostConstruct
    public void init(){
        
        groupeList=groupeFacade.findAll();
    }
    
    
    public void enregistrerGroupe(){
        try{
            
            groupe.setIdgroupe(groupeList.size()+1);
            groupeFacade.create(groupe);
            jsfu.sendInfoMessage("Enregistrement reussit!!!");
            groupe=new Groupe();
            
        }catch(Exception ex){
            jsfu.sendErrorMessage("Erreur!!!\n Veeuillez reessayer svp!");
        }
        
        init();
    }
    
    
    public void supprimerGroupe(){
        try {
            groupeFacade.remove(groupeSelected);
            groupeSelected=new Groupe();
            jsfu.sendInfoMessage("Suppression reussit!!");
        } catch (Exception e) {
            jsfu.sendErrorMessage("Suppression echouee!!");
        }
        init();
    }
    
    public void editGroup(RowEditEvent event){
        Groupe grpe= ((Groupe) event.getObject());
        try {
            groupeFacade.edit(grpe);
            jsfu.sendInfoMessage("Edition reussit!!!");
        } catch (Exception e) {
            jsfu.sendErrorMessage("Edition echouee!! Veuillez reessayer!!");
        }
        init();
    }
    
    public void cancelEdit(RowEditEvent event){
        jsfu.sendInfoMessage("Operation annulee!!");
    }
    
}
