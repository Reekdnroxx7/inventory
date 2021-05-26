package com.x404.admin.manage.codeg;

import com.x404.admin.manage.codeg.entity.DatasourceConfig;
import com.x404.admin.manage.codeg.model.FieldConfig;
import com.x404.admin.manage.codeg.model.TableConfig;

import java.io.File;
import java.util.List;

public interface CodeGenerator {

    public abstract File generate(DatasourceConfig dataSource,
                                  TableConfig table, List<FieldConfig> fields, String pkg,
                                  String module, String description) throws Exception;

}