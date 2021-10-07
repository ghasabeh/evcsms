package devolon.fi.evcsms.model.dto.response;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
public enum ResponseType {
    GENERAL("GENERAL"),
    EXCEPTION("EXCEPTION");

    private final String value;

    ResponseType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
