package currencyExchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public abstract class Statistic {
    protected Double bought;
    protected Double sold;
    protected Double balance;
}
