<!DOCTYPE html> 
<html>
<head>
    <link href="/static/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/static/css/bootstrap-responsive.css" rel="stylesheet" />
    <link href="/static/css/custom.css" rel="stylesheet" />
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>

</head>
<body class="preview" data-spy="scroll" data-target=".subnav" data-offset="50" style="padding: 100px;">
<script type="text/javascript" src="/static/js/ejs_production.js"></script>


<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>
            <a class="brand" href="/tweet">Mini Twitter</a>
            <div class="nav-collapse" id="main-menu"><ul class="nav" id="main-menu-left">
            </ul>
                <ul class="nav pull-right" id="main-menu-right">
                    <li><a rel="tooltip" href="/search/"  data-original-title="Search for a user">Search</a></li>
                    <% if (null != session.getAttribute("userName")) { %>
                    <li>
                        <a rel="tooltip" href="/user/"  data-original-title="Customize your settings">
                            Hello, <%= session.getAttribute("userName") %>
                        </a>
                    </li>
                    <li><a rel="tooltip" href="/auth/logout"  data-original-title="Logout">Logout</a></li>
                    <%}%>
                </ul>
            </div>
        </div>
   </div>
 </div>
