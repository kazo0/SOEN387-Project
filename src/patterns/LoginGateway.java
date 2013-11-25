package patterns;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.DBStatus;
import models.DomainObject;
import models.User;
import database.DBAccess;
import database.JdbcUtilViaSSH;
import database.SSHjdbcSession;

public class LoginGateway 
{
	private static LoginGateway soleInstance = null;
	
	public static LoginGateway getInstance() 
	{
		if (soleInstance == null) soleInstance =  new LoginGateway();	
		return soleInstance;
	}
		
	public User Login(String un, String pw)
	{
			try
			{
				String query = "select * from Users where username ='" + un 
						     + "' and password ='" + pw + "'";				
				ResultSet rs = DBAccess.getInstance().ExecuteQuery(query);
				
				if(rs == null)
					return null;
				rs.next();
				int id = rs.getInt("id");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String username = rs.getString("username");
				String password = rs.getString("password");
				boolean isAdmin = rs.getBoolean("isAdmin");
				String phone = rs.getString("phone");
				String city = rs.getString("city");
				String country = rs.getString("country");
				String province = rs.getString("province");
				String line1 = rs.getString("line1");
				String line2 = rs.getString("line2");
				return new User(id, firstName,lastName,username,password,isAdmin, phone, city, country, province, line1, line2);
			}
			catch (Exception ex) 
			{
				return null;
			}			
	}
	
	public User getUser(int id)
	{
		try
		{
			String query = "select * from Users where id ='" + id + "'";				
			ResultSet rs = DBAccess.getInstance().ExecuteQuery(query);
			
			if(rs == null)
				return null;
			rs.next();
			int id_ = rs.getInt("id");
			String firstName = rs.getString("firstName");
			String lastName = rs.getString("lastName");
			String username = rs.getString("username");
			String password = rs.getString("password");
			boolean isAdmin = rs.getBoolean("isAdmin");
			String phone = rs.getString("phone");
			String city = rs.getString("city");
			String country = rs.getString("country");
			String province = rs.getString("province");
			String line1 = rs.getString("line1");
			String line2 = rs.getString("line2");
			
			return new User(id_, firstName,lastName,username,password,isAdmin, phone, city, country, province, line1, line2);
		}
		catch (Exception ex) 
		{
			return null;
		}			
		
	}
	
	public void dispose()
	{
		soleInstance = null;
	}
	
}

