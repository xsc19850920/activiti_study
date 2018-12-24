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
							<span class="icon"> <i class="icon-th"></i>
							</span>
							<h5>Process</h5>
						</div>
						<div class="widget-content nopadding">
							<table class="table table-bordered table-striped">
								<thead>
									<tr>
										<th>部署流程id</th>
										<th>流程名称</th>
										<th>部署时间</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
								<c:forEach items="${list }" varStatus="status" var="li" >
									<tr>
										<td>${li.id }</td>
										<td>${li.name }</td>
										<td>
										<fmt:formatDate value="${li.deploymentTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
										</td>
										<td><a title="Icon Title" class="btn btn-danger btn-mini" href="${ctx }/process/del/${li.id}">删除</a></td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					
					
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon">
								<i class="icon-align-justify"></i>									
							</span>
							<h5>Rest of elements...</h5>
						</div>
						<div class="widget-content nopadding">
							<form action="${ctx }/process/deployment" method="post" class="form-horizontal" enctype="multipart/form-data">
								<div class="control-group">
									<label class="control-label">Process Name</label>
									<div class="controls">
										<input type="text" name="processName"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">File upload input</label>
									<div class="controls">
										<div class="uploader" id="uniform-undefined"><input name="file" type="file" size="19" style="opacity: 0;"><span class="filename">No file selected</span><span class="action">Choose File</span></div>
									</div>
								</div>
								
								<div class="form-actions">
									<a href="javascript:;" class="btn btn-primary submitBtn">Save</a>
								</div>
							</form>
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
		<script src="${ctx }/resources/js/jquery.uniform.js"></script>
		<script type="text/javascript">
		$(function(){
			$(".submitBtn").click(function(e){
				e.preventDefault();
				$(this).closest('form').ajaxSubmit({  
	                success: function(data){  
	                    $('#content').html(data);
	                },  
	                error: function(XmlHttpRequest, textStatus, errorThrown){  
	                    alert( "error");  
	                }  
	            });  
			});
		});
		</script>
</body>
</html>