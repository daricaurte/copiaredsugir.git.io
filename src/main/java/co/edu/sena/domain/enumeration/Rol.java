package co.edu.sena.domain.enumeration;

/**
 * The Rol enumeration.
 */
public enum Rol {
    AFFILIATE("Affiliate"),
    COACH("Coach");

    private final String value;

    Rol(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
