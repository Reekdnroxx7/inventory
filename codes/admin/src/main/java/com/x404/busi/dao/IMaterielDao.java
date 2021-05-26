package com.x404.busi.dao;


import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.x404.admin.core.mybatis.annotation.MyBatisRepository;
import com.x404.busi.entity.Materiel;
import com.xc350.web.base.mybatis.dao.MybatisExample;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.util.List;
@MyBatisRepository
public interface IMaterielDao {


	/**主键查询
	 * @param key
	 * @return
	 */
	@Select({
        "select",
        "id,procedure_name,material_code,material_name,quantity,unit,position,create_date,create_by,update_date,update_by,remarks",
        "from bom_materiel",
        "where id= #{id,jdbcType=VARCHAR}"
    })
	@Results({
		@Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
		@Result(column="procedure_name", property="procedure_name", jdbcType=JdbcType.VARCHAR),
		@Result(column="material_code", property="material_code", jdbcType=JdbcType.VARCHAR),
		@Result(column="material_name", property="material_name", jdbcType=JdbcType.VARCHAR),
		@Result(column="quantity", property="quantity", jdbcType=JdbcType.INTEGER),
		@Result(column="unit", property="unit", jdbcType=JdbcType.CHAR),
		@Result(column="position", property="position", jdbcType=JdbcType.VARCHAR),
		@Result(column="create_date", property="create_date", jdbcType=JdbcType.TIMESTAMP),
		@Result(column="create_by", property="create_by", jdbcType=JdbcType.VARCHAR),
		@Result(column="update_date", property="update_date", jdbcType=JdbcType.TIMESTAMP),
		@Result(column="update_by", property="update_by", jdbcType=JdbcType.VARCHAR),
		@Result(column="remarks", property="remarks", jdbcType=JdbcType.VARCHAR)
	})
	public Materiel selectByKey(Serializable key);

	/**查询所有
	 * @return
	 */
	@Select("select * from bom_materiel ")
	@Results({
		@Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
		@Result(column="procedure_name", property="procedure_name", jdbcType=JdbcType.VARCHAR),
		@Result(column="material_code", property="material_code", jdbcType=JdbcType.VARCHAR),
		@Result(column="material_name", property="material_name", jdbcType=JdbcType.VARCHAR),
		@Result(column="quantity", property="quantity", jdbcType=JdbcType.INTEGER),
		@Result(column="unit", property="unit", jdbcType=JdbcType.CHAR),
		@Result(column="position", property="position", jdbcType=JdbcType.VARCHAR),
		@Result(column="create_date", property="create_date", jdbcType=JdbcType.TIMESTAMP),
		@Result(column="create_by", property="create_by", jdbcType=JdbcType.VARCHAR),
		@Result(column="update_date", property="update_date", jdbcType=JdbcType.TIMESTAMP),
		@Result(column="update_by", property="update_by", jdbcType=JdbcType.VARCHAR),
		@Result(column="remarks", property="remarks", jdbcType=JdbcType.VARCHAR)
	})
	public List<Materiel> selectAll();


	/**
	 * 如果带有分页 此分页仅支持mysql 建议用pageList
	 * @param example
	 * @return
	 */
	@SelectProvider(type = MaterielSqlProvider.class, method = "selectByExample")
	@Results({
		@Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
		@Result(column="procedure_name", property="procedure_name", jdbcType=JdbcType.VARCHAR),
		@Result(column="material_code", property="material_code", jdbcType=JdbcType.VARCHAR),
		@Result(column="material_name", property="material_name", jdbcType=JdbcType.VARCHAR),
		@Result(column="quantity", property="quantity", jdbcType=JdbcType.INTEGER),
		@Result(column="unit", property="unit", jdbcType=JdbcType.CHAR),
		@Result(column="position", property="position", jdbcType=JdbcType.VARCHAR),
		@Result(column="create_date", property="create_date", jdbcType=JdbcType.TIMESTAMP),
		@Result(column="create_by", property="create_by", jdbcType=JdbcType.VARCHAR),
		@Result(column="update_date", property="update_date", jdbcType=JdbcType.TIMESTAMP),
		@Result(column="update_by", property="update_by", jdbcType=JdbcType.VARCHAR),
		@Result(column="remarks", property="remarks", jdbcType=JdbcType.VARCHAR)
	})
	List<Materiel> selectByExample(MybatisExample example);

	/**分页查询，使用此方法，注意pageBounds ，example不要同时有排序
	 * @param example
	 * @param pageBounds
	 * @return
	 */
	@SelectProvider(type = MaterielSqlProvider.class, method = "pageList")
	@Results({
		@Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
		@Result(column="procedure_name", property="procedure_name", jdbcType=JdbcType.VARCHAR),
		@Result(column="material_code", property="material_code", jdbcType=JdbcType.VARCHAR),
		@Result(column="material_name", property="material_name", jdbcType=JdbcType.VARCHAR),
		@Result(column="quantity", property="quantity", jdbcType=JdbcType.INTEGER),
		@Result(column="unit", property="unit", jdbcType=JdbcType.CHAR),
		@Result(column="position", property="position", jdbcType=JdbcType.VARCHAR),
		@Result(column="create_date", property="create_date", jdbcType=JdbcType.TIMESTAMP),
		@Result(column="create_by", property="create_by", jdbcType=JdbcType.VARCHAR),
		@Result(column="update_date", property="update_date", jdbcType=JdbcType.TIMESTAMP),
		@Result(column="update_by", property="update_by", jdbcType=JdbcType.VARCHAR),
		@Result(column="remarks", property="remarks", jdbcType=JdbcType.VARCHAR)
	})
	List<Materiel> pageList(@Param("example") MybatisExample example, PageBounds pageBounds);

	/**查总数
	 * @param example
	 * @return
	 */
	@SelectProvider(type = MaterielSqlProvider.class, method = "countByExample")
	int countByExample(MybatisExample example);

	/**插入 @see {@link #insertSelective}
	 * @param entity
	 */
	@Insert({ "insert into bom_materiel",
		"( id,procedure_name,material_code,material_name,quantity,unit,position,create_date,create_by,update_date,update_by,remarks )",
		"values (",
	    "#{id,jdbcType=VARCHAR}, #{procedure_name,jdbcType=VARCHAR}, #{material_code,jdbcType=VARCHAR}, #{material_name,jdbcType=VARCHAR}, #{quantity,jdbcType=INTEGER}, #{unit,jdbcType=CHAR}, #{position,jdbcType=VARCHAR}, #{create_date,jdbcType=TIMESTAMP}, #{create_by,jdbcType=VARCHAR}, #{update_date,jdbcType=TIMESTAMP}, #{update_by,jdbcType=VARCHAR}, #{remarks,jdbcType=VARCHAR} ",
	")"})
	public void insert(Materiel entity);

	/**选择插入
	 * @param entity
	 */
	@InsertProvider(type = MaterielSqlProvider.class, method = "insertSelective")
	public void insertSelective(Materiel entity);

	/**批量插入
	 * @param entityList
	 */
	@InsertProvider(type = MaterielSqlProvider.class, method = "insertBatch")
	public void insertBatch(List<Materiel> entityList);



	/**主键删除
	 * @param key
	 * @return
	 */
	@Delete({ "delete from bom_materiel", "where id= #{id,jdbcType=VARCHAR}" })
	public int deleteByKey(Serializable key);


	/**条件删除
	 * @param example
	 * @return
	 */
	@DeleteProvider(type = MaterielSqlProvider.class, method = "deleteByExample")
	int deleteByExample(MybatisExample example);


	/**主键更新
     * @param t
     */
    @UpdateProvider(type=MaterielSqlProvider.class, method="updateByKey")
	void updateByKey(Materiel t);

	/**主键更新
     * @param t
     */
	@UpdateProvider(type=MaterielSqlProvider.class, method="updateByKeySelective")
	void updateByKeySelective(Materiel t);

	 /**条件更新
     * @param t
     * @param example
     */
    @UpdateProvider(type=MaterielSqlProvider.class, method="updateByExample")
    void updateByExample(Materiel t, MybatisExample example);

	/**条件更新
     * @param t
     * @param example
     */
	@UpdateProvider(type=MaterielSqlProvider.class, method="updateByExampleSelective")
	void updateByExampleSelective(@Param("record") Materiel t, @Param("example") MybatisExample example);

}
