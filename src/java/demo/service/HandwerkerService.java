package demo.service;

import demo.exceptions.InvalidKombinationException;
import demo.exceptions.InvalidUsernameException;
import demo.gen.Generator;
import demo.model.Account;
import demo.model.Bauplan;
import demo.model.Handwerker;
import demo.model.Item;
import demo.model.Material;
import demo.model.MaterialKategorie;
import demo.model.Skill;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class HandwerkerService {
    
    @PersistenceContext
    private EntityManager em;

    public void registrieren(String username, String passwort) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidUsernameException {
        Account a = new Account(username, passwort);
        if(!isUsernameFrei(username)) {
            throw new InvalidUsernameException("Username bereits vergeben", username);
        }
        Handwerker h = new Handwerker("Franz", "Brandwein");
        h.setAccount(a);
        em.persist(h);
        a.setHandwerker(h);
        em.persist(a);
        erzeugeMaterialien();
        erzeugeBauplaene(h);
    }
    
    public void einloggen(String username, String passwort) throws InvalidUsernameException, InvalidKombinationException {
        if(isUsernameFrei(username)) {
            throw new InvalidUsernameException("Username existiert nicht", username);
        }
        try {
             Account a = em.createNamedQuery("Account.FindByName", Account.class)
                .setParameter("username", username)
                .getSingleResult();
            byte[] ar = Generator.hashWithSalt(passwort, a.getSalt());
            if(!Arrays.equals(a.getHashedPassword(), ar)) {
                throw new InvalidKombinationException("Username oder Passwort ungültig", username, passwort);
            }           
            em.merge(a);
            FacesContext.getCurrentInstance().getAttributes().put("account", a);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(HandwerkerService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HandwerkerService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    /**
     * Gibt an ob der verwendete Username, bei der Registrierung noch frei ist
     * @param username der gewünschte Username
     * @return true (Username frei), false (Username nicht frei)
     */
    public boolean isUsernameFrei(String username) {
        long anzahl = em.createNamedQuery("Account.CountByName", long.class)
                .setParameter("username", username)
                .getSingleResult();
        return (anzahl == 0);
    }

    public void erzeugeMaterialien() {
        if((long)em.createNamedQuery("Material.Count").getSingleResult() == 0L) {
            List<Material> materialien = new ArrayList<>();
            materialien.add(new Material("Wildleder", 0, MaterialKategorie.LEDER));
            materialien.add(new Material("Alpha-Leder", 0.5, MaterialKategorie.LEDER));
            materialien.add(new Material("Mythenleder", 1.0, MaterialKategorie.LEDER));
            materialien.add(new Material("Drachenleder", 1.5, MaterialKategorie.LEDER));
            materialien.add(new Material("Nadelholz", 0, MaterialKategorie.HOLZ));
            materialien.add(new Material("Hartholz", 0.5, MaterialKategorie.HOLZ));
            materialien.add(new Material("Steineiche", 1.0, MaterialKategorie.HOLZ));
            materialien.add(new Material("Düsterholz", 1.5, MaterialKategorie.HOLZ));
            materialien.add(new Material("Bronze", 0, MaterialKategorie.METALL));
            materialien.add(new Material("Eisen", 0.5, MaterialKategorie.METALL));
            materialien.add(new Material("Mithril", 1.0, MaterialKategorie.METALL));
            materialien.add(new Material("Adamant", 1.5, MaterialKategorie.METALL));
            materialien.add(new Material("Wolfspelz", 0, MaterialKategorie.PELZ));
            materialien.add(new Material("Tigerpelz", 0.5, MaterialKategorie.PELZ));
            materialien.add(new Material("Löwenpelz", 1.0, MaterialKategorie.PELZ));
            materialien.add(new Material("Schneetigerpelz", 1.5, MaterialKategorie.PELZ));
            
            // Materialen persistieren
            for (Material material : materialien) {
                persistiereMaterial(material);
            }
        }
        
    }

    private void persistiereMaterial(Material material) {
        em.persist(material);
    }

    public void erzeugeBauplaene(Handwerker h) {
        h = em.merge(h);
        if((long)em.createNamedQuery("Bauplan.Count").getSingleResult() == 0L) {
            List<Bauplan> bauplaene = new ArrayList<>();
            bauplaene.add(new Bauplan("Pelzumhang", 0, MaterialKategorie.LEDER, 8, 0, 0, 0, 3));
            bauplaene.add(new Bauplan("Wanderstecken", 0, MaterialKategorie.HOLZ, 3, 2 , 5, 15, 0));
            bauplaene.add(new Bauplan("Messer", 0, MaterialKategorie.METALL, 5, 4, 0, 20, 0));
            bauplaene.add(new Bauplan("Dolch", 4, MaterialKategorie.METALL, 10, 8, 0, 35, 0, bauplaene.get(2)));
        
            // Baupläne persistieren
            for (Bauplan bauplan : bauplaene) {
                persistiereBauplan(bauplan);
            }
            
            // Skills erzeugen
            List<Bauplan> startBauplaene = em.createNamedQuery("Bauplan.FindByVorgaengerNull", Bauplan.class).getResultList();
            List<Skill> skills = new ArrayList<>();
            for (Bauplan b : startBauplaene) {
                skills.add(new Skill(0, h, b));
            }
            
            // Skills einem Handwerker zuordnen
            for (Skill skill : skills) {
                h.getSkills().add(skill);
            }
            
            // Skills persistieren
            for (Skill skill : skills) {
                persistierenSkill(skill);
            }
        }
    }

    private void persistiereBauplan(Bauplan bauplan) {
        em.persist(bauplan);
    }

    public List<Bauplan> alleBauplaene() {
        return em.createNamedQuery("Bauplan.FindAll", Bauplan.class).getResultList();
    }

    public Account erzeugeItem(Skill skill, long materialId) {
        skill = em.merge(skill);
        Account account = em.merge(skill.getHandwerker().getAccount());
        
        Material material = em.createNamedQuery("Material.FindById", Material.class)
                .setParameter("materialId", materialId)
                .getSingleResult();
        Item item = new Item(skill, material);
        em.persist(item);
        item.setAccount(account);
        account.getItems().add(item);
        
        // Skill verbessern falls passend gewürfelt wird
        Random gen = new Random();
        int wert = gen.nextInt(100);
        
        if(wert < Skill.ANFANGSQUALITAET - (Skill.STEIGERUNGSINTERVALL * skill.getSkillwert())) {
            skill.setSkillwert(skill.getSkillwert() + 1);
            
            // Bei Skillverbesserung überprüfen ob der Nachfolgerbauplan freigeschaltet werden kann
            List<Bauplan> bauplaeneNeuFreigeschaltet = em.createNamedQuery("Bauplan.FindByVorgaengerUndVorgaengerSkill", Bauplan.class)
                    .setParameter("vorgaenger", skill.getBauplan())
                    .setParameter("skillImVorgaenger", skill.getSkillwert())
                    .getResultList();
            List<Skill> skillsNeuFreigschaltet = new ArrayList<>();   
            
            // Neu freigeschaltete Skills erstellen
            for (Bauplan bauplan : bauplaeneNeuFreigeschaltet) {
                skillsNeuFreigschaltet.add(new Skill(0, skill.getHandwerker(), bauplan));
            }
            
            // Neu freigeschlatete Skills an den Account binden
            for (Skill s : skillsNeuFreigschaltet) {
                account.getHandwerker().getSkills().add(s);
            }
            
            // Neue Skills persistieren
            for (Skill s : skillsNeuFreigschaltet) {
                persistierenSkill(s);
            }
        }
        return account;
    }

    public List<Material> holeMaterialenProKategorie(MaterialKategorie matKat) {
        return em.createNamedQuery("Material.FindByMaterialKategorie", Material.class)
                .setParameter("matKat", matKat)
                .getResultList();
    }

    private void persistierenSkill(Skill skill) {
        em.persist(skill);
    }

    public List<Skill> alleSkills(Handwerker h) {
        return em.createNamedQuery("Skill.FindByHandwerker", Skill.class)
                .setParameter("handwerker", h)
                .getResultList();
    }

    public Account verkaufen(Item item) {
        item = em.merge(item);
        
        Account account = item.getAccount();
        account = em.merge(account);
        account.setKupfermuenzen(account.getKupfermuenzen() + item.getPreis());
        em.remove(item);
        account.getItems().remove(item);
        return account;
    }
}
