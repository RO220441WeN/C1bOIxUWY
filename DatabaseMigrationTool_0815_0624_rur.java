// 代码生成时间: 2025-08-15 06:24:32
import play.db.Database;
import play.db.evolutions.Evolution;
import play.db.evolutions.Evolutions;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;
import java.util.stream.Collectors;

public class DatabaseMigrationTool extends Controller {

    // Inject Play Framework's Database object
    private final Database db;

    // Constructor that injects the database
    public DatabaseMigrationTool(Database db) {
        this.db = db;
    }

    // Migration action that applies all pending evolutions
    public Result migrateDatabase() {
        try {
            Evolutions evolutions = Evolutions.forDefaultDataSource(db);
            List<Evolution> pendingEvolutions = evolutions.getPendingEvolutions();

            if (pendingEvolutions.isEmpty()) {
                // No pending evolutions, database is up to date
                return ok("No pending database migrations.");
            } else {
                // Apply pending evolutions
                evolutions.evolve();
                return ok("Database migrations applied successfully.");
            }
        } catch (Exception e) {
            // Handle any exceptions that occur during migration
            return internalServerError("Error applying database migrations: " + e.getMessage());
        }
    }

    // Helper method to get pending evolutions
    private List<String> getPendingEvolutions() {
        Evolutions evolutions = Evolutions.forDefaultDataSource(db);
        return evolutions.getPendingEvolutions().stream()
                .map(Evolution::number)
                .collect(Collectors.toList());
    }

    // Method to rollback the last evolution
    public Result rollbackLastEvolution() {
        try {
            Evolutions evolutions = Evolutions.forDefaultDataSource(db);
            evolutions.down(1);
            return ok("Last database evolution rolled back successfully.");
        } catch (Exception e) {
            return internalServerError("Error rolling back database evolution: " + e.getMessage());
        }
    }
}
