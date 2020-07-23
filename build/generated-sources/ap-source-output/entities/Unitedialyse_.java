package entities;

import entities.Poste;
import entities.Seance;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2020-07-23T22:02:43")
@StaticMetamodel(Unitedialyse.class)
public class Unitedialyse_ { 

    public static volatile SingularAttribute<Unitedialyse, Integer> idunite;
    public static volatile SingularAttribute<Unitedialyse, String> categorie;
    public static volatile SingularAttribute<Unitedialyse, String> libelleunite;
    public static volatile ListAttribute<Unitedialyse, Poste> posteList;
    public static volatile SingularAttribute<Unitedialyse, Integer> nombreposte;
    public static volatile ListAttribute<Unitedialyse, Seance> seanceList;

}