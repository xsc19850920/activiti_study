<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<div id="sidebar">
	<a href="#" class="visible-phone"><i class="icon icon-home"></i> Dashboard</a>
	<ul>
		<li class="active"><a href="${ctx }"><i class="icon icon-home"></i> <span>Dashboard</span></a></li>
		<li class="submenu "><a href="#"><i class="icon icon-th-list"></i> <span>Leave Bill</span> <span class="label">3</span></a>
			<ul>
				<li ><a href="javascript:;" title="${ctx }/leavebill/list">list</a></li>
			</ul></li>
		<li class="submenu"><a href="#"><i class="icon icon-file"></i> <span>Process</span> <span class="label">4</span></a>
			<ul>
				<li><a href="javascript:;" title="${ctx }/process/list">deployment</a></li>
				<li><a href="javascript:;" title="${ctx }/task/list">task</a></li>
			</ul>
		</li>
	</ul>
</div>
