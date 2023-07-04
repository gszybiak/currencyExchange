package currencyExchange.database;

import currencyExchange.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseOperationTransactions {
    private static final Logger transactionLog = LogManager.getLogger(DatabaseOperationTransactions.class);
    private String tableName = "Transactions";

    public void addTransaction(int userId, Date transactionDate, double amount, String currency, String transactionType, double exchangeRate, Statement statement) {
        try {
            String sqlQuery = "INSERT INTO " + tableName  + " (UserID, TransactionDate, Amount, Currency, TransactionType, ExchangeRate) VALUES " +
                    "(" + userId + ", '" + transactionDate + "', " + amount + ", '" + currency + "', '" +
                    transactionType + "', " + exchangeRate + ")";
            statement.execute(sqlQuery);
        } catch (SQLException e) {
            transactionLog.error("Błąd podczas dodawania nowej transakcji", new Exception(e.getMessage()));
        }
    }

    public Transaction getTransactionById(int transactionId, Statement statement) {
        try {
            String sqlQuery = "SELECT * FROM " + tableName + " where Id = " + transactionId;
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            statement.execute(sqlQuery);

            Transaction transaction = new Transaction(resultSet.getInt("id"), resultSet.getInt("userId"),
                    resultSet.getDate("transactionDate"), resultSet.getDouble("amount"),
                    resultSet.getString("currency"), resultSet.getString("transactionType"),
                    resultSet.getInt("exchangeRate"));
            resultSet.close();
            return transaction;

        } catch (SQLException e) {
            transactionLog.error("Błąd podczas pobierania danych transakcji", new Exception(e.getMessage()));
            return null;
        }
    }
}
