<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<aside class="Hui-aside">
	<div class="menu_dropdown bk_2">
		<dl id="menu-appmgr">
			<dt><i class="Hui-iconfont" style="font-size: 18px;">&#xe6d1;&nbsp;</i>&nbsp;宠物信息管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a href="/pet-server/admin/petmgr" title="">宠物列表</a></li>
					<li><a href="/pet-server/admin/adoptmgr" title="">领养记录</a></li>
					<li><a href="/pet-server/admin/blogmgr" title="">动态管理</a></li>
				</ul>
			</dd>
		</dl>
		<dl id="menu-appmgr">
			<dt><i class="Hui-iconfont" style="font-size: 18px;">&#xe70d;&nbsp;</i>&nbsp;用户信息管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a href="/pet-server/admin/usermgr" title="">用户列表</a></li>
					<li><a href="/pet-server/admin/accmgr" title="">举报审核</a></li>
					<!-- <li><a href="/pet-server/admin/usermgr" title="">黑名单</a></li> -->
				</ul>
			</dd>
		</dl>
	</div>
</aside>
<div class="dislpayArrow hidden-xs"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>