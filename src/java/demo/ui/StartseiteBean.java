package demo.ui;

import demo.model.Account;
import demo.model.Item;
import demo.model.Material;
import demo.model.MaterialKategorie;
import demo.model.Skill;
import demo.service.HandwerkerService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class StartseiteBean implements Serializable {
    
    private Account account;
    
    private String kupfermuenzenAusgabe;
    
    private long materialId;
    
    @Inject
    private HandwerkerService service;
    
    @PostConstruct
    public void init() {
        account = (Account)FacesContext.getCurrentInstance().getAttributes().get("account");
        anpassenKupfermuenzen();
    }   

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(long materialId) {
        this.materialId = materialId;
    }

    public String getKupfermuenzenAusgabe() {
        return kupfermuenzenAusgabe;
    }

    public void setKupfermuenzenAusgabe(String kupfermuenzenAusgabe) {
        this.kupfermuenzenAusgabe = kupfermuenzenAusgabe;
    }
    
    public void erzeugeItem(Skill skill) {
        account = service.erzeugeItem(skill, materialId);
    }
    
    public List<Material> holeMaterialenProKategorie(MaterialKategorie matKat) {
        return service.holeMaterialenProKategorie(matKat);
    }
    
    public void verkaufen(Item item) {
        account = service.verkaufen(item);
        anpassenKupfermuenzen();
    }
    
    private void anpassenKupfermuenzen() {
        long muenzen = account.getKupfermuenzen();
        
        int platin = 0;
        int gold = 0;
        int silber = 0;
        
        while(muenzen >= 1000) {
            platin++;
            muenzen -= 1000;
        }
        
        while(muenzen >= 100) {
            gold++;
            muenzen -= 100;
        }
        
        while(muenzen >= 10) {
            silber++;
            muenzen -= 10;
        }
        
        this.kupfermuenzenAusgabe = "" + platin + "P " + gold + "G " + silber + "S " + muenzen + "K"; 
    }
}
