package nz.co.iswe.android.airplay;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import android.app.Activity;
import android.os.Bundle;

public class AirPlayServerActivity extends Activity {
    
	private static final Logger LOG = Logger.getLogger(AirPlayServerActivity.class.getName());
	
	private AirPlayServer airPlayServer;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Cipher rsaPkCS1OaepCipher = null;
        String transformation = "RSA/None/OAEPWithSHA1AndMGF1Padding";
        try {
        	rsaPkCS1OaepCipher = Cipher.getInstance(transformation);
        	
        	LOG.info("Cipher acquired sucessfully: " + rsaPkCS1OaepCipher.toString());
		} 
        catch (NoSuchAlgorithmException e) {
			LOG.log(Level.SEVERE, "Error getting the Cipher. transformation: " + transformation, e);
		} 
        catch (NoSuchPaddingException e) {
        	LOG.log(Level.SEVERE, "Error getting the Cipher. transformation: " + transformation, e);
		}
		
		
        airPlayServer = AirPlayServer.getIstance();
        
        airPlayServer.setRtspPort(5000);
        
        Thread thread = new Thread(airPlayServer);
        thread.start();
    }
}