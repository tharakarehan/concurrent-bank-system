package enums;

public enum AccountType {
    SAVINGS(300.0, "Savings"),
    CHECKING(200.0, "Checking"),
    MONEY_MARKET(100.0, "Money Market");

    private final double annualFee;

    private final String stringValue;

    AccountType(double annualFee, String stringValue) {
        this.annualFee = annualFee;
        this.stringValue = stringValue;
    }

    public double getAnnualFee() {
        return annualFee;
    }

    public String getStringValue() {
        return stringValue;
    }
}
