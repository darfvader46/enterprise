package demo.exceptions;

/**
 * Wird geworfen, falls der Username existiert,
 * aber das angegebene Passwort falsch ist
 * @author pgmeinwi
 */
public class InvalidKombinationException extends Exception {

    private final String username;
    private final String passwort;
    
    public InvalidKombinationException(String msg, String username, String passwort) {
        super(msg);
        this.username = username;
        this.passwort = passwort;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswort() {
        return passwort;
    }
    
    
}
