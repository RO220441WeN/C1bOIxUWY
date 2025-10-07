// 代码生成时间: 2025-10-08 03:05:20
import play.db.Database;
    import play.db.Databases;
    import play.libs.F;
    import play.mvc.Controller;
    import play.mvc.Result;

    import javax.inject.Inject;
    import javax.inject.Singleton;
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;

    /**
     * A PlayFramework controller for database version control.
     */
    @Singleton
    public class DatabaseVersionControl extends Controller {

        private final Database db;

        /**
         * Injects the database connection.
         *
         * @param db The database connection.
         */
        @Inject
        public DatabaseVersionControl(Database db) {
            this.db = db;
        }

        /**
         * Retrieves the current database version.
         *
         * @return A Result with the current database version.
         */
        public F.Promise<Result> getCurrentVersion() {
            return F.Promise.promise(() -> {
                try (Connection connection = db.getConnection();
                     PreparedStatement stmt = connection.prepareStatement("SELECT version FROM schema_version");
                     ResultSet rs = stmt.executeQuery()) {

                    if (rs.next()) {
                        String version = rs.getString("version");
                        return ok("Current database version: " + version);
                    } else {
                        return badRequest("No version information found.");
                    }
                } catch (SQLException e) {
                    return internalServerError("There was a problem accessing the database: " + e.getMessage());
                }
            });
        }

        /**
         * Updates the database version.
         *
         * @param newVersion The new version number.
         * @return A Result indicating the success or failure of the update.
         */
        public F.Promise<Result> updateVersion(String newVersion) {
            return F.Promise.promise(() -> {
                try (Connection connection = db.getConnection();
                     PreparedStatement stmt = connection.prepareStatement("UPDATE schema_version SET version = ?")) {

                    stmt.setString(1, newVersion);
                    int updatedRows = stmt.executeUpdate();

                    if (updatedRows > 0) {
                        return ok("Database version updated to: " + newVersion);
                    } else {
                        return badRequest("No changes were made to the database version.");
                    }
                } catch (SQLException e) {
                    return internalServerError("There was a problem updating the database: " + e.getMessage());
                }
            });
        }
    }