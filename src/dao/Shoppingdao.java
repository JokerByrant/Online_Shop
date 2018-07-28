package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Goods;
import entity.Order;
import entity.User;
import sun.launcher.resources.launcher;
import entity.Address;
import entity.Comment;
import entity.Detail;
import util.JDBCUtil;

/*
 * ���ڹ����dao
 * 
 * */
public class Shoppingdao {
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	//�Ѷ�������t-order�����ڶ����ύ��������û���ַ����Ǯ���Զ�����status=false��
	public Boolean addorder(Order ord) throws SQLException{
		String sql = "INSERT INTO t_order(id,uid,date,status) "+ "VALUES(?,?,?,?)";
		con=JDBCUtil.getConn();
		pstmt = con.prepareStatement(sql);
		
		pstmt.setString(1,ord.getId());
		pstmt.setInt(2,ord.getUser().getId());
		pstmt.setDate(3, ord.getDate());
		pstmt.setInt(4, ord.getStatus());
		
		
		
		 int i= pstmt.executeUpdate();
		 JDBCUtil.free(con, pstmt, rs);
         if(i==0){
             return false;
         }else{
        	 return true;
         }
	}
	
	//�ύ��ַ��t_address
	public Boolean addaddress(entity.Address address) throws SQLException{
		String sql = "INSERT INTO t_address(id,uid,city,province,detail) "+ "VALUES(?,?,?,?,?)";
		con=JDBCUtil.getConn();
		pstmt = con.prepareStatement(sql);
		
		pstmt.setInt(1,address.getId());
		pstmt.setInt(2,address.getUser().getId());
		pstmt.setString(3,address.getCity());
		pstmt.setString(4, address.getProvince());
		pstmt.setString(5, address.getDetail());
		
		
		 int i= pstmt.executeUpdate();
		 JDBCUtil.free(con, pstmt, rs);
         if(i==0){
             return false;
         }else{
        	 return true;
         }
	}
	//���¶�����status=true ��t_order���͵�ַ
	public boolean changeorder(int status,int addr_id,String string) throws SQLException{
		String sql = " UPDATE t_order SET status=?,addr_id=? "+ " WHERE id =?";
		System.out.println(status+","+addr_id+","+string);
		con=JDBCUtil.getConn();
		pstmt=con.prepareStatement(sql);
		
		pstmt.setInt(1, status);
		pstmt.setInt(2, addr_id);
		pstmt.setString(3,string);
	
        int i= pstmt.executeUpdate();
        JDBCUtil.free(con, pstmt, rs);
         if(i==0){
             return false;
         }else{
        	 return true;
         }
	}
	//�Ѷ�����ϸ�ڼ��뵽 t_detail��
	public Boolean addorderdetail(Detail orderdetail) throws SQLException{
		String sql = "INSERT INTO t_detail(id,oid,gid,num) "+ "VALUES(?,?,?,?)";
		con=JDBCUtil.getConn();
		pstmt = con.prepareStatement(sql);
		
		pstmt.setInt(1,orderdetail.getId());
		pstmt.setString(2,orderdetail.getOrder().getId());
		pstmt.setInt(3,orderdetail.getGoods().getId());
		pstmt.setInt(4,orderdetail.getNum());
		
		 int i= pstmt.executeUpdate();
		 JDBCUtil.free(con, pstmt, rs);
         if(i==0){
             return false;
         }else{
        	 return true;
         }
	}
	//ͨ������id�ҵ����ж���ϸ��(���)����t_order��
		public java.util.List<Detail> getdetailById(String parseInt) throws SQLException {
			String sql = " SELECT * FROM t_detail "+ " WHERE oid =? ";
			con=JDBCUtil.getConn();
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, parseInt);

			rs = pstmt.executeQuery();
			java.util.List<Detail> orderdetails=new ArrayList<>();
			while(rs.next()){
				Detail orderdetail=new Detail();
				orderdetail.setId(rs.getInt("id"));
				Order order = new Order();
				order.setId(parseInt);
				orderdetail.setOrder(order);
				Goods good = new Goods();
				good.setId(rs.getInt("gid"));
				orderdetail.setGoods(good);
				orderdetail.setNum(rs.getInt("num"));
				
				orderdetails.add(orderdetail);
			 }
			JDBCUtil.free(con, pstmt, rs);
			return orderdetails;
			
		}
	//ͨ���û�id�ҵ����ж���(���)����t_order��
	public java.util.List<Order> getOrderById(int parseInt) throws SQLException {
		String sql = " SELECT * FROM t_order "+ " WHERE uid =? ";
		con=JDBCUtil.getConn();
		pstmt = con.prepareStatement(sql);
		
		pstmt.setInt(1, parseInt);
		
		rs = pstmt.executeQuery();

		java.util.List<Order> orders=new ArrayList<>();
		while(rs.next()){
			Order order=new Order();
			order.setId(rs.getString("id"));
			User user = new User();
			user.setId(parseInt);
			order.setUser(user);
			order.setDate(rs.getDate("date"));
			order.setStatus(rs.getInt("status"));
			Address address = new Address();
			address.setId(rs.getInt("addr_id"));
			order.setAddress(address);
			orders.add(order);
		 }
		JDBCUtil.free(con, pstmt, rs);
		return orders;
		
	}
	
	// ͨ����ַid�ҵ���ַ
	public Address showAddressByid(int id){
		con = JDBCUtil.getConn();
		String sql = "select * from t_address where id=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			
			Address address = new Address();
			while(rs.next()){
				address.setId(id);
				User user = new User();
				user.setId(rs.getInt(2));
				address.setUser(user);
				address.setCity(rs.getString(3));
				address.setProvince(rs.getString(4));
				address.setDetail(rs.getString(5));
			}
			return address;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			JDBCUtil.free(con, pstmt, rs);
		}	
		
		
		
	}
	
	// ɾ������
	public boolean delOrderAndDetail(String id){
		con = JDBCUtil.getConn();
		String sql = "delete from t_detail where oid=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			sql = "delete from t_order where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			int j = pstmt.executeUpdate();
			if(j>0){
				System.out.println("2");
				return true;
			}
			else {
				System.out.println("3");
				return false;
			} 

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.free(con, pstmt, rs);
		}
	}
	
	// ͨ���û�id�ҵ������������Ʒ
	public List<Goods> showGoodsById(int id){
		con = JDBCUtil.getConn();
		String sql = "select distinct gid from t_detail where oid in("
				+ "select id from t_order where uid=?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			
			List<Goods> goodss = new ArrayList<>();
			while(rs.next()){
				Goods goods = new Goods();
				goods.setId(rs.getInt(1));
				goodss.add(goods);
			}
			return goodss;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			JDBCUtil.free(con, pstmt, rs);
		}
		
		
	}
	
	// ������ۺ�����
	public boolean addComment(float score,String content,int uid,int gid){
		con = JDBCUtil.getConn();
		String sql = "insert into t_comment(score,content,uid,gid) values(?,?,?,?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setFloat(1, score);
			pstmt.setString(2, content);
			pstmt.setInt(3, uid);
			pstmt.setInt(4, gid);
			int i = pstmt.executeUpdate();
			
			if(i>0){
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.free(con, pstmt, rs);
		}
	}
	
	// ������Ʒ��������
	public List<Comment> showCommentsByGoods(int gid){
		con = JDBCUtil.getConn();
		String sql = "select * from t_comment where gid=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, gid);
			rs = pstmt.executeQuery();
			
			List<Comment> comments = new ArrayList<>();
			while(rs.next()){
				Comment comment = new Comment();
				comment.setId(rs.getInt(1));
				comment.setScore(rs.getFloat(2));
				comment.setContent(rs.getString(3));
				User user = new User();
				user.setId(rs.getInt(4));
				Goods goods = new Goods();
				goods.setId(rs.getInt(5));
				comment.setUser(user);
				comment.setGoods(goods);
				comments.add(comment);
			}
			return comments;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			JDBCUtil.free(con, pstmt, rs);
		}
	}
	
	// �����û�id�����û�
	public String selectUserById(int id) {
		con = JDBCUtil.getConn();
		String sql = "select name from t_user where id=?";
		
		String name = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				name = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.free(con, pstmt, rs);
		}
		
		return name;
	}
	
	// �����û���������������
	public List<Comment> selectCommentsByUser(User user){
		con = JDBCUtil.getConn();
		String sql = "select * from t_comment where uid=?";
		
		List<Comment> comments = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, user.getId());
			rs = pstmt.executeQuery();
			comments = new ArrayList<>();
			
			while(rs.next()){
				Comment comment = new Comment();
				comment.setId(rs.getInt(1));
				comment.setScore(rs.getFloat(2));
				comment.setContent(rs.getString(3));
				Goods goods = new Goods();
				goods.setId(rs.getInt(5));
				comment.setUser(user);
				comment.setGoods(goods);
				comments.add(comment);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.free(con, pstmt, rs);
		}
		
		return comments;
	}
	
	
}
