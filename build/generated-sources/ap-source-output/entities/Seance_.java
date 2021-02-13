package entities;

import entities.Branchement;
import entities.Groupe;
import entities.Jour;
import entities.Patient;
import entities.Unitedialyse;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2020-09-27T23:21:12")
@StaticMetamodel(Seance.class)
public class Seance_ { 

    public static volatile ListAttribute<Seance, Branchement> branchementCollection;
    public static volatile SingularAttribute<Seance, String> libelleseance;
    public static volatile SingularAttribute<Seance, Unitedialyse> idunite;
    public static volatile SingularAttribute<Seance, Jour> idjour;
    public static volatile ListAttribute<Seance, Patient> patientList;
    public static volatile SingularAttribute<Seance, Integer> nombre_poste;
    public static volatile ListAttribute<Seance, Groupe> groupeList;
    public static volatile SingularAttribute<Seance, Integer> idseance;
    public static volatile SingularAttribute<Seance, String> heuredebut;
    public static volatile SingularAttribute<Seance, String> heurefin;

}