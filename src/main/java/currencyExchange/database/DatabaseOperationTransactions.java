package currencyExchange.database;

import currencyExchange.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

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

    public StatisticUsd getStatisticUsdById(int userId, Statement statement) {
        StatisticUsd statisticUsd = null;
        String sqlQuery = "SELECT (select sum(amount) AS bought FROM " + tableName  + " WHERE TransactionType = \"buy\")," +
                "(select sum(amount) AS sold FROM " + tableName  + " WHERE TransactionType = \"sold\") FROM " + tableName  + " " +
                "WHERE transactionType = \"usd\" and id=" + userId;
        try {
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            if (resultSet.next()) {
                statisticUsd = new StatisticUsd(resultSet.getDouble("bought"), resultSet.getDouble("sold"),
                        Math.abs(resultSet.getDouble("bought") - resultSet.getDouble("bought")));
            }
            resultSet.close();
            return statisticUsd;

        } catch (SQLException e) {
            transactionLog.error("Błąd podczas pobierania danych o statystykach USD", new Exception(e.getMessage()));
            return null;
        }
    }

    public StatisticEur getStatisticEurById(int userId, Statement statement) {
        StatisticEur statisticEur = null;
        String sqlQuery = "SELECT (select sum(amount) AS bought FROM " + tableName  + " WHERE TransactionType = \"buy\")," +
                "(select sum(amount) AS sold FROM " + tableName  + " WHERE TransactionType = \"sold\") FROM " + tableName  + " " +
                "WHERE transactionType = \"eur\" and id=" + userId;
        try {
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            if (resultSet.next()) {
                statisticEur = new StatisticEur(resultSet.getDouble("bought"), resultSet.getDouble("sold"),
                        Math.abs(resultSet.getDouble("bought") - resultSet.getDouble("bought")));
            }
            resultSet.close();
            return statisticEur;

        } catch (SQLException e) {
            transactionLog.error("Błąd podczas pobierania danych o statystykach EUR", new Exception(e.getMessage()));
            return null;
        }
    }

    public StatisticGbp getStatisticGbpById(int userId, Statement statement) {
        StatisticGbp statisticGbp = null;
        String sqlQuery = "SELECT (select sum(amount) AS bought FROM " + tableName  + " WHERE TransactionType = \"buy\")," +
                "(select sum(amount) AS sold FROM " + tableName  + " WHERE TransactionType = \"sold\") FROM " + tableName  + " " +
                "WHERE transactionType = \"gbp\" and id=" + userId;
        try {
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            if (resultSet.next()) {
                statisticGbp = new StatisticGbp(resultSet.getDouble("bought"), resultSet.getDouble("sold"),
                        Math.abs(resultSet.getDouble("bought") - resultSet.getDouble("bought")));
            }
            resultSet.close();
            return statisticGbp;

        } catch (SQLException e) {
            transactionLog.error("Błąd podczas pobierania danych o statystykach GBP", new Exception(e.getMessage()));
            return null;
        }
    }
}
