

Ext.namespace("Config");

Config.load = function(){
	Config.formPanel.load({
		url: 'config.ajax?method=allConfig',
		success:function(){
			Config.formPanel.getForm().getFields().each(function (item) {
				var name = item['name'];
				item.on("change",function(me,newValue,oldValue){
					if(me.getSubmitValue){
						var submitValue = me.getSubmitValue();
						if(submitValue){
							Config.save(name,submitValue);
						}
					}
				});

			})
		}
	});
}

Config.save = function (key, value){
	Ext.Ajax.request({
		url: 'config.ajax?method=saveConfig',
		params:{ key : key , value:value},
		success: function(response){
		}
	});
};

Config.formPanel = Ext.create('Ext.form.Panel', {
	title : "业务参数配置",
	bodyPadding : 10,
	layout:'column',
	items : [
		{
			columnWidth: .33,
			border : 0,
			items:[
				{
					xtype:"textfield",
					allowDecimals:true,
					fieldLabel : '最小利润率',
					id:"PROFIT_RATE",
					name : 'PROFIT_RATE',
				}
			]
		},
		{
			columnWidth: .33,
			border : 0,
			items:[
				{
					xtype:"textfield",
					allowDecimals:true,
					fieldLabel : '最大利润率',
					id:"MAX_PROFIT",
					name : 'MAX_PROFIT',
				}
			]
		},
		{
			columnWidth: .33,
			border : 0,
			items:[
				{
					xtype:"checkbox",
					labelWidth:130,
					inputValue  : 'true',
					uncheckedValue : 'false',
					fieldLabel : '只做利记网上升赔率',
					name : 'ONLY_SBOBET',
				}
			]
		},

		{
			columnWidth: .33,
			border : 0,
			items:[
				{
					xtype:"checkbox",
					inputValue  : 'true',
					uncheckedValue : 'false',
					fieldLabel : '强制补货',
					name : 'FORCE_REVERSE',
				}
			]
		},
		{
			columnWidth: .33,
			border : 0,
			layout:'column',
			items:[
				{
					width: 300,
					xtype: 'radiogroup',
					fieldLabel : '投注金额',
					items: [
						{ boxLabel: '最小金额', name: 'ORDER_MONEY_TYPE', inputValue: 'MINIMAL',checked : true },
						{ boxLabel: '固定金额(元)', name: 'ORDER_MONEY_TYPE', inputValue: 'FIXED_MONEY',
							listeners  : {
								"change" : function (me, newValue, oldValue){
									if(newValue){
										Ext.getCmp("FIXED_MONEY").show();
									}else {
										Ext.getCmp("FIXED_MONEY").hide();
									}
								}
							}
						}
						]

				},
				{
					xtype: 'textfield',
					fieldLabel : '投注金额',
					width:100,
					hideLabel :true,
					id : "FIXED_MONEY",
					name : "FIXED_MONEY"
				}

			]
		},
		{
			columnWidth: .33,
			border : 0,
			items:[
				{
					xtype:"textfield",
					labelWidth:210,
					allowDecimals:true,
					fieldLabel : '下注开始时间（开赛前分钟）',
					name : 'MAX_START_TIME',
				}
			],
			hidden:true
		},
		{
			columnWidth: .33,
			border : 0,
			items:[
				{
					xtype:"textfield",
					labelWidth:210,
					allowDecimals:true,
					fieldLabel : '下注截止时间（开赛前分钟）',
					name : 'ORDER_DEADLINE',
				}
			]
		},
		{
			columnWidth: .33,
			border : 0,
			items:[
				{
					xtype:"textfield",
					// labelWidth:210,
					allowDecimals:true,
					fieldLabel : '佣金返率',
					name : 'KICKBACK_RATE',
				}
			]
		},
		{
			columnWidth: .33,
			border : 0,
			items:[
				{
					xtype:"textfield",
					// labelWidth:210,
					allowDecimals:true,
					fieldLabel : '投注间隔（秒）',
					name : 'ORDER_INTERVAL'
				}
			],
			hidden:true
		},
		{
			columnWidth: .33,
			border : 0,
			items:[
				{
					xtype:"textfield",
					// labelWidth:210,
					allowDecimals:true,
					fieldLabel : '最小赔率过滤',
					name : 'MIN_ODDS'
				}
			]
		}

		,
		{
			columnWidth: .33,
			border : 0,
			items:[
				{
					xtype:"textfield",
					// labelWidth:210,
					allowDecimals:true,
					fieldLabel : 'PS3838网址',
					name : 'PS3838_HTTP_HOST'
				}
			]
		},
		{
			columnWidth: .33,
			border : 0,
			items:[
				{
					xtype:"textfield",
					// labelWidth:210,
					allowDecimals:true,
					fieldLabel : 'ISN网址',
					name : 'ISN_HTTP_HOST'
				}
			]
		}

		,
		{
			columnWidth: .33,
			border : 0,
			items:[
				{
					xtype:"textfield",
					// labelWidth:210,
					allowDecimals:true,
					fieldLabel : 'SBOBET_RATE',
					name : 'SBOBET_RATE'
				}
			]
		}
		,
		{
			columnWidth: .33,
			border : 0,
			items:[
				{
					xtype:"textfield",
					// labelWidth:210,
					allowDecimals:true,
					fieldLabel : 'PS3838_RATE',
					name : 'PS3838_RATE'
				}
			]
		}
		,
		{
			columnWidth: .33,
			border : 0,
			items:[
				{
					xtype:"textfield",
					// labelWidth:210,
					allowDecimals:true,
					fieldLabel : 'ISN_RATE',
					name : 'ISN_RATE'
				}
			]
		}




		]
});