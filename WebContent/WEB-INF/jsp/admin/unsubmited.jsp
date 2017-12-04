<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="myTemplate" tagdir="/WEB-INF/tags/"%>

<myTemplate:template>
	<jsp:body>
	<h3>${exp.name }</h3>
          <div class="table-responsive">
		<table class="table table-striped table-condensed table-hover">
		<thead>
			<tr>
				 <th>#</th>
				 <th>姓名</th>        
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${students }" var="i" varStatus="s">
				<tr>
				<td>${s.count }</td>
				<td>${i.name }</td>
				
			</tr>
			</c:forEach>
			</tbody>
	</table>
	</div>

        </jsp:body>
</myTemplate:template>