Ext.define('MyDesktop.Dict', {
	extend : 'Ext.ux.desktop.Module',

	requires : ['Ext.data.ArrayStore', 'Ext.util.Format', 'Ext.grid.Panel',
			'Ext.grid.RowNumberer'],

	id : 'dict',
//	name:'',
//	jss:[],
//	iconCls:'',
	init : function() {
		this.launcher = {
			text : '字典管理',
			iconCls : 'book_edit'
		};
	},
	loaded : false,
	loadScript: function(){
		if(this.loading){
			return ;
		}
		this.loading=true;
		if(!this.loaded){
			Ext.Loader.loadScriptFile("webpage/system/dict/js/dict.js",
				this.setLoaded,
				this.setLoaded,
				this,
				true
				);
			Ext.Loader.loadScriptFile(
			"webpage/system/dict/js/dictGroup.js",
			this.setLoaded,
			this.setLoaded,
			this,
			true
			);
		}
	},
	setLoaded:function(){
		this.loaded = true;
	},
	createWindow : function() {	
		var desktop = this.app.getDesktop();
		if (!this.win) {
			var win = desktop.createWindow({
				id : 'dict',
				title : '字典管理',
				width : 740,
				height : 480,
				iconCls : 'icon-grid',
				animCollapse : false,
				constrainHeader : true,
				layout : 'fit',
				items : DictGroup.gridPanel
			});
			this.win = win;
		}
		return this.win;
	}
});
