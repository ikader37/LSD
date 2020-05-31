/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import entities.Branchement;
import entities.Patient;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

/**
 *
 * @author ilboudo
 */
@Stateless
public class BranchementFacade extends AbstractFacade<Branchement> {

    @PersistenceContext(unitName = "LSDPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BranchementFacade() {
        super(Branchement.class);
    }
    
    public List<Object[]> getBranchementGrpeByDateLib(){
        String sql="select datebranch,libellebranchement from branchement group by datebranch,libellebranchement;";
       List<Object[]> mm= (List<Object[]>)em.createNativeQuery(sql).getResultList();
        for(Object[] a:mm){
            System.out.println("A:"+a[0]+" B:"+a[1]);
        }
        return mm;
    }
    
    
    public List<Branchement> getBranchementByDateAndLibelle(Object[] e){
     
        String sql="select idbranchement from branchement where datebranch='"+e[0]+"' and libellebranchement = '"+e[1]+"'";
        List<Integer> idBr=(List<Integer>)em.createNativeQuery(sql).getResultList();
        List<Branchement> brList=new ArrayList<>();
        
        for(int i=0;i<idBr.size();i++){
            Branchement br=(Branchement)em.find(Branchement.class, idBr.get(i));
            System.out.println("ID :"+idBr.get(i));
            brList.add(br);
        }
        return brList;
    }
    
    public List<Patient> patientNonProgramm(Branchement data){
         String sql="select p.idpatient from patient p where p.idpatient not in (select b.idpatient from branchement b where b.datebranch ='"+data.getDatebranch()+"' and b.libellebranchement='"+data.getLibellebranchement()+"')";
         List<Integer> idp=(List<Integer>)em.createNativeQuery(sql).getResultList();
         List<Patient> patients=new ArrayList<>();
         for(int i=0;i<idp.size();i++){
             Patient p=em.find(Patient.class, idp.get(i));
             patients.add(p);
         }
         return patients;
     }
    
    public void mettreAjourBranchement(Branchement br){
        
        String sql="update branchement set idpatient ="+br.getIdpatient().getIdpatient()+" where idbranchement="+br.getIdbranchement();
        
        em.createNativeQuery(sql).executeUpdate();
    }
    
    public List<Branchement> listBranchementNonValide(){
        
        String sql="select idbranchement from branchement where etatbranch is null";
        List<Integer> idl=(List<Integer>)em.createNativeQuery(sql).getResultList();
        List<Branchement> brL=new ArrayList<>();
        for(int i=0;i<idl.size();i++){
            System.out.println("NON :"+idl.get(i));
            Branchement br=em.find(Branchement.class, idl.get(i));
            brL.add(br);
        }
        return brL;
    }
    
    public List<Branchement> listBranchementValide(){
        String sql="select idbranchement from branchement where etatbranch is not null";
        List<Integer> idl=(List<Integer>)em.createNativeQuery(sql).getResultList();
        List<Branchement> brL=new ArrayList<>();
        for(int i=0;i<idl.size();i++){
            Branchement br=em.find(Branchement.class, idl.get(i));
            brL.add(br);
        }
        return brL;
    }
    
    public List<Patient> listDesHospitaliser(){
        String sql="select idpatient from branchement where hospitalise is true group by idpatient";
        List<Integer> idl=(List<Integer>)em.createNativeQuery(sql).getResultList();
        List<Patient> brL=new ArrayList<>();
        for(int i=0;i<idl.size();i++){
            Patient br=em.find(Patient.class, idl.get(i));
            brL.add(br);
        }
        return brL;
    }
    
    
    public List<Branchement> listBranchementReport(){
     String sql="select b.idbranchement from branchement b,patient p where p.idpatient =b.idpatient and p.fg_sortie is false and b.fg_passe='R' and b.etatbranch is null";
     
     List<Integer> idB=(List<Integer>)em.createNativeQuery(sql).getResultList();
     List<Branchement> br=new ArrayList<>();
     
     for(int i=0;i<idB.size();i++){
         Branchement b=em.find(Branchement.class, idB.get(i));
         br.add(b);
     }
        return br;
    }
    
    
    public List<Branchement> listBranchementNonReport(){
     String sql="select b.idbranchement from branchement b,patient p where p.idpatient =b.idpatient and p.fg_sortie is false and b.fg_passe='P' and b.etatbranch is null";
     
     List<Integer> idB=(List<Integer>)em.createNativeQuery(sql).getResultList();
     List<Branchement> br=new ArrayList<>();
     
     for(int i=0;i<idB.size();i++){
         Branchement b=em.find(Branchement.class, idB.get(i));
         br.add(b);
     }
        return br;
    }
    
    
    public List<Patient> historiqueBranchement(){
        
        String sql="select distinct(b.idpatient) as idpatient from branchement b,patient p where p.idpatient=b.idpatient";
        List<Patient> pl=new ArrayList<>();
        List<Integer> id=(List<Integer>)em.createNativeQuery(sql).getResultList();
        
        for(Integer i:id){
            Patient p=em.find(Patient.class, i);
            pl.add(p);
        }
        return pl;
    }
}
