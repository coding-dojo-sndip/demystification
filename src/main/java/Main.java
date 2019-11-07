import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
	
	/**
	 * Fait le taf(f)
	 */
	public static void main(String[] args) throws SQLException {
		System.out.println("Hello world");
		Connection connection = DriverManager.getConnection("jdbc:h2:mem:");
		connection.close();
	}
	
}