package dao;

import java.util.List;

import entity.Goods;
import entity.Page;

public interface GoodsDao {
	public List<Goods> showAllGoodsAndSort(Page page);
	public List<Goods> showAllGoodsByPrice(String sort,Page page);
	public Goods showGoodsDetailById(int id);
	public List<Goods> searchGoodsAndSort(String key,Page page);
	public List<Goods> showGoodsByKind(int kid);
}
