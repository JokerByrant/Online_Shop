package service.imp;

import java.util.List;

import dao.GoodsDao;
import dao.imp.GoodsDaoImp;
import entity.Goods;
import entity.GoodsKind;
import entity.Page;
import service.GoodsService;

public class GoodsServiceImp implements GoodsService{
	GoodsDao gd = new GoodsDaoImp();
	
	@Override
	public List<Goods> showAllGoodsAndSort(Page page) {
		return gd.showAllGoodsAndSort(page);
	}

	@Override
	public List<Goods> sortGoodsByPrice(String sort,Page page) {
		return gd.showAllGoodsByPrice(sort,page);
	}

	@Override
	public Goods showGoodsDetail(Goods goods) {
		return gd.showGoodsDetailById(goods.getId());
	}

	@Override
	public List<Goods> searchGoodsAndSort(String key,Page page) {
		return gd.searchGoodsAndSort(key, page);
	}

	@Override
	public List<Goods> showGoodsByKind(GoodsKind gk) {
		return gd.showGoodsByKind(gk.getId());
	}

}
