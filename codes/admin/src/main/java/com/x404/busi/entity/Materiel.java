package com.x404.busi.entity;

import java.io.Serializable;

/**
 * @Title: Materiel
 * @author
 * @version V1.0
 *
 */
public class Materiel implements Serializable {
    private static final long serialVersionUID = 1L;
	/**主键*/
	private String id;
	/**工序名称*/
	private String procedure_name;
	/**物料编号*/
	private String material_code;
	/**物料名称*/
	private String material_name;
	/**数量*/
	private Integer quantity;
	/**单位*/
	private String unit;
	/**位置*/
	private String position;
	/**创建时间*/
	private java.util.Date create_date;
	/**创建人*/
	private String create_by;
	/***/
	private java.util.Date update_date;
	/***/
	private String update_by;
	/**备注*/
	private String remarks;
	public Materiel(){
		super();
	}
	public Materiel(String id){
		this();
		this.id= id;
	}

	public String getId(){
		return id;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getProcedure_name(){
		return procedure_name;
	}

	public void setProcedure_name(String procedure_name){
		this.procedure_name = procedure_name;
	}

	public String getMaterial_code(){
		return material_code;
	}

	public void setMaterial_code(String material_code){
		this.material_code = material_code;
	}

	public String getMaterial_name(){
		return material_name;
	}

	public void setMaterial_name(String material_name){
		this.material_name = material_name;
	}

	public Integer getQuantity(){
		return quantity;
	}

	public void setQuantity(Integer quantity){
		this.quantity = quantity;
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
