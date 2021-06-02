<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <link href="${ctx }/webpage/login/static/css/zice.style.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx }/webpage/login/static/css/buttons.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx }/webpage/login/static/css/icon.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${ctx }/webpage/login/static/css/tipsy.css" media="all"/>
    <style type="text/css">
        html {
            background-image: none;
        }

        #versionBar {
            background-color: #212121;
            position: fixed;
            width: 100%;
            height: 35px;
            bottom: 0;
            left: 0;
            text-align: center;
            line-height: 35px;
            z-index: 11;
            -webkit-box-shadow: black 0px 10px 10px -10px inset;
            -moz-box-shadow: black 0px 10px 10px -10px inset;
            box-shadow: black 0px 10px 10px -10px inset;
        }

        .copyright {
            text-align: center;
            font-size: 10px;
            color: #CCC;
        }

        .copyright a {
            color: #A31F1A;
            text-decoration: none
        }

        .on_off_checkbox {
            width: 0px;
        }

        .login-button {
            padding: 3px 0;
            width: 960px;
            margin-left: auto;
            margin-right: auto;
        }

        #login .logo {
            width: 500px;
            height: 51px;
        }
    </style>
</head>
<body>

<div id="alertMessage"></div>
<div id="successLogin"></div>
<div class="text_success"><img src="${ctx }/webpage/login/static/images/loader_green.gif" alt="Please wait"/> <span>登陆成功!请稍后....</span>
</div>

<div id="login">

    <div class="ribbon" style="background-image: url(${ctx}/webpage/login/static/images/typelogin.png);"></div>
    <div class="inner">
        <div class="logo"><img src="${ctx }/webpage/login/static/images/head.png"/>
            <!--   <img src="webpage/_login/static/images/foot.png" /> --> </div>
        <div class="formLogin">
            <form name="formLogin" id="formLogin" action="main.do" check="login.sdict?method=checkuser" method="post">
                <input name="userKey" type="hidden" id="userKey"
                       value="D1B5CC2FE46C4CC983C073BCA897935608D926CD32992B5900"/>
                <div class="tip"><input class="userName" name="loginName" type="text" id="loginName" title="用户名"
                                        value='' iscookie="true" nullmsg="请输入用户名!"/></div>
                <div class="tip"><input class="password" name="password" type="password" id="password" title="密码"
                                        value='' nullmsg="请输入密码!"/></div>
                <div id='checkcodediv' style=" vertical-align:middle"></div>
                <div id='twodimcode' style="float: right; margin-right: -0px;"><img
                        src='twodimenCode.sdict?method=getTwoDimCode'></div>
                <div class="loginButton">


                    <div class="login-button">
                        <div>
                            <ul class="uibutton-group">
                                <li><a class="uibutton normal" href="#" id="but_login">登陆</a></li>
                                <li>&nbsp;&nbsp;</li>
                                <li><a class="uibutton normal" href="#" id="forgetpass">重置</a></li>

                            </ul>


                        </div>

                        <div class="clear"></div>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="shadow"></div>
</div>
<!--_login div-->
<div class="clear"></div>
<div id="versionBar">
    <div class="copyright">&copy; 版权所有 <span class="tip"><a href="#" title="JEECP">jeecp</a> (推荐使用IE8+,谷歌浏览器可以获得更快,更安全的页面响应速度)技术支持:<a
            href="#" title="JEECP">jeecp</a></span></div>
</div>
<!-- Link JScript-->
<script type="text/javascript" src="${ctx }/plug-in/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx }/plug-in/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx }/webpage/login/static/js/jquery-jrumble.js"></script>
<script type="text/javascript" src="${ctx }/webpage/login/static/js/jquery.tipsy.js"></script>
<script type="text/javascript" src="${ctx }/webpage/login/static/js/iphone.check.js"></script>
<script type="text/javascript" src="${ctx }/webpage/login/static/js/login.js"></script>
<script type="text/javascript" src="${ctx }/plug-in/lhgDialog/lhgdialog.min.js"></script>
</body>
</html>