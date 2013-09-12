import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseUtils {
	
	public void connectDatabase() throws SQLException {
		String user = "root";
		String password = "123456";
		String url = "jdbc:mysql://localhost:3306/mydb";
		String driver = "com.mysql.jdbc.Driver";
		String tableName = "studinfo";
		String sqlstr;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
	         try{
	        	 Class.forName(driver);
	        	 conn = DriverManager.getConnection(url, user, password);
	        	 stmt = conn.createStatement();
	        	 sqlstr = "insert into " + tableName+ " values ('20000908','honey',21)";
	        	 stmt.executeUpdate(sqlstr);
	        	 
	         } catch (Exception e) {
	        	 System.out.println(e.getMessage());
	         } finally {
	        	 if(rs != null) rs.close();
	        	 if(stmt != null) stmt.close();
	        	 if(conn != null) conn.close();
	         }
	}
}
