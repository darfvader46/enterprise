package demo.model;

import java.io.Serializable;
import java.util.Random;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Patrick
 */
@Entity
public class Item implements Serializable {
    
    public static final int ANFANGSQUALITAET = 51;
    public static final int QUALITAETSSTEIGERUNG = 3;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @ManyToOne
    private Bauplan bauplan;
    
    private int qualitaet;
    
    private Material material;
    
    @ManyToOne
    private Account account;
    
    public Item() {
    }
    
    public Item(Skill skill, Material material) {
        Random gen = new Random();
        this.bauplan = skill.getBauplan();
        this.material = material;
        this.qualitaet = ANFANGSQUALITAET + (QUALITAETSSTEIGERUNG * skill.getSkillwert())  + gen.nextInt(20);
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
    public Bauplan getBauplan() {
        return bauplan;
    }

    public void setBauplan(Bauplan bauplan) {
        this.bauplan = bauplan;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQualitaet() {
        return qualitaet;
    }

    public void setQualitaet(int qualitaet) {
        this.qualitaet = qualitaet;
    }
    
    public int getPreis() {
        return (int) Math.round(this.bauplan.getPreis() * this.getQualitaetProzent() * this.getQualitaetProzent());
    }
    
    public int getAngriff() {
        return (int) Math.round((this.bauplan.getAngriff() * this.getQualitaetProzent()) * (material.getBonuswert()+1));
    }
    
    public int getVerteidigung() {
        return (int) Math.round((this.bauplan.getVerteidigung() * this.getQualitaetProzent()) * (material.getBonuswert()+1));
    }
    
    public int getSchaden() {
        return (int) Math.round((this.bauplan.getSchaden() * this.getQualitaetProzent()) * (material.getBonuswert()+1));
    }
    
    public int getRuestung() {
        return (int) Math.round((this.bauplan.getRuestung() * this.getQualitaetProzent()) * (material.getBonuswert()+1));
    }
    
    private double getQualitaetProzent() {
        return this.qualitaet * 1.0 / 100;
    }
}
