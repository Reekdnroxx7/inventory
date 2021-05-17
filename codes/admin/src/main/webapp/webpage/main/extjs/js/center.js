
var center_panel = new Ext.TabPanel(
		{
			region : 'center',
			deferredRender : false,
			resizeTabs : true,
			enableTabScroll : true,
			activeTab : 0,
			items : [ {
				id:'main',
				title : '主页',
				autoScroll : true,
				html : '<iframe scrolling="auto" id="iframe-main" frameborder="0"  width="100%" height="100%" src="/oa/main/home.jsp"></iframe>'
			} ],
			listeners:{
				tabchange:function( tabPanel, newCard, oldCard, eOpts ){
					Ext.util.Cookies.set('currentMenuId',newCard.id);
				}
			}
		
});

