<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
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
    <span id="top"></span>
    <div id="tooplate_body_wrapper">
        <div id="tooplate_wrapper">
            <div id="tooplate_header"> 
                <div id="tooplate_menu">
                    <ul>
                        <c:choose>
                            <c:when test="${not empty userId}">
                                <li><a href="">Hi, ${userId}!</a></li>
                            </c:when>
                            <c:otherwise>                                
                                <li><a href="">Login</a></li>
                            </c:otherwise>
                        </c:choose>
                        <li><a href="#home">New Game</a></li>
                        <li><a href="#aboutus">Join Game</a></li>
                        <li><a href="#blog">Rules</a></li>
                        <li><a href="#portfolio">Rating</a></li>
                        <li class="last"><a href="#contactus">Logout</a></li>
                    </ul>    	
                </div> <!-- end of tooplate_menu -->         
            </div> <!-- end of header -->
            <div id="tooplate_main">
                <div class="content_box">
                    <c:set var="statusDescription" value="Created"/>
                    <c:choose>
                        <c:when test="${gameboard.status eq 1}">
                            <c:set var="statusDescription" value="Ongoing"/>
                        </c:when>
                        <c:when test="${gameboard.status eq 2}">
                            <c:set var="statusDescription" value="Finished"/>
                        </c:when>
                    </c:choose>
                    <div class="content_title content_ct"><h2>Game ${gameId} (${statusDescription}) </h2></div>
                    <div class="content">
                        <c:choose>
                            <c:when test="${gameboard.status eq 0}">
                                Press <a href="/green3-evolution/game?op=start_game">start</a> to run a game!
                            </c:when>
                            <c:when test="${gameboard.status eq 1}">
                                <c:set var="stageDescription" value="Evolution"/>
                                <c:choose>
                                    <c:when test="${gameboard.roundStage eq 1}">
                                        <c:set var="stageDescription" value="Food supply determination"/>
                                    </c:when>
                                    <c:when test="${gameboard.roundStage eq 2}">
                                        <c:set var="stageDescription" value="Feeding"/>
                                    </c:when>
                                    <c:when test="${gameboard.roundStage eq 3}">
                                        <c:set var="stageDescription" value="Extinction"/>
                                    </c:when>
                                </c:choose>
                                <ul>
                                    <li> Cards left: ${gameboard.cardsLeft}</li>
                                    <li> Round: ${gameboard.currentRound}</li>
                                    <li> Stage: ${stageDescription}</li>
                                    <li> Turn of player: ${gameboard.turnPlayer}</li>
                                    
                               </ul>
                            </c:when>
                        </c:choose>
                        
                    </div>
                    <c:forEach items="${gameboard.players}" var="player">
                        <c:if test="${player.user eq userId and not empty player.cardsOnHand}">
                            <div class="content">
                                <table border="3">
                                    <tr>
                                        <c:forEach items="${player.cardsOnHand}" var="playersCard">
                                            <td> <b><h3>Card ${playersCard.id}</h3></b>
                                                <table border="2">
                                                    <c:forEach items="${playersCard.properties}" var="property">
                                                        <tr>                                                      
                                                            <td>
                                                                <h4>${property.description}</h4>
                                                                <c:if test="${gameboard.roundStage eq 0 and gameboard.turnPlayer eq userId}">
                                                                    <form action="applycard">
                                                                        <c:if test="${not property.animal}">Target: <input type="text" name="animalId" size="3"/></c:if>
                                                                        <c:if test="${property.twin}">Target2: <input type="text" name="linkedAnimalId" size="3"/></c:if>
                                                                        <input type="hidden" name="playerId" value="${player.id}"/>
                                                                        <input type="hidden" name="cardId" value="${playersCard.id}"/>
                                                                        <input type="hidden" name="propertyType" value="${property.type}"/>
                                                                        <input type="submit" value="Apply"/>                                                                    
                                                                    </form>
                                                                </c:if>
                                                            </td>                                                        
                                                        </tr>
                                                    </c:forEach>
                                                </table>
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>
                            </div>
                        </c:if>
                    </c:forEach>    
                    
                    <c:forEach items="${gameboard.players}" var="player">
                        <div class="content_title content_ct">
                            <h3>
                                ${player.user}
                            </h3>
                        </div>
                        <div class="content">
                            <c:if test="${not empty player.animals}">
                                <table>
                                    <tr>
                                        <c:forEach items="${player.animals}" var="animal">
                                            <td>
                                                <table>
                                                    <tr>
                                                        <td>
                                                            <b>Animal #${animal.id}</b>
                                                        </td>
                                                    </tr>
                                                    
                                                    <c:forEach items="${animal.properties}" var="property"> 
                                                        <tr>
                                                            <td>
                                                                ${property.description}
                                                            </td>
                                                        </tr>
                                                        
                                                    </c:forEach>
                                                </table>
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </table>
                            </c:if>
                        </div>
                    </c:forEach>
                </div>
            </div>            
        </div>
    </div>

</body>
</html>
