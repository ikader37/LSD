/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import controlers.UtilisateurController;
import controlers.AbstractBean;
import entities.Profil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import models.ProfilFacade;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author ZORE
 */
@Named(value = "profileController")
@SessionScoped
public class ProfileController extends AbstractBean implements Serializable {

    /**
     * Creates a new instance of ProfileController
     */
    private Profil profile = new Profil();
    private Profil selectedProfil;
    private List<Profil> filteredProfiles;
    @Inject
    private ProfilFacade facade;
    private List<Profil> liste = null;

    public ProfileController() {
    }

    public void create() {
        try {
            profile.setCreele(UtilisateurController.date());
            profile.setCreepar(UtilisateurController.getUserConnecte().getLoginuser());
            facade.create(profile);
            profile=new Profil();
            displayInfoMessage("profile inseré");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            displayInfoMessage("profile non inseré");
        }finally{
            init();
        }
    }

    public void delete(Profil p){
        try {
            facade.remove(p);
            displayInfoMessage("suppression effectuée avec succès");
        } catch (Exception e) {
            displayErrorMessage("erreur de suppression");
        }
        init();
    }
     public void update(RowEditEvent event){
        try {
            facade.edit((Profil) event.getObject());
            displayInfoMessage("profil édité "+((Profil) event.getObject()).getIdprofil());
        } catch (Exception e) {
            displayErrorMessage("profil non édité "+((Profil) event.getObject()).getIdprofil());
        }
        init();
    }
     public void cancelled(){
         displayInfoMessage("vous avez annulez");
     }
    @PostConstruct
    public void init() {
        liste = facade.findAll();
    }

    public Profil getProfile() {
        return profile;
    }

    public List<Profil> getListe() {
        return liste;
    }

    public List<Profil> getFilteredProfiles() {
        return filteredProfiles;
    }

    public Profil getSelectedProfil() {
        return selectedProfil;
    }

    public void setSelectedProfil(Profil selectedProfil) {
        this.selectedProfil = selectedProfil;
    }

}
