package demo.ui;

import demo.exceptions.InvalidKombinationException;
import demo.exceptions.InvalidUsernameException;
import demo.hilfsklassen.RegistierngsMessage;
import demo.service.HandwerkerService;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class UserBean implements Serializable {
    
    private String username;
    private String passwort;
    
    private RegistierngsMessage message = new RegistierngsMessage();
    
    @Inject
    private HandwerkerService service;

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUsername() {
        return username;
    }

    public HandwerkerService getService() {
        return service;
    }
    
    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public RegistierngsMessage getMessage() {
        return message;
    }

    public void setMessage(RegistierngsMessage message) {
        this.message = message;
    }
    
    /**
     * Wird ausgeführt, wenn der User einen neuen Account registrieren will
     */
    public void registrieren() {
        try {
            service.registrieren(username, passwort);
            message.validieren(true);
        } catch(InvalidUsernameException | UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            message.validieren(false);
            ausgebenFehlermeldung(ex.getMessage(), "meineForm:account");
        }
    }
    
    /**
     * Wird ausgeführt wenn der User sich anmelden will
     * @return Bei erfolgreicher Registration wird zur Startseite navigiert
     *         Bei fehlgeschlagener Registration wird die Seite neu geladen
     */
    public String einloggen() {
        try {
            service.einloggen(username, passwort);
            return "startseite";
        } catch (InvalidUsernameException | InvalidKombinationException ex ) {
            ausgebenFehlermeldung(ex.getMessage(), "meineForm:account"); 
        }
        return "index";
    }
    
    /**
     * Gibt eine Fehlermeldung an die Oberfläche weiter
     * @param message Die auszugebene Nachricht
     * @param anzeigeId An welchem Objekt diese Nachricht angezeigt wird
     */
    public static void ausgebenFehlermeldung(String message,String anzeigeId){
        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
        fc.addMessage(anzeigeId, msg);
    }
    
}
