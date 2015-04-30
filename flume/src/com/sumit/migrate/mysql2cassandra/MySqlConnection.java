package com.sumit.migrate.mysql2cassandra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.flume.Context;
/**
 * 
 * @author Sumit Kumar
 *
 */
public class MySqlConnection {
	
	private String host,user,password;
	private int port;
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	MySqlConnection(Context context)
	{
		initialize(context);
	}
	
	private void initialize(Context context) {
	
		host = context.getString("mysqlHost");
		port = context.getInteger("mysqlPort");
		user = context.getString("mysqlUser");
		password = context.getString("mysqlPassword");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/test?"+"user="+user+"&password="+password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<String> getData() {
		List<String> data = new ArrayList<String>();
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select name from user");
			while(resultSet.next()) {
				String userName = resultSet.getString("name");
				data.add(userName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public void cleanUp() {
		if(resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
