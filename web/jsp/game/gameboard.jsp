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
                                    <li><a href="/green3-evolution/logout">Logout</a></li>
                                </c:when>
                                <c:otherwise>                                
                                    <li><a href="/green3-evolution/login">Login</a></li>
                                </c:otherwise>
                            </c:choose>
                            <li><a href="/green3-evolution/game?op=new_game">New Game</a></li>
                            <li><a href="/green3-evolution/hosted">Join Game</a></li>
                            <li><a href="/green3-evolution/game?op=user_games">My Games</a></li>
                            <li class="last"><a href="/green3-evolution/">Home</a></li>                            
                        </ul>    	
                </div> <!-- end of tooplate_menu -->         
            </div> <!-- end of header -->
            <div id="tooplate_main">
                <div class="content_box">                    
                    <c:set var="statusDescription" value="Created"/>
                    <c:choose>
                        <c:when test="${gameboard.status eq 1}">
                            <c:set var="statusDescription" value="Ongoing"/>
                            <c:choose>
                                <c:when test="${gameboard.intellectualAnimalId > 0}">
                                    <c:set var="intMode" value="on"/>
                                    <c:set var="defMode" value="off"/>
                                </c:when>
                                <c:when test="${gameboard.intellectualAnimalId < 1 and gameboard.defendingAnimalId > 0}">
                                    <c:set var="defMode" value="on"/>
                                    <c:set var="intMode" value="off"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="defMode" value="off"/>
                                    <c:set var="intMode" value="off"/>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:when test="${gameboard.status eq 2}">
                            <c:set var="statusDescription" value="Finished"/>
                        </c:when>
                    </c:choose>
                    <div class="content_title content_ct"><h2>Game ${gameId} (${statusDescription}) </h2></div>
                    <div class="content">
                        <c:if test="${not empty param.error}">
                            <div style="color: red">
                                <c:choose>
                                    <c:when test="${param.error eq '1' or param.error eq '2'}">
                                        Invalid target animal. Please recheck and enter valid one.
                                    </c:when>
                                        <c:when test="${param.error eq '3'}">
                                        Animal field can't be empty.
                                    </c:when>
                                </c:choose>
                            </div>                            
                        </c:if>
                        
                        <c:choose>
                            <c:when test="${gameboard.status eq 0}">
                                <h2 style="color: red;">Press <a href="/green3-evolution/game?op=start_game">start</a> to run a game!</h2>
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
                                <h4 style="color: black;"> <ul>
                                        <li> Cards left: <u>${gameboard.cardsLeft}</u></li>
                                    <li> Round: <u>${gameboard.currentRound}</u></li>
                                    <li> Stage: <u>${stageDescription}</u></li>
                                    <li> Turn of player: <u>${gameboard.turnPlayer}</u></li>
                                    <c:if test="${gameboard.roundStage eq 2}">
                                        <li> Food supply: <u>${gameboard.supply}</u></li>                                        
                                    </c:if>
                               </ul>
                                </h4>
                                    <c:forEach items="${gameboard.players}" var="player">
                                        <c:if test="${player.user eq userId and userId eq gameboard.turnPlayer and gameboard.roundStage eq 2 and defMode eq 'off'}">
                                            <h3 style="color: red;"><c:choose>
                                                <c:when test="${player.active eq 1 and gameboard.supply>0 and not (player.hungryAnimalsCount eq 0)}">
                                                    <form action="feed">
                                                        <input type="hidden" name="playerId" value="${player.id}"/>
                                                        Animal #<input type="text" name="animalId" size="3"/><br/>
                                                        <input type="submit" value="Feed"/> 
                                                    </form>
                                                    
                                                </c:when>
                                                <c:when test="${player.active eq 0 and (gameboard.supply>0 or player.hasPredator)}">
                                                    <a href="passfeed?playerId=${player.id}">Done</a>
                                                </c:when>
                                               <c:when test="${player.hungryAnimalsCount eq 0 or not (gameboard.supply>0 or player.hasPredator)}">
                                                    <a href="finishfeed?playerId=${player.id}">Finish</a>
                                                </c:when>
                                            </c:choose></h3>
                                        </c:if> 
                                    </c:forEach>
                            </c:when>
                        </c:choose>                        
                    </div>
                    <c:choose>
                        <c:when test="${gameboard.roundStage eq 0}">
                            <c:forEach items="${gameboard.players}" var="player">
                                <c:if test="${player.user eq userId and not empty player.cardsOnHand}">
                                    <div class="content">
                                        <c:if test="${gameboard.turnPlayer eq userId}">
                                            <h3 style="color: black;"><a href="/green3-evolution/pass?playerId=${player.id}">Pass</a></h3>
                                        </c:if>
                                        <table border="3">
                                            <tr>
                                                <c:forEach items="${player.cardsOnHand}" var="playersCard">
                                                    <td> <b><h3>Card ${playersCard.id}</h3></b>
                                                        <table border="2">
                                                            <c:forEach items="${playersCard.properties}" var="property">
                                                                <tr>                                                      
                                                                    <td>
                                                                        <h4>${property.description}</h4>
                                                                        <c:if test="${gameboard.turnPlayer eq userId}">
                                                                            <form action="applycard">
                                                                                <c:if test="${not property.animal}">Target<c:if test="${property.twin}">1</c:if>: <input type="text" name="animalId" size="3"/><br/></c:if>
                                                                                <c:if test="${property.twin}">Target2: <input type="text" name="linkedAnimalId" size="3"/><br/></c:if>
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
                        </c:when>
                        <c:when test="${gameboard.roundStage eq 1}">
                            <c:if test="${gameboard.turnPlayer eq userId}">
                                <div class="content">
                                    <h3 style="color: red;"><a href="/green3-evolution/supply">Generate food supply</a></h3>
                                </div>                                
                            </c:if>
                        </c:when>
                         <c:when test="${gameboard.roundStage eq 3}">
                            <c:if test="${gameboard.turnPlayer eq userId}">
                                <div class="content">
                                    <h3 style="color: black;"><a href="/green3-evolution/extinction">Run extinction</a></h3>
                                </div>                                
                            </c:if>
                        </c:when>
                    </c:choose>
                    
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
                                                <table  border="2">
                                                    <tr>
                                                        <td>
                                                            <b>Animal #${animal.id}</b>
                                                        </td>
                                                    </tr>
                                                    <c:choose>
                                                        <c:when test="${animal.inRegen eq 1}">
                                                            <tr>
                                                                <td>
                                                                    Regenerating
                                                                </td>
                                                            </tr>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:forEach items="${animal.properties}" var="property">
                                                                <c:if test="${(intMode eq 'on') and (gameboard.turnPlayer eq userId) and (gameboard.defendingAnimalId eq animal.id) and ((property.type eq 12) or (property.type eq 14) or (property.type eq 25) or (property.type eq 27))}">
                                                                    <form action="block">
                                                                        <input type="hidden" name="playerId" value="${player.id}"/>
                                                                        <input type="hidden" name="linkedAnimalId" value="${gameboard.intellectualAnimalId}"/>
                                                                        <input type="hidden" name="animalId" value="${animal.id}"/>
                                                                        <input type="hidden" name="propertyId" value="${property.id}"/>
                                                                        <input type="submit" value="Block"/>
                                                                    </form>   
                                                                </c:if>
                                                                <c:if test="${not((property.type eq 29) and not (player.user eq userId)) and intMode eq 'off'}">                                                  
                                                                    <tr>
                                                                        <td>
                                                                            ${property.description}<c:if test="${property.linkedAnimalId > 0}">-#${property.linkedAnimalId}</c:if>
                                                                            <c:if test="${(property.active eq 1) and (player.active eq 1) and (gameboard.turnPlayer eq userId) and (player.user eq userId)}">
                                                                                <c:choose>
                                                                                    <c:when test="${property.type eq 1 and defMode eq 'off' and (animal.feed < animal.neededFeed) and animal.inShell eq 0}">
                                                                                        <form action="attack">
                                                                                            <input type="hidden" name="playerId" value="${player.id}"/>
                                                                                            <input type="hidden" name="linkedAnimalId" value="${animal.id}"/>
                                                                                            Animal #<input type="text" name="animalId" size="3"/><br/>
                                                                                            <input type="submit" value="Attack"/>
                                                                                        </form>
                                                                                    </c:when>
                                                                                    <c:when test="${property.type eq 12 and defMode eq 'on' and gameboard.defendingAnimalId eq animal.id}">
                                                                                        <form action="defend">
                                                                                            <input type="hidden" name="playerId" value="${player.id}"/>
                                                                                            <input type="hidden" name="linkedAnimalId" value="${gameboard.attackingAnimalId}"/>
                                                                                            <input type="hidden" name="animalId" value="${animal.id}"/>
                                                                                            <input type="hidden" name="propertyType" value="${property.type}"/>
                                                                                            <input type="submit" value="Defend"/>
                                                                                        </form>                                                                                    
                                                                                    </c:when>
                                                                                    <c:when test="${property.type eq 13 and (animal.feed < animal.neededFeed) and defMode eq 'off' and gameboard.cardsLeft > 0 and property.refreshing eq 0}">
                                                                                        <form action="hibernation">
                                                                                            <input type="hidden" name="playerId" value="${player.id}"/>
                                                                                            <input type="hidden" name="animalId" value="${animal.id}"/>                                                                                    
                                                                                            <input type="submit" value="Hibernation"/>
                                                                                        </form>
                                                                                    </c:when>
                                                                                    <c:when test="${property.type eq 23 and (animal.feed < animal.neededFeed) and defMode eq 'off' and pecializationACount < 2}">
                                                                                        <form action="specialization">
                                                                                            <input type="hidden" name="playerId" value="${player.id}"/>
                                                                                            <input type="hidden" name="animalId" value="${animal.id}"/>                                                                                    
                                                                                            <input type="submit" value="Use"/>
                                                                                        </form>
                                                                                    </c:when>
                                                                                    <c:when test="${property.type eq 25 and defMode eq 'on' and gameboard.defendingAnimalId eq animal.id}">
                                                                                        <form action="mimic">
                                                                                            <input type="hidden" name="playerId" value="${player.id}"/>
                                                                                            <input type="hidden" name="linkedAnimalId" value="${gameboard.attackingAnimalId}"/>
                                                                                            <input type="hidden" name="animalId" value="${animal.id}"/>
                                                                                            Animal #<input type="text" name="mimicAnimalId" size="3"/><br/>
                                                                                            <input type="hidden" name="propertyType" value="${property.type}"/>
                                                                                            <input type="submit" value="Mimicry"/>
                                                                                        </form>                                                                                    
                                                                                    </c:when>
                                                                                    <c:when test="${property.type eq 27 and defMode eq 'on' and gameboard.defendingAnimalId eq animal.id}">
                                                                                        <form action="run">
                                                                                            <input type="hidden" name="playerId" value="${player.id}"/>
                                                                                            <input type="hidden" name="linkedAnimalId" value="${gameboard.attackingAnimalId}"/>
                                                                                            <input type="hidden" name="animalId" value="${animal.id}"/>
                                                                                            <input type="hidden" name="propertyType" value="${property.type}"/>
                                                                                            <input type="submit" value="Run"/>
                                                                                        </form>                                                                                    
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                        Not supported yet
                                                                                    </c:otherwise>
                                                                                    <c:if test="${defMode eq 'on' and animal.tailLossActive}">
                                                                                        <form action="loss">
                                                                                            <input type="hidden" name="playerId" value="${player.id}"/>                                                                                    
                                                                                            <input type="hidden" name="animalId" value="${animal.id}"/>
                                                                                            <input type="hidden" name="propertyType" value="${property.type}"/>
                                                                                            <input type="hidden" name="linkedAnimalId" value="${gameboard.attackingAnimalId}"/>
                                                                                            <input type="submit" value="Loss"/>
                                                                                        </form>
                                                                                    </c:if>
                  
                                                                                    <c:if test="${(defMode eq 'off') and (property.neddedFeed eq 0) and animal.metamorphose and (animal.feed < animal.neededFeed)}">
                                                                                        <form action="metamorphose">
                                                                                            <input type="hidden" name="playerId" value="${player.id}"/>                                                                                    
                                                                                            <input type="hidden" name="animalId" value="${animal.id}"/>
                                                                                            <input type="hidden" name="propertyId" value="${property.id}"/>                                                                                            
                                                                                            <input type="submit" value="Metamorphose"/>
                                                                                        </form>
                                                                                    </c:if>
                                                                                </c:choose>
                                                                            </c:if>                                                                    
                                                                        </td>
                                                                    </tr>
                                                                </c:if>
                                                            </c:forEach>
                                                              <c:if test="${gameboard.roundStage eq 2}"> 
                                                                  <tr>
                                                                        <td>
                                                                            Feed: ${animal.feed}/${animal.neededFeed}
                                                                        </td>
                                                                    </tr>                                                         
                                                              </c:if>
                                                        </c:otherwise>   
                                                    </c:choose> 
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
