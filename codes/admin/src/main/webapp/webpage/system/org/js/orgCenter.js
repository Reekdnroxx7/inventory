var parentName = new Ext.form.field.Text({
			fieldLabel : '上级机构:',
			width : 450,
			id : 'parentName',
			readOnly : true,
			fieldStyle : 'background:#E6E6E6',
			name : 'parentName'
		});
var orgId = new Ext.form.field.Text({
			fieldLabel : '机构ID:',
			width : 450,
			id : 'id',
			readOnly : true,
			// hidden : true,
			fieldStyle : 'background:#E6E6E6',
			name : 'id'
		});
var parentId = new Ext.form.field.Text({
			fieldLabel : '上级机构:',
			width : 450,
			id : 'parentId',
			readOnly : true,
			hidden : true,
			fieldStyle : 'background:#E6E6E6',
			name : 'parentId'
		});

var code = new Ext.form.field.Text({
			fieldLabel : '机构代号:',
			width : 450,
			id : 'code',
			name : 'code'
		});
var nameField = new Ext.form.field.Text({
			fieldLabel : '机构名称:',
			width : 450,
			id : 'name',
			allowBlank : false,
			name : 'name'
		});
var level = new Ext.form.field.Text({
			fieldLabel : '机构等级:',
			width : 450,
			id : 'level',
			hidden : true,
			name : 'level'
		});		
var type = new Ext.form.RadioGroup({
			fieldLabel : "机构类型",
			horizontal : true,
			vertical : true,
			anchor : '83%',
			items : [{
						boxLabel : '公司',
						inputValue : '1',
						name : 'type',
						checked : 'true'
					}, {
						boxLabel : '部门',
						name : 'type',
						inputValue : '2'
					}, {
						boxLabel : '小组',
						inputValue : '3',
						name : 'type'
					}]
		});

var address = new Ext.form.field.Text({
			fieldLabel : '联系地址:',
			width : 450,
			id : 'address',
			name : 'address'
		});
var zipCode = new Ext.form.field.Text({
			fieldLabel : '邮政编码:',
			width : 450,
			id : 'zipCode',
			name : 'zipCode'
		});
var master = new Ext.form.field.Text({
			fieldLabel : '负责人:',
			width : 450,
			id : 'master',
			name : 'master'
		});
var phone = new Ext.form.field.Text({
			fieldLabel : '电话:',
			width : 450,
			id : 'phone',
			name : 'phone'
		});
var fax = new Ext.form.field.Text({
			fieldLabel : '传真:',
			width : 450,
			id : 'fax',
			name : 'fax'
		});
var email = new Ext.form.field.Text({
			fieldLabel : '邮箱:',
			width : 450,
			id : 'email',
			name : 'email'
		});
var _saveType = new Ext.form.Hidden({
			name : '_saveType',
			id : 'saveType',
			value : 'add'
		});
var centerPanel = Ext.create('Ext.form.Panel', {
			title : '机构维护',
			bodyPadding : 10,
			defaultAlign : 'center',
			buttonAlign : 'center',
			region : 'center',
			items : [parentName, orgId, parentId,level, code, nameField, type,
					address, zipCode, master, phone, fax, email, _saveType],
			buttons : [{
						id : 'save',
						text : '添加',
						handler : save
					}]
		});
function save() {
	centerPanel.getForm().submit({
				clientValidation : true,
				url : 'org.ajax?method=save',
				success : function(form, action,record) {
					Ext.Msg.alert('提示', '操作成功', function() {
							});
				treeStore.reload();
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
