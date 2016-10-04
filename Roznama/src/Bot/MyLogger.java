package Bot;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class MyLogger {

	
	 private static MyLogger instance = null;
	    protected final static Logger log = Logger.getLogger(MyLogger.class);

	    private MyLogger() {
	        super();
	    }

	    public static MyLogger getInstance(){
	        if(instance  == null){
	            instance  = new MyLogger();
	            BasicConfigurator.configure();
	            log.setLevel(Level.ALL);
	        }
	        return instance;
	    }

	    public void info(String myclass, Object msg) {
	        log.info("[" + myclass + "] " + msg);

	    }

	    public void error(String myclass, String msg, Exception ce) {               
	        log.error("[" + myclass + "] " + msg, ce);      
	    }

	    public void warning(String myclass, String msg) {
	        log.warn("[" + myclass + "] " + msg);
	    }    
	
}
