/*  重新定义Ext.tree.Column 不得已 
 * 改变 href 和 iconCls
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
	               '<span class="{textCls} {childCls}">{value}</span>',
	      ]
});		

/**
 * 定义Menu 类
 */
Ext.define('Sys.Menu', {
    extend: 'Ext.data.TreeModel',
    fields: ["id",'type','name','url','parentId','parentIds',
             'icon','sort', 'transcode','target','level','_saveType','isShow',
	         {name:'leaf',convert:function(value,record){
	        	 return record.data.type == '0';
	         }}
    ] 
});

Ext.namespace("Menu");
Menu.dictBox = Ext.create("Sys.DictBox",{
	groupCode:'sys_menu.type'
});

//删除
Menu.delRecord = function(grid, rowIndex, colIndex){
	Ext.Msg.confirm("提示","你确定要删除该记录么?",function(confirm){
		if("yes" == confirm){
			var rec = grid.getStore().getAt(rowIndex);
			Ext.Ajax.request({
			    url: 'menu.ajax?method=deleteMenu',
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

Menu.refresh = function (){
	Ext.Ajax.request({
	    url: 'menu.ajax?method=refresh',
	    success: function(response){
	        var text = response.responseText;
	        var result = Ext.JSON.decode(text);
	        if(result.success){
	        	Ext.Msg.alert("信息","刷新成功");
	        	Menu.treeStore.reload();
	        }else{
	        	Ext.Msg.alert("错误",result.message);
	        }
	    }
	});
};



//菜单树store
Menu.treeStore = new Ext.data.TreeStore({
	 nodeParam : 'menuId',
	 proxy: { 
         type: 'ajax',  
         url: 'menu.ajax?method=listMenu'    	
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

//菜单panel
Menu.menu_panel = Ext.create("Ext.tree.Panel",{
	title:'菜单管理',
	autoScroll : true,
	store : Menu.treeStore,
	rootVisible : true, 
	rowLines:true,
	columnLines :true,
	dockedItems: [{
    	xtype: 'toolbar',
	    dock: 'top',
	    items: [
	        {text:'刷新',handler:function(){Menu.refresh();},iconCls:'Pagerefresh'}
	    ]
    }],
	columns:[{
		text: '菜单名称',
		xtype: 'mytreecolumn', //extjs tree 必须定义一个treecolumn
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
			{iconCls:'Delete',tooltip:'删除',handler:Menu.delRecord},
			'-',
  	  		{
				iconCls:'Cogadd',tooltip:'添加按钮权限',
				isDisabled: function( view, rowIdx, colIdx, item, record){
					return !record.isLeaf();
			
				},
				handler:function(grid, rowIndex, colIndex){      	  				
					var rec = grid.getStore().getAt(rowIndex);
					Operation.win.showMenu(rec);
				}
  	  		}]
		},{
			text:'可访问类',
			dataIndex:'transcode',
			width: 200
    	},{
			text:'目的地址',
			dataIndex:'target',
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





















