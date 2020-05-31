/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import entities.Patient;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author HP-FOLIO
 */
@Stateless
public class PatientFacade extends AbstractFacade<Patient> {

    @PersistenceContext(unitName = "LSDPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PatientFacade() {
        super(Patient.class);
    }
    
    
    public List<Patient> patientListActu(){
        
       // String sql="SELECT * FROM public.patient p";
        List<Integer> idP=(List<Integer>)em.createNativeQuery("select idpatient from patient where fg_sortie is false").getResultList();
        List<Patient> p=new ArrayList<>();
        for(Integer id:idP){
            Patient d=(Patient)em.find(Patient.class, id);
            p.add(d);
        }
        return p;
    }
    
    public List<Patient> patientListSortie(){
        String sql="select idpatient from patient where fg_sortie is true";
        List<Integer> idP=(List<Integer>)em.createNativeQuery(sql).getResultList();
        List<Patient> p=new ArrayList<>();
        for(Integer id:idP){
            Patient d=(Patient)em.find(Patient.class, id);
            p.add(d);
        }
        return p;
    }
}
