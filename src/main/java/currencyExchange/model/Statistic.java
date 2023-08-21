package currencyExchange.model;

import currencyExchange.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Statistic {
    private BigDecimal bought;
    private BigDecimal sold;
    private BigDecimal balance;
    private CurrencyType currencyType;
}
