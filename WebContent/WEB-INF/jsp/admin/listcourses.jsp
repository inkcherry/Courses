<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="myTemplate" tagdir="/WEB-INF/tags/"%>

<myTemplate:template>
	<jsp:body>
          <div class="table-responsive">
		<table class="table table-striped table-condensed table-hover">
		<thead>
			<tr>
				 <th>#</th>
				 <th>课程</th>
                  <th>日期</th>
                  <th>操作</th>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${courses }" var="i" varStatus="s">
				<tr>
				<td>${s.count }</td>
				<td>${i.name }</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd HH:MM"
									value="${i.insertTime }" /></td>
           <td><a class="btn btn-primary" href="admin/course/${i.id }/listexps" role="button">维护</a>
           </td>
			</tr>
			</c:forEach>
			</tbody>
	</table>
	</div>

          <form class="form-horizontal" action="admin/addcourse" method="post">
		<div class="form-group">
						<label for="name" class="col-sm-2 col-md-2 control-label">课程名称</label>
						<div class="col-sm-10 col-md-4">
							<input type="text" class="form-control" placeholder="课程名称" required
						name="name">
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