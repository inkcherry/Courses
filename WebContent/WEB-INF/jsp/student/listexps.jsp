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
			$('[data-target=#myModal]').click(function() {
				var value = $(this).val();
				var title =  $(this).data("title");
				var filetype = $(this).data("filetype");
				$('input[name=expid]').val(value);
				$('#myModalLabel').html(title);
				$("#file-1").prop("accept", filetype);
			});
			
			$('#file-1').fileinput({
				showPreview : false,
				showUpload : true,
				showRemove : true,
				initialCaption : "上传实验报告",
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
				 <th>课程</th>
				 <th>实验</th>
                  <th>发布时间</th>
                  <th>完成时间</th>
                  <th>操作</th>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${exps }" var="i" varStatus="s">
				<tr>
				<td>${s.count }</td>
				<td>${i.course.name }</td>
				<td>${i.name }</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
									value="${i.insertTime }" /></td>
				
				<td><c:forEach items="${details }" var="d">
					<c:if test="${d.experiment.id== i.id}">
					<fmt:formatDate pattern="yyyy-MM-dd HH:mm"
									value="${d.completeTime }" />
					</c:if>
				</c:forEach>
				</td>
				<td>
				<button class="btn btn-primary" data-toggle="modal" data-target="#myModal" 
				data-title="${i.name }" data-filetype="${i.fileExtension }" value="${i.id }">上传</button>
				<c:forEach items="${details }" var="d">
					<c:if test="${d.experiment.id== i.id}">
					<a class="btn btn-primary" role="button" href="download/${d.experiment.id}">下载</a>
					</c:if>
				</c:forEach></td>
			</tr>
			</c:forEach>
			</tbody>
	</table>
	</div>
         
          
          
          
          <!-- 模态框（Modal） -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" >
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h6 class="modal-title" id="myModalLabel">上传实验报告</h6>
				</div>
				<div class="modal-body">
				
				<form class="form-horizontal" action="addexp" method="post" enctype="multipart/form-data">
					<input type="hidden" name="expid" value="">
				
					<input id="file-1" type="file" name="uploadFile" multiple data-min-file-count="0" accept="">

	</form>	
				
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
          
        </jsp:body>
</myTemplate:template>