package currencyExchange.enums;

import java.util.Optional;

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

    public static Optional<String> getNameForKey(String key) {
        for (CurrencyType currencyType : CurrencyType.values()) {
            if (currencyType.name().equals(key)) {
                return Optional.of(currencyType.getName());
            }
        }
        return Optional.empty();
    }
}
