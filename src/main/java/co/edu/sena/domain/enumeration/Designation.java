package co.edu.sena.domain.enumeration;

/**
 * The Designation enumeration.
 */
public enum Designation {
    One("1.0"),
    Two("2.0"),
    Three("3.0"),
    Four("4.0"),
    Five("5.0");

    private final String value;

    Designation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
