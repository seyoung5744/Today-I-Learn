package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.ResultSet;
import java.sql.Statement;

public class V2__bulk_update_books extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws Exception {

        try (Statement select = context.getConnection().createStatement()) {
            try (ResultSet rows = select.executeQuery("select id from books order by id")) {

                while (rows.next()) {
                    int id = rows.getInt(1);
                    String newName = "홍길동";

                    try (Statement update = context.getConnection().createStatement()) {
                        update.execute("update books set author='" + newName + "' where id=" + id);
                    }
                }
            }
        }
    }
}
