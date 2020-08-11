package demo.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/**
 *
 * @author Patrick
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Bauplan.Count", query = "SELECT COUNT(b.id) FROM Bauplan b"),
    @NamedQuery(name = "Bauplan.FindAll", query = "SELECT b FROM Bauplan b"),
    @NamedQuery(name = "Bauplan.FindByVorgaengerNull", query="SELECT b FROM Bauplan b WHERE b.vorgaenger is null"),
    @NamedQuery(name = "Bauplan.FindByVorgaengerUndVorgaengerSkill", query="SELECT b FROM Bauplan b WHERE b.vorgaenger = :vorgaenger AND b.skillImVorgaenger = :skillImVorgaenger")
})
public class Bauplan implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    private String bezeichnung;
    
    private int skillImVorgaenger;
    
    @OneToOne
    private Bauplan vorgaenger;
    
    private MaterialKategorie materialKategorie;
    
    private int preis;
    private int angriff;
    private int verteidigung;
    private int schaden;
    private int ruestung;
    
    public Bauplan() {
    }

    public Bauplan(String bezeichnung, int skillImVorgaenger, MaterialKategorie materialKategorie,
            int preis, int angriff, int verteidigung, int schaden, int ruestung) {
        this(bezeichnung,skillImVorgaenger,materialKategorie,preis,angriff,verteidigung,schaden,ruestung,null);
    }
    
    public Bauplan(String bezeichnung, int skillImVorgaenger, MaterialKategorie materialKategorie,
        int preis, int angriff, int verteidigung, int schaden, int ruestung, Bauplan vorgaenger) {
        this.bezeichnung = bezeichnung;
        this.skillImVorgaenger = skillImVorgaenger;
        this.materialKategorie = materialKategorie;
        this.preis = preis;
        this.angriff = angriff;
        this.verteidigung = verteidigung;
        this.schaden = schaden;
        this.ruestung = ruestung;
        this.vorgaenger = vorgaenger;
    }

    public Bauplan getVorgaenger() {
        return vorgaenger;
    }

    public void setVorgaenger(Bauplan vorgaenger) {
        this.vorgaenger = vorgaenger;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public int getSkillImVorgaenger() {
        return skillImVorgaenger;
    }

    public void setSkillImVorgaenger(int skillImVorgaenger) {
        this.skillImVorgaenger = skillImVorgaenger;
    }

    public MaterialKategorie getMaterialKategorie() {
        return materialKategorie;
    }

    public void setMaterialKategorie(MaterialKategorie materialKategorie) {
        this.materialKategorie = materialKategorie;
    }

    public int getPreis() {
        return preis;
    }

    public void setPreis(int preis) {
        this.preis = preis;
    }

    public int getAngriff() {
        return angriff;
    }

    public void setAngriff(int angriff) {
        this.angriff = angriff;
    }

    public int getVerteidigung() {
        return verteidigung;
    }

    public void setVerteidigung(int verteidigung) {
        this.verteidigung = verteidigung;
    }

    public int getSchaden() {
        return schaden;
    }

    public void setSchaden(int schaden) {
        this.schaden = schaden;
    }

    public int getRuestung() {
        return ruestung;
    }

    public void setRuestung(int ruestung) {
        this.ruestung = ruestung;
    }
    
    
}