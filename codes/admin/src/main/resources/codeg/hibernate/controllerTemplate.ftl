package ${package};

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.levelappro.web.core.common.controller.BaseController;
import com.levelappro.web.core.common.hibernate.query.CriteriaQuery;
import com.levelappro.web.core.common.query.HibernateQueryHelper;
import com.levelappro.web.core.common.util.ResourceUtil;
import com.levelappro.web.core.common.json.AjaxJson;
import com.levelappro.web.core.common.page.PageList;
import ${source_root_package}.${module}.entity.${entityName};
import ${source_root_package}.${module}.service.I${entityName}Service;
import com.levelappro.web.oa.sys.entity.Menu;
import com.levelappro.web.oa.sys.utils.MenuUtils;

@Controller
@RequestMapping("/${entityName?uncap_first}")
public class ${entityName}Controller extends BaseController {
	
	@Autowired
	private I${entityName}Service ${entityName?uncap_first}Service;
	
	
	public I${entityName}Service get${entityName}Service() {
		return ${entityName?uncap_first}Service;
	}

	public void set${entityName}Service(I${entityName}Service ${entityName?uncap_first}Service) {
		this.${entityName?uncap_first}Service = ${entityName?uncap_first}Service;
	}


	@RequestMapping(params = "method=list${entityName}")
	@ResponseBody
	public PageList<${entityName}> list${entityName}(HttpServletRequest request,PageList<${entityName}> page) {
		CriteriaQuery cq = HibernateQueryHelper.generateFromRequest(request);
		return  ${entityName?uncap_first}Service.getPageList(cq, page);
	}

	@RequestMapping(params = "method=save${entityName}")
	@ResponseBody
	public AjaxJson save${entityName}(HttpServletRequest request, ${entityName} ${entityName?uncap_first}) {
		String saveType = ServletRequestUtils.getStringParameter(request,
				"_saveType", "add");
		if ("add".equals(saveType)) {
			this.${entityName?uncap_first}Service.save(${entityName?uncap_first});
		} else {
			this.${entityName?uncap_first}Service.update(${entityName?uncap_first});
		}
		AjaxJson ajaxJson = new AjaxJson(true, "操作成功");
		ajaxJson.setObj(${entityName?uncap_first});
		return ajaxJson;
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "method=delete${entityName}")
	@ResponseBody
	public AjaxJson del${entityName}(${entityName} ${entityName?uncap_first}, HttpServletRequest request) {
		this.${entityName?uncap_first}Service.delete(${entityName?uncap_first});
		return new AjaxJson(true, "操作成功");
	}
	
																
}

