<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>메인 페이지</title>
    <link rel="stylesheet" type="text/css" href="css/style.css"> <!-- 스타일시트 경로 -->
</head>
<body>
    <h1>환영합니다!</h1>
    <div id="login-section">
        <h2>로그인</h2>
        <form action="loginSubmission.do" method="post">
            <label for="loginUsername">아이디:</label>
            <input type="text" id="id" name="id" required>
            <br>
            <label for="loginPassword">비밀번호:</label>
            <input type="password" id="password" name="password" required>
            <br>
            <input type="submit" value="로그인">
        </form>
    </div>

    <div id="signup-section">
        <h2>회원가입</h2>
        <form action="signup.do" method="post">
            <label for="signupUsername">아이디:</label>
            <input type="text" id="signupUsername" name="username" required>
            <br>
            <label for="signupPassword">비밀번호:</label>
            <input type="password" id="signupPassword" name="password" required>
            <br>
            <label for="signupEmail">이메일:</label>
            <input type="email" id="signupEmail" name="email" required>
            <br>
            <input type="submit" value="회원가입">
        </form>
    </div>
    ${serverId}
</body>
</html>
