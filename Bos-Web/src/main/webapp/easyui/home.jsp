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
<body class="easyui-layout">
		
		<div title="xxx 管理系统"  style="height: 100px;" data-options="region:'north'">北部区域</div>
		
		<div title="菜单" style="width: 150px;" data-options="region:'west'">
			<div class="easyui-accordion" data-options="fit:true">	<!--自适应 -->
				<div title="面板1">
					<ul id="ztree" class="ztree"></ul>
				</div>
				<script>
					$(function(){
						
						var setting = {
								data: {
									simpleData: {
										enable: true,
									}
								},
								callback: { 			// 动态的回调函数 
									onClick:function( event, treeId, treeNode) {
												
											// 解决目录标签不会无效的按钮
											// alert(treeNode.tId + ", " + treeNode.name);
											if(treeNode.page != undefined){
												
												var e = $("#tab").tabs("exists",treeNode.name);
												
												if(e){ 	// 存在，就选择该 tab 页
													$("#tab").tabs("select",treeNode.name);
												}else{
													
													$("#tab").tabs("add",{
														
														title:treeNode.name,
														closable:true,
														content:'<iframe height="100%" width="100%" frameborder="0" src='+treeNode.page+' > </iframe>  '
														
													});
													
												}
												
												
												
											}
										
										    
										    
										}
									}
								};
						
						var url = "${APP_PATH}/json/menu.json";
						
						$.post(url,{},function(data){
							// 得到的数据是 json 格式
							// 在 init 的第三个参数中增加 data 
							$.fn.zTree.init($("#ztree"), setting, data);
							
						},'json');
					});
					
				</script>
				
				
				
				<div title="面板2">
					<ul id="ztree" class="ztree"></ul>
					
					<script>
						$(function(){
							
							var setting = {};
							var nodes =[
							
								{
									"name":"结点1","children":[
										{"name":"结点1"},
										{"name":"结点2"}
									]
								},
								{"name":"结点2"},
								{"name":"结点3"}
							];
													
							 $.fn.zTree.init($("#ztree"), setting, nodes);
							
						});
					</script>
					
					
				</div>
				<div title="面板3">
					
					<ul id="ztree2" class="ztree"></ul>
					<script>
						$(function(){
							
							var setting = {
								data: {
									simpleData: {
										enable: true,		// 只需要在此打开简单 data 即可
									}
								}
							};
							var nodes1 = [
								{"id":1, "pId":0, "name":"根路径" } ,
								{"id":11,"pId":1, "name":"第一层"    },
								{"id":12 , "pId":1,"name":"第三层"    },
								{"id":111,"pId":11,"name":"第二层"    }
							];
							
							$.fn.zTree.init($("#ztree2"), setting, nodes1);	// 构造一个  ztree
							
						});
						
					</script>
					
					
				</div>
			</div>
			
		</div>
		
		<div  data-options="region:'center'" style="height: 100%;">
			
			<div class="easyui-tabs" id="tab">
				<div data-options="closable:'true'" title="法师"></div>
				<div data-options="iconCls:'icon-cut'" title="战士"></div>
				<div title="刺客"></div>
			</div>
		</div>
		
		<div style="width:200px ;" data-options="region:'east'">东部区域</div>
		
		<div style="height: 100px;" data-options="region:'south'">南部区域</div>
		
		
	</body>
</html>