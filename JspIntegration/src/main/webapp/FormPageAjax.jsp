<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="wicket" uri="http://wicketstuff-jee-web.org/functions/jsp" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Send a parameter to Wicket page via AJAX</title>
</head>
<body>
	Send a parameter to Wicket page via AJAX with a form.
	
	<form id="form" method="post" onsubmit="${wicket:ajaxFormSubmit('POST')}">
        <input type="text" name="textvalue" value="testvalue"/>
        <input type="submit" value="Submit"/>
    </form>    
</body>
</html>