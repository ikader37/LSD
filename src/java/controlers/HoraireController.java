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

/**
 *
 * @author ilboudo
 */
@Named("HoraireController")
@SessionScoped
public class HoraireController extends AbstractBean implements Serializable {
    
    
    private Horaire horaire=new Horaire();
    private Jour jour=new Jour();
    private Horaire selectedHoraire =new Horaire();
    private Jour selectedJour=new Jour();
    
    private List<Horaire> horaireList=new ArrayList<>();
    private List<Jour> jourList=new ArrayList<>();
    
    @Inject
    HoraireFacade horaireFacade;
    @Inject
    JourFacade jourFacade;
    
    @PostConstruct
    public void init(){
        
        jourList=jourFacade.findAll();
        horaireList=horaireFacade.findAll();
        
    }

    public Horaire getHoraire() {
        return horaire;
    }

    public void setHoraire(Horaire horaire) {
        this.horaire = horaire;
    }

    public Jour getJour() {
        return jour;
    }

    public void setJour(Jour jour) {
        this.jour = jour;
    }

    public Horaire getSelectedHoraire() {
        return selectedHoraire;
    }

    public void setSelectedHoraire(Horaire selectedHoraire) {
        this.selectedHoraire = selectedHoraire;
    }

    public Jour getSelectedJour() {
        return selectedJour;
    }

    public void setSelectedJour(Jour selectedJour) {
        this.selectedJour = selectedJour;
    }

    public List<Horaire> getHoraireList() {
        return horaireList;
    }

    public void setHoraireList(List<Horaire> horaireList) {
        this.horaireList = horaireList;
    }

    public List<Jour> getJourList() {
        return jourList;
    }

    public void setJourList(List<Jour> jourList) {
        this.jourList = jourList;
    }
    
    
    
    public void enregistrerHeur(){
        
    }
    
    public void enregistrerJour(){
        
    }
}
