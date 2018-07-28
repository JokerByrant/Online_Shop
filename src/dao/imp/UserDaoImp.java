package dao.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.UserDao;
import entity.User;
import util.JDBCUtil;

public class UserDaoImp implements UserDao{
	private Connection conn;
	private PreparedStatement st;
	private ResultSet rs;
	
	@Override
	public boolean addUser(User user) {
		conn = JDBCUtil.getConn();
		String sql = "insert into t_user(name,password,email,phone) values(?,?,?,?)";
		try {
			st = conn.prepareStatement(sql);
			st.setString(1, user.getName());
			st.setString(2, user.getPassword());
			st.setString(3, user.getEmail());
			st.setString(4, user.getPhone());
			
			int flag = st.executeUpdate();
			
			if(flag > 0){
				return true;
			}
			return false;
		} catch (SQLException e) {
			return false;
		} finally {
			JDBCUtil.free(conn, st, rs);
		}
	}

	@Override
	public User selectUserByNameAndPwd(String name, String password) {
		conn = JDBCUtil.getConn();
		String sql = "select * from t_user where name=? and password=?";
		User user = null;
		try {
			st = conn.prepareStatement(sql);
			st.setString(1, name);
			st.setString(2, password);
			rs = st.executeQuery();
			
			while(rs.next()){
				user = new User();
				user.setId(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setEmail(rs.getString(4));
				user.setPhone(rs.getString(5));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.free(conn, st, rs);
		}
		
		return user;
	}

	@Override
	public List<User> selectAllUsers() {
		conn = JDBCUtil.getConn();
		String sql = "select * from t_user";
		List<User> users = new ArrayList<>();
		try {
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			
			while(rs.next()){
				User user = new User();
				user.setId(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setEmail(rs.getString(4));
				user.setPhone(rs.getString(5));
				users.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.free(conn, st, rs);
		}
		
		return users;
	}

}
