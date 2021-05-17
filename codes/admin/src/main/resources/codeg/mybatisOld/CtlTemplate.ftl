package ${package}.ctl;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beasy.web.core.common.controller.BaseController;
import com.beasy.web.core.common.json.AjaxJson;
import com.beasy.web.core.common.mybatis.query.MybatisExample;
import com.beasy.web.core.common.page.PageList;
import com.beasy.web.core.common.query.MybatisCriteriaHelper;
import ${package}.entity.${entityName};
import ${package}.service.I${entityName}Service;
@Controller
@RequestMapping("/${entityName?uncap_first}")
public class ${entityName}Ctl extends BaseController {
	private final static Log LOG =  LogFactory.getLog(${entityName}Ctl.class);
	private I${entityName}Service ${entityName?uncap_first}Service;

	@Autowired
	public void set${entityName}Service(I${entityName}Service ${entityName?uncap_first}Service) {
		this.${entityName?uncap_first}Service = ${entityName?uncap_first}Service;
	}

	@RequestMapping(params="method=list${entityName}")
	@ResponseBody
	/**
	 * 用mybatis实现页面的分页显示
	 */
	public PageList<${entityName}> list${entityName}(HttpServletRequest request) {
		MybatisExample mybatisExample = MybatisCriteriaHelper.generateFromRequest(request);
		
		PageList<${entityName}> list=${entityName?uncap_first}Service.PageList(mybatisExample);
		return  list;
	}
	
	

	@RequestMapping(params="method=save${entityName}")
	@ResponseBody
	/*
	 * 用mybatis来增加表数据
	 */
	public AjaxJson save${entityName}(HttpServletRequest request,${entityName} ${entityName?uncap_first}) {
		
		String saveType = ServletRequestUtils.getStringParameter(request,
				"_saveType", "add");
		try {
			if ("add".equals(saveType)) {
				${entityName?uncap_first}Service.save(${entityName?uncap_first});
			} else {
			   ${entityName?uncap_first}Service.updateByIdSelective(${entityName?uncap_first}); 
			}
			return new AjaxJson(true, ${entityName?uncap_first});
			
		} catch (Exception e) {
			LOG.error(e);
			return new AjaxJson(false,"操作失败");
		}
	}

	
	
	/**
	 * 删除这个是用mybatis的方式
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params="method=delete${entityName}")
	@ResponseBody
	public AjaxJson delete${entityName}(@RequestParam("id")String id,HttpServletRequest request) {
	
		try {
			${entityName?uncap_first}Service.deleteById(id);
			return new AjaxJson(true, "操作成功");
		} catch (Exception e) {
			LOG.error(e);
			return new AjaxJson(false, "操作失败");
		}
	}
	
																
}