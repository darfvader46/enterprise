package demo.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 *
 * @author Patrick
 */

@Entity
@NamedQueries({
    @NamedQuery(name = "Skill.FindByHandwerker", query = "SELECT s FROM Skill s WHERE s.handwerker = :handwerker")
})
public class Skill implements Serializable {
    
    // Der Standard-Prozentwert liegt bei
    // Bei Skill 0: Der 100-seitige Würfel zeigt 40 -> 40 < 50 -> Der Skillwert wird um eins erhöht
    public static final int ANFANGSQUALITAET = 50;
    
    // Die Steigerungsrate des Anfangs-Prozentwertes
    // Bei Skill 1: Der 100-seitige Würfel zeigt 47 -> 47 > 50-5 -> Der Skillwert wird nicht erhöht
    public static final int STEIGERUNGSINTERVALL = 5;    
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @Min(0)
    @Max(10)
    private int skillwert;
    
    @ManyToOne
    private Handwerker handwerker;
    
    @ManyToOne
    private Bauplan bauplan;
    
    public Skill() {
    }
    
    public Skill(int skillwert, Handwerker handwerker, Bauplan bauplan) {
        this.skillwert = skillwert;
        this.handwerker = handwerker;
        this.bauplan = bauplan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSkillwert() {
        return skillwert;
    }

    public void setSkillwert(int skillwert) {
        this.skillwert = skillwert;
    }

    public Handwerker getHandwerker() {
        return handwerker;
    }

    public void setHandwerker(Handwerker handwerker) {
        this.handwerker = handwerker;
    }

    public Bauplan getBauplan() {
        return bauplan;
    }

    public void setBauplan(Bauplan bauplan) {
        this.bauplan = bauplan;
    }
}
