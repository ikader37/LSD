package entities;

import entities.Localite;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2020-07-23T22:02:43")
@StaticMetamodel(Pays.class)
public class Pays_ { 

    public static volatile SingularAttribute<Pays, Integer> idpays;
    public static volatile SingularAttribute<Pays, String> libellepays;
    public static volatile ListAttribute<Pays, Localite> localiteList;

}