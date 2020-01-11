<%@ taglib uri="http://www.springframework.org/security/tags"
    prefix="security"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<div style="padding: 5px; border: 1px solid #ccc;">
 
    <a href="">Home</a>
    
        ||
    
    <a href="userInfo">User Info</a>
 
    <security:authorize access="isAuthenticated()">
        ||
        <a href="logout">Logout</a>
 
    </security:authorize>
 
    <security:authorize access="!isAuthenticated()">
        ||
        <a href="login">Login</a>
 
    </security:authorize>
 
    <c:if test="${not empty pageContext.request.userPrincipal.name}">
        ||
        <span>Welcome : ${pageContext.request.userPrincipal.name}</span>
        
                      <ul>
                    <c:forEach items="${userDetails.authorities}" var="auth">
                        <li>${auth.authority }</li>
                    </c:forEach>
                </ul>
    </c:if>
</div>