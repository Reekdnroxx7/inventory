Ext.namespace("CodegField");
CodegField.queryBox = Ext.create("Sys.DictBox", {
			groupCode : 'codeg.querytype'
		});
CodegField.typeBox = Ext.create("Sys.DictBox", {
			groupCode : 'codeg.fieldtype'
});

CodegField.showBox = Ext.create("Sys.DictBox", {
	groupCode : 'codeg.showtype'
});
CodegField.xtypeBox = Ext.create("Sys.DictBox", {
	groupCode : 'codeg.xtype'
});
CodegField.query = function() {
	var record = CodegField.gridWin.getRecord();
	var datasource = CodegTable.dataSource;
	if (record) {
		var params = {};
		params.tableName = record.data.name;
		params.dataSourceId = CodegTable.dataSource.data.id;
		CodegField.store.load({
					params : params
				});
	}
};
CodegField.store = Ext.create('Ext.data.Store', {
			fields : ['tableName', 'fieldName', 'displayName', 'length','xtype',
					'dbType', 'queryType', 'showType', 'primaryKey',
					'dictGroupCode','qualifiedType','editable'],
			pageSize : -1,
			proxy : {
				type : 'ajax',
				url : "datasourceConfig.ajax?method=listCodegField",
				reader : {
					type : 'json',
					root : 'resultList',
					totalProperty : 'totalCount'
				}
			},
			onUpdate:function(record, operation, modifiedFieldNames, eOpts){
				if("edit" == operation){
					if(modifiedFieldNames[0] == "queryType"){
						record.set("showType",0);
					}
				}
			}
		});

CodegField.gridPanel = Ext.create('Ext.grid.Panel', {
			width : 550,
			height : 400,
			rowLines : true,
			columnLines : true,
			store : CodegField.store,
			loadRecord : function(rec) {
				this.record = rec;
				CodegField.store.load({
							params : rec.data
						});
			},
			getRecord : function() {
				return this.record;
			},
			plugins : [Ext.create('Ext.grid.plugin.CellEditing', {
						clicksToEdit : 1
					})],
			columns : [{
						text : '字段名',
						dataIndex : 'fieldName'
					}, {
						editor : {
							xtype : 'textfield'
						},
						text : '显示名',
						dataIndex : 'displayName'
					},  {						
						text : '字段类型',
						dataIndex : 'dbType',
						renderer : function(v,meta,record) {
							var value = record.data.qualifiedType.javaType;
							if(value.lastIndexOf(".") > 0){
								return value.substring(value.lastIndexOf(".")+1);
							}
							return value;
						}
					}/*,{						
						text : '控件类型',
						dataIndex : 'xtype',
						renderer : function(v) {
							if (!v) {
								v = 'textfield';
							}
							return CodegField.xtypeBox.getDictValue(v);
						},
						editor:Ext.create("Sys.DictBox", {
							groupCode : 'codeg.xtype'
				})
					}, {
						text : '是否显示',
						dataIndex : 'showType',
						renderer : function(v) {
							if (!v) {
								v = '0';
							}
							return CodegField.showBox.getDictValue(v);
						},
						editor:Ext.create("Sys.DictBox", {
									groupCode : 'codeg.showtype'
						})
					}, {
						text : '可编辑',
						dataIndex : 'editable',
						xtype : 'checkcolumn'
					}, {
						text : '查询类型',
						dataIndex : 'queryType',
						renderer : function(v) {
							if (!v) {
								v = 'no';
							}
							return CodegField.queryBox.getDictValue(v);
						},
						editor : Ext.create("Sys.DictBox", {
									groupCode : 'codeg.querytype'
						})
					}, {
						field : {
							xtype : 'textfield'
						},
						dataIndex : 'dictGroupCode',
						text : '使用字典'
					}*/]
		});
CodegField.basePkg = new Ext.form.field.Text({
	fieldLabel : '基础包名(小写):',
	width : 500,
	listeners:{
		change : function( f, newValue , oldValue, eOpts ){
			var value = newValue + "." + CodegField.module.getValue();
			CodegField.pkg.setValue(value);	
		},
		render:function( f, eOpts ){
			var value = Ext.util.Cookies.get("CodegField.basePkg");
			if(!value){
				value="com.eengoo.web.manage";
			}
			f.setValue(value);
		}
	}	
});
CodegField.module = new Ext.form.field.Text({
	fieldLabel : '模块名称(小写):',
	width : 500,
	name:'module',
	allowBlank : false,
	listeners:{
		change : function( f, newValue , oldValue, eOpts ){
			var value =  CodegField.basePkg.getValue() + "." + newValue;
			CodegField.pkg.setValue(value);	
		},
		render:function( f, eOpts ){
			var value = Ext.util.Cookies.get("CodegField.module");
			f.setValue(value);
		}
	}
});
CodegField.pkg = new Ext.form.field.Text({
	fieldLabel : '包名',
	width : 500,
	allowBlank : false,
	id : 'pkg',
	name:'pkg',
	listeners:{
		render:function( f, eOpts ){
			var value = Ext.util.Cookies.get("CodegField.pkg");
			f.setValue(value);
		}
	}
});
CodegField.description = new Ext.form.field.Text({
		fieldLabel : '功能描述:',
		width : 500,
		allowBlank : false,
		id : 'description',
		name : 'description'
});
CodegField.formPanel = Ext.create('Ext.form.Panel', {
			bodyPadding : 10,
			items : [CodegField.basePkg,CodegField.module,CodegField.pkg,CodegField.description]
		});
CodegField.gridWin = Ext.create('Ext.window.Window', {
	        id:'congigwin',
			title : '参数配置',
			layout : 'anchor',
			items : [{
						xtype : 'form',
						items : CodegField.formPanel,
						anchor : '100% 30%'
					}, {
						xtype : 'panel',
						items : CodegField.gridPanel,
						anchor : '100% 70%'
					}],
			closeAction : 'hide',
			showRecord : function(record) {
				this.record = record;
				var description= record.data.name + "管理";
				if(record.data.displayName){
					description = record.data.displayName+"管理";
				}
				CodegField.description.setValue(description);
				this.show();
				CodegField.query();
			},
			getRecord : function() {
				return this.record;
			},
			
			buttons : [{
						text : '确定',
						handler : preGenerate
					}]
		});
		

		
		
		
function preGenerate() {
						
	var record = CodegField.gridWin.getRecord();
	Ext.util.Cookies.set("CodegField.basePkg",CodegField.basePkg.getValue());
	Ext.util.Cookies.set("CodegField.module",CodegField.module.getValue());
	Ext.util.Cookies.set("CodegField.pkg",CodegField.pkg.getValue());
//	Ext.util.Cookies.set("CodegField.description",CodegField.description.getValue());
	generate(record);
							
}

function generate(rec) {
	var records = CodegField.store.data.items;
	var fieldList = new Array();
	Ext.each(records, function(record) {
				var enStr = Ext.JSON.encode(record.data);
				fieldList.push(enStr);
			});
	CodegField.formPanel.getForm().submit({
		url : getGeneratorUrl(),
		params : {
			fieldList : fieldList,
			tableName : rec.data.name
		},
		success : function(form, action) {
//			Ext.Msg.alert("信息", '创建成功');
			var docId = action.result.obj.id;
			var url = "common.do?method=download&docId="+docId;
			window.location.href= url;
//			CodegField.gridWin.hide();
		},
		failure : function(form, action) {
			switch (action.failureType) {
				case Ext.form.action.Action.CLIENT_INVALID :
					Ext.Msg.alert('失败', '无效的值');
					break;
				case Ext.form.action.Action.CONNECT_FAILURE :
					Ext.Msg.alert('失败', '网络连接失败');
					break;
				case Ext.form.action.Action.SERVER_INVALID :
					Ext.Msg.alert('失败', action.result.message);
			}
		}
	});
};
