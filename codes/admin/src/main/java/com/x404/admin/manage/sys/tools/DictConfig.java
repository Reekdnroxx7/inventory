package com.x404.admin.manage.sys.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.net.URL;

public class DictConfig {

    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private final static Logger LOGGER = LoggerFactory.getLogger(DictConfig.class);
    public final static String RESOURCE = "dict.xml";
    private DictTables dictTables;

    public DictConfig() {
        URL uri = DictConfig.class.getClassLoader().getResource("dict.xml");
        File f = new File(uri.getFile());

//		try {
//			new DynConfig(f) {
//
//				@Override
//				public void fileChanged(File file) {
//					load(file);
//				}
//
//
//			};
//		} catch (FileNotFoundException e) {
//			LOGGER.error("加载参数失败",e);
//		}
    }

    public DictTables getDictTables() {
        return dictTables;
    }

    private void load(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(DictTable.class, DictTables.class);
            this.dictTables = (DictTables) context.createUnmarshaller().unmarshal(file);
            this.changeSupport.firePropertyChange("dictTables", null, dictTables);
        } catch (JAXBException e) {
            LOGGER.error("加载字典配置失败", e);
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
        if (dictTables != null) {
            this.changeSupport.firePropertyChange("dictTables", null, dictTables);
        }
    }


}
