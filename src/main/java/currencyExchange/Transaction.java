package currencyExchange;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class Transaction {
    int id;
    int userId;
    Date transactionDate;
    Double amount;
    String currency;
    String transactionType;
    int exchangeRate;
}
