<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${!ajaxRequest}">
<html>
<head>
	<meta name="_csrf_header" th:content="${_csrf.headerName}">
	<meta name="_csrf" th:content="${_csrf.token}">
	<title>cache | mvc-showcase</title>
	<link href="<c:url value="/resources/form.css" />" rel="stylesheet"  type="text/css" />		
	<script type="text/javascript" src="<c:url value="/resources/jquery/1.6/jquery.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/jqueryform/2.8/jquery.form.js" />"></script>	
</head>
<body>
</c:if>
	<div id="cacheContent">
		<h2>Cache</h2>
		<p>
			See the <code>org.springframework.samples.mvc.cache</code> package for the @Controller code	
		</p>
		
		<div class="header">
	  		<c:if test="${not empty message}">
				<div id="message" class="success">${message}</div>	  		
	  		</c:if>
		</div>
		<fieldset style="padding:0.7em; border:1px solid #ddd; margin:0 0 0.5em 0">
			<legend>DATA</legend>
	  		<textarea id="textareaData" path="inquiryDetails" style="display:block;width:100%;height:200px; resize:none"><c:out value="${data}"/></textarea>
	  		<label id="updateResultLabel"></label>
	  	</fieldset>
		<p style="height:10px">
			<button id="updateBtn" type="submit" style="float:right">update</button>
		</p>		
		<ul>
			<li>
				<a id="getTypeData" class="textLink" type="get" href="<c:url value="/cache/data" />"> REQUEST DATA / GET / async </a>
				<span id="getTypeDataResponse" class="success" style="display:none">
					<link href="/resources/form.css" rel="stylesheet" type="text/css">		
					<div class="success">
						<h3 class="status">response header</h3>
						<div class="header"></div>
						<h3>response body</h3>
						<div class="body"></div>
					</div>
				</span>
			</li>
			<li>
				<a id="getTypeEtagData" class="textLink" type="get" href="<c:url value="/cache/etag" />"> REQUEST DATA / GET / async / ETag / eHcache </a>
				<span id="getTypeEtagDataResponse" class="success" style="display:none">
					<link href="/resources/form.css" rel="stylesheet" type="text/css">		
					<div class="success">
						<h3 class="status">response header</h3>
						<div class="header"></div>
						<h3>response body</h3>
						<div class="body"></div>
					</div>
				</span>				
			</li>
			<li>
				<a id="postTypeEtagData" class="textLink" type="post" href="<c:url value="/cache/post-etag" />"> REQUEST DATA / POST / async / ETag / eHcache </a>
				<span id="postTypeEtagDataResponse" class="success" style="display:none">
					<link href="/resources/form.css" rel="stylesheet" type="text/css">		
					<div class="success">
						<h3 class="status">response header</h3>
						<div class="header"></div>
						<h3>response body</h3>
						<div class="body"></div>
					</div>
				</span>				
			</li>
		</ul>
		<script type="text/javascript">
			$(document).ready(function() {
				var header = $("meta[name='_csrf_header']").attr('content');
				var token = $("meta[name='_csrf']").attr('content');
				function res(resSpanId, result, resHeader, text, status){
					$("#"+resSpanId).attr("style","");
					$("#"+resSpanId+" > div").attr("class",result);
					$("#"+resSpanId+" > div > h3.status").html("response header (status :"+status+")");
					$("#"+resSpanId+" > div > div.header").html(resHeader);
					$("#"+resSpanId+" > div > div.body").html(text);
				};
				$("a.textLink").click(function(){
					var link = $(this);
					var url = link.attr("href");
					var type = link.attr("type");
					var jsonData = type.toUpperCase()=="POST" ? JSON.stringify({key:"test"}) : encodeURIComponent(JSON.stringify({key:"test"}));
					var resSpanId = link.attr("id")+"Response";  
					$.ajax({ url: url,
						beforeSend: function(xhr){
							let reqUrl = url;
							if(sessionStorage.getItem(reqUrl+"Etag")!=null){
								xhr.setRequestHeader("If-None-Match",sessionStorage.getItem(reqUrl+"Etag"))
							}
							xhr.setRequestHeader(header, token);
						}, 
						dataType: "text",
						type:type,
						data:jsonData,  
						success: function(text, status, request) {
							let reqUrl = url;
							if(type.toUpperCase()=="POST"){
								if(request.status==200){
									sessionStorage.setItem(reqUrl, text);
									sessionStorage.setItem(reqUrl+"Etag", request.getResponseHeader("Etag"));
								}else if(request.status==304){
									text = sessionStorage.getItem(reqUrl);
								}
							}
							res(resSpanId, "success", request.getAllResponseHeaders(), text, request.status);
						}, 
						error: function(xhr,status) {  
							res(resSpanId, "error", xhr.getAllResponseHeaders(), "", xhr.status);
						}
					});
					return false;
				});
				$("#updateBtn").click(function(){
					var textArea = $("#textareaData");
					var jsonData = {key:"test", data : textArea.val()};
					var updateResultLabel = $("#updateResultLabel");
					$.ajax({ url: "/cache/new-data",  
							beforeSend: function(xhr){xhr.setRequestHeader(header, token);},
							type:"post", 
							data:JSON.stringify(jsonData),
							dataType:"json", 
							contentType:"application/json;charset=utf-8",
							success: function(text) { MvcUtil.showSuccessResponse("success", updateResultLabel); }, 
							error: function(xhr) {  MvcUtil.showErrorResponse(xhr.responseText, "error", updateResultLabel); }
							});
					return false;
				});
			});
			
			
		</script>	
	</div>
<c:if test="${!ajaxRequest}">
</body>
</html>
</c:if>