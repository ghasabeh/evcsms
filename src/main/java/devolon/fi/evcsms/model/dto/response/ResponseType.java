package devolon.fi.evcsms.model.dto.response;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
public enum ResponseType {
    GENERAL("general"),
    EXCEPTION("exception");

    private final String value;

    ResponseType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
