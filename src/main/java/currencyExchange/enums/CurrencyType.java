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

    public static String getNameForKey(String key) {
        for (CurrencyType currencyType : CurrencyType.values()) {
            if (currencyType.name().equals(key)) {
                return currencyType.getName();
            }
        }
        return null;
    }
}
