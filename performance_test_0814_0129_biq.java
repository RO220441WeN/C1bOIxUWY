// 代码生成时间: 2025-08-14 01:29:53
import com.typesafe.config.Config;
    import com.typesafe.config.ConfigFactory;
    import io.gatling.app.Gatling;
    import io.gatling.core.Predef.*;
    import io.gatling.core.config.GatlingConfiguration;
    import io.gatling.core.config.GatlingPropertiesBuilder;
    import io.gatling.http.Predef.*;
    import scala.io.Source;

    import java.nio.file.Paths;

    /**
     * PerformanceTest is a Play Framework application for load testing using Gatling.
     * This class sets up the Gatling simulation and runs the performance tests.
     */
    public class PerformanceTest {

        /**
         * Main method to start the Gatling simulation.
         * @param args Command line arguments
         */
        public static void main(String[] args) {
            try {
                // Load configuration file
                Config config = ConfigFactory.load();
                String simulationClass = config.getString("simulationClass");

                // Build Gatling properties
                GatlingPropertiesBuilder gatlingPropsBuilder = new GatlingPropertiesBuilder();
                gatlingPropsBuilder.simulationClass(simulationClass);
                gatlingPropsBuilder.dataDirectory(Paths.get("data"));
                gatlingPropsBuilder.resultsDirectory(Paths.get("results"));

                // Run Gatling simulation
                Gatling.run(simulationClass, gatlingPropsBuilder.build());
            } catch (Exception e) {
                // Error handling
                e.printStackTrace();
                System.err.println("Error occurred during performance testing: " + e.getMessage());
            }
        }
    }

