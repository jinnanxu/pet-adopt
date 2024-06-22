<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<![endif]-->
<link href="${pageContext.request.contextPath}/static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/static/h-ui.admin/css/H-ui.login.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/static/h-ui.admin/css/style.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/lib/Hui-iconfont/1.0.8/iconfont.css" rel="stylesheet" type="text/css" />
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script><![endif]-->
<title>系统登录</title>
</head>
<body>
<input type="hidden" id="TenantId" name="TenantId" value="" />
<div class="header">宠物领养后台管理系统</div>
<div class="loginWraper" style="background: #3283AC url(../images/emp.jpeg) no-repeat center;">
	<div id="loginform" class="loginBox">
		<h3 class="text-c c-white">&nbsp;管理员登录</h3>
		<form action="/pet-server/admin/login-form" method="post" class="form form-horizontal" id="loginForm">
			<div class="row cl">
				<label class="form-label col-xs-3"><i class="Hui-iconfont" style="color: #fff;">&#xe60d;</i></label>
				<div class="formControls col-xs-8">
					<input id="username" name="username" type="text" placeholder="账号名" class="input-text size-L">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-3"><i class="Hui-iconfont" style="color: #fff;">&#xe60e;</i></label>
				<div class="formControls col-xs-8">
					<input id="pwd" name="pwd" type="password" placeholder="登录密码" class="input-text size-L">
				</div>
			</div>
			<div class="row cl">
				<div class="formControls col-xs-8 col-xs-offset-3">
					<input name="" type="submit" class="btn btn-success radius size-L" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;">
					<input name="" type="reset" class="btn btn-default radius size-L" value="&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;">
				</div>
			</div>
			<c:if test="${msg ne null }">
				<div class="row cl">
					<label class="form-label col-xs-3"></label>
					<div class="formControls col-xs-8">
						<span style="color: red; font-size: 16px;"><i class="Hui-iconfont">&#xe6cd;</i>${msg }</span>
					</div>
				</div>
			</c:if>
		</form>
	</div>
</div>
<div class="footer">Copyright 宠物领养平台</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui/js/H-ui.js"></script>
</body>
</html>