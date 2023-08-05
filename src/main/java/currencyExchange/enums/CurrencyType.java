package currencyExchange.enums;

public enum CurrencyType {
    EURO("eur"),
    DOLAR("usd"),
    FUNT("gbp");

    private final String name;

    CurrencyType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
