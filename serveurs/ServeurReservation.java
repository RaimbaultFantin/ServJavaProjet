package serveurs;

import java.io.IOException;
import java.net.ServerSocket;

import services.ServiceReservation;

public class ServeurReservation implements Runnable {

	private ServerSocket socket;

	/**
	 * 
	 * @param port
	 * @throws IOException
	 */
	public ServeurReservation(int port) throws IOException {
		this.socket = new ServerSocket(port);
	}

	@Override
	public void run() {
		while (true) {
			try {
				new Thread(new ServiceReservation(socket.accept())).start();
			} catch (IOException e) {
				try {
					this.socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.err.println("Arr�t du serveur " + this.socket.getLocalPort());
			}
		}

	}

}
