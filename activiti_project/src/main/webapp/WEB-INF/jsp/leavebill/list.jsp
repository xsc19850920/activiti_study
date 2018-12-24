<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<title>Unicorn Admin</title>
		<meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link rel="stylesheet" href="${ctx }/resources/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${ctx }/resources/css/bootstrap-responsive.min.css" />
		<link rel="stylesheet" href="${ctx }/resources/css/uniform.css" />
		<link rel="stylesheet" href="${ctx }/resources/css/select2.css" />		
		<link rel="stylesheet" href="${ctx }/resources/css/unicorn.main.css" />
		<link rel="stylesheet" href="${ctx }/resources/css/unicorn.grey.css" class="skin-color" />	
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /></head>
<body>
		<div id="breadcrumb">
			<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a> <a href="#" class="current">Dashboard</a>
		</div>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon"> <i class="icon-th"></i> </span>
							<h5>Leave Bill</h5>
							<div class="buttons">
								<a title="Icon Title" class="btn btn-mini" href="${ctx }/leavebill/opt"><i class="icon-plus"></i>Add Leave Bill</a>
								<!-- <a title="Icon Title" class="btn btn-mini" href="#"><i class="icon-print"></i> Print</a> -->
							</div>
						</div>
						<div class="widget-content nopadding">
							<table class="table table-bordered table-striped">
								<thead>
									<tr>
										<th>请假人</th>
										<th>请假原因</th>
										<th>请假天数</th>
										<th>备注</th>
										<th>状态</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
								<c:forEach items="${list }" varStatus="status" var="li" >
									<tr>
										<td>${li.user.name }</td>
										<td>${li.reason }</td>
										<td>${li.leaveDays }</td>
										<td>${li.remark }</td>
										<td>
										<c:if test="${li.state == 0 }">草稿</c:if>
										<c:if test="${li.state == 1 }">办理中</c:if>
										<c:if test="${li.state == 2 }">办理完成</c:if>
										</td>
										<td>
										<a title="Icon Title" class="btn btn-primary btn-mini" href="${ctx }/leavebill/opt?leaveBillId=${li.id}">查看</a>
										<c:if test="${li.state == 0 }">
											<a title="Icon Title" class="btn btn-info btn-mini" href="${ctx }/leavebill/opt">修改</a>
											<a title="Icon Title" class="btn btn-info btn-mini" href="${ctx }/process/start/${li.id}">提交申请</a>
										</c:if>
										<c:if test="${li.state == 2 || li.state == 0}">
										<a title="Icon Title" class="btn btn-danger btn-mini" href="${ctx }/leavebill/del/${li.id}">删除</a>
										</c:if>
										</td>
										
									</tr>
								</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="row-fluid">
				<div id="footer" class="span12">
					2012 &copy; Unicorn Admin. Brought to you by <a href="https://wrapbootstrap.com/user/diablo9983">diablo9983</a>
				</div>
			</div>
		</div>
		<script src="${ctx }/resources/js/unicorn2.js"></script>
</body>
</html>