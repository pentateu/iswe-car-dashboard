package nz.co.iswe;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;

public class BluetoothTestActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        //Bluetooth test
	    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	    if (mBluetoothAdapter == null) {
	        // Device does not support Bluetooth
	    	
	    }
        
    }
}