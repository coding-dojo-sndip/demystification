package fr.insee.codingdojo.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jboss.jandex.Main;

public class GererDesProperties {

	/**
	 * 
	 * @param args : récupérés du main
	 * @throws IOException
	 */
	public static void gererDesProperties(String[] args) throws IOException {
		String message = "Hello world";
		// Source 1 : argument de la ligne de commande
		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i]);
		}
		// message = args[0];

		// Source 2 : argument -Dcle=valeur
		message = System.getProperty("message");

		// Source 3 : variable d'environnement
		message = System.getenv("message");

		// Source 4 : fichier de properties livr� avec l'application
		// (src/main/resources)
		InputStream inputStream = Main.class.getResourceAsStream("/properties.txt");
		Properties properties = new Properties();
		properties.load(inputStream);
		message = properties.getProperty("message");

		// Source 5 : fichier de properties externe � l'application
		inputStream = new FileInputStream("$CATALINA_HOME/webapps/application.properties");
		properties = new Properties();
		properties.load(inputStream);
		message = properties.getProperty("message");
	}

}