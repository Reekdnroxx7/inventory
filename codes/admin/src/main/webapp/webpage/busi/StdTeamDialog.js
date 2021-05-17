Ext.namespace("SbobetMatch");
//字典配置
//方法
SbobetMatch.query = function(){
	var params = SbobetMatch.getParams("queryBar");
	SbobetMatch.store.load({
		params : params ,
		callback : function(records, operation, success) {
			if (success == false) {
				Ext.Msg.alert("错误", SbobetMatch.store.proxy.reader.jsonData.message );
			}
		}
	});
};

SbobetMatch.getParams = function (){
	var docks= SbobetMatch.gridPanel.getDockedItems('toolbar[dock="top"]');
	var params= {};
	for(var i =0 ;i <docks.length;i++){
		var bar = docks[i];
		bar.items.each(
			function(item,index){
				if(item.getValue){
					var name = item['name'];
					var value = item.getValue();
					if(item.xtype == 'datefield'){
						value=item.getRawValue();
					}
					if(value){
						params[name]=value;
					}
				}
			}
		);
	}
	return params;
};

SbobetMatch.reset = function(){
	var docks= SbobetMatch.gridPanel.getDockedItems('toolbar[dock="top"]');
	var params= {};
	for(var i =0 ;i <docks.length;i++){
		var bar = docks[i];
		bar.items.each(
			function(item,index){
				if(item.setValue){
					var name = item['name'];
					item.setValue('');
				}
			}
		);
	}
};



SbobetMatch.store = Ext.create('Ext.data.Store',{
	fields:[
		'id',
		'sport_id',
		'league_id',
		'event_id',
		'start_time',
		'home',
		'away',
		'live_status',
		'status'
	],
	pageSize:20,
	proxy: {
    	type: 'ajax',
        url : "sbobetmatch.ajax?method=listMatch",
        reader: {
            type: 'json',
            root: 'resultList',
            totalProperty: 'totalCount'
        }
    }
});
SbobetMatch.store.load();

SbobetMatch.gridPanel = Ext.create('Ext.grid.Panel',{
	store:SbobetMatch.store,
	rowLines:true,
	columnLines : true,
    selModel: Ext.create("Ext.selection.CheckboxModel", {
        injectCheckbox: 1,//checkbox位于哪一列，默认值为0
        mode: "single",//multi,simple,single；默认为多选multi
        checkOnly: false,//如果值为true，则只用点击checkbox列才能选中此条记录
        allowDeselect: true,//如果值true，并且mode值为单选（single）时，可以通过点击checkbox取消对其的选择
        enableKeyNav: true
    }),
	columns:[
	{xtype: 'rownumberer'},
	{
		text:'',
		dataIndex:'id',
		hidden:true,
		width:200
	},
	{
		text:'',
		dataIndex:'sport_id',
		hidden:true,
		width:200
	},
	{
		text:'',
		dataIndex:'league_id',
		hidden:true,
		width:200
	},
	{
		text:'',
		dataIndex:'event_id',
		hidden:true,
		width:200
	},
	{
		text:'开始时间',
		dataIndex:'start_time',
        renderer:function(value){
            return Ext.Date.format(new Date(value),("Y-m-d H:i"));
        },
		width:200
	},
	{
		text:'主队名称',
		dataIndex:'home',
		width:200
	},
	{
		text:'客队名称',
		dataIndex:'away',
		width:200
	},

	{
		text:'赛事的状态',
		dataIndex:'status',
		width:200
	}
	],
	dockedItems:[{
		xtype: 'toolbar',
		dock: 'top',
		items:[
            '主队:',{ id:"ps3838_home|like",xtype:'textfield', name:'home|like'}
            ,'-',
            '客队',{ id:"ps3838_away|like",xtype:'textfield', name:'away|like'}
            ,'-',
            '开始时间:',{ id:"ps3838_date_start",xtype:'datefield',format:'Y-m-d',name:'start_time|ge'},'至',{ id:"ps3838_date_end", xtype:'datefield',format:'Y-m-d', name:'start_time|le'}
		       ]
	},{
    	xtype: 'toolbar',
	    dock: 'top',
	    items: [
	        {text:'查询',handler:SbobetMatch.query,iconCls:'Magnifier'},'-',
	        {text:'重置',iconCls:'Pagerefresh',handler:SbobetMatch.reset}
	    ]
    },{
        xtype: 'pagingtoolbar',
        store: SbobetMatch.store,
        dock: 'bottom',
        displayInfo: true
    }]
});

Ext.define('SbobetMatch.MatchDialog',{
    extend:"Ext.window.Window",
    title:'平博网赛事选择',
    width:1000,
    height:600,
    layout:"fit",
    items: SbobetMatch.gridPanel,
    closeAction : 'hide',
    onMatchSelected : null,
    getSelectedMatch:function(){
        if(SbobetMatch.gridPanel.getSelectionModel().hasSelection()){
			return SbobetMatch.gridPanel.getSelectionModel().getSelection()[0];
		}
    },

    showByQuery:function (match) {
		SbobetMatch.reset();
        this.match = match;
        var home = match.get("home");
        var away = match.get("away");
        var date = match.get("start_time");
        if(home){
            Ext.getCmp("ps3838_home|like").setValue(home);
        }
        // if(away){
        //     Ext.getCmp("ps3838_away|like").setValue(away);
        // }
        if(date){
            date = new Date(date);
            var end = Ext.Date.add(date, Ext.Date.DAY, 1);
            Ext.getCmp("ps3838_date_start").setValue(date);
            Ext.getCmp("ps3838_date_end").setValue(end);
        }
        SbobetMatch.query();
        this.show();
    },
    initComponent: function () {
        var me = this;
        me.buttons = [
            {text:'提交',handler:function () {
                if(!SbobetMatch.gridPanel.getSelectionModel().hasSelection()){
                    Ext.Msg.alert("错误","请选择一赛事");
                }else{
                    if(this.onMatchSelected){
                        this.onMatchSelected(this.match,this.getSelectedMatch());
                    }
                    this.hide();
                }
            },scope:me},
            {text:'取消',handler:function(){this.hide();},scope:me}
        ];
        me.callParent();
    }

});

