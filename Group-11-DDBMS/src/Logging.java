import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Logging {
    private static Logger log = LogManager.getLogger(Logging.class);

   public static void main(String args[]) {

       BasicConfigurator.configure();

       log.info("Info here");
       log.debug("Debug here");
       log.fatal("Fatal here");
       log.error("Error here");
       log.trace("Trace here");
       log.warn("Warning here");
   }
}
