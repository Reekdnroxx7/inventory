package com.x404.admin.manage.codeg.restful.ctl;

import com.x404.admin.core.json.AjaxJson;
import com.x404.admin.manage.codeg.CodeGenerator;
import com.x404.admin.manage.codeg.mybatis.*;
import com.x404.admin.manage.codeg.restful.RestCtlGenerator;
import com.x404.admin.manage.codeg.ctl.BaseCodegCtl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/restfulcodeg")
public class RestfulCodegCtl extends BaseCodegCtl {


//	@RequestMapping(params = "method=listCodegTable")
//	@ResponseBody
//	public PageList<TableConfig> listCodegTable(HttpServletRequest request) {
//		return super.listCodegTable(request);
//	}
//
//	@RequestMapping(params = "method=listCodegField")
//	@ResponseBody
//	public PageList<FieldConfig> listCodegField(HttpServletRequest request,
//			String tableName) {
//		return super.listCodegField(request, tableName);
//	}


    @RequestMapping(params = "method=codeGenerate")
    @ResponseBody
    public AjaxJson generate(HttpServletRequest request, HttpServletResponse response) {
        return super.generate(request, response);
    }

    @Override
    protected List<CodeGenerator> getGenerators() {
        List<CodeGenerator> gs = new ArrayList<CodeGenerator>();
        gs.add(new RestCtlGenerator());
        gs.add(new MybatisEntityGenerator());
        gs.add(new MybatisExampleGenerator());
        gs.add(new MybatisIDaoGenerator());
        gs.add(new MybatisIServiceGenerator());
        gs.add(new MybatisServiceGenerator());
        gs.add(new MybatisMapperGenerator());
        return gs;
    }
}
