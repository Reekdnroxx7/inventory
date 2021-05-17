
var top_panel = Ext.create('Ext.container.Container', {
			region : 'north',
			height : 34,
			layout : {
				type : 'hbox',
				align : 'middle'
			},
			defaults : {
				xtype : 'component'
			},
			items : [{
						html : 'Jeecp工作平台',
						width : '900',
						style : 'padding-left:20px;font-size:22px;color:#FFFFFF;'
					}, {
						flex : 1,
						html : '<div id="" ><table><tr>' + '<td>登陆用户：'
								+ loginName + '</td>' + '<td>所属机构：' + org
								+ '</td>' + '</tr></table></div>'
					}, {
						width : '100',
						xtype : 'button',
						text : '退出',
						handler : logout
					}]
		});
function logout() {
	Ext.Ajax.request({
				url : 'loginController.do?method=logout',
				success:function(){
				}
			})
}