package com.x404.admin.core.databind;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;

import java.util.Date;


public class MyWebBinding implements WebBindingInitializer {

    @Override
    public void initBinder(WebDataBinder binder) {
        // 1. 使用spring自带的CustomDateEditor
        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // binder.registerCustomEditor(Date.class, new
        // CustomDateEditor(dateFormat, true));
        //2. 自定义的PropertyEditorSupport
        binder.registerCustomEditor(Date.class, new DateConvertEditor());
    }


}
