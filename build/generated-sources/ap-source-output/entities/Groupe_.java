package entities;

import entities.Patient;
import entities.Seance;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2020-07-23T22:02:43")
@StaticMetamodel(Groupe.class)
public class Groupe_ { 

    public static volatile SingularAttribute<Groupe, String> libellegroupe;
    public static volatile SingularAttribute<Groupe, Integer> intval_jour;
    public static volatile ListAttribute<Groupe, Patient> patientList;
    public static volatile SingularAttribute<Groupe, Integer> taille;
    public static volatile SingularAttribute<Groupe, Integer> idgroupe;
    public static volatile SingularAttribute<Groupe, Integer> nbseancesem;
    public static volatile SingularAttribute<Groupe, Boolean> fixe;
    public static volatile ListAttribute<Groupe, Seance> seanceList;

}