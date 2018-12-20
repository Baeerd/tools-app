<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
    <meta charset="GBK"/>
    <script src="js/jquery.min.js"></script>
    <script src="js/plugin.js"></script>
    <script type="text/javascript">

        function test() {
            $("#register1").submit();
        }
    </script>
</head>
<body>

<form id="register1" action="/system/login">
    <input type="text" name="username" value="hpc"><br>
    <input type="text" name="password" value="123"><br>
    <input type="text" name="sex" value="M"><br>
    <input type="text" name="age" value="100"><br>
    <input type="text" name="phone" value="13011112222"><br>
    <input type="text" name="email" value="xxx@xxx.com"><br>

    <select name="111" class="initSelect" typeid="test">
        <option value="#value">#name</option>
    </select>

    <select name="222" class="initSelect" typeId="test2">
        <option value="#value" style="background: antiquewhite;">#name</option>
    </select>
</form>
<button value="测试login" onclick="test();">测试login</button>
</body>
</html>