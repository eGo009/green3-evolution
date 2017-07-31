/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.game.servlet;

import com.green3.evolution.action.Action;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.green3.evolution.game.GameConstants;
import com.green3.evolution.game.action.GameActionType;
import com.green3.evolution.game.factory.GameActionFactory;
import com.green3.evolution.game.model.GameBoard;
import com.green3.evolution.model.CommonEntity;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;


/**
 *
 * @author Alex_Ihnatsiuck
 */
public class GameServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String operation = (String) request.getParameter(GameConstants.PARAM_OPERATION);
        GameActionType operationType = GameActionType.GET_GAMEBOARD;
        if (null!=operation && !operation.isEmpty()){
            operationType = GameActionType.valueOf(operation.toUpperCase());
        }
        Action action = GameActionFactory.createAction(operationType);
        if (null==action){
            return;
        }
        Map<String,Object> params = createParamsMap(request, operationType);
        CommonEntity model = action.execute(params);
        
        switch (operationType){
            case FEED: 
                break;
            case USE_CARD:
                  break;
            case GET_GAMEBOARD:
                request.setAttribute("gameboard", model);
                getServletContext().getRequestDispatcher("/jsp/game/gameboard.jsp").forward(request, response);
                break;
            case GET_CARDS:
                break;
            case NEW_GAME:
                int gameId = ((GameBoard) model).getId();
                HttpSession session = request.getSession();
                session.setAttribute(GameConstants.PARAM_GAME_ID, gameId);
                response.sendRedirect("/green3-evolution/game");
                break;
            case USER_GAMES:
                request.setAttribute("gameContainer", model);
                getServletContext().getRequestDispatcher("/jsp/my-games.jsp").forward(request, response);
                break;
            case CHOOSE_GAME:
                String gameIdParam = (String) request.getParameter(GameConstants.PARAM_GAME_ID);
                gameId = Integer.valueOf(gameIdParam);
                session = request.getSession();
                session.setAttribute(GameConstants.PARAM_GAME_ID, gameId);
                response.sendRedirect("/green3-evolution/game");
                break;
            case START_GAME: 
                response.sendRedirect("/green3-evolution/game");
                break;
            default:
                request.setAttribute("gameboard", model);
                getServletContext().getRequestDispatcher("/jsp/game/gameboard.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    protected Map<String, Object> createParamsMap(HttpServletRequest request, GameActionType operationType) {
        Map<String, Object> paramsMap = new HashMap<String,Object>();
        Object gameIdParam = request.getSession().getAttribute("gameId");
        if (gameIdParam!=null){
            int gameId = (int)gameIdParam;
            paramsMap.put(GameConstants.PARAM_GAME_ID, gameId);
        }
        
        Object userIdParam = request.getSession().getAttribute("userId");
        if (userIdParam!=null){
            String userId = (String)userIdParam;
            paramsMap.put(GameConstants.PARAM_USER_ID, userId);
        }
        return paramsMap;
    }

}
