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
		宠物领养信息管理
		<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
	</nav>
	<div class="Hui-article">
		<article class="cl pd-20 container" style="padding-top: 0; width: 98%;">
			<p>&nbsp;</p>
			<div class="mt-10">
				<table id="example" class="table table-bordered table-hover">
					<tbody>
						<tr class="info">
							<th></th>
							<th>发布日期</th>
							<th>宠物类别</th>
							<th>状态</th>
							<th>发布人</th>
							<th>地点</th>
							<th>健康状况</th>
							<th>操作</th>
						</tr>
						<c:forEach items="${page.records }" var="row" varStatus="vs">
							<tr>
								<td>
									<img alt="" src="${row.pet.pic }" style="width: 50px; height: 50px;">
								</td>
								<td><fmt:formatDate value="${row.pet.publishTime }" pattern="yyyy-MM-dd"/></td>
								<td>${row.pet.subCategory }<span class="c-999">[${row.pet.gender }]</span></td>
								<td>
									<c:choose>
										<c:when test='${row.pet.status eq "待领养" }'>
											<span class="label label-primary radius">${row.pet.status }</span>
										</c:when>
										<c:when test='${row.pet.status eq "已领养" }'>
											<span class="label label-success radius">${row.pet.status }</span>
										</c:when>
										<c:otherwise>
											<span class="label label-default radius">${row.pet.status }</span>
										</c:otherwise>
									</c:choose>
								</td>
								<td>${row.user.nickName }</td>
								<td>${row.pet.posTxt }</td>
								<td>${row.pet.jieyu } | ${row.pet.mianyi }</td>
								<td>
									<c:if test='${row.pet.status eq "待领养" }'>
									<a href="#" onclick="delcfm(${row.pet.petId});"><button class="btn btn-danger size-S"><i class="Hui-iconfont" style="color: #fff;">&#xe6e2;</i>&nbsp;屏蔽</button></a>
									</c:if>
									<c:if test='${row.pet.status eq "已屏蔽" }'>
									<a href="#" onclick="delcfm2(${row.pet.petId});"><button class="btn btn-success size-S"><i class="Hui-iconfont" style="color: #fff;">&#xe6e2;</i>&nbsp;显示</button></a>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<p align="right">
				<font >当前页:第${pageView.currentpage}页 | 总记录数:${pageView.totalrecord}条 | 每页显示:${pageView.maxresult}条 | 总页数:${pageView.totalpage}页</font>　
				 <c:choose>
				<c:when test="${pageView.currentpage==1}"><font color="#FFFFFF">第1页</font></c:when>
				<c:otherwise><a href="petmgr?currentpage=1">第1页</a></c:otherwise>
				</c:choose>
				<c:if test="${pageView.currentpage>2}"><a href="petmgr?currentpage=${pageView.currentpage-1}">上一页</a></c:if>
				<c:if test="${pageView.currentpage!=1&&pageView.currentpage!=pageView.totalpage}"><b><font>第${pageView.currentpage}页</font></b></c:if>
				<c:if test="${pageView.currentpage<pageView.totalpage}"><a href="petmgr?currentpage=${pageView.currentpage+1}">下一页</a></c:if>
				<c:choose>
				<c:when test="${pageView.currentpage<pageView.totalpage}"><a href="petmgr?currentpage=${pageView.totalpage}">末页</a></c:when>
				<c:otherwise><font color="#FFFFFF">末页</font></c:otherwise>
				</c:choose>
				<br/></p>
			</div>
		</article>
	</div>
</section>

<!--_footer 作为公共模版分离出去-->
<jsp:include page="../../include/_footer.jsp"></jsp:include>
<!--/_footer /作为公共模版分离出去-->
<!-- 信息删除确认 -->  
<div class="modal fade" id="delcfmModel">  
  <div class="modal-dialog">  
    <div class="modal-content message_align">  
      <div class="modal-header">  
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>  
        <h4 class="modal-title">提示信息</h4>  
      </div>  
      <div class="modal-body">  
        <p>确定屏蔽该条领养信息？</p>  
      </div>  
      <div class="modal-footer">  
         <input type="hidden" id="url"/>  
         <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>  
         <a  onclick="urlSubmit()" class="btn btn-success" data-dismiss="modal">确定</a>  
      </div>  
    </div><!-- /.modal-content -->  
  </div><!-- /.modal-dialog -->  
</div><!-- /.modal --> 

<div class="modal fade" id="delcfmModel2">  
  <div class="modal-dialog">  
    <div class="modal-content message_align">  
      <div class="modal-header">  
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>  
        <h4 class="modal-title">提示信息</h4>  
      </div>  
      <div class="modal-body">  
        <p>确定显示该条领养信息？</p>  
      </div>  
      <div class="modal-footer">  
         <input type="hidden" id="url2"/>  
         <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>  
         <a  onclick="urlSubmit2()" class="btn btn-success" data-dismiss="modal">确定</a>  
      </div>  
    </div><!-- /.modal-content -->  
  </div><!-- /.modal-dialog -->  
</div><!-- /.modal --> 

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery.raty.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript">
	function delcfm(rid) {  
	    $('#url').val("/pet-server/admin/updatepet?petid="+rid);//给会话中的隐藏属性URL赋值  
	    $('#delcfmModel').modal();  
	}  
	function urlSubmit(){  
	 var url=$.trim($("#url").val());//获取会话中的隐藏属性URL  
	 window.location.href=url;    
	}  



	function delcfm2(rid) {  
	    $('#url2').val("/pet-server/admin/updatepet2?petid="+rid);//给会话中的隐藏属性URL赋值  
	    $('#delcfmModel2').modal();  
	}  
	function urlSubmit2(){  
	 var url=$.trim($("#url2").val());//获取会话中的隐藏属性URL  
	 window.location.href=url;    
	}  

</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>