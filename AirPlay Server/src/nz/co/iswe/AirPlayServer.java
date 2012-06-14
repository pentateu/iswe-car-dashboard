package nz.co.iswe;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

/**
 * AirPlay Server Implementation
 * @author rafael almeida
 *
 */
public class AirPlayServer implements Runnable {

	public void run() {
		// TODO Auto-generated method stub

		// Map<String, Object> mdnsProperties = new HashMap<String, Object>();

		ServiceInfo serviceInfo = ServiceInfo.create(
		// type
				"_airplay._tcp.local",
				// name
				"AndroidAirplayService",
				// subtype
				null,
				// port
				22555, "Androind Airplay Service");

		try {
			JmDNS jmDNS = JmDNS.create();

			jmDNS.registerService(serviceInfo);

			// service registered
			// start listener
			startListener();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void startListener() throws Exception {
		int port = 22555;
		while (true) {
			ServerSocket providerSocket = new ServerSocket(port);
			Socket s = providerSocket.accept();
			while (true) {
				byte[] ba = read(s);
				if (ba.length > 0) {
					String read = new String(ba);
					if (read.indexOf("/reverse") != -1) {

						String response = "HTTP/1.1 101 Switching Protocols\r\nDate: "
								+ new Date()
								+ "\r\nUpgrade: PTTH/1.0\r\nConnection: Upgrade\r\n\r\n";

						write(s, response.getBytes());
						s = providerSocket.accept();
					} 
					else if (read.indexOf("POST /scrub") != -1) {
						String response = "HTTP/1.1 200 OK\r\n" + "Date: "
								+ new Date() + "\r\n"
								+ "Content-Length: 0\r\n\r\n";

						write(s, response.getBytes());

					} 
					else if (read.indexOf("POST /play HTTP/1.1") != -1) {
						// the play http packet should contain the video url
						String url = getUrl(read);
						String startPos = getStartPos(read);
						break;

					}
				}
				Thread.sleep(1000);
			}
			s.shutdownInput();
			s.shutdownOutput();
			s.close();
			providerSocket.close();

		}
	}

	private String getStartPos(String read) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getUrl(String read) {
		// TODO Auto-generated method stub
		return null;
	}

	private void write(Socket s, byte[] bytes) throws Exception {
		OutputStream os = s.getOutputStream();
		os.write(bytes);
	}

	//read the socket data
	private byte[] read(Socket s) throws Exception {
		InputStream is = s.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while (is.available() > 0) {
			System.out.println(is.available());
			byte[] ba = new byte[10000];
			int read = is.read(ba);
			while (read > 0) {
				baos.write(ba, 0, read);
				if (is.available() > 0) {
					read = is.read(ba);
				} else {
					read = 0;
				}
			}
		}
		return baos.toByteArray();
	}


}
