<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/context/mytags.jsp"%>
<%@include file="/context/extjs.jsp"%>
<%@include file="/context/system.jsp"%>
<%@page import="com.x404.beat.manage.sys.tools.UserInfo"%>
<%@page import="com.x404.beat.manage.sys.utils.UserUtils"%>
<%
	UserInfo userInfo = UserUtils.getCurrentUserInfo();
%>
<!DOCTYPE html >
<html>
<link rel="shortcut icon" href="${ctx}/favicon.ico" type="image/x-icon">
<link href="${ctx }/plug-in\common\css\icon.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript"
		src="${ctx}/webpage/main/extjs/js/left.js">
</script>
	<script type="text/javascript"
		src="${ctx}/webpage/main/extjs/js/center.js">
</script>
	<head>
		<title>Jeecp</title>
	</head>
<script type="text/javascript">
var loginName = '<%=userInfo.getUser().getLoginName()%>'
var org = '<%=userInfo.getOrg().getName()%>'
Ext.onReady(function() {
	var viewport = new Ext.Viewport( {
		layout : 'border',
		items : [ west_panel, center_panel, top_panel ]
	});
});
var top_panel = Ext.create('Ext.container.Container', {
	region : 'north',
	height : 60,
	layout : {
		type : 'hbox',
		align : 'middle'
	},
	defaults : {
		xtype : 'component'
	},
	items : [
			{
				html : 'Jeecp工作平台',
				width : '900',
				style : 'letter-spacing:8px;padding-left:50px;font-size:30px;font-family:华文行楷;'
			},
			{
				flex : 1,
				html : '<div id="" ><table><tr>' + '<td>登陆用户：' + loginName
						+ '</td>' + '<td>&nbsp;&nbsp;&nbsp;所属机构：' + org
						+ '</td>' + '</tr></table></div>'
			}, Ext.create("Ext.Button", {
				renderTo : Ext.get("div2"),
				id : "bt",
				text:'<font color="#000000" size="2" >控制面板</font>',
				iconCls : "Cog",
				menu : {
					items : [ {
						iconCls : 'key',
						text : '修改密码',
						handler : changepwd
					}, {
						iconCls : 'StyleEdit',
						text : '风格切换',
						handler : showStyle
					}, {
						iconCls : 'logout',
						text : '退出登录',
						handler : logout
					} ]
				}
			}).showMenu(), {
				width : '50'
			} ]
});
var pwdForm = Ext.create('Ext.form.Panel', {
	items : [ {
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
	} ]
});
var pwdWin = Ext.create("Ext.window.Window", {
	title : "修改密码",
	closeAction : 'hide',
	bodyPadding : 5,
	layout : "fit",
	items : pwdForm,
	buttons : [ {
		xtype : "button",
		text : "确定",
		handler : savenewpwd
	}, {
		xtype : "button",
		text : "取消",
		handler : function() {
			this.up("window").close();
		}
	} ]
});
function changepwd() {
	pwdWin.show();
}

function savenewpwd() {
	if (pwdForm.getForm().getValues()["newpassword"] != pwdForm.getForm()
			.getValues()["newpassword2"]) {
		Ext.Msg.alert("错误信息", "您两次输入的密码不一致,请检查后重新输入！")
	} else {
		pwdForm.getForm().submit( {
			url : 'user.ajax?method=savenewpwd',
			success : function(form, action) {
				Ext.Msg.alert("提示", '密码修改成功,请用新密码重新登陆');
				pwdWin.close();
			},
			failure : function(form, action) {
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
}

var styleType = new Ext.form.Panel( {
	items : [ {
		fieldLabel : "皮肤选择",
		horizontal : true,
		vertical : true,
		columns : 1,
		xtype : 'radiogroup',
		items : [ {
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
		} ]
	} ,{
		fieldLabel : "风格选择",
		horizontal : true,
		vertical : true,
		columns : 1,
		xtype : 'radiogroup',
		items : [ {
			boxLabel : '经典',
			inputValue : 'classical',
			name : 'style'
		}, {
			boxLabel : '桌面',
			name : 'style',
			inputValue : 'desktop'
		}]
	} ]

});
var styleWin = Ext.create("Ext.window.Window", {
	title : "更换皮肤",
	bodyPadding : 5,
	closeAction : 'hide',
	width : 300,
	height : 250,
	layout : "fit",
	items : styleType,
	buttons : [ {
		xtype : "button",
		text : "确定",
	handler : saveStyle
			}, {
				xtype : "button",
				text : "取消",
				handler : function() {
					this.up("window").close();
				}
			} ]
});

function showStyle() {
	styleWin.show();
}
function saveStyle() {
	styleType.getForm().submit({
	url : 'user.ajax?method=saveStyle',
	success:function(){
	styleWin.close();
	Ext.Msg.alert("提示","样式修改成功，请刷新页面")
	}
	})
}
function logout() {
	Ext.Msg.confirm("确认","确定退出该系统吗 ?",function(confirm){
		if ("yes" == confirm) {
		Ext.Ajax.request({
				url : 'login.ajax?method=logout',
				success: function(data){
				  if(data="success"){
				   window.location.reload();
				  }

				}
			})}
	})

}
</script>
	<body>
	</body>

</html>