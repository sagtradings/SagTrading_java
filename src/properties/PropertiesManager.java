package properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;



public class PropertiesManager {
	private static PropertiesManager instance;
	private Properties prop;
	public static PropertiesManager getInstance(){
		if(instance == null){
			instance = new PropertiesManager();
		}
		return instance;
	}
	
	public String getProperty(String property){
		return prop.getProperty(property);
	}
	
	private PropertiesManager(){
    	prop = new Properties();
    	 
    	try {
               //load a properties file
    		prop.load(new FileInputStream("config.properties"));
    		
               //get the property value and print it out
            System.out.println(prop.getProperty("marketdataurl"));
            System.out.println(prop.getProperty("tradedataurl"));
 
    	} catch (IOException ex) {
    		prop.setProperty("marketdataurl", "tcp://180.166.165.179:41213");
    		prop.setProperty("tradedataurl", "tcp://180.166.165.179:41205");
        }
	}
}
