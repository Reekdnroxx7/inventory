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

SbobetMatch.save = function (){
	SbobetMatch.formPanel.getForm().submit({
		 clientValidation: true,
		 url: 'sbobetmatch.ajax?method=saveSbobetMatch',
		 success: function(form, action) {
			 SbobetMatch.formWin.hide();
			 SbobetMatch.store.reload();
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

SbobetMatch.connect = function (match,ps3838Match){
        Ext.Ajax.request({
            url: 'sbobetmatch.ajax?method=connect',
            params:{matchId:match.get("id"),ps3838_match_id:ps3838Match.get("id")},
            success: function(response){
                var text = response.responseText;
                var result = Ext.JSON.decode(text);
                if(result.success){
                    SbobetMatch.store.reload();
                }else{
                    Ext.Msg.alert("错误",result.message);
                }
            }
        });
};

SbobetMatch.disconnect = function (grid, rowIndex, colIndex){
        var store = grid.getStore();
        var rec = store.getAt(rowIndex);
        Ext.Ajax.request({
            url: 'sbobetmatch.ajax?method=disconnect',
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
        'unique_key',
		'stdHome',
		'stdAway',
		'stdStartTime',
		'status',
		'deleted'
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
	title:'利记比赛管理',
	store:SbobetMatch.store,
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
		dataIndex:'stdStartTime',
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
    // {
    //     text:'匹配信息',
    //     dataIndex:'unique_key',
    //     renderer:function(value,meta,rec){
    //         if(!value){
    //             return "未匹配到赛事"
    //         }else{
    //             return rec.get("stdHome") + " VS " + rec.get("stdAway");
    //         }
    //     },
    //     width:300
    // },
	// {
	// 	xtype:'actioncolumn',
	// 	text : '操作',
	// 	align: 'center',
	// 	items:[{
     //        iconCls:'Connect',tooltip:'配对',
     //        isDisabled: function( view, rowIdx, colIdx, item, record){
     //            return record.get("unique_key");
     //        },
     //        handler:function(grid, rowIndex, colIndex){
     //            var rec = grid.getStore().getAt(rowIndex);
     //            SbobetMatch.ps3838Dialog.showByQuery(rec);
     //        }},
     //        '-',
     //        {
     //            iconCls:'Disconnect',
     //            tooltip:'取消配对',
     //            isDisabled: function( view, rowIdx, colIdx, item, record){
     //                return !record.get("unique_key");
     //            },
     //            handler:function(grid, rowIndex, colIndex){
     //                SbobetMatch.disconnect(grid,rowIndex,colIndex);
     //            }
     //        }
	// 	       ]
     // }
		],
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
SbobetMatch.formPanel = Ext.create('Ext.form.Panel',{
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

// SbobetMatch.ps3838Dialog = new PS3838Match.MatchDialog({
//     modal:true,
//     onMatchSelected : function (match,ps3838Match) {
//         SbobetMatch.connect(match,ps3838Match);
//     }
// });