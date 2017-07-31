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
                        <li><a href="">Hi, Player!</a></li>
                        <li><a href="#home">New Game</a></li>
                        <li><a href="#aboutus">Join Game</a></li>
                        <li><a href="#blog">Rules</a></li>
                        <li><a href="#portfolio">Rating</a></li>
                        <li class="last"><a href="#contactus">Logout</a></li>
                    </ul>    	
                </div> <!-- end of tooplate_menu -->         
            </div> <!-- end of header -->
            <div id="tooplate_main">
                <c:if test="${not empty gameContainer.createdGames}">
                   <div class="content_box">
                        <div class="content_title content_ct"><h2>Join game:</h2></div>
                        <div class="content">                            
                            <ul>
                                <c:forEach var="gameItem" items="${gameContainer.createdGames}">
                                    <li><a href="/green3-evolution/joingame?gameId=${gameItem.id}">Game ${gameItem.id}</a></li>
                                </c:forEach>
                            </ul>
                        </div> 
                   </div>
                </c:if>                
            </div>            
        </div>
    </div>

</body>
</html>
