package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBAccess {
	
	private static Connection connection;
	private static SSHjdbcSession ssHsession;
	
	private static DBAccess instance = null;
	
	
	public static DBAccess getInstance() 
	{
		if (instance == null)
		{
			instance = new DBAccess();
		}
		return instance;
	}
	
	private DBAccess()
	{
		ssHsession = JdbcUtilViaSSH.getConnection();
		connection = ssHsession.getConnection();
	}
	
	public void dispose()
	{
		JdbcUtilViaSSH.close(null, null, ssHsession);
		instance = null;
	}
	
	
	public ResultSet ExecuteQuery (String query) {
		ResultSet result = null;
		try {
			Statement st = connection.createStatement();
			result = st.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		
	}
	
	public void Execute (String query) {
		try {
			Statement st = connection.createStatement();
			st.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int ExecuteInsert (String query) {
		ResultSet result = null;
		int id = -1;
		try {
			Statement st = connection.createStatement();
			st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			result = st.getGeneratedKeys();
			result.next();
			id =  result.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
		
	}
}
