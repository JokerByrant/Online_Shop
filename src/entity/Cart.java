package entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

//购物车类
public class Cart {

	//购买商品的集合
	private HashMap<Goods,Integer> goods;
	
	//购物车的总金额
	private float totalPrice;

	//构造方法
	public Cart()
	{
		goods = new HashMap<Goods,Integer>();
		totalPrice = 0.0f;
	}
	
	
	public HashMap<Goods, Integer> getGoods() {
		return goods;
	}

	public void setGoods(HashMap<Goods, Integer> goods) {
		this.goods = goods;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	//添加商品进购物车的方法
	public boolean addGoodsInCart(Goods item ,int number)//商品和数量
	{
		if(goods.containsKey(item))
		{
			goods.put(item, goods.get(item)+number);
		}
		else
		{
			goods.put(item, number);	
		}
		calTotalPrice(); //重新计算购物车的总金额
		return true;
	}
	
	//删除商品的方法
	public Cart removeGoodsFromCart(Goods item)
	{
		goods.remove(item);
		calTotalPrice(); //重新计算购物车的总金额
		return this;
	}
	
	//统计购物车的总金额
	public float calTotalPrice()
	{
		float sum = 0.0f;
		Set<Goods> keys = goods.keySet(); //获得键的集合
		Iterator<Goods> it = keys.iterator(); //获得迭代器对象
	    while(it.hasNext())
	    {
	    	Goods i = it.next();
	    	sum += i.getPrice()* goods.get(i);
	    }
	    this.setTotalPrice(sum); //设置购物车的总金额
	    return this.getTotalPrice();
	}
	
//	public static void main(String[] args) {
//		
//		//先创建两个商品对象
//		Items i1 = new Items(1,"沃特篮球鞋","温州",200,500,"001.jpg");
//		Items i2 = new Items(2,"李宁运动鞋","广州",300,500,"002.jpg");
//		Items i3 = new Items(1,"沃特篮球鞋","温州",200,500,"001.jpg");
//		
//		Cart c = new Cart();
//		c.addGoodsInCart(i1, 1);
//		c.addGoodsInCart(i2, 2);
//		//再次购买沃特篮球鞋，购买3双
//		c.addGoodsInCart(i3, 3);
//		
//		
//		//变量购物商品的集合
//		Set<Map.Entry<Items, Integer>> items= c.getGoods().entrySet();
//		for(Map.Entry<Items, Integer> obj:items)
//		{
//			System.out.println(obj);
//		}
//		
//		
//		System.out.println("购物车的总金额："+c.getTotalPrice());
//		
//	}
	
}
