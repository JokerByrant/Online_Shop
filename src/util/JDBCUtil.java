package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class JDBCUtil {
	private static String driverClass;
	private static String url;
	private static String username;
	private static String password;
	
	//类加载时初始化
	static{
		ResourceBundle rb = ResourceBundle.getBundle("db");
		driverClass = rb.getString("driverClass");
		url = rb.getString("url");
		username = rb.getString("username");
		password = rb.getString("password");
	}
	
   
	/**
	 * 连接的获取
	 * @return
	 */
	public static Connection getConn(){
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url,username,password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}
	
	 /**
     * 资源释放
     * @param conn
     * @param st
     * @param rs
     */
	public static void free(Connection conn,Statement st,ResultSet rs){
		try {
			if(conn!=null){
				conn.close();
			}
			if(st!=null){
				st.close();
			}
			if(rs!=null){
				rs.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
