package entities;

import entities.Seance;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2020-05-31T22:00:50")
@StaticMetamodel(Jour.class)
public class Jour_ { 

    public static volatile SingularAttribute<Jour, Integer> idjour;
    public static volatile SingularAttribute<Jour, String> libellejour;
    public static volatile ListAttribute<Jour, Seance> seanceList;

}