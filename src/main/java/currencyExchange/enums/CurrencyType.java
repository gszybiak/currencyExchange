package currencyExchange.enums;

public enum CurrencyType {
    EUR("eur"),
    USD("usd"),
    GBP("gbp");

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
