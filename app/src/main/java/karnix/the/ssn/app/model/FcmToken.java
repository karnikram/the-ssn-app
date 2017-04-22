package karnix.the.ssn.app.model;

/**
 * Created by adithya321 on 4/22/17.
 */

public class FcmToken {

    private String value;

    public FcmToken() {
    }

    public FcmToken(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "FcmToken{" +
                "value='" + value + '\'' +
                '}';
    }
}
