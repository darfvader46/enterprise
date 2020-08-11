package demo.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;

/**
 *
 * @author Patrick
 */

@Entity
public class Handwerker implements Serializable {
    @Id
    @TableGenerator(name = "ID_GENERATOR", table = "X_GENARTOR")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ID_GENERATOR")
    private int id;
    private String vorname;
    private String nachname;
    
    @OneToMany(mappedBy = "handwerker")
    private List<Skill> skills;
    
    @OneToOne
    private Account account;
    
    public Handwerker() {
    }
    
    public Handwerker(String vorname, String nachname) {
        this.vorname = vorname;
        this.nachname = nachname;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
}
