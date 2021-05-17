/*
 * ! Ext JS Library 4.0 Copyright(c) 2006-2011 Sencha Inc. licensing@sencha.com
 * http://www.sencha.com/license
 */

Ext.define('MyDesktop.Codeg', {
	extend : 'Ext.ux.desktop.Module',

	requires : ['Ext.data.ArrayStore', 'Ext.util.Format', 'Ext.grid.Panel',
			'Ext.grid.RowNumberer'],

	id : 'codeg',

	init : function() {
		this.launcher = {
			text : '代码生成',
			iconCls : 'icon-grid'
		};
	},

	createWindow : function() {
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('grid-win');
		if (!win) {
			Ext.namespace("CodegField");
			CodegField.queryBox = Ext.create("Sys.DictBox", {
						groupCode : 'codeg.querytype'
					});
			CodegField.showBox = Ext.create("Sys.DictBox", {
						groupCode : 'codeg.showtype'
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
			}

			CodegField.store = Ext.create('Ext.data.Store', {
						fields : ['tableName', 'fieldName', 'displayName',
								'length', 'dbType', 'queryType',
								'dictGroupCode'],
						pageSize : -1,
						proxy : {
							type : 'ajax',
							url : "codegCtl.ajax?method=listCodegField",
							reader : {
								type : 'json',
								root : 'resultList',
								totalProperty : 'totalCount'
							}
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
										return CodegField.typeBox
												.getDictValue(v)
									}
								}, {
									text : '查询类型',
									dataIndex : 'queryType',
									renderer : function(v) {
										if (!v) {
											v = 'no';
										}
										return CodegField.queryBox
												.getDictValue(v);
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
								}, {
									text : '显示类型',
									dataIndex : 'showType',
									renderer : function(v) {
										if (!v) {
											v = '0';
										}
										return CodegField.showBox
												.getDictValue(v);
									},
									editor : Ext.create("Sys.DictBox", {
												groupCode : 'codeg.showtype'
											})
								}, {
									text : '编辑类型',
									dataIndex : ''
								}]
					});
			CodegField.pkg = new Ext.form.field.Text({
						fieldLabel : '模块名称(小写):',
						width : 500,
						allowBlank : false,
						// id : 'pkg',
						name : 'pkg'
					});
			CodegField.description = new Ext.form.field.Text({
						fieldLabel : '功能描述:',
						width : 500,
						allowBlank : false,
						// id : 'description',
						name : 'description'
					});
			CodegField.formPanel = Ext.create('Ext.form.Panel', {
						bodyPadding : 10,
						items : [CodegField.pkg, CodegField.description]
					});
			CodegField.gridWin = Ext.create('Ext.window.Window', {
						title : '参数配置',
						draggable : false,
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
									handler : function() {
										var record = CodegField.gridWin
												.getRecord();
										generate(record);

									}
								}]
					})

			function generate(rec) {
				var records = CodegField.store.data.items;
				var fieldList = new Array();
				Ext.each(records, function(record) {
							var enStr = Ext.JSON.encode(record.data);
							fieldList.push(enStr);
						});
				CodegField.formPanel.getForm().submit({
							url : 'codegCtl.ajax?method=codeGenerate',
							params : {
								fieldList : fieldList,
								tableName : rec.data.name
							},
							success : function(form, action) {
								Ext.Msg.alert("信息", '创建成功');
								CodegField.gridWin.hide()
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
										Ext.Msg.alert('失败',
												action.result.message);
								}
							}
						});
			}
			Ext.namespace("CodegTable");
			CodegTable.query = function() {
				var params = CodegTable.getParams("queryBar");
				CodegTable.store.load({
					params : params,
					callback : function(records, operation, success) {
						if (success == false) {
							Ext.Msg
									.alert(
											"错误",
											CodegTable.store.proxy.reader.jsonData.message);
						}
					}
				});
			}
			CodegTable.save = function() {
				CodegTable.formPanel.getForm().submit({
							clientValidation : true,
							url : 'codegCtl.ajax?method=saveCodegTable',
							success : function(form, action) {
								CodegTable.formWin.hide();
								CodegTable.store.reload();
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
										Ext.Msg.alert('失败',
												action.result.message);
								}
							}
						});
			};
			CodegTable.del = function(grid, rowIndex, colIndex) {
				var store = grid.getStore();
				var rec = store.getAt(rowIndex);
				Ext.Ajax.request({
							url : 'codegCtl.ajax?method=deleteCodegTable',
							params : rec.data,
							success : function(response) {
								var text = response.responseText;
								var result = Ext.JSON.decode(text);
								if (result.success) {
									Ext.Msg.alert("信息", "删除成功");
									store.reload();
								} else {
									Ext.Msg.alert("错误", result.message);
								}
							}
						});
			};

			CodegTable.store = Ext.create('Ext.data.Store', {
						fields : ['name', 'displayName'],
						proxy : {
							type : 'ajax',
							url : "codegCtl.ajax?method=listCodegTable",
							reader : {
								type : 'json',
								root : 'resultList',
								totalProperty : 'totalCount'
							}
						}
					});
			CodegTable.store.load();
			CodegTable.gridPanel = Ext.create('Ext.grid.Panel', {
//						title : '代码生成',
						store : CodegTable.store,
						rowLines : true,
						columnLines : true,
						columns : [{
									text : '表名',
									dataIndex : 'name',
									width : 200
								}, {
									text : '显示名称',
									dataIndex : 'dispalyName',
									width : 200
								}, {
									xtype : 'actioncolumn',
									text : '操作',
									align : 'center',
									items : [{
										iconCls : 'Pageadd',
										tooltip : '生成配置',
										handler : function(grid, rowIndex,
												colIndex) {
											var rec = grid.getStore()
													.getAt(rowIndex);
											CodegField.gridWin.showRecord(rec);
										}
									}]
								}],
						dockedItems : [{
									xtype : 'toolbar',
									dock : 'top',
									items : ['表名:', {
												xtype : 'textfield',
												name : 'groupCode|like'
											}, '显示名称:', {
												xtype : 'textfield',
												name : 'groupName|like'
											}]
								}, {
									xtype : 'toolbar',
									dock : 'top',
									items : [{
												text : '查询',
												handler : CodegTable.query,
												iconCls : 'Magnifier'
											}]
								}]
					});

			CodegTable.getParams = function() {
				var docks = CodegTable.gridPanel
						.getDockedItems('toolbar[dock="top"]');
				var params = {};
				// alert(outputObject(docks[0]));
				for (var i = 0; i < docks.length; i++) {
					var bar = docks[i];
					bar.items.each(function(item, index) {
								if (item.getValue) {
									var name = item['name'];
									var value = item.getValue();
									params[name] = value;
								}
							});
				}
				return params;
			}


			win = desktop.createWindow({
						id : 'codeg',
						title : '代码生成',
						width : 740,
						height : 480,
						iconCls : 'icon-grid',
						animCollapse : false,
						constrainHeader : true,
						layout : 'fit',
						items : CodegTable.gridPanel

					});
		}
		return win;
	}
});
