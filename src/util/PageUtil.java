package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.Page;

public class PageUtil {
	public static int total(String table,int num){
		Connection conn = JDBCUtil.getConn();
		Statement st = null;
		ResultSet rs = null;
		String sql = "select count(*) from " + table;
		try {
			
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			
			if(rs.next()){
				int total = rs.getInt(1);
				return total%num == 0 ? total/num :total/num + 1;
			}
			return 0;
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		} finally{
			JDBCUtil.free(conn, st, rs);
		}
	}

}
