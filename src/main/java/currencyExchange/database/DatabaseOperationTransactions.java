package currencyExchange.database;

import currencyExchange.controller.LoginWindowController;
import currencyExchange.enums.CurrencyType;
import currencyExchange.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

public class DatabaseOperationTransactions {
    private static final Logger transactionLog = LogManager.getLogger(DatabaseOperationTransactions.class);
    private String tableName = "Transactions";

    public void addTransaction(int userId, Date transactionDate, BigDecimal amount, String currency, String transactionType, BigDecimal exchangeRate, Statement statement) {
        try {
            String sqlQuery = "INSERT INTO " + tableName  + " (UserID, TransactionDate, Amount, Currency, TransactionType, ExchangeRate) VALUES " +
                    "(" + userId + ", '" + transactionDate + "', " + amount + ", '" + currency + "', '" +
                    transactionType + "', " + exchangeRate + ")";
            statement.execute(sqlQuery);
        } catch (SQLException e) {
            transactionLog.error("Error while adding a new transaction", new Exception(e.getMessage()));
        }
    }

    public Statistic getStatisticById(int userId, CurrencyType currencyType, Statement statement) {
        Statistic statistic = null;
        String sqlQuery = "SELECT (select sum(amount) FROM " + tableName  + " WHERE TransactionType = \"buy\" and currency = \"" + currencyType.getName() + "\") AS bought ," +
                "(select sum(amount) FROM " + tableName  + " WHERE TransactionType = \"sell\" and currency = \"" + currencyType.getName() + "\")  AS sold, currency FROM " + tableName  + " " +
                "WHERE userId= " + userId + " LIMIT 1";
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
            transactionLog.error("Error fetching USD statistics data", new Exception(e.getMessage()));
            return null;
        }
    }

    public static Statistic loadStatistics(CurrencyType currencyType){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        DatabaseOperationTransactions databaseOperationTransactions = new DatabaseOperationTransactions();
        Statistic statistic = databaseOperationTransactions.getStatisticById(LoginWindowController.customer.getId(), currencyType.GBP, databaseConnection.getStatement());
        databaseConnection.disconnect();
        return statistic;
    }
}
