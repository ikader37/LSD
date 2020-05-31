/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import entities.Groupe;
import entities.Seance;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author HP-FOLIO
 */
@Stateless
public class GroupeFacade extends AbstractFacade<Groupe> {

    @PersistenceContext(unitName = "LSDPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GroupeFacade() {
        super(Groupe.class);
    }
    
    
    public void enregistrerGroupeSeance(Groupe groupe,Seance seance){
        
        String sql="insert into seancegroupe (idgroupe,idseance) values(:idgroupe,:idseance)";
        
        em.createQuery(sql).setParameter("idgroupe", groupe.getIdgroupe()).setParameter("idseance", seance.getIdseance()).executeUpdate();
        
    }
    
}
