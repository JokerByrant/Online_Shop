package entity;

public class Goods {
	private int id;
	private String name;
	private String brief_info;	//简要描述
	private String detail_info;	//详情描述
	private float price;
	private String small_img;	//图片地址
	private String big_img;
	private float score;	//商品评价
	private GoodsKind gk;	//商品种类
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBrief_info() {
		return brief_info;
	}
	public void setBrief_info(String brief_info) {
		this.brief_info = brief_info;
	}
	public String getDetail_info() {
		return detail_info;
	}
	public void setDetail_info(String detail_info) {
		this.detail_info = detail_info;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getSmall_img() {
		return small_img;
	}
	public void setSmall_img(String small_img) {
		this.small_img = small_img;
	}
	public String getBig_img() {
		return big_img;
	}
	public void setBig_img(String big_img) {
		this.big_img = big_img;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public GoodsKind getGk() {
		return gk;
	}
	public void setGk(GoodsKind gk) {
		this.gk = gk;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.getId()+this.getName().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(this==obj)
		{
			return true;
		}
		if(obj instanceof Goods)
		{
			Goods i = (Goods)obj;
			if(this.getId()==i.getId()&&this.getName().equals(i.getName()))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "Goods [id=" + id + ", name=" + name + ", brief_info=" + brief_info + ", detail_info=" + detail_info
				+ ", price=" + price + ", small_img=" + small_img + ", big_img=" + big_img + ", score=" + score
				+ ", gk=" + gk + "]";
	}


}
