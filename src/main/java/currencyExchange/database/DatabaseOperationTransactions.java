package currencyExchange.database;

import currencyExchange.controller.LoginWindowController;
import currencyExchange.enums.CurrencyType;
import currencyExchange.model.*;
import currencyExchange.services.LoginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.Locale;
import java.util.Optional;

public class DatabaseOperationTransactions {
    private static final Logger transactionLog = LogManager.getLogger(DatabaseOperationTransactions.class);
    private final String tableName = "Transactions";

    public void addTransaction(int userId, Date transactionDate, BigDecimal amount, Optional<String> currency, String transactionType, BigDecimal exchangeRate, Statement statement) {
        try {
            Locale.setDefault(Locale.US);
            String sqlQuery = String.format("INSERT INTO %s (UserID, TransactionDate, Amount, Currency, TransactionType, ExchangeRate) VALUES (%d, '%s', %f, '%s', '%s', %f)",
                    tableName, userId, transactionDate, amount, currency.get(), transactionType, exchangeRate);
            statement.execute(sqlQuery);
        } catch (SQLException e) {
            transactionLog.error("Error while adding a new transaction", new Exception(e.getMessage()));
        }
    }

    public Statistic getStatisticById(int userId, CurrencyType currencyType, Statement statement) {
        Statistic statistic = null;
        String sqlQuery = String.format("SELECT COALESCE((SELECT SUM(amount) FROM %s WHERE TransactionType = 'buy' AND currency = '%s'), 0.0) AS bought, " +
                        "COALESCE((SELECT SUM(amount) FROM %s WHERE TransactionType = 'sell' AND currency = '%s'), 0.0) AS sold, currency " +
                        "FROM %s WHERE userId = %d LIMIT 1",
                tableName, currencyType.getName(), tableName, currencyType.getName(), tableName, userId);

        try {
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            if (resultSet.next()) {
                statistic = new Statistic(resultSet.getBigDecimal("bought"), resultSet.getBigDecimal("sold"),
                        resultSet.getBigDecimal("sold").subtract(resultSet.getBigDecimal("bought")),
                        currencyType);
            }
            resultSet.close();
            return statistic;

        } catch (SQLException e) {
            transactionLog.error("Error fetching" + currencyType.getName() + " statistics data", new Exception(e.getMessage()));
            return null;
        }
    }

    public static Statistic loadStatistics(CurrencyType currencyType){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        DatabaseOperationTransactions databaseOperationTransactions = new DatabaseOperationTransactions();
        Statistic statistic = databaseOperationTransactions.getStatisticById(LoginService.customer.getId(), currencyType, databaseConnection.getStatement());
        databaseConnection.disconnect();
        return statistic;
    }
}
