package entities;

import entities.Utilisateur;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2020-05-31T22:00:50")
@StaticMetamodel(Profil.class)
public class Profil_ { 

    public static volatile CollectionAttribute<Profil, Utilisateur> utilisateurCollection;
    public static volatile SingularAttribute<Profil, String> creepar;
    public static volatile SingularAttribute<Profil, String> idprofil;
    public static volatile SingularAttribute<Profil, String> libelleprofil;
    public static volatile SingularAttribute<Profil, Date> modifierle;
    public static volatile SingularAttribute<Profil, String> modifiepar;
    public static volatile SingularAttribute<Profil, Date> creele;

}