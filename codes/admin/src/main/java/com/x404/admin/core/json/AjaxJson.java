package com.x404.admin.core.json;

import java.util.Map;


/**
 * $.ajax后需要接受的JSON
 *
 * @author
 */
public class AjaxJson {

    private boolean success = true;// 是否成功
    private String message = "操作成功";// 提示信息
    private Object data = null;// 其他信息
    private Map<String, Object> attributes;// 其他参数

    public static final AjaxJson SUCCESS = new AjaxJson(true, "操作成功");


    public AjaxJson() {
        super();
        // TODO Auto-generated constructor stub
    }


    public AjaxJson(boolean success, Object data) {
        super();
        this.success = success;
        this.data = data;
    }


    public AjaxJson(boolean success, String message) {
        super();
        this.success = success;
        this.message = message;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
