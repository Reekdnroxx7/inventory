package com.x404.beat.manage.codeg;

import com.x404.beat.manage.codeg.entity.DatasourceConfig;
import com.x404.beat.manage.codeg.model.FieldConfig;
import com.x404.beat.manage.codeg.model.TableConfig;

import java.io.File;
import java.util.List;

public interface CodeGenerator {

    public abstract File generate(DatasourceConfig dataSource,
                                  TableConfig table, List<FieldConfig> fields, String pkg,
                                  String module, String description) throws Exception;

}