package entity;

import java.sql.Date;

public class Order {
	private String id;
	private Date date;	//订单生成日期
	private int status;	//订单状态
	private Address address; //订单地址
	private User user;	//订单拥有者
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "Order [id=" + id + ", date=" + date + ", status=" + status + ", address=" + address + ", user=" + user
				+ "]";
	}
	
	
}
