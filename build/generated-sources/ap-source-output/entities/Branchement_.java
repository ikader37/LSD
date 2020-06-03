package entities;

import entities.Horaire;
import entities.Patient;
import entities.Poste;
import entities.Seance;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2020-05-31T22:00:50")
@StaticMetamodel(Branchement.class)
public class Branchement_ { 

    public static volatile SingularAttribute<Branchement, Horaire> idhoraire;
    public static volatile SingularAttribute<Branchement, String> etatbranch;
    public static volatile SingularAttribute<Branchement, String> heurefin;
    public static volatile SingularAttribute<Branchement, Boolean> hospitalise;
    public static volatile SingularAttribute<Branchement, Patient> idpatient;
    public static volatile SingularAttribute<Branchement, String> commentairemed;
    public static volatile SingularAttribute<Branchement, Integer> idbranchement;
    public static volatile SingularAttribute<Branchement, String> prescription;
    public static volatile SingularAttribute<Branchement, Seance> idseance;
    public static volatile SingularAttribute<Branchement, String> fg_passe;
    public static volatile SingularAttribute<Branchement, String> heuredebut;
    public static volatile SingularAttribute<Branchement, Date> datebranch;
    public static volatile SingularAttribute<Branchement, Poste> idposte;
    public static volatile SingularAttribute<Branchement, String> libellebranchement;

}