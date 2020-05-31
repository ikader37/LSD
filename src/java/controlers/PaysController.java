/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;
import entities.*;
import java.io.Serializable;
import javax.enterprise.context.*;
import javax.inject.Inject;
import javax.inject.Named;
import models.*;
import java.util.*;
import javax.annotation.PostConstruct;
import org.primefaces.event.RowEditEvent;
import utilities.JSFUtils;

/**
 *
 * @author ilboudo
 */

@Named("paysController")
@SessionScoped
public class PaysController extends AbstractBean implements Serializable{
    
}
