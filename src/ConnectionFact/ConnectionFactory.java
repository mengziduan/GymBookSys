package ConnectionFact;

import java.sql.Connection;
import java.sql.DriverManager;
public class ConnectionFactory {
	
	private static String url="jdbc:sqlserver://localhost:1433;DatabaseName=GymManageSys";
	private static String userName="sa";
	private static String password="duan508508";
	
	
	public static Connection getConnection(){
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			return DriverManager.getConnection(url,userName,password);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
