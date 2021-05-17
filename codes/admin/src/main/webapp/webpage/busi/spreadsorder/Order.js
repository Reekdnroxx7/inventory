

Ext.namespace("SpreadOrder");

//字典配置
var StatusCode = [
    ['0','全部'],
    // ['0',''],
    ['1','有效'],
    ['2','成功']
]

var STATUS = [];
STATUS[0] = "无效";
STATUS[1] = "单边失败";
STATUS[2] = "成功";
STATUS[3] = "补货成功";
STATUS[4] = "强制补货成功";


//ComboBox 定义：
var statusCombo = new Ext.form.ComboBox({
    hiddenName: 'status|ge',            //提交时的 combo Name
    name:"status|ge",
    allowBlank: 'true',            //是否允许为空
    width:150,
    triggerAction: 'all',           //默认为"query"，选择某值后，再次选择时只出现匹配选项，"all"表示再次选择时出现所有项
    emptyText : '请选择',            //未选择时显示的文字
    blankText : '该选项必选填写',    //未选择时，提交表单显示的错误信息,
    store: new Ext.data.SimpleStore({
    fields: ['value', 'text'],
    data : StatusCode
    }),
    value:'1',
    mode: 'local',                  //数据加载模式，local 代表本地数据
    valueField: 'value',            //选项的 value 值，提交时传递的该值
    displayField: 'text'
});


//方法
SpreadOrder.changeStatus = function (rec, status){
//	  var rec = grid.getStore().getAt(rowIndex);
	var data = rec.data;

	var url ;
	if(status ){
		url = "spreadsorder.ajax?method=stopReverse";
	}else {
        url = "spreadsorder.ajax?method=resumeReverse";
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
		'_id',
		"home_cn_name",
		"away_cn_name",
		"league_name",
		"league_cn_name",
		"initSpread",
		"createTime",
		"totalMoney",
		"first",
		"second",
		"fix",
		"pauseErrorHandler",
		"status"

	],
	pageSize:20,
	proxy: {
    	type: 'ajax',
        url : "spreadsorder.ajax?method=listSpreadOrder",
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
function renderOrder(order) {
    var request = order.request;
    var response = order.orderResult;
    var status = response.success ? "成功":"失败";
    var date = Ext.Date.format(new Date(request.spread.initTime["$date"]), ("Y-m-d H:i:s.u"));
    return "<div>盘口：" + request.team + "</div>" +
        "<div>平台：" + request.spread.platform + "</div>" +
        "<div>金额：" + request.money.toFixed(3) + "</div>" +
        "<div>状态：" + status + "</div>" +
        "<div>主队赔率：" + request.spread.homeOdds + "</div>" +
        "<div>客队赔率：" + request.spread.awayOdds + "</div>" +
        "<div>赔率更新时间:" + date + "</div>"
}


SpreadOrder.gridPanel = Ext.create('Ext.grid.Panel',{
	title:'订单',
	store:SpreadOrder.store,
	rowLines:true,
	columnLines : true,
	columns:[
	{xtype: 'rownumberer'},
	{
		text:'主键',
		dataIndex:'_id',
		renderer: function (v) {
			return v["$oid"];
        },
		// hidden:true,
		width:200
	},
	{
		text:'赛事id',
		dataIndex:'allSpread',
		hidden:true,
		width:200
	},
	{
		text:'赛事',
        renderer:function(v,meta,rec){
			rec = rec.data;
			var status = STATUS[rec.status]

			var match = rec.initSpread.match;
			var date  =  Ext.Date.format(new Date(match.startTime["$date"]),("Y-m-d H:i"));
			var createTime  =  Ext.Date.format(new Date(rec.createTime["$date"]),("Y-m-d H:i:s.u"));
            return "<div>"+ rec.initSpread.match.home +"&nbsp;Vs&nbsp;" +rec.initSpread.match.away +"</div>"+
                "<div>开赛时间:"+date+"</div>"+
                "<div>让分:" + rec.initSpread.hdp+"</div> "+
                "<div>金额:" + rec.totalMoney.toFixed(3)+"</div> "+
                "<div>状态:" + status+"</div> "+
                "<div>投注时间:"+createTime+"</div>";
        },
		width:300
	},
		{
			text:'投注1',
			renderer:function(v,meta,rec){
				rec = rec.data;
				var order = rec.first;

                return renderOrder(order);
			},
			width:300
		},

        {
            text:'投注2',
            renderer:function(v,meta,rec){
                rec = rec.data;
                var order = rec.second;
				if(order){
                    return renderOrder(order);
                }
            },
            width:300
        },

        {
            text:'补货',
            renderer:function(v,meta,rec){
                rec = rec.data;
                var order = rec.fix;
                if(order){
                    return renderOrder(order);
                }
            },
            width:300
        },

        {
		xtype:'actioncolumn',
		text : '操作',
        hidden:false,
		align: 'center',
		items:[

			{iconCls:'Controlstartblue',tooltip:'开始自动补货',handler:function(grid, rowIndex, colIndex){
				var rec = grid.getStore().getAt(rowIndex);
				SpreadOrder.changeStatus(rec,false);
			},isDisabled : function(v,r,c,i,record){
                return !record.data.pauseErrorHandler  && record.dataType.status ==1;
			}},'-',
			{iconCls:'Controlpause',tooltip:'停止自动补货',handler:function(grid, rowIndex, colIndex){
				var rec = grid.getStore().getAt(rowIndex);
				SpreadOrder.changeStatus(rec,true);
			},isDisabled : function(v,r,c,i,record){
				return record.data.pauseErrorHandler  && record.dataType.status ==1;
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
				 	'下注时间:',{ xtype:'datefield',format:'Y-m-d',name:'updateTime|ge'},'至',{ xtype:'datefield',format:'Y-m-d', name:'updateTime|le'}
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




