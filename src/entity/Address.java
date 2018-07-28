package entity;

public class Address {
	private int id;
	private User user; //地址拥有者
	private String city;
	private String province;	//省份
	private String detail;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	@Override
	public String toString() {
		return "Address [id=" + id + ", user=" + user + ", city=" + city + ", province=" + province + ", detail="
				+ detail + "]";
	}
	
	
}
