package com.x404.admin.manage.sys.entity;

import com.x404.admin.core.entity.DataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 权限操作表
 *
 * @author 张代浩
 */
@Entity
@Table(name = "sys_operation")
public class Operation extends DataEntity implements java.io.Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String operationName;
    private String operationCode;
    private String transcode;
    private String menuId;


    public Operation() {
        super();
    }

    public Operation(String transcode, String operationCode) {
        super();
        this.transcode = transcode;
        this.operationCode = operationCode;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    //	private Short status;
    @Column(name = "operation_name")
    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    @Column(name = "operation_code")
    public String getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode;
    }

    public String getTranscode() {
        return transcode;
    }

    public void setTranscode(String transcode) {
        this.transcode = transcode;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((operationCode == null) ? 0 : operationCode.hashCode());
        result = prime * result
                + ((transcode == null) ? 0 : transcode.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Operation other = (Operation) obj;
        if (operationCode == null) {
            if (other.operationCode != null)
                return false;
        } else if (!operationCode.equals(other.operationCode))
            return false;
        if (transcode == null) {
            if (other.transcode != null)
                return false;
        } else if (!transcode.equals(other.transcode))
            return false;
        return true;
    }


}