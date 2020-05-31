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

@Named("localiteController")
@SessionScoped
public class LocaliteController extends AbstractBean implements Serializable{
    
    private Localite localite=new Localite();
    private List<Localite> localiteList=new ArrayList<>();
    private JSFUtils jsfu=new JSFUtils();
    
    private Localite selectedLocalite=new Localite();
    
    
    @Inject
    LocaliteFacade localiteFacade;
    
    
    @PostConstruct
    public void init(){
        localiteList=localiteFacade.findAll();
    }

    public Localite getLocalite() {
        return localite;
    }

    public void setLocalite(Localite localite) {
        this.localite = localite;
    }

    public List<Localite> getLocaliteList() {
        return localiteList;
    }

    public void setLocaliteList(List<Localite> localiteList) {
        this.localiteList = localiteList;
    }

    public Localite getSelectedLocalite() {
        return selectedLocalite;
    }

    public void setSelectedLocalite(Localite selectedLocalite) {
        this.selectedLocalite = selectedLocalite;
    }
    
    
    public void enregistrerLocalite(){
        
        try {
            localiteFacade.create(localite);
            localite=new Localite();
            jsfu.sendInfoMessage("Enregistrement reussit!!");
            
        } catch (Exception e) {
            
            jsfu.sendErrorMessage("Enregistrement echoue!!");
        }
        init();

    }
    
    
    public void supprimerLocalite(){
        
        try {
            localiteFacade.remove(selectedLocalite);
            jsfu.sendInfoMessage("Suppression reussit!!");
        } catch (Exception e) {
            
            jsfu.sendErrorMessage("Suppression echouee!!");
        }
        init();
        
    }
    
    
    public void cancelEdit(RowEditEvent event){
        
       jsfu.sendInfoMessage("Operation annulee!!");
        
    }
    
    public void editLocalite(RowEditEvent event){
         Localite loc=(Localite)event.getObject();
        try {
            localiteFacade.edit(loc);
            jsfu.sendInfoMessage("Edition reussit!!");
        } catch (Exception e) {
            jsfu.sendErrorMessage("edition echouee!!");
        }
        
    }
    
}
