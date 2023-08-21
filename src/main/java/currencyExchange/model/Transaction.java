package currencyExchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@AllArgsConstructor
public class Transaction {
    private int id;
    private int userId;
    private Date transactionDate;
    private BigDecimal amount;
    private String currency;
    private String transactionType;
    private int exchangeRate;
}
