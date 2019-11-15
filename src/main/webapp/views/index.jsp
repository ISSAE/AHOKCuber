<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script type="text/javascript" src="../js/jquery-1.4.4.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <script type="text/javascript">
        function del(id) {
            $.get("/delPerson?id=" + id, function (data) {
                if ("success" == data.result) {
                    alert("Delete successful");
                    window.location.reload();
                } else {
                    alert("Delete failed");
                }
            });
        }
    </script>
</head>
<body>
<h3><a href="/toAddPerson">Add user</a></h3>
<table border="1">
    <tbody>
    <tr>
        <th>Full name</th>
        <th>mailbox</th>
        <th>state</th>
        <th>operation</th>
    </tr>
    <c:if test="${!empty personList}">
        <c:forEach items="${personList}" var="person">
            <tr>
                <td>${person.name }</td>
                <td>${person.email }</td>
                <td>${person.status }</td>
                <td>
                    <a href="/getPerson?id=${person.id }">edit</a>
                    <a href="javascript:del('${person.id}')">delete</a>
                </td>
            </tr>
        </c:forEach>
    </c:if>
    </tbody>
</table>
</body>
</html>