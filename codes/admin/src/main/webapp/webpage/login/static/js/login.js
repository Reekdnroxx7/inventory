$(document).ready(function() {
	getCookie();
	onfocus();
	$(".on_off_checkbox").iphoneStyle();
	$('.tip a ').tipsy({
		gravity : 'sw'
	});
//	$('#login').show().animate({
//		opacity : 1
//	}, 2000);
//	$('.logo').show().animate({
//		opacity : 1,
//		top : '32%'
//	}, 800, function() {
//		$('.logo').show().delay(1200).animate({
//			opacity : 1,
//			top : '1%'
//		}, 300, function() {
//			$('.formLogin').animate({
//				opacity : 1,
//				left : '0'
//			}, 300);
//			$('.userbox').animate({
//				opacity : 0
//			}, 200).hide();
//		});
//	});

});
$('.userload').click(function(e) {
	$('.formLogin').animate({
		opacity : 1,
		left : '0'
	}, 300);
	$('.userbox').animate({
		opacity : 0
	}, 200, function() {
		$('.userbox').hide();
	});
});
// 重置
$('#forgetpass').click(function(e) {
	$(":input").each(function() {
	$('#'+this.name).val("");
	});
});
// 点击登录
$('#but_login').click(function(e) {
	submit();
});
//回车登录
$(document).keydown(function(e){
	if(e.keyCode == 13) {
		submit();
	}
});
//表单提交
function submit()
{
	var submit = true;
	$("input[nullmsg]").each(function() {
		if ($("#" + this.name).val() == "") {
			showError($("#" + this.name).attr("nullmsg"), 500);
			jrumble();
			setTimeout('hideTop()', 1000);
			submit = false;
			return false;
		}
	});
	if (submit) {
		hideTop();
//		loading('核实中..', 1);
		Login();
//		setTimeout("Login()", 1000);
	}

}
var inputtimes=0;
//登录处理函数
function Login() {
	setCookie();
	var actionurl=$('form').attr('action');//提交路径
	var checkurl=$('form').attr('check');//验证路径
	 var formData = new Object();
	var data=$(":input").each(function() {
		 formData[this.name] =$("#"+this.name ).val();
	});
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : checkurl,// 请求的action路径
		data : formData,
		error : function() {// 请求失败处理函数
		},
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
				loginsuccess();
				setTimeout("window.location.href='"+actionurl+"'", 1000);
			} else {
				inputtimes=d.obj;
				if(d.message == "a"){
					$.dialog.confirm("数据库无数据,是否初始化数据?", function(){
						window.location = "init.jsp";
					}, function(){
						//
					});
				} else{
					
					if(inputtimes>2){
						 if(inputtimes>3){
							   showError(d.message);
						 }else{
							  showError("输入多次错误，请输入验证码"); 
						 }
						
						var testdiv = document.getElementById("checkcodediv");
                        testdiv.innerHTML=" <input id='checkcode'  style='width:140px;' class='checkcode' name='checkcode' type='text' id='checkcode' title='验证码'  nullmsg='请输入验证码!' \/>&nbsp;&nbsp;<img id='num' src='checkcode.do?method=getcheckcode' \/><a href='javascript:;' onclick=\"document.getElementById('num').src = 'checkcodeController.do?method=getcheckcode&&'+(new Date()).getTime()\">换一张<\/a>";
						setTimeout("reload()",2000);			;
					}else{
					              showError(d.message);
					     }
					}
				}
					
			}
		
	});
}

function setCookie(){
	
	location.reload() 
}

//设置cookie
function setCookie()
{
	if ($('#on_off').val() == '1') {
		$("input[iscookie='true']").each(function() {
			$.cookie(this.name, $("#"+this.name).val(), "/",24);
			$.cookie("COOKIE_NAME","true", "/",24);
		});
	} else {
		$("input[iscookie='true']").each(function() {
			$.cookie(this.name,null);
			$.cookie("COOKIE_NAME",null);
		});
	}
}
//读取cookie
function getCookie()
{
	var COOKIE_NAME=$.cookie("COOKIE_NAME");
	if (COOKIE_NAME !=null) {
		$("input[iscookie='true']").each(function() {
			$($("#"+this.name).val( $.cookie(this.name)));
		});
		$("#on_off").attr("checked", true);
		$("#on_off").val("1");
	} 
	else
	{
		$("#on_off").attr("checked", false);
		$("#on_off").val("0");
	}
}
//点击消息关闭提示
$('#alertMessage').click(function() {
	hideTop();
});
//显示错误提示
function showError(str) {
	$('#alertMessage').addClass('error').html(str).stop(true, true).show().animate({
		opacity : 1,
		right : '0'
	}, 500);

}
//验证通过加载动画
function loginsuccess()
{
	$("#login").animate({
		opacity : 1,
		top : '49%'
	}, 200, function() {
		$('.userbox').show().animate({
			opacity : 1
		}, 500);
		$("#login").animate({
			opacity : 0,
			top : '60%'
		}, 500, function() {
			$(this).fadeOut(200, function() {
				$(".text_success").slideDown();
				$("#successLogin").animate({
					opacity : 1,
					height : "200px"
				}, 1000);
			});
		});
	});
}
function showSuccess(str) {
	$('#alertMessage').removeClass('error').html(str).stop(true, true).show().animate({
		opacity : 1,
		right : '0'
	}, 500);
}

function onfocus() {
	if ($(window).width() > 480) {
		$('.tip input').tipsy({
			trigger : 'focus',
			gravity : 'w',
			live : true
		});
	} else {
		$('.tip input').tipsy("hide");
	}
}

function hideTop() {
	$('#alertMessage').animate({
		opacity : 0,
		right : '-20'
	}, 500, function() {
		$(this).hide();
	});
}
//加载信息
function loading(name, overlay) {
	$('body').append('<div id="overlay"></div><div id="preloader">' + name + '..</div>');
	if (overlay == 1) {
		$('#overlay').css('opacity', 0.1).fadeIn(function() {
			$('#preloader').fadeIn();
		});
		return false;
	}
	$('#preloader').fadeIn();
}

function unloading() {
	$('#preloader').fadeOut('fast', function() {
		$('#overlay').fadeOut();
	});
}
// 表单晃动
function jrumble() {
	$('.inner').jrumble({
		x : 4,
		y : 0,
		rotation : 0
	});
	$('.inner').trigger('startRumble');
	setTimeout('$(".inner").trigger("stopRumble")', 500);
}