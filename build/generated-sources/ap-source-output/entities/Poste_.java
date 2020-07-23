package entities;

import entities.Branchement;
import entities.Unitedialyse;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2020-07-23T22:02:43")
@StaticMetamodel(Poste.class)
public class Poste_ { 

    public static volatile ListAttribute<Poste, Branchement> branchementCollection;
    public static volatile SingularAttribute<Poste, Unitedialyse> idunite;
    public static volatile SingularAttribute<Poste, String> libelleposte;
    public static volatile SingularAttribute<Poste, String> etatposte;
    public static volatile SingularAttribute<Poste, Integer> idposte;
    public static volatile SingularAttribute<Poste, String> refconstructeur;

}