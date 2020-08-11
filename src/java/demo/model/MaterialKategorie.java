package demo.model;

/**
 *
 * @author Patrick
 */

public enum MaterialKategorie {
    METALL("Metall"),HOLZ("Holz"),LEDER("Leder"),PELZ("Pelz");
    
    private String bezeichnung;

    
    private MaterialKategorie(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }
    
    
}
