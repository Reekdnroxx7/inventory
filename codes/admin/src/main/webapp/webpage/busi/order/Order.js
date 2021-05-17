

Ext.namespace("SpreadOrder");

//字典配置
var StatusCode = [
    ['','全部'],
    // ['0',''],
    ['1','单边失败'],
    ['2','成功']
]

//ComboBox 定义：
var statusCombo = new Ext.form.ComboBox({
    hiddenName: 'status',            //提交时的 combo Name
    name:"status",
    allowBlank: 'true',            //是否允许为空
    width:150,
    triggerAction: 'all',           //默认为"query"，选择某值后，再次选择时只出现匹配选项，"all"表示再次选择时出现所有项
    emptyText : '请选择',            //未选择时显示的文字
    blankText : '该选项必选填写',    //未选择时，提交表单显示的错误信息,
    store: new Ext.data.SimpleStore({
    fields: ['value', 'text'],
    data : StatusCode
    }),
    value:'2',
    mode: 'local',                  //数据加载模式，local 代表本地数据
    valueField: 'value',            //选项的 value 值，提交时传递的该值
    displayField: 'text'
});


//方法
SpreadOrder.changeStatus = function (rec, status){
//	  var rec = grid.getStore().getAt(rowIndex);
	var data = rec.data;

	var url ;
	if(status == 9){
		url = "spreadorder.ajax?method=stopReverse";
	}else if(status == 1){
		url = "spreadorder.ajax?method=resumeReverse";
	}else {
		return ;
	}

	Ext.Ajax.request({
		url: url,
		params:data,
		success: function(response){
			var text = response.responseText;
			var result = Ext.JSON.decode(text);
			if(result.success){
				Ext.Msg.alert("信息","操作成功");
				SpreadOrder.store.reload();
			}else{
				Ext.Msg.alert("错误",result.message);
			}
		}
	});
};

SpreadOrder.query = function(){
	SpreadOrder.store.load({
		callback : function(records, operation, success) {

			if (success == false) {
				Ext.Msg.alert("错误", SpreadOrder.store.proxy.reader.jsonData.message );
			}
		}
	});
};

SpreadOrder.getParams = function (){
	var docks= SpreadOrder.gridPanel.getDockedItems('toolbar[dock="top"]');
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

SpreadOrder.reset = function(){
	var docks= SpreadOrder.gridPanel.getDockedItems('toolbar[dock="top"]');
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



SpreadOrder.store = Ext.create('Ext.data.Store',{
	fields:[
		'id',
		'allSpread',
		'home_order',
		'away_order',
		"home_cn_name",
		"away_cn_name",
		"league_name",
		"league_cn_name",
		"status",
		"fixed",
		'update_time',
		{ name : "start_time",convert: function(v,r){
			return r.get("allSpread").start_time;
		}},
	],
	pageSize:20,
	proxy: {
    	type: 'ajax',
        url : "spreadorder.ajax?method=listSpreadOrder",
        reader: {
            type: 'json',
            root: 'resultList',
            totalProperty: 'totalCount'
        }
    }
});
SpreadOrder.store.on("beforeload",function (source, operation) {
	var params = SpreadOrder.getParams("queryBar");
	operation.params = Ext.apply(operation.params || {}, params);
});



SpreadOrder.gridPanel = Ext.create('Ext.grid.Panel',{
	title:'订单',
	store:SpreadOrder.store,
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
		text:'赛事id',
		dataIndex:'allSpread',
		hidden:true,
		width:200
	},
	{
		text:'主队',
        dataIndex:'allSpread',
        renderer:function(value){

            return value.home;
        },
		hidden:true,
		width:200
	},
		{
			text:'客队',
			dataIndex:'allSpread',
			renderer:function(value){
				return value.away;
			},
			hidden:true,
			width:200
		},
		{
			text:'主队(中)',
			dataIndex:'home_cn_name',
			renderer: function (value,meta,rec) {
				if(value){
					return value;
				}
				return rec.get("allSpread").home;
			},
			width:200
		},
		{
			text:'客队(中)',
			dataIndex:'away_cn_name',
			renderer: function (value,meta,rec) {
				if(value){
					return value;
				}
				return rec.get("allSpread").away;
			},
			width:200
		},
		{
			text:'联赛(中)',
			dataIndex:'league_cn_name',
			renderer: function (value,meta,rec) {
				if(value){
					return value;
				}
				return rec.get("league");
			},
			width:200
		},

	{
		text:'开始时间',
		dataIndex:'start_time',
        renderer:function(value){
            return Ext.Date.format(new Date(value),("Y-m-d H:i"));
        },
		width:170
	},

	{
		text:'让分',
        dataIndex:'allSpread',
        renderer:function(value){
            return value.hdp;
        },
		width:70
	},
		{
			text:'下注时间',
			dataIndex:'update_time',
			renderer:function(value){
				return Ext.Date.format(new Date(value),("Y-m-d H:i:s"));
			},
			width:170
		},
		{
            text:'主队bet',
            dataIndex:'home_order',
            renderer:function(value){
                if(!value){
                    return "";
                }
                var result = value['@class'];
				if(result){
					result = result.substr(result.lastIndexOf("."));
					result = result.substr(1,result.length-6);
				}
				result = result + "&nbsp;" + Ext.Date.format(new Date(value['update_time']),("Y-m-d H:i:s"));
				result = result + "</br>";
				result = result +"赔率：" + value.spread.homeOdds.toFixed(3);
                result = result + "</br>"
                result = result + "金额：" + value.money.toFixed(3);
                result = result + "</br>"
                result = result + "状态："
                if(value.success){
                    result = result + "成功";
                }else{
                    result = result + "失败";
                }
                return result;
            },
            width:200
        },
        {
            text:'客队bet',
            dataIndex:'away_order',
            renderer:function(value){
                if(!value){
                    return "";
                }
				var result = value['@class'];
				if(result){
					result = result.substr(result.lastIndexOf("."));
					result = result.substr(1,result.length-6);
				}
				result = result + "&nbsp;" + Ext.Date.format(new Date(value['update_time']),("Y-m-d H:i:s"));
				result = result + "</br>";
                result = result +"赔率：" + value.spread.awayOdds.toFixed(3);
                result = result + "</br>"
                result = result + "金额：" + value.money.toFixed(3);
                result = result + "</br>"
                result = result + "状态："
                if(value.success){
                    result = result + "成功";
                }else{
                    result = result + "失败";
                }
                return result;
            },
            width:200
        },
		{
			text:'状态',
			dataIndex:'status',
			renderer:function(value,meta,rec){
				if(value == 2){
					if(rec.get("fixed")){
						return "自动补货成功"
					}else{
						return "成功"
					}
				}
				if(value == 1){
					return "单边失败"
				}
				if(value == 9){
					return "手工补货"
				}

				return "失败";
			},
			width:100
		},
        {
		xtype:'actioncolumn',
		text : '操作',
        hidden:false,
		align: 'center',
		items:[

			{iconCls:'Controlstartblue',tooltip:'开始自动补货',handler:function(grid, rowIndex, colIndex){
				var rec = grid.getStore().getAt(rowIndex);
				SpreadOrder.changeStatus(rec,1);
			},isDisabled : function(v,r,c,i,record){
				return record.data.status != 9;
			}},'-',
			{iconCls:'Controlpause',tooltip:'停止自动补货',handler:function(grid, rowIndex, colIndex){
				var rec = grid.getStore().getAt(rowIndex);
				SpreadOrder.changeStatus(rec,9);
			},isDisabled : function(v,r,c,i,record){
				return record.data.status != 1;
			}}
		       ]
     }],
	dockedItems:[{
		xtype: 'toolbar',
		dock: 'top',
		items:[
					'状态:',statusCombo
					// ,'-',
					// '客队',{ xtype:'textfield', name:'away|like'}
					// ,'-',
					// "只查看匹配成功数据",{xtype:"checkboxfield",name:"maxHandicap.enough", inputValue: 'true', checked:true}
					,'-',
				 	'下注时间:',{ xtype:'datefield',format:'Y-m-d',name:'update_time|ge', value:new Date()},'至',{ xtype:'datefield',format:'Y-m-d', name:'update_time|le'}
		       ]
	},{
    	xtype: 'toolbar',
	    dock: 'top',
	    items: [
	        {text:'查询',handler:SpreadOrder.query,iconCls:'Magnifier'},
            {text:'重置',iconCls:'Pagerefresh',handler:SpreadOrder.reset}
	    ]
    },{
        xtype: 'pagingtoolbar',
        store: SpreadOrder.store,
        dock: 'bottom',
        displayInfo: true
    }]
});




