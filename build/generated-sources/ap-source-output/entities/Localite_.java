package entities;

import entities.Patient;
import entities.Pays;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2020-09-27T23:21:12")
@StaticMetamodel(Localite.class)
public class Localite_ { 

    public static volatile ListAttribute<Localite, Patient> patientList;
    public static volatile SingularAttribute<Localite, Pays> idpays;
    public static volatile SingularAttribute<Localite, Integer> idlocalite;
    public static volatile SingularAttribute<Localite, String> region;
    public static volatile SingularAttribute<Localite, String> libellelocalite;

}