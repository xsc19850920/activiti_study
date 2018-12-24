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
							<span class="icon">
								<i class="icon-align-justify"></i>									
							</span>
							<h5>Rest of elements...</h5>
						</div>
						<div class="widget-content nopadding">
							<form action="${ctx }/task/complete" method="post" class="form-horizontal"  id="taskComplete">
								<input type="hidden" value="${obj.id}" name="leaveBillId"/>
								<input type="hidden" value="${taskId}" name="taskId"/>
								<input type="hidden" name="opt"/>
								<div class="control-group">
									<label class="control-label">Reason</label>
									<div class="controls">
										<input type="text"  value="${obj.reason }" readonly="readonly"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">Remark</label>
									<div class="controls">
										<input type="text"  value="${obj.remark }" readonly="readonly"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">leave Days</label>
									<div class="controls">
										<input type="text"  value="${obj.leaveDays }" readonly="readonly"/>
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">审批意见</label>
									<div class="controls">
										<textarea name="common"></textarea>
									</div>
								</div>
								
								<div class="form-actions">
									<c:forEach items="${list }" var="li">
									<a href="javascript:;" class="btn btn-primary submitBtn" >${li }</a>
									</c:forEach>
								</div>
							</form>
						</div>
					</div>
					
					
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
										<th>批注人</th>
										<th>批注时间</th>
										<th>批注类型</th>
										<th>批注信息</th>
									</tr>
								</thead>
								<tbody>
								<c:forEach items="${commonList }" varStatus="status" var="li" >
									<tr>
										<td>${li.userId }</td>
										<td>
										<fmt:formatDate value="${li.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
										</td>
										<td>${li.type }</td>
										<td>${li.fullMessage }</td>
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
	<script type="text/javascript">
	$(function(){
		$(".submitBtn").click(function(e){
			e.preventDefault();
			$('input[name=opt]').val($(this).text());
			$("#taskComplete").ajaxSubmit({  
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