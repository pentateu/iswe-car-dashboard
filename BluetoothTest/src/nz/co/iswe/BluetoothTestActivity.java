package nz.co.iswe;

import java.util.Set;
import java.util.logging.Logger;

import android.app.Activity;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class BluetoothTestActivity extends Activity {
   
	static int REQUEST_ENABLE_BT = 50;
	
	static Logger LOGGER = Logger.getLogger(BluetoothTestActivity.class.getName());
	
	// Create a BroadcastReceiver for ACTION_FOUND
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
	    public void onReceive(Context context, Intent intent) {
	        String action = intent.getAction();
	        // When discovery finds a device
	        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
	            // Get the BluetoothDevice object from the Intent
	            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	            // Add the name and address to an array adapter to show in a ListView
	            LOGGER.info(device.getName() + "\n" + device.getAddress());
	            
	            if(isKnownDevice(device.getName())){
	            	connectToDevice(device);
	            	
	            }
	        }
	    }
	};
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Register the BroadcastReceiver
    	IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
    	registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
    	
        //Bluetooth test
	    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	    if (mBluetoothAdapter == null) {
	        // Device does not support Bluetooth
	    	
	    }
	    else{
	    	//Bluetooth is suported
	    	
	    	if ( ! mBluetoothAdapter.isEnabled()) {
	    	    //Bluetooth is not enabled
	    		Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	    	    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
	    	}
	    	else{
	    		//Bluetooth enabled
	    		startBluetoothProcess();
	    	}
	    }
        
    }
    
    /**
     * Called with an activity initiated by this apps returns
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	//check the request code
    	if(requestCode == REQUEST_ENABLE_BT){
    		//Bluetooth action
    		if(resultCode == RESULT_CANCELED){
    			//task was canceled and did not execute 
    			//do nothing
    		}
    		else if(resultCode == RESULT_OK){
    			//task was completed successfully
    			startBluetoothProcess();
    		}
    		else{
    			//unknown result.. do nothing
    		}
    		
    	}
    }

    /**
     * The device's Bluetooth is enabled and ready to start the process
     */
	private void startBluetoothProcess() {
		
		//First look for paired devices
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		
		
		boolean deviceFound = false;
		// If there are paired devices
		if (pairedDevices.size() > 0) {
		    // Loop through paired devices
		    for (BluetoothDevice device : pairedDevices) {
		        // Add the name and address to an array adapter to show in a ListView
		    	LOGGER.info(device.getName() + "\n" + device.getAddress());
		    	if(isKnownDevice(device.getName())){
		    		deviceFound = true;
	            	connectToDevice(device);
	            }
		    }
		}
		
		if( ! deviceFound){
			//2:Discovering devices
			mBluetoothAdapter.startDiscovery();
		}
		
	}
	
	private void connectToDevice(BluetoothDevice device) {
		BluetoothA2dp a2dp = device.get
	}

	/** 
     * Check if the bluetooth device is known
     * 
     * @param name
     * @return
     */
	private boolean isKnownDevice(String name) {
		return "xxx".equals(name);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}
}