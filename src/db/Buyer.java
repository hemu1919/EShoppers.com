package db;

import java.io.Serializable;

public class Buyer implements Serializable {
	
	private long id;
	private String fname;
	private String lname;
	private String email;
	private String passwd;
	private String addr;
	private int pincode;
	private String mobile;
	private Cart cart;
	
	public Buyer() {}

	public Buyer(String fname, String lname, String email, String passwd, String addr, int pincode, String mobile,
			Cart cart) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.passwd = passwd;
		this.addr = addr;
		this.pincode = pincode;
		this.mobile = mobile;
		this.cart = cart;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public long getCart() {
		return cart.getId();
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Cart getCartObj() {
		return cart;
	}
	
}
