<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="wicket" uri="http://wicketstuff-jee-web.org/functions/jsp" %>        
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    <p>
    You can also use a simple link to send query parameters. 
    <a href="#" onClick="${wicket:ajaxGetWithQuery('textvalue=theLinkName')}">Click here</a> to send parameter 'textvalue=theLinkName'.
    </p>
</body>
</html>