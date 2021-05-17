/*
 * ! Ext JS Library 4.0 Copyright(c) 2006-2011 Sencha Inc. licensing@sencha.com
 * http://www.sencha.com/license
 */
//系统菜单树根节点
Ext.define('Menu', {
    extend: 'Ext.data.Model',
    fields: ["id",'type','name','children','target' ,'type' ]   
});
Ext.define('MyDesktop.App', {
	extend : 'Ext.ux.desktop.App',

	requires : ['Ext.window.MessageBox', 'Ext.ux.desktop.ShortcutModel',
			'MyDesktop.Settings','MyDesktop.Dict'/*,
			'MyDesktop.Icon', 'MyDesktop.User', 'MyDesktop.Role',
			'MyDesktop.Org', 'MyDesktop.Codeg'*/],
	modules:[],
	init : function() {
		// custom logic before getXYZ methods get called...

		this.callParent();
		me=this,
		this.getStore().load({
			scope:me,
			synchronous : true,
			callback: function(records, operation, success) {
				for(var i = 0; i<records.length;i++){
					  me.createModule(records[i].data);
				}	      
			}			
		});
		// now ready...
	},	
	getStore : function(){
		if(!this.store){
			this.store =  new Ext.data.Store({
				 nodeParam : 'menuId',
				 proxy: { 
			         type: 'ajax',  
			         url: 'loginController.ajax?method=menuTree&menuId=1'    	
			     },
			     model:'Menu',
			     sorters:[{property:'sort'}]
			});
		}
		return this.store;
	},
	createModule:function(menu){
//		alert(menu.text);
//		var t = menu.name||menu.text;
//		ShowObjProperty(menu);
		if(menu.type == "0"){
			this.addModule(menu);
		}
		if(menu.children){
			for(var i=0;i<menu.children.length;i++){
				this.createModule(menu.children[i]);
			}
		}
	},
	addModule:function(menu){
		
	},
	getModules : function() {
		return [new MyDesktop.Dict()];
	},
	getDesktopConfig : function() {
		var me = this, ret = me.callParent();

		return Ext.apply(ret, {
					// cls: 'ux-desktop-black',

					contextMenuItems : [{
								text : '桌面背景',
								handler : me.onSettings,
								scope : me
							}],

					shortcuts : Ext.create('Ext.data.Store', {
								model : 'Ext.ux.desktop.ShortcutModel',
								data : [{
											name : '字典管理',
											iconCls : 'grid-shortcut',
											module : 'dict'
										}/*, {
											name : '菜单管理',
											iconCls : 'grid-shortcut',
											module : 'menu'
										}, {
											name : '图标管理',
											iconCls : 'grid-shortcut',
											module : 'icon'
										}, {
											name : '用户管理',
											iconCls : 'accordion-shortcut',
											module : 'user'
										}, {
											name : '角色管理',
											iconCls : 'grid-shortcut',
											module : 'role'
										}, {
											name : '机构管理',
											iconCls : 'grid-shortcut',
											module : 'org'
										}, {
											name : '代码生成',
											iconCls : 'grid-shortcut',
											module : 'codeg'
										}*/]
							}),
					wallpaper : 'webpage/main/desktop/wallpapers/Wood-Sencha.jpg',
					wallpaperStretch : true
				});
	},

	// config for the start menu
	getStartConfig : function() {
		var me = this, ret = me.callParent();

		return Ext.apply(ret, {
					title : '导航',
					iconCls : 'user',
					height : 300,
					toolConfig : {
						width : 100,
						items : [{
									text : '风格切换',
									iconCls : 'StyleEdit',
									handler : showStyle
								}, {
									text : '修改密码',
									iconCls : 'key',
									handler : changepwd
								}, '-', {
									text : '退出系统',
									iconCls : 'logout',
									handler : logout,
									scope : me
								}]
					}
				});
	},

	getTaskbarConfig : function() {
		var ret = this.callParent();

		return Ext.apply(ret, {
					quickStart : [{
								name : '快捷键',
								iconCls : 'accordion',
								
								module : 'acc-win'
							}, {
								name : '快捷键',
								iconCls : 'icon-grid',
								
								module : 'grid-win'
							}],
					trayItems : [{
								xtype : 'trayclock',
								flex : 1
							}]
				});
	},

	onSettings : function() {
		var dlg = new MyDesktop.Settings({
					desktop : this.desktop
				});
		dlg.show();
	}
});
var pwdForm = Ext.create('Ext.form.Panel', {
			items : [{
						xtype : "textfield",
						margin : '10,0,0,0',
						name : "newpassword",
						fieldLabel : "新密码",
						inputType : 'password',
						allowBlank : false
					}, {
						xtype : "textfield",
						margin : '10,0,0,0',
						name : "newpassword2",
						fieldLabel : "确认密码",
						inputType : 'password',
						allowBlank : false
					}]
		});
var pwdWin = Ext.create("Ext.window.Window", {
			title : "修改密码",
			closeAction : 'hide',
			bodyPadding : 5,
			// width:300,
			// height:200,
			layout : "fit",
			items : pwdForm,
			buttons : [{
						xtype : "button",
						text : "确定",
						handler : savenewpwd
					}, {
						xtype : "button",
						text : "取消",
						handler : function() {
							this.up("window").close();
						}
					}]
		});
function changepwd() {
	pwdWin.show();
}

function savenewpwd() {
	if (pwdForm.getForm().getValues()["newpassword"] != pwdForm.getForm()
			.getValues()["newpassword2"]) {
		Ext.Msg.alert("错误信息", "您两次输入的密码不一致,请检查后重新输入！")
	} else {
		pwdForm.getForm().submit({
					url : 'userController.ajax?method=savenewpwd',
					success : function(form, action) {
						Ext.Msg.alert("提示", '密码修改成功,请用新密码重新登陆');
						pwdWin.close();
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
	}
}

var styleType = new Ext.form.Panel({
			items : [{
						fieldLabel : "窗体颜色",
						horizontal : true,
						vertical : true,
						columns : 1,
						xtype : 'radiogroup',
						items : [{
									boxLabel : '经典',
									inputValue : 'ext-all.css',
									name : 'type'
								}, {
									boxLabel : '灰色',
									name : 'type',
									inputValue : 'ext-all-gray.css'
								}, {
									boxLabel : '蓝色',
									inputValue : 'ext-all-neptune.css',
									name : 'type'
								}]
					}, {
						fieldLabel : "风格选择",
						horizontal : true,
						vertical : true,
						columns : 1,
						xtype : 'radiogroup',
						items : [{
									boxLabel : '经典',
									inputValue : 'main/extjs/main',
									name : 'style'
								}, {
									boxLabel : '桌面',
									name : 'style',
									inputValue : 'main/desktop/desktop'
								}]
					}]

		});
var styleWin = Ext.create("Ext.window.Window", {
			title : "风格切换",
			bodyPadding : 5,
			closeAction : 'hide',
			width : 300,
			height : 250,
			layout : "fit",
			items : styleType,
			buttons : [{
						xtype : "button",
						text : "确定",
						handler : saveStyle
					}, {
						xtype : "button",
						text : "取消",
						handler : function() {
							this.up("window").close();
						}
					}]
		});

function showStyle() {
	styleWin.show();
}
function saveStyle() {
	styleType.getForm().submit({
				url : 'userController.ajax?method=saveStyle',
				success : function() {
					styleWin.close();
					Ext.Msg.alert("提示", "样式修改成功，请刷新页面")
				}
			})
}
function logout() {
	Ext.Msg.confirm("确认", "确定退出该系统吗 ?", function(confirm) {
				if ("yes" == confirm) {
					Ext.Ajax.request({
								url : 'loginController.ajax?method=logout',
								success : function(data) {
									if (data = "success") {
										window.location.reload();
									}

								}
							})
				}
			})

}