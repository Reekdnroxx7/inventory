package ${package}.ctl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eengoo.web.core.common.controller.BaseController;
import com.eengoo.web.core.common.json.AjaxJson;
import com.eengoo.web.core.common.mybatis.query.MybatisExample;
import com.eengoo.web.core.common.page.PageList;
import com.eengoo.web.core.common.query.MybatisCriteriaHelper;
import ${package}.entity.${entityName};
import ${package}.service.I${entityName}Service;


/**
 * 
 * @author Administrator  
 * 
 *  /${entityName?uncap_first}s      HTTP GET =>     模糊查询 name-[eq,LIKE,IN,GT,   GE, LT, LE] {@see QueryFieldOP} =value
 *  /${entityName?uncap_first}/{ ${primaryKey.javaFieldName}}　 HTTP　GET　=>　　得到 ${primaryKey.javaFieldName}的 
 *  /${entityName?uncap_first}/{ ${primaryKey.javaFieldName}}　 HTTP　DELETE　=>　删除 ${primaryKey.javaFieldName}的 
 *  /${entityName?uncap_first} 　 HTTP　PUT　=>　　更新 ${primaryKey.javaFieldName}的  要求Content-Type : application/x-www-form-urlencoded
 *  /${entityName?uncap_first}　　　    HTTP　POST　=>　　新增    要求Content-Type : application/x-www-form-urlencoded
 */

@Controller
public class ${entityName}Ctl extends BaseController {
	private I${entityName}Service ${entityName?uncap_first}Service;

	@Autowired
	public void set${entityName}Service(I${entityName}Service ${entityName?uncap_first}Service) {
		this.${entityName?uncap_first}Service = ${entityName?uncap_first}Service;
	}

	/**
     * 模糊查询
     * @param request
     * @return
     */
    @RequestMapping(value = "/${entityName?uncap_first}s", method = RequestMethod.GET)  
    @ResponseBody   
    public AjaxJson get${entityName}(HttpServletRequest request){
        MybatisExample mybatisExample = MybatisCriteriaHelper.generateFromRequest(request,${entityName}.class);
        PageList<${entityName}> list=${entityName?uncap_first}Service.PageList(mybatisExample);       
        return  new AjaxJson(true,list);
    }
    
    
     <#if primaryKey??>
    /**
     * restful 获取数据
     */
    @RequestMapping(value="/${entityName?uncap_first}/{${primaryKey.javaFieldName}}",method=RequestMethod.GET)
    @ResponseBody   
    public AjaxJson get${entityName}(HttpServletRequest request,@PathVariable String  ${primaryKey.javaFieldName}) {
        logger.info("获取${entityName?uncap_first}信息 ${primaryKey.javaFieldName}=" +  ${primaryKey.javaFieldName});  
        ${entityName} ${entityName?uncap_first} = this.${entityName?uncap_first}Service.findById( ${primaryKey.javaFieldName});
        return new AjaxJson(true, ${entityName?uncap_first});
    }
    
    
    /**
     * restful 新增数据  
     */
    @RequestMapping(value = "/${entityName?uncap_first}", method = RequestMethod.POST)  
    @ResponseBody   
    public AjaxJson add${entityName}(HttpServletRequest request,${entityName} ${entityName?uncap_first}) {
        logger.info("增加${entityName?uncap_first}信息");  
        this.${entityName?uncap_first}Service.save(${entityName?uncap_first});
        return new AjaxJson(true, ${entityName?uncap_first});
    }
    
    @RequestMapping(value = "/${entityName?uncap_first}", method = RequestMethod.PUT)
    @ResponseBody 
    public  AjaxJson update${entityName}(HttpServletRequest request,${entityName} ${entityName?uncap_first}) { 
        
        logger.info("更新${entityName?uncap_first}信息 ${primaryKey.javaFieldName}=" + ${entityName?uncap_first}.get${primaryKey.javaFieldName?cap_first}());  
        this.${entityName?uncap_first}Service.updateByIdSelective(${entityName?uncap_first});
        return new AjaxJson(true,"操作成功");
    }  

    
    
    /**
     * 删除数据
     *   
     */
    @RequestMapping(value="/${entityName?uncap_first}/{${primaryKey.javaFieldName}}",method=RequestMethod.DELETE)
    @ResponseBody
    public AjaxJson delete${entityName}(@PathVariable String  ${primaryKey.javaFieldName},HttpServletRequest request) {
        logger.info("删除${entityName?uncap_first}信息 ${primaryKey.javaFieldName}=" +  ${primaryKey.javaFieldName});  
        this.${entityName?uncap_first}Service.deleteById( ${primaryKey.javaFieldName});
        return new AjaxJson(true, "操作成功");
    }
    </#if>
}