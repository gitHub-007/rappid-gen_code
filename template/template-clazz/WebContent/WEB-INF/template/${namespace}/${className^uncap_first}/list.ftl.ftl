<#assign classNameLower =  clazz.className?uncap_first>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<@head title="${clazz.remark!''}列表" author="${copyright?upper_case}" copyright="${copyright?upper_case}"/>
<link href="<@jspEl value="base"/>/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<@jspEl value="base"/>/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="<@jspEl value="base"/>/resources/admin/js/common.js"></script>
<script type="text/javascript" src="<@jspEl value="base"/>/resources/admin/js/list.js"></script>
<script type="text/javascript">
$().ready(function() {

	[@flash_message /]

});
</script>
</head>
<body>
	<div class="breadcrumb">
		<a href="<@jspEl value="base"/>/admin/common/index"><@jspEl value='message("admin.breadcrumb.home")'/></a> &raquo; ${clazz.remark!''}列表 <span><@jspEl value='message("admin.page.total", page.total)'/></span>
	</div>
	<form id="listForm" action="list" method="get">
		<div class="bar">
			<a href="add" class="iconButton">
				<span class="addIcon">&nbsp;</span><@jspEl value='message("admin.common.add")'/>
			</a>
			<div class="buttonGroup">
				<a href="javascript:;" id="deleteButton" class="iconButton disabled">
					<span class="deleteIcon">&nbsp;</span><@jspEl value='message("admin.common.delete")'/>
				</a>
				<a href="javascript:;" id="refreshButton" class="iconButton">
					<span class="refreshIcon">&nbsp;</span><@jspEl value='message("admin.common.refresh")'/>
				</a>
				<div id="pageSizeMenu" class="dropdownMenu">
					<a href="javascript:;" class="button">
					<@jspEl value='message("admin.page.pageSize")'/><span class="arrow">&nbsp;</span>
					</a>
					<ul>
						<li[#if page.pageSize == 10] class="current"[/#if] val="10">10</li>
						<li[#if page.pageSize == 20] class="current"[/#if] val="20">20</li>
						<li[#if page.pageSize == 50] class="current"[/#if] val="50">50</li>
						<li[#if page.pageSize == 100] class="current"[/#if] val="100">100</li>
					</ul>
				</div>
			</div>
			<div id="searchPropertyMenu" class="dropdownMenu">
				<div class="search">
					<span class="arrow">&nbsp;</span>
					<input type="text" id="searchValue" name="searchValue" value="<@jspEl value='page.searchValue'/>" maxlength="200" />
					<button type="submit">&nbsp;</button>
				</div>
				<ul>
					<li[#if page.searchProperty == "title"] class="current"[/#if] val="title">标题</li>
				</ul>
			</div>
		</div>
		<table id="listTable" class="list">
			<tr>
				<th class="check">
					<input type="checkbox" id="selectAll" />
				</th>
				<th>
					<a href="javascript:;" class="sort" name="title">标题</a>
				</th>
				<th>
					<span><@jspEl value='message("admin.common.action")'/></span>
				</th>
			</tr>
			[#list page.content as ${classNameLower}]
				<tr>
					<td>
						<input type="checkbox" name="ids" value="<@jspEl value='${classNameLower}.id'/>" />
					</td>
					<td>
					</td>
					<td>
						<a href="edit?id=<@jspEl value='${classNameLower}.id'/>">[<@jspEl value='message("admin.common.edit")'/>]</a>
					</td>
				</tr>
			[/#list]
		</table>
		[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
			[#include "/admin/include/pagination.ftl"]
		[/@pagination]
	</form>
</body>
</html>
