var localObj = window.location;

var contextPath = localObj.pathname.split("/")[1];

var basePath = localObj.protocol+"//"+localObj.host+"/"+contextPath;

var ctx=basePath;



//系统图标 combox
Ext.define('Sys.Icon', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'css',  type: 'string'},
        {name: 'name'}
    ]
});
Ext.define('Sys.IconStore',{
	extend:'Ext.data.Store',
	 proxy: { 
        type: 'ajax',  
        url: 'sysDict.sdict?method=iconList',
       
    	reader: {
            type: 'json',
            root: 'resultList',
            totalProperty: 'totalCount'
        }
    },
    model:'Sys.Icon' 
});
Ext.define('Sys.IconBox', {
	extend: 'Ext.form.field.ComboBox',	
	valueField : 'css',
	displayField : 'name',
    store: new Sys.IconStore()
	,listConfig :{
		getInnerTpl : function(display){
			return '<span class="{css} icon" style="background-position:center left;">&nbsp;</span>'
			+'<span>&nbsp;{name}</span>';
		}
	}
});


// 系统字典 combox
Ext.define('Sys.Dict', {
    extend: 'Ext.data.Model',
    idProperty : 'dictCode',
	fields:['dictCode','dictValue']	
});
Sys.Dict.dicts=new Ext.util.HashMap();
Sys.Dict.getDictValue = function(groupCode,dictCode){
	var store =  Sys.Dict.register(groupCode);
	if(store){
		return store.getDictValue(dictCode);
	}else{
		return dictCode;
	}
};
Sys.Dict.register = function(groupCode){
	var store = this.dicts.get(groupCode);
	if(!store){
		store = new Sys.DictStore({groupCode:groupCode});
		this.dicts.add(groupCode,store);
	}
	return store;
};


Ext.define('Sys.DictStore',{
	extend:'Ext.data.Store',
	model: 'Sys.Dict',
	groupCode:undefined,
	constructor: function(config) {
		config.proxy=Ext.create('Ext.data.proxy.Ajax',{ 
			type: 'ajax',  
			url: 'sysDict.sdict?method=dictList&groupCode='+config.groupCode,
			buildRequest:function(operation){
				var request = Ext.data.proxy.Server.prototype.buildRequest(this,operation);
		        	request.async=false;
		        	return request;
	        },
			reader: {
				type: 'json',
				root: 'resultList',
				totalProperty: 'totalCount'
			}
		});
		this.callParent(arguments);
		this.load();
    },
    load :function(){
    	if(this.loaded){
    		return this;
    	}
    	this.loaded = true;
    	this.callParent(arguments);
    },
    getDictValue:function(dictCode){ 
    	var dict= this.getById(""+dictCode);
    	if(dict){
    		return dict.data.dictValue;
    	}else{
    		return dictCode;
    	}
    }
});

Ext.define('Sys.DictBox', {
	extend: 'Ext.form.field.ComboBox',	
	valueField : 'dictCode',
	displayField : 'dictValue',
    constructor: function(config) {
    	var store = Sys.Dict.register(config.groupCode);
    	config.store = config.store || store;
    	this.callParent(arguments);
    },
    doQuery:function(){
    	this.expand();
    },
    getDictValue:function(dictCode){ 
    	return this.getStore().getDictValue(dictCode);
    }
});

Ext.define('Sys.Org', {
    extend: 'Ext.data.TreeModel',
	fields:['id','name','code']
});
Ext.define('Sys.OrgStore',{
	extend:'Ext.data.TreeStore',
	nodeParam : 'orgId',
	proxy : {
		type : 'ajax',
		url : 'sysDict.sdict?method=orgTree'
	},
	model : 'Sys.Org',
	root : {
		expanded : true,// 根节点是否展开
		id : '-',
		leaf : false
	},
	sorters:[{property:'code'}]
});

Ext.define('Sys.OrgBox', {
	extend: 'Ext.ux.TreePicker',
	xtype:'treepicker',
	displayField:'name',
	rootVisible : false,
    constructor: function(config) {
    	config.store = new Sys.OrgStore(config);
    	this.callParent(arguments);
    },
    getDictValue:function(orgId){ 
    	var org= this.getStore().getById(orgId);
    	if(org){
    		return org.data.name;
    	}else{
    		return orgId;
    	}
    },
    onRender:function(config){
    	this.callParent(arguments);
    	this.getPicker().expandAll();
    }
});


Ext.define('Cms.Channel', {
    extend: 'Ext.data.TreeModel',
	fields:['id','name']
});
Ext.define('Cms.ChannelStore',{
	extend:'Ext.data.TreeStore',
	nodeParam : 'id',
	proxy : {
		type : 'ajax',
		url : 'sysDict.sdict?method=channelTree'
	},
	model : 'Cms.Channel',
	root : {
		expanded : true,// 根节点是否展开
		id : '1',
		leaf : false
	}
});

Ext.define('Cms.ChannelBox', {
	extend: 'Ext.ux.TreePicker',
	xtype:'treepicker',
	displayField:'name',
	rootVisible : false,
    constructor: function(config) {
    	config.store = new Cms.ChannelStore(config);
    	this.callParent(arguments);
    },
    getDictValue:function(id){ 
    	var channel= this.getStore().getById(id);
    	if(channel){
    		return channel.data.name;
    	}else{
    		return channel;
    	}
    },
    onRender:function(config){
    	this.callParent(arguments);
    	this.getPicker().expandAll();
    }
});



Sys.loadScript=function(config) {
    var head = document.head || document.getElementsByTagName("head")[0] || document.documentElement,
    script,
    options={};
    if (typeof config === "object") {
    	options = config;
    	url = undefined;
    }
    s = options || {};
    url = url || s.url;
    callback = s.success;
    script = document.createElement("script");
    script.async = s.async || false;
    script.type = "text/javascript";
    if (s.charset) {
    	script.charset = s.charset;
    }
    if(s.cache === false){
    	url = url+( /\?/.test( url ) ? "&" : "?" )+ "_=" +(new Date()).getTime();
    }
    script.src = url;
    head.insertBefore(script, head.firstChild);
    if(callback){
	    document.addEventListener ? script.addEventListener("load", callback, false) : script.onreadystatechange = function() {
	    	
		    if (/loaded|complete/.test(script.readyState)) {
			    script.onreadystatechange = null;
			    callback();
		    }
	    };
    }
};







