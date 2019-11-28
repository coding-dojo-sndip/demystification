import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import fr.insee.codingdojo.utils.FaireDuHibernate;
import fr.insee.codingdojo.utils.GererDesProperties;
import fr.insee.codingdojo.utils.MiniTomcat;

public class Main {

	/**
	 * Fait le taf(f)
	 * 
	 */
	public static void main(String[] args) throws SQLException, IOException {

		// Connexion manuelle à la base de données
		Connection connection = DriverManager.getConnection("jdbc:h2:mem:");
		connection.close();

		// Hibernate
		FaireDuHibernate.testHibernate();

		// Gestion des properties
		GererDesProperties.gererDesProperties(args);

		// Spring avec fichier XML
		XmlBeanFactory factory = new XmlBeanFactory(new ClassPathResource("beans.xml"));
		UnTrucInterface truc = factory.getBean(UnTrucInterface.class);
		truc.faireUnTruc();

		// Spring avec annotations
		ApplicationContext app = new AnnotationConfigApplicationContext(Main.class);
		UnTrucInterface truc2 = app.getBean(UnTrucInterface.class);
		truc2.faireUnTruc();

		// Ecouter les requetes sur le reseau
		MiniTomcat.ecouterLesRequetes();

	}

	@Bean
	UnTruc getUnTruc() {
		UnTruc truc = new UnTruc();
		truc.setTexte("Bonjour");
		return truc;
	}

}