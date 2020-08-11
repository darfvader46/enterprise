package demo.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Patrick
 */

@Entity
@NamedQueries({
    @NamedQuery(name = "Material.Count", query = "SELECT COUNT(m.id) FROM Material m"),
    @NamedQuery(name = "Material.FindById", query = "SELECT m FROM Material m WHERE m.id = :materialId"),
    @NamedQuery(name = "Material.FindByMaterialKategorie", query = "SELECT m FROM Material m WHERE m.materialKategorie = :matKat ORDER BY m.id")
})
public class Material implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String bezeichnung;
    private double bonuswert;
    
    private MaterialKategorie materialKategorie;
    
    public Material() {}
    
    public Material(String bezeichnung, double bonuswert, MaterialKategorie materialKategorie) {
        this.bezeichnung = bezeichnung;
        this.bonuswert = bonuswert;
        this.materialKategorie = materialKategorie;
    }
    
    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }
    
    public String getBezeichnung() {
        return bezeichnung;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBonuswert() {
        return bonuswert;
    }

    public void setBonuswert(double bonuswert) {
        this.bonuswert = bonuswert;
    }

    public MaterialKategorie getMaterialKategorie() {
        return materialKategorie;
    }

    public void setMaterialKategorie(MaterialKategorie materialKategorie) {
        this.materialKategorie = materialKategorie;
    }
    
    
}
