
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<%@tag description="My Template" pageEncoding="UTF-8"%>
<%@attribute name="header" fragment="true"%>
<%@attribute name="main" fragment="true"%>
<%@attribute name="footer" fragment="true"%>
<html lang="zh-CN">
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<!-- Flat-ui -->
<link href="resources/css/flat-ui.min.css" rel="stylesheet">
<!-- Default CSS-->
<link href="resources/css/default.css" rel="stylesheet">
<link href="resources/images/favicon.ico" rel="shortcut icon"  type="image/x-icon">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="resources/js/html5shiv.min.js"></script>
      <script src="resources/js/respond.min.js"></script>
    <![endif]-->
<!-- Private -->
<jsp:invoke fragment="header" />
<title>专业课程管理平台</title>
</head>
<body>
	<!-- 导航 -->
	<nav class="navbar navbar-inverse">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
					aria-expanded="false" aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="">专业课程管理平台</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">
							<strong>${user.name }, 
								<c:choose>
									<c:when test="${user.level>=10 }">老师</c:when>
									<c:otherwise>同学</c:otherwise>
								</c:choose>
							</strong>
							<span class="caret"></span>
						</a>
						<ul class="dropdown-menu">
							<li><a href="updateusersetting">个人设置</a></li>
							<li class="divider"></li>
							<li><a href="logout">退出</a></li>
						</ul></li>
				</ul>
			</div>
	</nav>
	<div class="container-fluid">
		<div class="row">
			<!-- 左导航 -->
			<div class="col-sm-3 col-md-2 sidebar">
			<c:if test="${user.level <10 }">
				<ul class="nav nav-sidebar">
					<li class="active"><a href="#">
							课程
							<span class="sr-only">(current)</span>
						</a></li>
						<li><a href="listexps">我的实验</a></li>
						<!-- <li><a href="listcourses">我的课程</a></li> -->
				</ul>
				</c:if>
				<c:if test="${user.level >=10 }">
				<ul class="nav nav-sidebar">
					<li class="active"><a href="#">
							课程管理
							<span class="sr-only">(current)</span>
						</a></li>
					<li><a href="admin/listcourses">课程管理</a></li>	
					<li><a href="admin/importstudentexcel">学生管理</a></li>
					
					<li><a href="admin/setting/usersetting">用户管理</a></li>
				</ul>
				</c:if>
				
			</div>
			<!-- 主界面 -->
			<div class="col-sm-9 col-md-10 main">
				<jsp:doBody></jsp:doBody>
			</div>
		</div>
	</div>

<!-- 全局异常显示  -->
	<c:if test="${exception != null}">
		<div class="modal fade" id="exception" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h6 class="modal-title" id="myModalLabel">异常</h6>
					</div>
					<div class="modal-body">
						<div class="text-danger">
							<strong>${exception }</strong>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>
	</c:if>

	<footer class="footer">
		<div class="container">
			<p class="text-muted">东北林业大学 软件工程专业. &copy; 2016</p>
		</div>
	</footer>

	<!-- Placed at the end of the document so the pages load faster -->
	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/flat-ui.min.js"></script>
	<script src="resources/js/application.js"></script>
	<script>
		$(function() {
			/* 全局异常显示  */
			if ("${exception}".length > 0) {
				$('#exception').modal("show"); 
			}
		});
	</script>
	<!--Private JS  -->
	<jsp:invoke fragment="footer" />
</body>
</html>