/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import entities.Unitedialyse;
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
@Named("UniteDialyseController")
@SessionScoped
public class UniteDialiseController extends AbstractBean implements Serializable{
    
    private Unitedialyse unitedialyse=new Unitedialyse();
    
    private List<String> categorieListe=new ArrayList<>();
    
    private Unitedialyse selectedUnite=new Unitedialyse();
    
    private JSFUtils jsfu=new JSFUtils();
    
    
    private List<Unitedialyse> unitedialyseList=new ArrayList<>();
    
    
    @Inject
    UnitedialyseFacade unitedialyseFacade;
    
    @PostConstruct
    public void init(){
        categorieListe=new ArrayList<>();
        categorieListe.add("Petite dialyse");
        categorieListe.add("Grande dialyse");
        
        unitedialyseList=unitedialyseFacade.findAll();
    }

    public Unitedialyse getUnitedialyse() {
        return unitedialyse;
    }

    public void setUnitedialyse(Unitedialyse unitedialyse) {
        this.unitedialyse = unitedialyse;
    }

    public Unitedialyse getSelectedUnite() {
        return selectedUnite;
    }

    public void setSelectedUnite(Unitedialyse selectedUnite) {
        this.selectedUnite = selectedUnite;
    }

    public List<Unitedialyse> getUnitedialyseList() {
        return unitedialyseList;
    }

    public void setUnitedialyseList(List<Unitedialyse> unitedialyseList) {
        this.unitedialyseList = unitedialyseList;
    }

    public List<String> getCategorieListe() {
        return categorieListe;
    }

    public void setCategorieListe(List<String> categorieListe) {
        this.categorieListe = categorieListe;
    }
    
    
    
    
    
    public void enregistrerUnite(){
        
        try {
            unitedialyseFacade.create(unitedialyse);
            
            unitedialyse=new Unitedialyse();
            jsfu.sendInfoMessage("Enregistrement reussit");
            unitedialyse=new Unitedialyse();
        } catch (Exception e) {
            jsfu.sendErrorMessage("Enregistrement echouee");
        }
        init();
    }
    
    
    public void supprimerEnregistrement(){
        
        try {
            unitedialyseFacade.remove(selectedUnite);
            selectedUnite=new Unitedialyse();
            jsfu.sendInfoMessage("Suppression reussit!!");
        } catch (Exception e) {
            
            jsfu.sendErrorMessage("Suppression echouee!!");
        }
        
        init();
    }
    
    
    public void editUnite(RowEditEvent event){
        
        Unitedialyse udia=(Unitedialyse)event.getObject();
        try {
            unitedialyseFacade.edit(udia);
            jsfu.sendInfoMessage("Edition reussit!!!");
        } catch (Exception e) {
            jsfu.sendErrorMessage("Edition echouee!!");
        }
        init();
        
        
    }
    
    public void cancelEdit(RowEditEvent event){
        jsfu.sendInfoMessage("Operation annulee");
    }
    
    
}
