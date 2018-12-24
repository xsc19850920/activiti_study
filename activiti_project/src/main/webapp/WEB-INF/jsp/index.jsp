<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>首页</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="${ctx }/resources/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx }/resources/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${ctx }/resources/css/fullcalendar.css" />	
<link rel="stylesheet" href="${ctx }/resources/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx }/resources/css/unicorn.grey.css" class="skin-color" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<div id="header">
		<h1><a href="${ctx }">Unicorn Admin</a></h1>		
	</div>
		
	<jsp:include page="/WEB-INF/jsp/common/nav.jsp" />
	<jsp:include page="/WEB-INF/jsp/common/sidebar.jsp" />
	<jsp:include page="/WEB-INF/jsp/common/style-switch.jsp" />
	<div id="content">
		<div id="breadcrumb">
			<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
			<a href="#" class="current">Dashboard</a>
		</div>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12 center" style="text-align: center;">					
					
				</div>	
			</div>
			
			<div class="row-fluid">
				<div id="footer" class="span12">
					2012 &copy; Unicorn Admin. Brought to you by <a href="https://wrapbootstrap.com/user/diablo9983">diablo9983</a>
				</div>
			</div>
		</div>
	</div>
	<script src="${ctx }/resources/js/jquery.min.js"></script>	
	<script src="${ctx }/resources/js/jquery.form.js"></script>	
	<script src="${ctx }/resources/js/unicorn.js"></script>
	<script src="${ctx }/resources/js/jquery.ui.custom.js"></script>
	<script src="${ctx }/resources/js/bootstrap.min.js"></script>
	<script src="${ctx }/resources/js/jquery.uniform.js"></script>
	<script src="${ctx }/resources/js/select2.min.js"></script>
	<script src="${ctx }/resources/js/jquery.dataTables.min.js"></script>
	<script src="${ctx }/resources/js/unicorn.tables.js"></script>
</body>
</html>