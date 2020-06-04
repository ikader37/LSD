package entities;

import entities.Profil;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2020-06-04T10:10:05")
@StaticMetamodel(Utilisateur.class)
public class Utilisateur_ { 

    public static volatile SingularAttribute<Utilisateur, String> loginuser;
    public static volatile SingularAttribute<Utilisateur, BigInteger> connecte;
    public static volatile SingularAttribute<Utilisateur, Integer> iduser;
    public static volatile SingularAttribute<Utilisateur, String> creepar;
    public static volatile SingularAttribute<Utilisateur, Date> modifiele;
    public static volatile SingularAttribute<Utilisateur, Profil> idprofil;
    public static volatile SingularAttribute<Utilisateur, String> loginpasswd;
    public static volatile SingularAttribute<Utilisateur, Date> derniereconnexion;
    public static volatile SingularAttribute<Utilisateur, String> modifiepar;
    public static volatile SingularAttribute<Utilisateur, Date> creele;

}