
package demo.exceptions;

/**
 *
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
