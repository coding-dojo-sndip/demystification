package fr.insee.codingdojo.utils;

import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import fr.insee.codingdojo.model.Utilisateur;

public class FaireDuHibernate {

	public static void testHibernate() {
		SessionFactory sessionFactory = configHibernate();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setQi(42);
		session.save(utilisateur);
		session.getTransaction().commit();
		session.close();
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
