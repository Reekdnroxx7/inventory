package com.x404.beat.manage.sys.tools;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "dictTables")
@XmlAccessorType(XmlAccessType.FIELD)
public class DictTables {
    @XmlElements(value = {@XmlElement(name = "dictTable")})
    private List<DictTable> dictTables;

    public List<DictTable> getDictTables() {
        return dictTables;
    }

    public void setDictTables(List<DictTable> dictTables) {
        this.dictTables = dictTables;
    }


}
