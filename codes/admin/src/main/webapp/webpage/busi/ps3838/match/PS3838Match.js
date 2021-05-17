Ext.namespace("PS3838Match");
//字典配置
//方法
PS3838Match.query = function(){
	var params = PS3838Match.getParams("queryBar");
	PS3838Match.store.load({
		params : params ,
		callback : function(records, operation, success) {
			if (success == false) {
				Ext.Msg.alert("错误", PS3838Match.store.proxy.reader.jsonData.message );
			}
		}
	});
};

PS3838Match.getParams = function (){
	var docks= PS3838Match.gridPanel.getDockedItems('toolbar[dock="top"]');
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

PS3838Match.reset = function(){
	var docks= PS3838Match.gridPanel.getDockedItems('toolbar[dock="top"]');
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

PS3838Match.save = function (){
	PS3838Match.formPanel.getForm().submit({
		clientValidation: true,
		url: 'ps3838match.ajax?method=savePS3838Match',
		success: function(form, action) {
			PS3838Match.formWin.hide();
			PS3838Match.store.reload();
		},
		failure: function(form, action) {
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
};

PS3838Match.connect = function (match,std_match){
	Ext.Ajax.request({
		url: 'ps3838match.ajax?method=connect',
		params:{matchId:match.get("id"),sbobet_match_id:std_match.get("id")},
		success: function(response){
			var text = response.responseText;
			var result = Ext.JSON.decode(text);
			if(result.success){
				PS3838Match.store.reload();
			}else{
				Ext.Msg.alert("错误",result.message);
			}
		}
	});
};

PS3838Match.disconnect = function (grid, rowIndex, colIndex){
	var store = grid.getStore();
	var rec = store.getAt(rowIndex);
	Ext.Ajax.request({
		url: 'ps3838match.ajax?method=disconnect',
		params:{matchId:rec.get("id")},
		success: function(response){
			var text = response.responseText;
			var result = Ext.JSON.decode(text);
			if(result.success){
				store.reload();
			}else{
				Ext.Msg.alert("错误",result.message);
			}
		}
	});
};

PS3838Match.store = Ext.create('Ext.data.Store',{
	fields:[
		'id',
		'sport_id',
		'league_id',
		'event_id',
		'start_time',
		'home',
		'away',
		'live_status',
		'unique_key',
		'stdHome',
		'stdAway',
		'status',
		'deleted'
	],
	pageSize:20,
	proxy: {
		type: 'ajax',
		url : "ps3838match.ajax?method=listMatch",
		reader: {
			type: 'json',
			root: 'resultList',
			totalProperty: 'totalCount'
		}
	}
});
PS3838Match.store.load();

PS3838Match.gridPanel = Ext.create('Ext.grid.Panel',{
	title:'智博比赛管理',
	store:PS3838Match.store,
	rowLines:true,
	columnLines : true,
	columns:[
		{xtype: 'rownumberer'},
		{
			text:'主键',
			dataIndex:'id',
			hidden:true,
			width:200
		},
		{
			text:'运动类型id',
			dataIndex:'sport_id',
			hidden:true,
			width:200
		},
		{
			text:'联赛id',
			dataIndex:'league_id',
			hidden:true,
			width:200
		},
		{
			text:'事件id',
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
			hidden:true,
			width:200
		},
		{
			text:'匹配信息',
			dataIndex:'unique_key',
			renderer:function(value,meta,record){
				if(record.get("stdHome") && record.get("stdAway")){
					return record.get("stdHome") + " VS " + record.get("stdAway");
				}else{
					return "未匹配到赛事"
				}
			},
			width:300
		},
		{
			xtype:'actioncolumn',
			text : '操作',
			align: 'center',
			items:[{
				iconCls:'Connect',tooltip:'配对',
				isDisabled: function( view, rowIdx, colIdx, item, record){
					return  record.get("stdHome") && record.get("stdAway");
				},
				handler:function(grid, rowIndex, colIndex){
					var rec = grid.getStore().getAt(rowIndex);
					PS3838Match.dialog.showByQuery(rec);
				}}/*,
				'-',
				{
					iconCls:'Disconnect',
					tooltip:'取消配对',
					isDisabled: function( view, rowIdx, colIdx, item, record){
						return !record.get("unique_key");
					},
					handler:function(grid, rowIndex, colIndex){
						PS3838Match.disconnect(grid,rowIndex,colIndex);
					}
				}*/
			]
		}],
	dockedItems:[{
		xtype: 'toolbar',
		dock: 'top',
		items:[
			'主队:',{ xtype:'textfield', name:'home|like'}
			,'-',
			'客队',{ xtype:'textfield', name:'away|like'}
			,'-',
			'开始时间:',{ xtype:'datefield',format:'Y-m-d',name:'start_time|ge'},'至',{ xtype:'datefield',format:'Y-m-d', name:'start_time|le'}
		]
	},{
		xtype: 'toolbar',
		dock: 'top',
		items: [
			{text:'查询',handler:PS3838Match.query,iconCls:'Magnifier'},'-',
			{text:'重置',iconCls:'Pagerefresh',handler:PS3838Match.reset}
		]
	},{
		xtype: 'pagingtoolbar',
		store: PS3838Match.store,
		dock: 'bottom',
		displayInfo: true
	}]
});
PS3838Match.formPanel = Ext.create('Ext.form.Panel',{
	bodyPadding: 10,
	defaultType:'textfield',
	items:[
		{fieldLabel:'',xtype:'numberfield',name:'sport_id',allowBlank:false},
		{fieldLabel:'',xtype:'numberfield',name:'league_id',allowBlank:false},
		{fieldLabel:'',xtype:'numberfield',name:'event_id',allowBlank:false},
		{fieldLabel:'开始时间',xtype:'datefield',format:'Y-m-d',name:'start_time',allowBlank:false},
		{fieldLabel:'主队名称',xtype:'textfield',name:'home',allowBlank:false},
		{fieldLabel:'客队名称',xtype:'textfield',name:'away',allowBlank:false},
		new Ext.form.Hidden({name:'id'}),
		new Ext.form.Hidden({name:'_saveType'})
	]
});

PS3838Match.dialog = new SbobetMatch.MatchDialog({
	modal:true,
	onMatchSelected : function (match,std_match) {
		PS3838Match.connect(match,std_match);
	}
});