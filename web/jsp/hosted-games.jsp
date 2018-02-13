<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
                            <li><a href="/green3-evolution/game?op=new_game">New Game</a></li>
                            <li><a href="/green3-evolution/game?op=user_games">My Games</a></li>
                            <li class="last"><a href="/green3-evolution/">Home</a></li>                            
                            
                        </ul>    	
                </div> <!-- end of tooplate_menu -->         
            </div> <!-- end of header -->
            <div id="tooplate_main">                
                   <div class="content_box">
                        <div class="content_title content_ct"><h2>Join game:</h2></div>
                        <div class="content"> 
                            <c:if test="${not empty gameContainer.createdGames}">
                            <ul>
                                <c:forEach var="gameItem" items="${gameContainer.createdGames}">
                                    <li><a href="/green3-evolution/joingame?gameId=${gameItem.id}">Game ${gameItem.id}</a></li>
                                </c:forEach>
                            </ul>
                            </c:if>
                            <c:if test="${empty gameContainer.createdGames}">
                                There are no hosted games
                            </c:if>    
                        </div> 
                   </div>                          
            </div>            
        </div>
    </div>

</body>
</html>
