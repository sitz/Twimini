<!DOCTYPE html> 
<html>
<head>
    <link href="/static/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/static/css/bootstrap-responsive.css" rel="stylesheet" />
    <link href="/static/css/custom.css" rel="stylesheet" />
    <link rel="icon" type="image/x-icon" href="http://www.favicon.cc/favicon/996/398/favicon.png" />
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
    <script type="text/javascript" src="/static/js/custom.js"></script>
    <title>TwiMini</title>
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
            <a class="brand" href="/tweet">TwiMini</a>
            <div class="nav-collapse" id="main-menu"><ul class="nav" id="main-menu-left">
            </ul>
                <ul class="nav pull-right" id="main-menu-right">
                    <% if (null != request.getAttribute("curUserName")) { %>
                    <li>
                        <form onsubmit="window.location = '/search/' + $('#query').val();return false;">
                            <input type="text" class="navbar-search" style="border-color:#CCC;" id="query" placeholder="Search"/>
                        </form>
                    </li>
                    <li>
                        <a rel="tooltip" href="/user/"  data-original-title="Customize your settings">
                            Hello, <%= request.getAttribute("curUserName") %>
                        </a>
                    </li>
                    <li><a rel="tooltip" href="/user/profile/edit"  data-original-title="Settings">Settings</a></li>
                    <li><a rel="tooltip" href="/auth/logout"  data-original-title="Logout">Logout</a></li>
                    <%} else {%>
                    <li>
                        <form class="form-inline" id="formLogin">
                            <input type="text" name="username" id="username" placeholder="Email" class="input-small" />
                            <input type="password" name="password" id="password" placeholder="Password" class="input-small" />
                            <button class="btn" onclick="return login();">Login</button>
                            <a class="btn" data-toggle="modal" href="#myModal" >Forgot Password</a>
                        </form>
                    </li>
                    <li>
                    </li>
                    <%}%>
                </ul>
            </div>
        </div>
   </div>
</div>
