<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="myTemplate" tagdir="/WEB-INF/tags/"%>

<myTemplate:template>
<jsp:attribute name="header">
	<link href="resources/css/fileinput.min.css" rel="stylesheet">
	</jsp:attribute>
	<jsp:attribute name="footer">
	<script src="resources/js/fileinput.min.js"></script>
	<script>
		$(function() {
			$('#file-1').fileinput({
				showPreview : false,
				browseClass : "btn btn-primary",
				showUpload : false,
				showRemove : false,
				initialCaption : "上传任务文件",
				maxFileSize : 50000,
				disabled : true,
			});
			
		});
	</script>
        
	</jsp:attribute>
	<jsp:body>
          <div class="table-responsive">
		<table class="table table-striped table-condensed table-hover">
		<thead>
			<tr>
				 <th>#</th>
				 <th>实验</th>
                  <th>发布时间</th>
                  <th>完成时间</th>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${details }" var="i" varStatus="s">
				<tr>
				<td>${s.count }</td>
				<td>${i.experiment.name }</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd HH:MM"
									value="${i.experiment.insertTime }" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd HH:MM"
									value="${i.completeTime }" /></td>
			</tr>
			</c:forEach>
			</tbody>
	</table>
	</div>
          
          
          <form class="form-horizontal" action="admin/uploadexpfile"
			method="post">
					<div class="form-group">
						<label for="title" class="col-sm-2 col-md-2 control-label">实验</label>
						<div class="col-sm-10 col-md-4">
						<select data-toggle="select" class="select select-primary mrs mbm" name="titleId">
					<c:forEach items="${details }" var="t">
						<option value="${t.experiment.id }">${t.experiment.name }</option>
					</c:forEach>
				</select>
						</div>
					</div>
					<div class="form-group">
				<label for="name" class="col-sm-2 col-md-2 control-label">任务文件</label>
				<div class="col-sm-10 col-md-5">
					<input id="file-1" type="file" name="uploadFile" multiple data-min-file-count="0" accept=".doc; .docx">
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
          
        </jsp:body>
</myTemplate:template>