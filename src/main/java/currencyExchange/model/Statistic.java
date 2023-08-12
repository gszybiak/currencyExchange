package currencyExchange.model;

import currencyExchange.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Statistic {
    private Double bought;
    private Double sold;
    private Double balance;
    private CurrencyType currencyType;
}
