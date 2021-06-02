package com.x404.busi.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author chaox
 * @version V1.0
 *
 */
public class Materiel implements Serializable {
	/**物料编号*/
	@Id
	private String code;
	/**物料名称*/
	private String name;
	/**单位*/
	private String unit;
	/**位置*/
	private String position;
	/**创建时间*/
	@CreatedDate
	private java.util.Date create_date;
	/**创建人*/
	@CreatedBy
	private String create_by;
	/***/
	@LastModifiedDate
	private java.util.Date update_date;
	/***/
	@LastModifiedBy
	private String update_by;
	/**备注*/
	private String remarks;
	public Materiel(){
		super();
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit(){
		return unit;
	}

	public void setUnit(String unit){
		this.unit = unit;
	}

	public String getPosition(){
		return position;
	}

	public void setPosition(String position){
		this.position = position;
	}

	public java.util.Date getCreate_date(){
		return create_date;
	}

	public void setCreate_date(java.util.Date create_date){
		this.create_date = create_date;
	}

	public String getCreate_by(){
		return create_by;
	}

	public void setCreate_by(String create_by){
		this.create_by = create_by;
	}

	public java.util.Date getUpdate_date(){
		return update_date;
	}

	public void setUpdate_date(java.util.Date update_date){
		this.update_date = update_date;
	}

	public String getUpdate_by(){
		return update_by;
	}

	public void setUpdate_by(String update_by){
		this.update_by = update_by;
	}

	public String getRemarks(){
		return remarks;
	}

	public void setRemarks(String remarks){
		this.remarks = remarks;
	}
}
