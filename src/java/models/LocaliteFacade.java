/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import entities.Localite;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author HP-FOLIO
 */
@Stateless
public class LocaliteFacade extends AbstractFacade<Localite> {

    @PersistenceContext(unitName = "LSDPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LocaliteFacade() {
        super(Localite.class);
    }
    
}
