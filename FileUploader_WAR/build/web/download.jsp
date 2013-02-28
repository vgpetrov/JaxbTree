<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>File Download</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <form method="POST" action="DownloadServlet">
            Id:
            <input type="text" name="id" id="id"/><br/>
            </br>
            <input type="submit" value="Download" name="download" id="download" />
        </form>
    </body>
</html>
