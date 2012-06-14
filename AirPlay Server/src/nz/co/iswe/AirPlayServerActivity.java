package nz.co.iswe;

import android.app.Activity;
import android.os.Bundle;

public class AirPlayServerActivity extends Activity {
    
	private AirPlayServer airPlayServer;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        airPlayServer = new AirPlayServer();
        
        Thread thread = new Thread(airPlayServer);
        thread.start();
    }
}