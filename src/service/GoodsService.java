package service;

import java.util.List;

import entity.Goods;
import entity.GoodsKind;
import entity.Page;

public interface GoodsService {
	public List<Goods> showAllGoodsAndSort(Page page);
	public List<Goods> sortGoodsByPrice(String sort,Page page);
	public Goods showGoodsDetail(Goods goods);
	public List<Goods> searchGoodsAndSort(String key,Page page);
	public List<Goods> showGoodsByKind(GoodsKind gk);

}
