package com.x404.beat.models;

import java.io.Serializable;

/**
 * @Title: AccountInfo
 * @author
 * @version V1.0
 *
 */
public class AccountInfo implements Serializable {
    private static final long serialVersionUID = 1L;
	/***/
	private String id;
	/***/
	private Double balance;
	/***/
	private String info;
	/***/
	private String name;
	/***/
	private String password;
	/***/
	private String platform;
	/***/
	private String tag;
	public AccountInfo(){
		super();
	}

	public String getId(){
		return id;
	}

	public void setId(String id){
		this.id = id;
	}

	public Double getBalance(){
		return balance;
	}

	public void setBalance(Double balance){
		this.balance = balance;
	}

	public String getInfo(){
		return info;
	}

	public void setInfo(String info){
		this.info = info;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getPassword(){
		return password;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPlatform(){
		return platform;
	}

	public void setPlatform(String platform){
		this.platform = platform;
	}

	public String getTag(){
		return tag;
	}

	public void setTag(String tag){
		this.tag = tag;
	}
}
