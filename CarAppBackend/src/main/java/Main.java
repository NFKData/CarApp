import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

	public static void main(String[] args) {
		Logger logger = LogManager.getLogger(Main.class);
		logger.info("Method Called: Main.main");
		logger.info("Parameters: " + args);
		logger.debug("afsd");
		logger.error("dfhdgf");
	}

}
