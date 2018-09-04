<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%request.setAttribute("APP_PATH", request. getContextPath()); %>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" type="text/css" href="${APP_PATH }/js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${APP_PATH }/js/easyui/themes/icon.css">
	<script type="text/javascript" src="${APP_PATH }/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="${APP_PATH }/js/easyui/jquery.easyui.min.js"></script>
	
	<link rel="stylesheet" href="${APP_PATH }/js/ztree/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${APP_PATH }/js/ztree/jquery.ztree.all-3.5.js"></script>
	
</head>
	<body >
		
		<table class="easyui-datagrid" >
			<thead>
				<tr>
					<th data-options="field:'id',width:100">Id</th>
					<th data-options="field:'name',width:100">Name </th>
					<th data-options="field:'age'">Age</th>
				</tr>			
			</thead>
			
			<tbody>
				<tr>	
					<td>001 </td>
					<td>jeck </td>
					<td>14</td>
				</tr>
				<tr>	
					<td>002 </td>
					<td>tom </td>
					<td>13</td>
				</tr>
				<tr>	
					<td>003 </td>
					<td>frank </td>
					<td>15</td>
				</tr>
			</tbody>
		</table>
		
		
		<hr>
		
		
		<table class="easyui-datagrid" data-options="url:'${pageContext.request.contextPath }/json/text.json'" >
			<thead>
				<tr>
					<th data-options="field:'id' ">Id</th>
					<th data-options="field:'name'" >Name </th>
					<th data-options="field:'age'">Age</th>
				</tr>
			</thead>
		</table>
		
		<hr>
		
		<table id="tab">
			<thead>
				<tr>
					<th data-options="field:'id' ">Id</th>
					<th data-options="field:'name'" >Name </th>
					<th data-options="field:'age'">Age</th>
				</tr>
			</thead>
		</table>
		
		<hr>
		<table id="dg"></table>
		
		<script type="text/javascript">
			
			$(function(){
				$('#dg').datagrid({ 

					url:'${pageContext.request.contextPath }/json/text.json', 
					singleSelect:true,
					columns:[[ 
						{field:'check',checkbox:true},
						{field:'id',title:'Code',width:100,sortable:true,order:"desc"}, 
	
						{field:'name',title:'Name',width:100,sortable:true}, 
	
						{field:'age',title:'Price',width:100,align:'right'} 
					

					]],
					
					toolbar: [{  		
						  iconCls: 'icon-edit',  		
						  handler: function(){alert('edit')}  	
						  },'-',{  		
						  iconCls: 'icon-help',  		
						  handler: function(){alert('help')}  	
						  }]  ,
					pagination:true

				}); 
			}); 
		
		</script>
		
		
		
		
		
	</body>
</html>