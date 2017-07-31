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
public class JoinGameServlet extends GameServlet {

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
        GameActionType operationType = GameActionType.JOIN_GAME;
        Action action = GameActionFactory.createAction(operationType);
        if (null==action){
            return;
        }
        String gameIdParam = (String) request.getParameter(GameConstants.PARAM_GAME_ID);
        Map<String,Object> params = createParamsMap(request, operationType, gameIdParam);
        action.execute(params);
        
        int gameId = Integer.valueOf(gameIdParam);
        HttpSession session = request.getSession();
        session.setAttribute(GameConstants.PARAM_GAME_ID, gameId);
        response.sendRedirect("/green3-evolution/game");
    }
    
    protected Map<String, Object> createParamsMap(HttpServletRequest request, GameActionType operationType, String gameIdParam) {
        Map<String, Object> params = super.createParamsMap(request, operationType);
        
        params.put(GameConstants.PARAM_GAME_ID, gameIdParam);
        return params;
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
}
