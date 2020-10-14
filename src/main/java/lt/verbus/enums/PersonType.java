package lt.verbus.enums;

public enum PersonType {
    PESSIMIST("PESIMISTAS(-Ė)"), REALIST("REALISTAS(-Ė)"), OPTIMIST("OPTIMISTAS(-Ė)");
    private final String lithuanianValue;

    PersonType(String lithuanianValue) {
        this.lithuanianValue = lithuanianValue;
    }

    public String getLithuanianValue() {
        return lithuanianValue;
    }
}