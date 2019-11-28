package fr.insee.codingdojo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MiniTomcat {

	public static void ecouterLesRequetes() throws IOException {
		// Démarrage de 2 threads à l'écoute des requêtes entrantes sur 8080
		ServerSocket serverSocket = new ServerSocket(8080);
		(new Thread(new Server(serverSocket, 1))).start();
		(new Thread(new Server(serverSocket, 2))).start();
	}

	public static class Server implements Runnable {

		// identifiant pour logger le thread utilisé
		private int id;
		private ServerSocket serverSocket;

		public Server(ServerSocket serverSocket, int id) {
			this.serverSocket = serverSocket;
			this.id = id;
		}

		@Override
		public void run() {
			// Le serveur va traiter un nombre indéterminé de requête
			while (true) {
				BufferedReader bufferedReader = null;
				PrintWriter writer = null;
				try {

					// Ecoute de la socket => le thread est bloqué ici jusqu'à qu'une requête entre
					Socket socket = serverSocket.accept();

					// Lecture des bytes en entree
					InputStream inputStream = socket.getInputStream();
					// Lecture plus précise des characteres
					InputStreamReader reader = new InputStreamReader(inputStream);
					// Lecture encore plus précise des lignes
					bufferedReader = new BufferedReader(reader);

					// Lecture de la première ligne
					String premiereLigneRequete = bufferedReader.readLine();
					System.out.println("La requete " + premiereLigneRequete + " est traité par le thread " + id);

					// Simulation d'un traitement
					Thread.sleep(3000);

					// Ecriture de la réponse
					OutputStream outputStream = socket.getOutputStream();
					writer = new PrintWriter(outputStream);
					writer.println("HTTP/1.1 404");
					// les headers seraient ici
					// ligne vide pour séparer headers et corps
					writer.println("");
					writer.println("Aucune ressource sur le serveur");
					// On envoie la réponse au serveur
					writer.flush();

				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				} finally {
					// on ferme les stream pour clore le traitement
					try {
						bufferedReader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					writer.close();
				}
			}
		}

	}

}