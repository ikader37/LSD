package entities;

import entities.Branchement;
import entities.Fonction;
import entities.Groupe;
import entities.Localite;
import entities.Seance;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2020-05-31T22:00:50")
@StaticMetamodel(Patient.class)
public class Patient_ { 

    public static volatile SingularAttribute<Patient, String> antecedent;
    public static volatile ListAttribute<Patient, Branchement> branchementCollection;
    public static volatile SingularAttribute<Patient, String> accompagnant;
    public static volatile SingularAttribute<Patient, Date> datesortie;
    public static volatile SingularAttribute<Patient, String> telephone2;
    public static volatile SingularAttribute<Patient, Fonction> idfonction;
    public static volatile SingularAttribute<Patient, Groupe> idgroupe;
    public static volatile SingularAttribute<Patient, String> telephone1;
    public static volatile SingularAttribute<Patient, String> telephonaccompagnant;
    public static volatile SingularAttribute<Patient, String> sexe;
    public static volatile SingularAttribute<Patient, String> prenompatient;
    public static volatile SingularAttribute<Patient, Date> dateentree;
    public static volatile SingularAttribute<Patient, Localite> idlocalite;
    public static volatile SingularAttribute<Patient, String> contraintefonction;
    public static volatile SingularAttribute<Patient, String> cause_sortie;
    public static volatile SingularAttribute<Patient, Integer> idpatient;
    public static volatile SingularAttribute<Patient, String> etatsante;
    public static volatile SingularAttribute<Patient, Boolean> fgSortie;
    public static volatile SingularAttribute<Patient, String> nompatient;
    public static volatile SingularAttribute<Patient, String> adresse;
    public static volatile SingularAttribute<Patient, Integer> nbreseance;
    public static volatile ListAttribute<Patient, Seance> seanceList;

}