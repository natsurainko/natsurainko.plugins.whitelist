package natsurainko.plugins.whitelist.componet;

import com.mysql.cj.jdbc.MysqlDataSource;
import natsurainko.plugins.whitelist.model.DatabaseInformation;
import natsurainko.plugins.whitelist.model.WhiteListFilterType;
import natsurainko.plugins.whitelist.model.WhiteListItem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private DatabaseInformation information;
    private MysqlDataSource dataSource = new MysqlDataSource();

    public DatabaseManager(DatabaseInformation information) {
        this.information = information;
        dataSource.setURL(information.url);
        dataSource.setUser(information.user);
        dataSource.setPassword(information.password);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void checkTable() throws SQLException {
        String statement =
                "CREATE TABLE IF NOT EXISTS natsurainko_whitelist (\n" +
                "`uuid` TINYTEXT NOT NULL,\n" +
                "`qNumber` TINYTEXT NOT NULL,\n" +
                "`id` TINYTEXT NOT NULL\n" +
                ");";

        getConnection().prepareStatement(statement).execute();
    }

    public List<WhiteListItem> readTable() throws SQLException {
        String statement =
                "SELECT * FROM natsurainko_whitelist";
        List<WhiteListItem> whiteList = new ArrayList<WhiteListItem>();

        ResultSet resultSet = getConnection().prepareStatement(statement).executeQuery();
        while (resultSet.next()){
            whiteList.add(new WhiteListItem(
                    resultSet.getString("uuid"),
                    resultSet.getString("qNumber"),
                    resultSet.getString("id"))
            );
        }

        return whiteList;
    }

    public void addItem(WhiteListItem item) throws SQLException {
        String statement =
                "INSERT INTO natsurainko_whitelist VALUES (\'{0}\', \'{1}\', \'{2}\')";

        getConnection().prepareStatement(statement
                .replace("{0}", item.uuid)
                .replace("{1}", item.qNumber)
                .replace("{2}", item.id))
                .execute();
    }

    public void removeItem(WhiteListFilterType type, String filter) throws SQLException {
        String statement =
                "DELETE FROM natsurainko_whitelist WHERE {0}=\'{1}\'";

        getConnection().prepareStatement(statement
                        .replace("{0}", type.getName())
                        .replace("{1}", filter))
                .execute();
    }
}
