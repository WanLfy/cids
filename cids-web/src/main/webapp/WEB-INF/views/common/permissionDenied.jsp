<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<title>没有权限访问该页面</title>

<link href="<%=request.getContextPath()%>/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>

<body>
<div class="row">
				<div class="col-md-12 page-500">
					<div class=" number">
                        403
                    </div>
					<div class=" details">
						<h3>没有权限访问该页面</h3>
						<p>
						您可以通过以下几种方式重试:<br/>
							1.请确认路径正确后重试;<br/>
							2.稍后重试或者联系管理员;<br/>
						</p>
					</div>
				</div>
			</div>
</body>
</html>