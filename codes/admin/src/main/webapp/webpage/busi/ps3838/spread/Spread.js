Ext.namespace("Spread");
//字典配置
//方法
Spread.query = function(){
	var params = Spread.getParams("queryBar");
	var filters = [];
	Spread.store.clearFilter(true);
	Ext.Object.each(params,function (key, value, me) {
		filters.push({property:key, value: new RegExp(".*?"+value+".*?")});
	});
	if(filters.length > 0){
		Spread.store.filter(filters);
	}else {
		Spread.store.clearFilter();
	}
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
						params[name]= value;
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
	};
	Spread.store.clearFilter();
};

Ext.define('PS3838Spread', {
	extend: 'Ext.data.Model',
	fields:[
		'id',
		'match',
		'homeOdds',
		'awayOdds',
		"deleted",
		"line_id",
		"altline_id",
		'hdp',
		{ name : "start_time",convert: function(v,r){
			return r.get("match").start_time;
		}},
		{ name : "home",convert: function(v,r){
			return r.get("match").home;
		}},
		{ name : "away",convert: function(v,r){
			return r.get("match").away;
		}}
	]
});

Spread.store = Ext.create('Ext.data.Store',{

	model:"PS3838Spread",
	// pageSize:20,
	proxy: {
    	type: 'ajax',
        url : "sbobetSpread.ajax?method=listSpread",
        reader: {
            type: 'json',
            root: 'resultList',
            totalProperty: 'totalCount'
        }
    },
	sorters: [
		// {
		// 	property : 'id',
		// 	direction: 'ASC'
		// },
		{
			property : 'start_time',
			direction: 'ASC'
		},
		{
			property : 'home',
			direction: 'ASC'
		},
		{
			property : 'hdp',
			direction: 'ASC'
		}
	]

});


Spread.gridPanel = Ext.create('Ext.grid.Panel',{
	title:'ps3838赔率',
	store:Spread.store,
	rowLines:true,
	columnLines : true,
	columns:[
	{xtype: 'rownumberer', width:40},
	{
		text:'主键',
		dataIndex:'id',
		// hidden:true,
		width:200
	},
	{
		text:'无效',
		dataIndex:'deleted',
		renderer : function (value) {
			if(value){
				return value;
			}
			return false;
		},
		hidden:true,
		width:200
	},
	{
		text:'赛事id',
		dataIndex:'match',
		renderer:function(value){
			return value.event_id;
		},
		hidden:true,
		width:200
	},
	{
		text:'盘口',
		dataIndex:"line_id",
		renderer:function(value){
			return value;
		},
		hidden:true,
		width:200
	},
		{
			text:'子盘口',
			dataIndex:"altline_id",
			renderer:function(value){
				return value;
			},
			hidden:true,
			width:200
		},
		{
		text:'主队',
		dataIndex:'home',
		width:200
	},	
	{
		text:'客队',
		dataIndex:'away',
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
		text:'让分',
		dataIndex:'hdp',
		renderer: function (value) {
			return 0-value;
		},
		width:70
	},	
	{
		text:'赔率',
		// dataIndex:'ps3838_odds',
		renderer: function (val,meta,rec) {
			return "<span style='color: red'>"+rec.get("homeOdds")+"</span><br/><span style='color: green'>"+rec.get("awayOdds")+"</span>"
		},
		width:100
	}],
	dockedItems:[{
		xtype: 'toolbar',
		dock: 'top',
		items:[
					'主队:',{ xtype:'textfield', name:'home'}
					,'-',
					'客队',{ xtype:'textfield', name:'away'}
		       ]
	},{
    	xtype: 'toolbar',
	    dock: 'top',
	    items: [
	        {text:'查询',handler:Spread.query,iconCls:'Magnifier'},
            {text:'重置',iconCls:'Pagerefresh',handler:Spread.reset}
	    ]
    }/*,{
        xtype: 'pagingtoolbar',
        store: Spread.store,
        dock: 'bottom',
        displayInfo: true
    }*/]
});

Ext.onReady(function() {
	Ext.tip.QuickTipManager.init();
	var viewport = new Ext.Viewport({
		layout : 'fit',
		items : [Spread.gridPanel]
	});
	// Spread.query();

	var sock = new WebSocket("ws://"+localObj.host+"/springws/ps3838.ws") ;
	sock.onopen = function(){
		console.log('Info: connection opened.');
	}

	sock.onmessage = function (event) {
		var array = Ext.JSON.decode(event.data);
		var update = [];
		var del = [];
		// Spread.grid.reconfigure(store);
		var startTime = new Date().getTime();
		for(var i=0; i<array.length;i++){
			var rec = array[i];
			var spread = Ext.create("PS3838Spread",rec);
			if(spread.get("deleted")){
				// Spread.store.add(rec);
				// update.push(rec);
				// Spread.store.remove(rec);
				console.log(spread.get("id")+"------ "+Spread.store.getById(spread.get("id")));
				del.push(spread)
			}else{
				update.push(spread);
			}
		}
		if(del.length > 0 ){
			console.log(Spread.store.data.length);
			Spread.store.remove(del);
			console.log(Spread.store.data.length);
		}
		Spread.store.add(update);
		Spread.store.resumeEvents();
		if(Spread.store.isFiltered( )){
			Spread.query();
		}else{
			Spread.store.sort();
		}
		var end = new Date().getTime();
		console.log("耗时"+(end-startTime)+"ms");
	};

	sock.onclose = function (event) {
		console.log('Info: connection closed.');
		console.log(event);
	};

});


	

