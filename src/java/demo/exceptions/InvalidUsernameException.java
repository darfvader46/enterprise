
package demo.exceptions;

/**
 * Wird geworfen falls der angefragte Username nicht exisitiert
 * @author pgmeinwi
 */
public class InvalidUsernameException extends Exception {

    
    private final String username;
    
    public InvalidUsernameException(String msg, String username) {
        super(msg);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
