var id = new Ext.form.field.Text({
			id : 'id',
			name : 'id',
			hidden:true
		});
var nameDetailField = new Ext.form.field.Text({
			fieldLabel : '角色名称',
			id : 'name',
			allowBlank : false,
			name : 'name'
		});
var _saveType = new Ext.form.Hidden({
			name : '_saveType',
			id : 'saveType',
			value : 'add'
		});
var detailPanel = Ext.create("Ext.form.Panel", {
			bodyPadding : 10,
			items : [id,nameDetailField, _saveType]
		});
var detailWin = Ext.create('Ext.window.Window', {
			title : '角色录入',
			layout : 'fit',
			items : detailPanel,
			closeAction : 'hide',
			showAdd : function() {
				var form = this.child('form');
				form.getForm().reset();
				form.getForm().findField("_saveType").setValue("add");
				this.show();
			},
			showUpdate : function(record) {
				var form = this.child('form');
				form.getForm().loadRecord(record);
				_saveType.setValue('update');
				this.show();
			},
			buttons : [{
						text : '提交',
						handler : save
					}, {
						text : '取消',
						handler : function() {
							detailWin.hide();
						}
					}]
		})
function save() {
	detailPanel.getForm().submit({
				clientValidation : true,
				url : 'role.ajax?method=save',
				success : function(form, action) {
					Ext.Msg.alert("信息", "操作成功");
					store.reload();
					detailWin.hide();
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
