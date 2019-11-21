import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import fr.insee.codingdojo.model.Utilisateur;

public class Main {

	/**
	 * Fait le taf(f)
	 * 
	 */
	public static void main(String[] args) throws SQLException, IOException {
		
		SessionFactory sessionFactory = configHibernate();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setQi(42);
		session.save(utilisateur);
		session.getTransaction().commit();
		session.close();
		
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
		
		// Source 4 : fichier de properties livré avec l'application (src/main/resources)
		InputStream inputStream = Main.class.getResourceAsStream("/properties.txt");
		Properties properties = new Properties();
		properties.load(inputStream);
		message = properties.getProperty("message");
		
		// Source 5 : fichier de properties externe à l'application
		inputStream = new FileInputStream("$CATALINA_HOME/webapps/application.properties");
		properties = new Properties();
		properties.load(inputStream);
		message = properties.getProperty("message");
		
		
		// Connexion manuelle à la base de données
		Connection connection = DriverManager.getConnection("jdbc:h2:mem:");
		connection.close();

		// DÃ©marrage de 2 threads Ã  l'Ã©coute des requÃªtes entrantes sur 8080
		ServerSocket serverSocket = new ServerSocket(8080);
		(new Thread(new Server(serverSocket, 1))).start();
		(new Thread(new Server(serverSocket, 2))).start();
	}
	
	public static SessionFactory configHibernate() {
		Configuration configuration = new Configuration();
        // Hibernate settings equivalent to hibernate.cfg.xml's properties
        Properties settings = new Properties();
        settings.put(Environment.DRIVER, "org.h2.Driver");
        settings.put(Environment.URL, "jdbc:h2:mem:");
        settings.put(Environment.USER, "sa");
        settings.put(Environment.PASS, "");
        settings.put(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
        settings.put(Environment.SHOW_SQL, "true");
        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        settings.put(Environment.HBM2DDL_AUTO, "create-drop");
        configuration.setProperties(settings);
        
        // Entitées connues par hibernate
        configuration.addAnnotatedClass(Utilisateur.class);
        
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
            .applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
		
	}

}