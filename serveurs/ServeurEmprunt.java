package serveurs;

import java.io.IOException;
import java.net.ServerSocket;
import services.ServiceEmprunt;

public class ServeurEmprunt implements Runnable{
private ServerSocket socket;
	
	public ServeurEmprunt(int port) throws IOException{
		this.socket = new ServerSocket(port);
	}

	@Override
	public void run() {
		while(true) {
			try {
				new Thread(new ServiceEmprunt(socket.accept())).start();
			} catch (IOException e) {
				try {
					this.socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.err.println("Arrêt du serveur " + this.socket.getLocalPort());
			}
		}
		
	}
}
