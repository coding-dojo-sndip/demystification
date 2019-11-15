import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

	/**
	 * Fait le taf(f)
	 * 
	 */
	public static void main(String[] args) throws SQLException, IOException {
		System.out.println("Hello world");
		Connection connection = DriverManager.getConnection("jdbc:h2:mem:");
		connection.close();

		// Démarrage de 2 threads à l'écoute des requêtes entrantes sur 8080
		ServerSocket serverSocket = new ServerSocket(8080);
		(new Thread(new Server(serverSocket, 1))).start();
		(new Thread(new Server(serverSocket, 2))).start();
	}

}