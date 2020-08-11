package demo.hilfsklassen;

public class RegistierngsMessage {
    
    private String rueckmeldung = "";
    private String color;
    
    public RegistierngsMessage() {
    }
    
    public String getRueckmeldungRegistration() {
        return rueckmeldung;
    }

    public void setRueckmeldungRegistration(String rueckmeldungRegistration) {
        this.rueckmeldung = rueckmeldungRegistration;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    /**
     * Setzt die beiden Parameter
     * @param erfolgreich gibt an ob die Registration erfolgreich war oder nicht
     */
    public void validieren(boolean erfolgreich) {
        this.rueckmeldung = erfolgreich ? "Registrierung erfolgreich" : "Registrierung fehlgeschlagen";
        this.color = erfolgreich ? "green" : "red";
    }
}
