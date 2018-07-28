package dao.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.GoodsDao;
import entity.Goods;
import entity.GoodsKind;
import entity.Page;
import entity.User;
import util.JDBCUtil;

public class GoodsDaoImp implements GoodsDao{
	private Connection conn;
	private PreparedStatement st;
	private ResultSet rs;
	
	@Override
	public List<Goods> showAllGoodsAndSort(Page page) {
		conn = JDBCUtil.getConn();
		String sql = "select g.id,g.name,g.brief_intro,g.detail_intro,g.price,g.small_img,g.big_img,g.score,gk.id,gk.name"
				+ " from t_goods g join t_goods_kind gk on g.kind=gk.id order by g.id desc limit ?,?";
		
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, (page.getCurrentPage()-1)*page.getNum());
			st.setInt(2, page.getNum());
			rs = st.executeQuery();
			List<Goods> goods = new ArrayList<>();
			
			while(rs.next()){
				Goods good = new Goods();
				good.setId(rs.getInt(1));
				good.setName(rs.getString(2));
				good.setBrief_info(rs.getString(3));
				good.setDetail_info(rs.getString(4));
				good.setPrice(rs.getFloat(5));
				good.setSmall_img(rs.getString(6));
				good.setBig_img(rs.getString(7));
				good.setScore(rs.getFloat(8));
				GoodsKind gk = new GoodsKind();
				gk.setId(rs.getInt(9));
				gk.setName(rs.getString(10));
				good.setGk(gk);
				goods.add(good);
			}
			
			return goods;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			JDBCUtil.free(conn, st, rs);
		}
	}

	@Override
	public List<Goods> showAllGoodsByPrice(String sort,Page page) {
		conn = JDBCUtil.getConn();
		String sql = "";
		if(sort.equals("asc")){
			sql = "select g.id,g.name,g.brief_intro,g.detail_intro,g.price,g.small_img,g.big_img,g.score,gk.id,gk.name"
					+ " from t_goods g join t_goods_kind gk on g.kind=gk.id order by g.price asc limit ?,?";
		} else {
			sql = "select g.id,g.name,g.brief_intro,g.detail_intro,g.price,g.small_img,g.big_img,g.score,gk.id,gk.name"
					+ " from t_goods g join t_goods_kind gk on g.kind=gk.id order by g.price desc limit ?,?";
		}
				
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, (page.getCurrentPage()-1)*page.getNum());
			st.setInt(2, page.getNum());
			rs = st.executeQuery();
			List<Goods> goods = new ArrayList<>();
			
			while(rs.next()){
				Goods good = new Goods();
				good.setId(rs.getInt(1));
				good.setName(rs.getString(2));
				good.setBrief_info(rs.getString(3));
				good.setDetail_info(rs.getString(4));
				good.setPrice(rs.getFloat(5));
				good.setSmall_img(rs.getString(6));
				good.setBig_img(rs.getString(7));
				good.setScore(rs.getFloat(8));
				GoodsKind gk = new GoodsKind();
				gk.setId(rs.getInt(9));
				gk.setName(rs.getString(10));
				good.setGk(gk);
				goods.add(good);
			}
			
			return goods;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			JDBCUtil.free(conn, st, rs);
		}
	}

	@Override
	public Goods showGoodsDetailById(int id) {
		conn = JDBCUtil.getConn();
		String sql = "";
		sql = "select g.id,g.name,g.brief_intro,g.detail_intro,g.price,g.small_img,g.big_img,g.score,gk.id,gk.name"
					+ " from t_goods g join t_goods_kind gk on g.kind=gk.id where g.id = ?";
				
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();
			Goods good = null;
			
			while(rs.next()){
				good = new Goods();
				good.setId(rs.getInt(1));
				good.setName(rs.getString(2));
				good.setBrief_info(rs.getString(3));
				good.setDetail_info(rs.getString(4));
				good.setPrice(rs.getFloat(5));
				good.setSmall_img(rs.getString(6));
				good.setBig_img(rs.getString(7));
				good.setScore(rs.getFloat(8));
				GoodsKind gk = new GoodsKind();
				gk.setId(rs.getInt(9));
				gk.setName(rs.getString(10));
				good.setGk(gk);
			}
			
			return good;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			JDBCUtil.free(conn, st, rs);
		}
	}

	@Override
	public List<Goods> searchGoodsAndSort(String key, Page page) {
		conn = JDBCUtil.getConn();
		String sql = "";
		sql = "select g.id,g.name,g.brief_intro,g.detail_intro,g.price,g.small_img,g.big_img,g.score,gk.id,gk.name "
				+ "from t_goods g join t_goods_kind gk on g.kind=gk.id "
				+ "where g.name like concat('%',?,'%') or g.brief_intro like concat('%',?,'%') or g.detail_intro like concat('%',?,'%') "
				+ "order by g.id desc limit ?,?";
				
		try {
			st = conn.prepareStatement(sql);
			st.setString(1, key);
			st.setString(2, key);
			st.setString(3, key);
			st.setInt(4, (page.getCurrentPage()-1)*page.getNum());
			st.setInt(5, page.getNum());
			rs = st.executeQuery();
			List<Goods> goods = new ArrayList<>();
			
			while(rs.next()){
				Goods good = new Goods();
				good.setId(rs.getInt(1));
				good.setName(rs.getString(2));
				good.setBrief_info(rs.getString(3));
				good.setDetail_info(rs.getString(4));
				good.setPrice(rs.getFloat(5));
				good.setSmall_img(rs.getString(6));
				good.setBig_img(rs.getString(7));
				good.setScore(rs.getFloat(8));
				GoodsKind gk = new GoodsKind();
				gk.setId(rs.getInt(9));
				gk.setName(rs.getString(10));
				good.setGk(gk);
				goods.add(good);
			}
			
			return goods;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			JDBCUtil.free(conn, st, rs);
		}
	}

	@Override
	public List<Goods> showGoodsByKind(int kid) {
		conn = JDBCUtil.getConn();
		String sql = "";
		sql = "select g.id,g.name,g.brief_intro,g.detail_intro,g.price,g.small_img,g.big_img,g.score,gk.id,gk.name"
					+ " from t_goods g join t_goods_kind gk on g.kind=gk.id where gk.id = ?";
//		select g.id,g.name,g.brief_intro,g.detail_intro,g.price,g.small_img,g.big_img,g.score,gk.id,gk.name from t_goods g join t_goods_kind gk on g.kind=gk.id where gk.id = 1
		List<Goods> goods = null;	
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, kid);
			rs = st.executeQuery();
			goods = new ArrayList<>();
			
			while(rs.next()){
				Goods good = new Goods();
				good.setId(rs.getInt(1));
				good.setName(rs.getString(2));
				good.setBrief_info(rs.getString(3));
				good.setDetail_info(rs.getString(4));
				good.setPrice(rs.getFloat(5));
				good.setSmall_img(rs.getString(6));
				good.setBig_img(rs.getString(7));
				good.setScore(rs.getFloat(8));
				GoodsKind gk = new GoodsKind();
				gk.setId(rs.getInt(9));
				gk.setName(rs.getString(10));
				good.setGk(gk);
				goods.add(good);
			}
			
			return goods;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("sth wrong");
			return null;
		} finally {
			JDBCUtil.free(conn, st, rs);
		}
	}


}
