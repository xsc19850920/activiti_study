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
							<form action="${ctx }/leavebill/opt" method="post" class="form-horizontal" >
								<div class="control-group">
									<label class="control-label">Reason</label>
									<div class="controls">
										<input type="text" name="reason" value="${obj.reason }"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">Remark</label>
									<div class="controls">
										<input type="text" name="remark" value="${obj.remark }"/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">leave Days</label>
									<div class="controls">
										<input type="text" name="leaveDays" value="${obj.leaveDays }"/>
									</div>
								</div>
								
								<div class="form-actions">
								<c:if test="${opt == 'add' }"><a href="javascript:;" class="btn btn-primary submitBtn">Save</a></c:if>
								<c:if test="${opt == 'view' }"><a href="javascript:;" class="btn btn-primary closeBtn">Save</a></c:if>
								</div>
							</form>
						</div>
					</div>
					
					
					
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon">
								<i class="icon-align-justify"></i>									
							</span>
							<h5>流程图</h5>
						</div>
						<div class="widget-content nopadding">
							<c:if test="${opt == 'view' }">
								<img  src="${ctx }/process/img/${obj.id}">
							</c:if>
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
			$(".form-horizontal").ajaxSubmit({  
                success: function(data){  
                    $('#content').html(data);
                },  
                error: function(XmlHttpRequest, textStatus, errorThrown){  
                    alert( "error");  
                }  
            });  
		});
		
		$(".closeBtn").click(function(e){
			$(".form-horizontal").ajaxSubmit({  
				url:$(this).attr('action','${ctx }leavebill/list'),
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