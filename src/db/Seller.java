package db;

import java.io.Serializable;

public class Seller implements Serializable {
	
	private long id;
	private String email;
	private String passwd;
	private String company_name;
	private String brand_name;
	
	public Seller() {
		super();
	}

	public Seller(String email, String passwd, String company_name, String brand_name) {
		super();
		this.email = email;
		this.passwd = passwd;
		this.company_name = company_name;
		this.brand_name = brand_name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	
}
