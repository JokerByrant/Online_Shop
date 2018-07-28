package entity;

public class Detail {
	private int id;
	private Order order;	//订单
	private Goods goods;	//商品
	private int num;	//数量
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	@Override
	public String toString() {
		return "Detail [id=" + id + ", order=" + order + ", goods=" + goods + ", num=" + num + "]";
	}
	
	
}
