package util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.GoodsKind;

public class SearchKindUtil {
	public static List<GoodsKind> showAllkind(){
		Connection conn = JDBCUtil.getConn();
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "select * from t_goods_kind";
		List<GoodsKind> gks = null;
		try {
			gks = new ArrayList<>();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				GoodsKind gk = new GoodsKind();
				gk.setId(rs.getInt(1));
				gk.setName(rs.getString(2));
				gks.add(gk);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return gks;
	}
}
