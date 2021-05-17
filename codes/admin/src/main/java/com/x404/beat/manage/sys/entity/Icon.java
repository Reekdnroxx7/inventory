package com.x404.beat.manage.sys.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "sys_icon")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Icon implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String css;
    private String name;
    private String path;

    @Id
    @Column(name = "css", nullable = false, length = 64)
    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    @Column(name = "name", length = 64)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "path", length = 128)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


}
