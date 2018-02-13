<html>    
    <head>
        <c:set var="context" value="/green3-evolution" />
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Evolution</title>
        <meta name="keywords" content="" />
        <meta name="description" content="" />

        <link href="css/tooplate_style.css" rel="stylesheet" type="text/css" />

        <!-- Arquivos utilizados pelo jQuery lightBox plugin -->
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="js/jquery.lightbox-0.5.js"></script>
        <link rel="stylesheet" type="text/css" href="css/jquery.lightbox-0.5.css" media="screen" />
        <!-- / fim dos arquivos utilizados pelo jQuery lightBox plugin -->
        <script type='text/javascript' src='js/jquery.min.js'></script>
        <script type='text/javascript' src='js/jquery.scrollTo-min.js'></script>
        <script type='text/javascript' src='js/jquery.localscroll-min.js'></script>
        <script type="text/javascript" src="js/jquery.lightbox-0.5.js"></script> 
        <!-- Ativando o jQuery lightBox plugin -->
        <script type="text/javascript">
            $(function () {
                $.localScroll();
                $('#map a').lightBox();
            });
        </script>

    </head>
    <body>
        
        <span id="top"></span>
        <div id="tooplate_body_wrapper">
            <div id="tooplate_wrapper">
                <div id="tooplate_header"> 
                    <div id="tooplate_menu">
                        <ul>
                            <c:choose>
                                <c:when test="${not empty userId}">
                                    <li><a href="">Hi, ${userId}!</a></li>
                                    <li><a href="/green3-evolution/logout">Logout</a></li>
                                </c:when>
                                <c:otherwise>                                
                                    <li><a href="/green3-evolution/login">Login</a></li>
                                </c:otherwise>
                            </c:choose>                            
                            <li><a href="/green3-evolution/hosted">Join Game</a></li>
                            <li><a href="/green3-evolution/game?op=user_games">My Games</a></li>
                            <li class="last"><a href="/green3-evolution/">Home</a></li> 
                        </ul>    	
                    </div> <!-- end of tooplate_menu -->         
                </div> <!-- end of header -->

                <ul>
                    <li> Game 1</li>
                    <li> Players: dew, ace, nick</li>
                    <li> Requests: no</li>
                    <li> <a href="/green3-evolution/game">Start</a></li>
                </ul>
            </div>
        </div>
    </body>
</html>
