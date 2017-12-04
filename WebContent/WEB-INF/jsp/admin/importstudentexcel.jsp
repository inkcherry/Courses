<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="myTemplate" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<myTemplate:template>
	<jsp:attribute name="header">
	<link href="resources/css/fileinput.min.css" rel="stylesheet">
</jsp:attribute>
	<jsp:attribute name="footer">
<script>
	$(function() {
		$('#file-1').fileinput({
			showPreview : false,
			showUpload : false,
			showRemove : false,
			browseClass : "btn btn-primary",
			initialCaption : "上传学生表格文件",
			maxFileSize : 50000,
		});
	})
</script>
<script src="resources/js/fileinput.min.js"></script>
</jsp:attribute>

	<jsp:body>
		<ol class="breadcrumb">
		<li>
			<a href="">主页</a>
		</li>
		<li>
			<a href="admin/invi/invimanagement">监考管理</a>
		</li>
		<li class="active">导入监考信息</li>
	</ol>

	<c:if test="${exception != null}">
		&nbsp&nbsp
		<div class="alert alert-danger alert-dismissable" role="alert">
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
			<strong>错误！</strong> ${exception }
		</div>
	</c:if>

	<form class="form-horizontal" enctype="multipart/form-data"
		action="admin/importstudentexcel" method="post">
		<div class="form-group">
			<label for="title" class="col-sm-2 col-md-2 control-label">课程</label>
			<div class="col-sm-10 col-md-4">
				<select data-toggle="select" class="select select-primary mrs mbm"
					name="courseid">
					<c:forEach items="${courses }" var="t">
						<option value="${t.id }">${t.name }</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group">
		<label for="title" class="col-sm-2 col-md-2 control-label">课程</label>
			<div class="col-sm-10 col-md-4">
				<input id="file-1" type="file" name="file" multiple
					data-min-file-count="1" accept=".xls,.xlsx">
			</div>
		</div>
		<div class="form-group">
						<div class="col-sm-2 col-md-2 control-label"></div>
						<div class="col-sm-10 col-md-4">
							<button type="submit" class="btn btn-primary btn-wide">提交</button>
							<button type="reset" class="btn btn-danger btn-wide" id="reset">重置</button>	
						</div>
					</div>
	</form>
	${count}
    </jsp:body>
</myTemplate:template>