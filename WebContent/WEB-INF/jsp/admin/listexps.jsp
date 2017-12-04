<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="myTemplate" tagdir="/WEB-INF/tags/"%>

<myTemplate:template>
	<jsp:body>
	<h3>${course.name }</h3>
          <div class="table-responsive">
		<table class="table table-striped table-condensed table-hover">
		<thead>
			<tr>
				 <th>#</th>
				 <th>实验</th>
                  <th>日期</th>
                  <th>操作</th>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${exps }" var="i" varStatus="s">
				<tr>
				<td>${s.count }</td>
				<td>${i.name }</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd HH:MM"
									value="${i.insertTime }" /></td>
				<td>
				<a class="btn btn-primary" href="admin/unsubmited/${i.id }" role="button">未提交学生</a>
					<a class="btn btn-primary" href="admin/downloadzip/${i.course.id }-${i.course.name}/${i.id}-${i.name}/" role="button">打包下载</a>
				</td>

			</tr>
			</c:forEach>
			</tbody>
	</table>
	</div>
          
          
          <form class="form-horizontal" action="admin/addexp" method="post">
			<input type="hidden" name="courseid" value="${courseid }">
					<div class="form-group">
						<label for="name" class="col-sm-2 col-md-2 control-label">实验名称</label>
						<div class="col-sm-10 col-md-4">
							<input type="text" class="form-control" placeholder="实验名称" required name="name">
						</div>
					</div>
					<div class="form-group">
						<label for="name" class="col-sm-2 col-md-2 control-label">文件类型</label>
						<div class="col-sm-10 col-md-4">
							<label class=" radio">
								<input type="radio"  name="fileExtension" data-toggle="radio" value=".doc, .docx">
								文档 .doc, .docx</label>
							<label class=" radio">
								<input type="radio"  name="fileExtension" data-toggle="radio" value=".xls, .xlsx">
								表格 .xls, .xlsx</label>
							<label class=" radio">
								<input type="radio"  name="fileExtension" data-toggle="radio" value=".zip, .rar, .7z">
								压缩包 .zip, .rar, .7z</label>
								<label class=" radio">
								<input type="radio"  name="fileExtension" data-toggle="radio" value="">
								无限制</label>
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