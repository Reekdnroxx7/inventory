Ext.namespace("Spread");
//字典配置
//方法
Spread.query = function(){
	var params = Spread.getParams("queryBar");
	Spread.store.load({
		params : params ,
		callback : function(records, operation, success) {
			if (success == false) {
				Ext.Msg.alert("错误", Spread.store.proxy.reader.jsonData.message );
			}
		}
	});
};

Spread.getParams = function (){
	var docks= Spread.gridPanel.getDockedItems('toolbar[dock="top"]');
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

Spread.reset = function(){
	var docks= Spread.gridPanel.getDockedItems('toolbar[dock="top"]');
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



Spread.store = Ext.create('Ext.data.Store',{
	fields:[
		'id',
		'home',
		'away',
		'startTime',
		'hdp',
		"league_name",
		'league_cn_name',
		'spreads',
		'max_away_odds'	,
		"maxHandicap",
		"SbobetSpread",
		"PS3838Spread",
		"IsnSpread_home",
		"IsnSpread_away",
		"fy_2",
		"fy_24",
		"fy_37"

	],
	pageSize:40,
	proxy: {
    	type: 'ajax',
        url : "spread.ajax?method=listSpread",
        reader: {
            type: 'json',
            root: 'resultList',
            totalProperty: 'totalCount'
        }
    }
});

function renderPlatform(rec, platform) {
	var spread = rec.spreads[platform];
	if(spread){
        return "<div>主队赔率"+ (spread.homeOdds ? spread.homeOdds.toFixed(3) : "--")+"</div>"+
            "<div>客队赔率:"+ (spread.awayOdds ? spread.awayOdds.toFixed(3) : "--")+"</div>"+
            "<div>" + Ext.Date.format(new Date(spread.initTime),("Y-m-d H:i:s.u"))+"</div> ";
    }
}

Spread.gridPanel = Ext.create('Ext.grid.Panel',{
	title:'让分盘',
	store:Spread.store,
	rowLines:true,
	columnLines : true,
    viewConfig:{
        enableTextSelection:true
    },
    columns:[
	{xtype: 'rownumberer'},
		{
			text:'主键',
			dataIndex:'_id',
			hidden:true,
			width:200
		},
		{
			text:"赛事信息",
			renderer : function (v,m,rec) {
                var startTime  =  Ext.Date.format(new Date(rec.data.startTime),("Y-m-d H:i:s"));
                return "<div>"+ rec.data.home +"&nbsp;Vs&nbsp;" +rec.data.away +"</div>"+
                    "<div>开赛时间:"+startTime+"</div>"+
                    "<div>让分:" + rec.data.hdp+"</div> ";
            },
            width:400
		},

        {
            text:"Sbobet",
            renderer : function (v,m,rec) {
              	return renderPlatform(rec.data,"Sbobet");
            },
            width:200
        },
        {
            text:"PS3838",
            renderer : function (v,m,rec) {
                return renderPlatform(rec.data,"PS3838");
            },
            width:200
        },
        {
            text:"ISN",
            renderer : function (v,m,rec) {
                return renderPlatform(rec.data,"ISN");
            },
            width:200
        },
		// {
	// 	text:'主队赔率',
	// 	dataIndex:'max_home_odds',
	// 	width:200
	// },
	// {
	// 	text:'客队赔率',
	// 	dataIndex:'max_away_odds',
	// 	width:200
	// },
	{
		xtype:'actioncolumn',
		text : '操作',
        hidden:true,
		align: 'center',
		items:[
		       ]
     }],
	dockedItems:[{
		xtype: 'toolbar',
		dock: 'top',
		items:[
					'队名:',{ xtype:'textfield', name:'_id|like'}
					// ,'-',
					// "只查看匹配成功数据",{xtype:"checkboxfield",name:"maxHandicap.enough", inputValue: 'true', checked:true}
					// ,'-',
				 	// '开始时间:',{ xtype:'datefield',format:'Y-m-d',name:'start_time|ge'},'至',{ xtype:'datefield',format:'Y-m-d', name:'start_time|le'}
		       ]
	},{
    	xtype: 'toolbar',
	    dock: 'top',
	    items: [
	        {text:'查询',handler:Spread.query,iconCls:'Magnifier'},
            {text:'重置',iconCls:'Pagerefresh',handler:Spread.reset}
	    ]
    },{
        xtype: 'pagingtoolbar',
        store: Spread.store,
        dock: 'bottom',
        displayInfo: true
    }]
});


// var sock;
//
// function createSock() {
// 	sock = new WebSocket("ws://" + localObj.host + "/springws/allspread.ws");
// 	sock.onopen = function () {
// 		console.log('Info: connection opened.');
// 	}
//
// 	sock.onmessage = function (event) {
// 		var rec = Ext.JSON.decode(event.data);
// 		var startTime = new Date().getTime();
// 		var platform = rec.platform;
// 		var spread = rec.spread;
// 		var id = spread.unique_key;
// 		var update = Spread.store.getById(id);
// 		if (update) {
// 			var org = update.get(platform);
// 			if (org.homeOdds != spread.homeOdds || org.awayOdds != spread.awayOdds) {
// 				update.set(platform, spread);
// 				update.set("update_time", startTime);
// 			}
// 			// update.data.spreads.platform = spread;
// 		}
//
// 		var end = new Date().getTime();
// 		console.log("耗时" + (end - startTime) + "ms");
// 	};
//
// 	sock.onclose = function (event) {
// 		console.log('Info: connection closed.');
// 		console.log(event);
// 		sock = createSock();
// 	};
// }
Ext.onReady(function() {
	Ext.tip.QuickTipManager.init();
	var viewport = new Ext.Viewport({
		layout : 'fit',
		items : [Spread.gridPanel]
	});
	Spread.query();
	// createSock();

});




