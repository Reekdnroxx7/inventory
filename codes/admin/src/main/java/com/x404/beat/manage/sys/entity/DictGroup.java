package com.x404.beat.manage.sys.entity;
// default package

import com.x404.beat.core.entity.DataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TTypegroup entity.
 */
@Entity
@Table(name = "sys_dict_group")
public class DictGroup extends DataEntity implements java.io.Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String groupName;
    private String groupCode;

    //	private List<Dict> dicts = new ArrayList<Dict>();
    @Column(name = "group_name")
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Column(name = "group_code")
    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

//	@OneToMany(fetch=FetchType.LAZY,mappedBy="groupCode")
//	@JsonIgnore
//	public List<Dict> getDicts() {
//		return dicts;
//	}
//	public void setDicts(List<Dict> dicts) {
//		this.dicts = dicts;
//	}
}