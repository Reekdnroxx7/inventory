/*
 * ! Ext JS Library 4.0 Copyright(c) 2006-2011 Sencha Inc. licensing@sencha.com
 * http://www.sencha.com/license
 */

Ext.define('MyDesktop.Menu', {
    extend: 'Ext.ux.desktop.Module',

    requires: [
        'Ext.data.ArrayStore',
        'Ext.util.Format',
        'Ext.grid.Panel',
        'Ext.grid.RowNumberer'
    ],

    id:'menu',

    init : function(){
        this.launcher = {
            text: '菜单管理',
            iconCls:'icon-grid'
        };
    },

    createWindow : function(){
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow('menu');
        if(!win){
        	/*
			 * 重新定义Ext.tree.Column 不得已 改变 href 和 iconCls
			 */
Ext.define('Ext.tree.MyColumn',{
	extend:'Ext.tree.Column',
	xtype:'mytreecolumn',
	cellTpl: [
	           '<tpl for="lines">',
	               '<img src="{parent.blankUrl}" class="{parent.childCls} {parent.elbowCls}-img ',
	               '{parent.elbowCls}-<tpl if=".">line<tpl else>empty</tpl>"/>',
	           '</tpl>',
	           '<img src="{blankUrl}" class="{childCls} {elbowCls}-img {elbowCls}',
	               '<tpl if="isLast">-end</tpl><tpl if="expandable">-plus {expanderCls}</tpl>"/>',
	           '<tpl if="checked !== null">',
	               '<input type="button" role="checkbox" <tpl if="checked">aria-checked="true" </tpl>',
	                   'class="{childCls} {checkboxCls}<tpl if="checked"> {checkboxCls}-checked</tpl>"/>',
	           '</tpl>',
	           '<img src="{blankUrl}" class="{childCls} {baseIconCls} ',
	               '{baseIconCls}-<tpl if="leaf">leaf<tpl else>parent</tpl> {icon}"',
	               '/>',
	               '<span class="{textCls} {childCls}">{value}</span>'
	      ]
});		

/**
 * 定义Menu 类
 */
Ext.define('Sys.Menu', {
    extend: 'Ext.data.TreeModel',
    fields: ["id",'type','name','url','parentId','parentIds',
             'icon','sort', 'href','level','_saveType',
	         {name:'leaf',convert:function(value,record){
	        	 return record.data.type == '0';
	         }}
    ] 
});

Ext.namespace("Menu");
Menu.dictBox = Ext.create("Sys.DictBox",{
	groupCode:'sys_menu.type'
});

// 删除
Menu.delRecord = function(grid, rowIndex, colIndex){
	Ext.Msg.confirm("提示","你确定要删除该记录么?",function(confirm){
		if("yes" == confirm){
			var rec = grid.getStore().getAt(rowIndex);
			Ext.Ajax.request({
			    url: 'menuController.ajax?method=deleteMenu',
			    params:rec.data,
			    success: function(response){
			        var text = response.responseText;
			        var result = Ext.JSON.decode(text);
			        if(result.success){
			        	var parent = Menu.treeStore.getNodeById(rec.data.parentId);
			        	parent.removeChild(rec)
			        	Ext.Msg.alert("信息","删除成功");
			        }else{
			        	Ext.Msg.alert("错误",result.message);
			        }
			    }
			});
		}		
	});	
};


// 菜单树store
Menu.treeStore = new Ext.data.TreeStore({
	 nodeParam : 'menuId',
	 proxy: { 
         type: 'ajax',  
         url: 'menuController.ajax?method=listMenu'    	
     },
     model:'Sys.Menu',
     root:{ 
    	 name:'顶级菜单',
         expanded : true,// 根节点是否展开
         icon:'folder',
         id:'1',
         type:'1'
     },
     sorters:[{property:'sort'}]
});

// 菜单panel
Menu.menu_panel = Ext.create("Ext.tree.Panel",{
//	title:'菜单管理',
	autoScroll : true,
	store : Menu.treeStore,
	rootVisible : true, 
	rowLines:true,
	columnLines :true,
	columns:[{
		text: '菜单名称',
		xtype: 'mytreecolumn', // extjs tree 必须定义一个treecolumn
		dataIndex: 'name',
		width: 250	        
	},{
		xtype:'actioncolumn',
		text:'操作',
		width:150,
		align: 'center',
		items:[{
			iconCls:'Applicationedit',tooltip:'编辑',
			handler:function(grid, rowIndex, colIndex){
				var rec = grid.getStore().getAt(rowIndex);
				Menu.detailWin.showEdit(rec);
			}},
			'-',
			{	
				iconCls:'Pageadd',
				tooltip:'添加子菜单',
				isDisabled: function( view, rowIdx, colIdx, item, record){
					return record.isLeaf();
				},
				handler:function(grid, rowIndex, colIndex){      	  				
					var rec = grid.getStore().getAt(rowIndex);	
					rec.expand(0);
					Menu.detailWin.showAdd(rec);
				}
			},
			'-',
			{iconCls:'delete',tooltip:'删除',handler:Menu.delRecord},
			'-',
  	  		{
				iconCls:'Cogadd',tooltip:'添加权限',
				isDisabled: function( view, rowIdx, colIdx, item, record){
					return !record.isLeaf();
			
				},
				handler:function(grid, rowIndex, colIndex){      	  				
					var rec = grid.getStore().getAt(rowIndex);
					Operation.win.showMenu(rec);
				}
  	  		}]
		},{
			text:'菜单url',
			dataIndex:'href',
			width: 200
    	},{
    		text:'菜单类型',
    		dataIndex:'type',
    		renderer:function(v){return Menu.dictBox.getDictValue(v)}
    	},{
    		text:'菜单排序',
    		dataIndex:'sort'
    	},{
    		text:'菜单图标',
    		dataIndex:'icon',
    		align: 'center',
    		renderer: function(v){
    			 return "<span class='icon "+v+"'>&nbsp;</span>";
    		}
		}      	
    ]
});

Ext.namespace("Operation");
Operation.store = new Ext.data.Store({
	fields: ['operationCode','operationName'],
	pageSize : -1,
	proxy: {
		type: 'ajax',
	    url : "menuController.ajax?method=listOperation",
	    reader: {
	        type: 'json',
	        root: 'resultList',
	        totalProperty: 'totalCount'
	    }
	}
});
Operation.gridPanel = Ext.create("Ext.grid.Panel",{
	store:Operation.store,
	width:400,
	height:300,
	rowLines:true,
	loadMask : false,
 	columnLines :true,
	columns:[{xtype: 'rownumberer'},
	         {text:'操作代码',dataIndex:'operationCode'},
	         {text:'操作名称',dataIndex:'operationName'},
	         {
	        	 text:'操作',
	        	 xtype:'actioncolumn',
	        	 flex:1,
	        	 align: 'center',
	        	 items:[{
	        		 iconCls:'delete',
	        		 altText:'删除',
	        		 handler:function(grid, rowIndex, colIndex){
	        			 var rec = grid.getStore().getAt(rowIndex);
	        			 Operation.delRecord(rec);
	        		 }
	        	 }]
	         }]
});

Operation.win = Ext.create('Ext.window.Window', {
	title:'权限管理',
    items: Operation.gridPanel, 
    modal:true,
    
    closeAction : 'hide',
    buttons:[{text:'添加',iconCls:'add',handler:function(){
    	Operation.detailWin.show();
    	Operation.detailPanel.getForm().reset();
    }}],
    showMenu:function(menu){
    	this.show();
    	this.menu = menu;
    	var params = {};
    	params.menuId=  menu.data.id;
    	Operation.store.load({params:params});
    },
	getMenu:function(){
		return this.menu;
	}
});

Operation.detailPanel = Ext.create('Ext.form.Panel', {
    bodyPadding: 10,
    defaultType: 'textfield',
    items: [{
        fieldLabel: '操作代码',
        name: 'operationCode',
        allowBlank: false
    },{
        fieldLabel: '操作名称',
        name: 'operationName',
        allowBlank: false
    },
    new Ext.form.Hidden({name:'_saveType',value:'add'})]
});

Operation.save = function(){
	var params = {};
	params.menuId=Operation.win.getMenu().data.id;
	var form = Operation.detailPanel.getForm();
	form.submit({
		 clientValidation: true,
		 url: 'menuController.ajax?method=saveOperation',
		 params:params,
		 success: function(form, action) {
			 Operation.detailWin.hide();
			 Operation.store.reload();
		 },
		 failure: function(form, action) {
		        switch (action.failureType) {
		            case Ext.form.action.Action.CLIENT_INVALID:
		                Ext.Msg.alert('失败', '无效的值');
		            break;
		        case Ext.form.action.Action.CONNECT_FAILURE:
		            Ext.Msg.alert('失败', '网络连接失败');
		            break;
		        case Ext.form.action.Action.SERVER_INVALID:
		           Ext.Msg.alert('失败', action.result.message);
		       }
		}
	});
}

Operation.delRecord = function(rec){
	Ext.Ajax.request({
	    url: 'menuController.ajax?method=deleteOperation',
	    params:rec.data,
	    success: function(response){
	        var text = response.responseText;
	        var result = Ext.JSON.decode(text);
	        if(result.success){
	        	Ext.Msg.alert("信息","删除成功");
	        	Operation.store.remove(rec);
	        }else{
	        	Ext.Msg.alert("错误",result.message);
	        }
	    }
	});
}

Operation.detailWin = Ext.create('Ext.window.Window', {
	title:'菜单管理',
    layout: 'fit',
    modal:true,
    items: Operation.detailPanel,
    closeAction : 'hide',
    buttons:[{text:'提交', handler:Operation.save }]
});
Menu.save=function(){
	Menu.detailPanel.getForm().submit({
		 clientValidation: true,
		 url: 'menuController.ajax?method=saveMenu',
		 success: function(form, action) {
		      Ext.Msg.alert("信息","操作成功");
		      if(Menu._saveType.getValue() == 'add'){
		    	  var menu = action.result.obj;
		    	  var rec = Ext.create('Sys.Menu',menu);
		    	  Menu.detailPanel.getParent().appendChild(rec);
		      }else{
		    	  Menu.detailPanel.getForm().updateRecord(); // 修改后数据信息同步
		      }	
		      Menu.detailWin.hide();
		 },
		 failure: function(form, action) {
		        switch (action.failureType) {
		            case Ext.form.action.Action.CLIENT_INVALID:
		                Ext.Msg.alert('失败', '无效的值');
		                break;
		            case Ext.form.action.Action.CONNECT_FAILURE:
		                Ext.Msg.alert('失败', '网络连接失败');
		                break;
		            case Ext.form.action.Action.SERVER_INVALID:
		               Ext.Msg.alert('失败', action.result.message);
		       }
	    }
	});
}

Menu.parentName = new Ext.form.field.Text({
	fieldLabel:'父菜单:',
	width:450,
	readOnly:true,
	fieldStyle:'background:#E6E6E6',
	name:'parentName'
});
Menu.parentId = new Ext.form.field.Text({
	fieldLabel:'父菜单:',
	width:450,
	readOnly:true,
	hidden:true,
	fieldStyle:'background:#E6E6E6',
	name:'parentId'
});
Menu.nameField = new Ext.form.field.Text({
	fieldLabel:'菜单名称:',
	width:450,
	allowBlank : false,
	name:'name'
});

Menu.type = new Ext.form.RadioGroup({
	 fieldLabel : "菜单类型",
	 horizontal:true, 
	 vertical: true,
	 width:350,
	 anchor:'100%',
	 items:[{boxLabel:'功能菜单',inputValue:'0',name:'type',checked:'true'},
	        {boxLabel:'父菜单',name:'type',inputValue:'1'}],
    listeners:{
    	'change': function(t, newValue, oldValue, eOpts){
    		if(newValue.type==1){ 
    			Menu.href.hide();
    		}else{
    			Menu.href.show();
    		}
    	}
    }
});

Menu.href = new Ext.form.field.Text({
	fieldLabel:'菜单路径:',
	width:450,
	name:'href'
});

Menu.icon = new Sys.IconBox({
	fieldLabel:'菜单图标:',
	name:'icon'
});

Menu.sort = new Ext.form.field.Number({
	fieldLabel:'菜单排序',
	value:30,
	name:'sort'
});

Menu.isShow = new Ext.form.RadioGroup({
	 fieldLabel : "是否显示",
	 width:350,
	 anchor:'95%',
	 items:[{boxLabel:'是',inputValue:'1',name:'isShow',checked:'true'},
	        {boxLabel:'否',name:'isShow',inputValue:'0'}]
});

Menu._saveType = new Ext.form.Hidden({name:'_saveType',value:'add'});


Menu.detailPanel = Ext.create('Ext.form.Panel', {
    bodyPadding: 10,
    defaultAlign:'center',
    buttonAlign: 'center',
    region: 'center',
    items: [Menu.parentName,Menu.parentId,Menu.nameField,Menu.type,Menu.href,
            Menu.icon,Menu.sort,Menu.isShow,Menu._saveType,
            new Ext.form.Hidden({name:'parentIds'})  
    		,new Ext.form.Hidden({name:'id'})
            ],   
    showEdit:function(record){
    	this.loadRecord(record);
    	var parentRec = Menu.treeStore.getById(record.data.parentId);
    	this.setParent(parent);
    	if(parentRec){
    		Menu.parentName.setValue(parentRec.data.name);
    	}
    	Menu._saveType.setValue('update');
    },
    showAdd:function(parent){
    	this.getForm().reset();
    	this.setParent(parent);
    	Menu.parentName.setValue(parent.data.name);
    	Menu.parentId.setValue(parent.data.id);
    	Menu._saveType.setValue('add');
    },
    setParent:function(record){
    	this.parent = record;
    },
    getParent:function(){
    	return this.parent ;
    }
});


Menu.detailWin = Ext.create('Ext.window.Window', {
	title:'菜单管理',
    layout: 'fit',
    items: Menu.detailPanel,
    closeAction : 'hide',    
    showAdd:function(parent){
    	Menu.detailPanel.showAdd(parent);
    	this.show();
    },
    showEdit:function(record){
    	Menu.detailPanel.showEdit(record);
    	this.show();
    },
    buttons:[{text:'提交',handler:Menu.save},{text:'取消',handler:function(){Menu.detailWin.hide();}}]    
})

            win = desktop.createWindow({
                id: 'menu',
                title:'菜单管理',
                width:960,
                height:540,
                iconCls: 'icon-grid',
                animCollapse:false,
                constrainHeader:true,
                layout: 'fit',
                items: Menu.menu_panel
            });
        }
        return win;
    }
});

