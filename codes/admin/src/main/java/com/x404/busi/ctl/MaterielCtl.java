package com.x404.busi.ctl;

import com.x404.admin.core.controller.BaseController;
import com.x404.admin.core.json.AjaxJson;
import com.x404.admin.core.util.UUIDUtils;
import com.x404.busi.entity.Materiel;
import com.x404.busi.service.MaterielService;
import com.xc350.web.base.mybatis.dao.MybatisExample;
import com.xc350.web.base.mybatis.query.MybatisCriteriaHelper;
import com.xc350.web.base.query.PageList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
@Controller
@RequestMapping("/materiel")
public class MaterielCtl extends BaseController
{
	private final static Log LOG =  LogFactory.getLog(MaterielCtl.class);
	private MaterielService materielService;

	@Autowired
	public void setMaterielService(MaterielService materielService) {
		this.materielService = materielService;
	}

	@RequestMapping(params="method=listMateriel")
	@ResponseBody
	/**
	 * 用mybatis实现页面的分页显示
	 */
	public PageList<Materiel> listMateriel(HttpServletRequest request) {
		MybatisExample mybatisExample = MybatisCriteriaHelper.generateFromRequest(request);

		PageList<Materiel> list= materielService.pageList(mybatisExample);
		return  list;
	}



	@RequestMapping(params="method=saveMateriel")
	@ResponseBody
	/*
	 * 用mybatis来增加表数据
	 */
	public AjaxJson saveMateriel(HttpServletRequest request, Materiel materiel) {

		String saveType = ServletRequestUtils.getStringParameter(request,
				"_saveType", "add");
		try {
			if ("add".equals(saveType)) {
				materiel.setId(UUIDUtils.next());
				materielService.insert(materiel);
			} else {
			   materielService.updateByKeySelective(materiel);
			}
			return new AjaxJson(true, materiel);

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
	@RequestMapping(params="method=deleteMateriel")
	@ResponseBody
	public AjaxJson deleteMateriel(@RequestParam("id")String id,HttpServletRequest request) {

		try {
			materielService.deleteByKey(id);
			return new AjaxJson(true, "操作成功");
		} catch (Exception e) {
			LOG.error(e);
			return new AjaxJson(false, "操作失败");
		}
	}


}