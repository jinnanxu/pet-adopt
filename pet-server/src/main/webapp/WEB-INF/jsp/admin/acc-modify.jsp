<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../../include/_meta.jsp"></jsp:include>
<title>后台管理系统</title>
</head>
<body>
<!--_header 作为公共模版分离出去-->
<jsp:include page="../../include/admin_header.jsp"></jsp:include>
<!--/_header 作为公共模版分离出去-->
<!--_menu 作为公共模版分离出去-->
<jsp:include page="../../include/admin_menu.jsp"></jsp:include>
<div class="dislpayArrow hidden-xs"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<!--/_menu 作为公共模版分离出去-->

<section class="Hui-article-box">
	<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页
		<span class="c-gray en">&gt;</span>
		举报信息管理
		<span class="c-gray en">&gt;</span>
		审核
		<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
	</nav>
	<div class="Hui-article">
		<article class="cl pd-20" style="width:680px;">
		<form action="updateAcc" method="post" class="form form-horizontal">
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3">举报人：</label>
				<div class="formControls col-xs-8 col-sm-9">${acc.jbr.nickName }</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3">被举报人：</label>
				<div class="formControls col-xs-8 col-sm-9">${acc.beijbr.nickName }</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3">举报内容：</label>
				<div class="formControls col-xs-8 col-sm-9">${acc.acc.reason }</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3">处理意见：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<textarea rows="5" cols="60" name="detail"></textarea>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3">是否拉黑：</label>
				<div class="formControls col-xs-8 col-sm-3">
					<select class="input-text" name="isbl">
						<option value="1">是</option>
						<option value="0">否</option>
					</select>
				</div>
			</div>
			<div class="row cl">
				<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
					<input type="hidden" value="${acc.acc.accId }" name="accid">
					<input class="btn btn-success radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
					<input class="btn btn-primary radius" type="reset" value="&nbsp;&nbsp;重设&nbsp;&nbsp;">
				</div>
			</div>
		</form>
	</article>
	</div>
</section>

<!--_footer 作为公共模版分离出去-->
<jsp:include page="../../include/_footer.jsp"></jsp:include>
<!--/_footer /作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript">

</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>