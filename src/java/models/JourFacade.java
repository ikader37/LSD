/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import entities.Jour;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author HP-FOLIO
 */
@Stateless
public class JourFacade extends AbstractFacade<Jour> {

    @PersistenceContext(unitName = "LSDPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JourFacade() {
        super(Jour.class);
    }
    
}
