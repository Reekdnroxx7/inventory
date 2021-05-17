Ext.namespace("CodegField");
CodegField.queryBox = Ext.create("Sys.DictBox", {
			groupCode : 'codeg.querytype'
		});
CodegField.typeBox = Ext.create("Sys.DictBox", {
			groupCode : 'codeg.fieldtype'
		});

CodegField.query = function() {
	var record = CodegField.gridWin.getRecord();
	if (record) {
		var params = {};
		params.tableName = record.data.name;
		CodegField.store.load({
					params : params
				});
	}
};

CodegField.store = Ext.create('Ext.data.Store', {
			fields : ['tableName', 'fieldName', 'displayName', 'length',
					'dbType', 'queryType', 'showType', 'dictGroupCode'],
			pageSize : -1,
			proxy : {
				type : 'ajax',
				url : "mybatiscodeg.ajax?method=listCodegField",
				reader : {
					type : 'json',
					root : 'resultList',
					totalProperty : 'totalCount'
				}
			},
			onUpdate:function(record, operation, modifiedFieldNames, eOpts){
				if("edit" == operation){
					if(modifiedFieldNames[0] == "queryType"){
						record.set("showType",1);
					}
				}
//				alert(modifiedFieldNames.length);
			}
		});

CodegField.gridPanel = Ext.create('Ext.grid.Panel', {
			width : 800,
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
					}, {
						editor : {
							xtype : 'textfield'
						},
						text : '长度',
						dataIndex : 'length'
					}, {
						field : {
							xtype : 'textfield'
						},
						text : '字段类型',
						dataIndex : 'dbType',
						renderer : function(v) {
							return CodegField.typeBox.getDictValue(v)
						}
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
									groupCode : 'codeg.querytype'/*,
									onChange : function(){
										Sys.ShowObjProperty(this);
									}
								*/
								})
					}, {
						field : {
							xtype : 'textfield'
						},
						dataIndex : 'dictGroupCode',
						text : '使用字典'
					}, {
						xtype : 'checkcolumn',
						text : '显示类型',
						dataIndex : 'showType',
						editor:new 	Ext.form.field.Checkbox()
					}, {
						text : '编辑类型',
						dataIndex : ''
					}]
		});
CodegField.pkg = new Ext.form.field.Text({
			fieldLabel : '模块名称(小写):',
			width : 500,
			allowBlank : false,
			id : 'pkg',
			name : 'pkg'
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
			items : [CodegField.pkg, CodegField.description]
		});
CodegField.gridWin = Ext.create('Ext.window.Window', {
	        id:'congigwin',
			title : '参数配置',
			// width : 800,
			// height : 400,
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
		})
		

		
		
		
function preGenerate() {
							var record = CodegField.gridWin.getRecord();
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
		url : 'mybatiscodeg.ajax?method=codeGenerate',
		params : {
			fieldList : fieldList,
			tableName : rec.data.name
		},
		success : function(form, action) {
			Ext.Msg.alert("信息", '创建成功');
			CodegField.gridWin.hide();
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
