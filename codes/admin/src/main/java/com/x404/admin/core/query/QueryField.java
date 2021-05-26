package com.x404.admin.core.query;


public class QueryField {
    protected String name;
    protected QueryFieldOP op;
    protected Object value;


    public QueryField() {
        super();
        // TODO Auto-generated constructor stub
    }

    public QueryField(String name, QueryFieldOP op, Object value) {
        super();
        this.name = name;
        this.op = op;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public QueryFieldOP getOp() {
        return op;
    }

    public void setOp(QueryFieldOP op) {
        this.op = op;
    }


    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }


}
