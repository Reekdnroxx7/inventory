package com.x404.admin.manage.sys.dao;


import com.x404.admin.core.mybatis.annotation.MyBatisRepository;
import com.x404.admin.manage.sys.entity.Task;
import com.xc350.web.base.mybatis.dao.MybatisExample;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.util.List;

@MyBatisRepository
public interface ITaskDao {


    /**
     * 主键查询
     *
     * @param key
     * @return
     */
    @Select({
            "select",
            "id,name,bean_name,method_name,cron_expression,status,create_time,update_time",
            "from sys_task",
            "where id= #{id,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "bean_name", property = "beanName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "method_name", property = "methodName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cron_expression", property = "cronExpression", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.CHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
    })
    public Task selectByKey(Serializable key);

    /**
     * 查询所有
     *
     * @return
     */
    @Select("select * from sys_task ")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "bean_name", property = "beanName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "method_name", property = "methodName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cron_expression", property = "cronExpression", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.CHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
    })
    public List<Task> selectAll();


    /**
     * 如果带有分页 此分页仅支持mysql 建议用pageList
     *
     * @param example
     * @return
     */
    @SelectProvider(type = TaskSqlProvider.class, method = "selectByExample")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "bean_name", property = "beanName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "method_name", property = "methodName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cron_expression", property = "cronExpression", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.CHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP)
    })
    List<Task> selectByExample(MybatisExample example);


    /**
     * 查总数
     *
     * @param example
     * @return
     */
    @SelectProvider(type = TaskSqlProvider.class, method = "countByExample")
    int countByExample(MybatisExample example);

    /**
     * 插入 @see {@link #insertSelective}
     *
     * @param entity
     */
    @Insert({"insert into sys_task",
            "(id,name,bean_name,method_name,cron_expression,status,create_time,update_time)",
            "values (",
            "#{id,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, #{beanName,jdbcType=VARCHAR}, #{methodName,jdbcType=VARCHAR}, #{cronExpression,jdbcType=VARCHAR}, #{status,jdbcType=CHAR}, #{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP} ",
            ")"})
    public void insert(Task entity);

    /**
     * 选择插入
     *
     * @param entity
     */
    @InsertProvider(type = TaskSqlProvider.class, method = "insertSelective")
    public void insertSelective(Task entity);

    /**
     * 选择插入
     *
     * @param entityList
     */
    @InsertProvider(type = TaskSqlProvider.class, method = "insertBatch")
    public void insertBatch(List<Task> entityList);


    /**
     * 主键删除
     *
     * @param key
     * @return
     */
    @Delete({"delete from sys_task", "where id= #{id,jdbcType=VARCHAR}"})
    public int deleteByKey(Serializable key);


    /**
     * 条件删除
     *
     * @param example
     * @return
     */
    @DeleteProvider(type = TaskSqlProvider.class, method = "deleteByExample")
    int deleteByExample(MybatisExample example);


    /**
     * 主键更新
     *
     * @param t
     */
    @UpdateProvider(type = TaskSqlProvider.class, method = "updateByKey")
    void updateByKey(Task t);

    /**
     * 主键更新
     *
     * @param t
     */
    @UpdateProvider(type = TaskSqlProvider.class, method = "updateByKeySelective")
    void updateByKeySelective(Task t);

    /**
     * 条件更新
     *
     * @param t
     * @param example
     */
    @UpdateProvider(type = TaskSqlProvider.class, method = "updateByExample")
    void updateByExample(Task t, MybatisExample example);

    /**
     * 条件更新
     *
     * @param t
     * @param example
     */
    @UpdateProvider(type = TaskSqlProvider.class, method = "updateByExampleSelective")
    void updateByExampleSelective(@Param("record") Task t, @Param("example") MybatisExample example);

}
