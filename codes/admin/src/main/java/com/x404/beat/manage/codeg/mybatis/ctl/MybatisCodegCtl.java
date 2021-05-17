package com.x404.beat.manage.codeg.mybatis.ctl;

import com.x404.beat.core.json.AjaxJson;
import com.x404.beat.manage.codeg.ctl.BaseCodegCtl;
import com.x404.beat.manage.codeg.manage.JsGenerator;
import com.x404.beat.manage.codeg.manage.JspGenerator;
import com.x404.beat.manage.codeg.mybatis.*;
import com.x404.beat.manage.codeg.CodeGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/mybatiscodeg")
public class MybatisCodegCtl extends BaseCodegCtl {


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
        gs.add(new MybatisCtlGenerator());
        gs.add(new MybatisEntityGenerator());
        gs.add(new MybatisExampleGenerator());
//		gs.add(new MybatisIDaoGenerator());
//        gs.add(new MybatisIServiceGenerator());
        gs.add(new MybatisServiceGenerator());
        gs.add(new JsGenerator());
        gs.add(new JspGenerator());
        gs.add(new MybatisAnoMapperGenerator());
        gs.add(new MybatisSqlProviderGenerator());
//		gs.add(new MybatisMapperGenerator());
        return gs;
    }
}
