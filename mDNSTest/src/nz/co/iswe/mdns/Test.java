package nz.co.iswe.mdns;

public class Test {

	public static void main(String[] args){
		
		new Thread(new AirPlayServer()).start();
		new Thread(new RAOPServer()).start();
		
	}
	
}
