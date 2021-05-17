package com.x404.beat.dao;


import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.x404.beat.core.mybatis.annotation.MyBatisRepository;
import com.x404.beat.models.AccountInfo;
import com.xc350.web.base.mybatis.dao.MybatisExample;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.util.List;


@MyBatisRepository
public interface IAccountInfoDao {


	/**主键查询
	 * @param key
	 * @return
	 */
	@Select({
        "select",
        "id,balance,info,name,password,platform,tag",
        "from account_info",
        "where id= #{id,jdbcType=BIGINT}"
    })
	@Results({
		@Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
		@Result(column="balance", property="balance", jdbcType=JdbcType.DOUBLE),
		@Result(column="info", property="info", jdbcType=JdbcType.VARCHAR),
		@Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
		@Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
		@Result(column="platform", property="platform", jdbcType=JdbcType.VARCHAR),
		@Result(column="tag", property="tag", jdbcType=JdbcType.VARCHAR)
	})
	public AccountInfo selectByKey(Serializable key);

	/**查询所有
	 * @return
	 */
	@Select("select * from account_info ")
	@Results({
		@Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
		@Result(column="balance", property="balance", jdbcType=JdbcType.DOUBLE),
		@Result(column="info", property="info", jdbcType=JdbcType.VARCHAR),
		@Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
		@Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
		@Result(column="platform", property="platform", jdbcType=JdbcType.VARCHAR),
		@Result(column="tag", property="tag", jdbcType=JdbcType.VARCHAR)
	})
	public List<AccountInfo> selectAll();


	/**
	 * 如果带有分页 此分页仅支持mysql 建议用pageList
	 * @param example
	 * @return
	 */
	@SelectProvider(type = AccountInfoSqlProvider.class, method = "selectByExample")
	@Results({
		@Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
		@Result(column="balance", property="balance", jdbcType=JdbcType.DOUBLE),
		@Result(column="info", property="info", jdbcType=JdbcType.VARCHAR),
		@Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
		@Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
		@Result(column="platform", property="platform", jdbcType=JdbcType.VARCHAR),
		@Result(column="tag", property="tag", jdbcType=JdbcType.VARCHAR)
	})
	List<AccountInfo> selectByExample(MybatisExample example);

	/**分页查询，使用此方法，注意pageBounds ，example不要同时有排序
	 * @param example
	 * @param pageBounds
	 * @return
	 */
	@SelectProvider(type = AccountInfoSqlProvider.class, method = "pageList")
	@Results({
		@Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
		@Result(column="balance", property="balance", jdbcType=JdbcType.DOUBLE),
		@Result(column="info", property="info", jdbcType=JdbcType.VARCHAR),
		@Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
		@Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
		@Result(column="platform", property="platform", jdbcType=JdbcType.VARCHAR),
		@Result(column="tag", property="tag", jdbcType=JdbcType.VARCHAR)
	})
	List<AccountInfo> pageList(@Param("example") MybatisExample example, PageBounds pageBounds);

	/**查总数
	 * @param example
	 * @return
	 */
	@SelectProvider(type = AccountInfoSqlProvider.class, method = "countByExample")
	int countByExample(MybatisExample example);

	/**插入 @see {@link #insertSelective}
	 * @param entity
	 */
	@Insert({ "insert into account_info",
		"( id,balance,info,name,password,platform,tag )",
		"values (",
	    "#{id,jdbcType=VARCHAR}, #{balance,jdbcType=DOUBLE}, #{info,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, #{platform,jdbcType=VARCHAR}, #{tag,jdbcType=VARCHAR} ",
	")"})
	public void insert(AccountInfo entity);

	/**选择插入
	 * @param entity
	 */
	@InsertProvider(type = AccountInfoSqlProvider.class, method = "insertSelective")
	public void insertSelective(AccountInfo entity);

	/**批量插入
	 * @param entityList
	 */
	@InsertProvider(type = AccountInfoSqlProvider.class, method = "insertBatch")
	public void insertBatch(List<AccountInfo> entityList);



	/**主键删除
	 * @param key
	 * @return
	 */
	@Delete({ "delete from account_info", "where id= #{id,jdbcType=VARCHAR}" })
	public int deleteByKey(Serializable key);


	/**条件删除
	 * @param example
	 * @return
	 */
	@DeleteProvider(type = AccountInfoSqlProvider.class, method = "deleteByExample")
	int deleteByExample(MybatisExample example);


	/**主键更新
     * @param t
     */
    @UpdateProvider(type=AccountInfoSqlProvider.class, method="updateByKey")
	void updateByKey(AccountInfo t);

	/**主键更新
     * @param t
     */
	@UpdateProvider(type=AccountInfoSqlProvider.class, method="updateByKeySelective")
	void updateByKeySelective(AccountInfo t);

	 /**条件更新
     * @param t
     * @param example
     */
    @UpdateProvider(type=AccountInfoSqlProvider.class, method="updateByExample")
    void updateByExample(AccountInfo t, MybatisExample example);

	/**条件更新
     * @param t
     * @param example
     */
	@UpdateProvider(type=AccountInfoSqlProvider.class, method="updateByExampleSelective")
	void updateByExampleSelective(@Param("record") AccountInfo t, @Param("example") MybatisExample example);

}
