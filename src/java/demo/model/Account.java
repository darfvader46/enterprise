package demo.model;

import demo.gen.Generator;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({
    @NamedQuery(name = "Account.CountByName", query = "SELECT COUNT(a.id) FROM Account a WHERE a.username = :username"),
    @NamedQuery(name = "Account.FindByName", query = "SELECT a FROM Account a WHERE a.username = :username")
})
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    private byte[] hashedPassword;
    private byte[] salt;
    
    private long kupfermuenzen;
    
    @OneToOne
    private Handwerker handwerker;
    
    @OneToMany(mappedBy = "account")
    private List<Item> items;
    
    public Account() {
    }
    
    public Account(String username, String passwort) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.username = username;
        this.salt = Generator.generateSalt();
        this.hashedPassword = Generator.hashWithSalt(passwort,  this.salt);
        this.kupfermuenzen = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public long getKupfermuenzen() {
        return kupfermuenzen;
    }

    public void setKupfermuenzen(long kupfermuenzen) {
        this.kupfermuenzen = kupfermuenzen;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(byte[] hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public Handwerker getHandwerker() {
        return handwerker;
    }

    public void setHandwerker(Handwerker handwerker) {
        this.handwerker = handwerker;
    }

    public byte[] getSalt() {
        return salt;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
