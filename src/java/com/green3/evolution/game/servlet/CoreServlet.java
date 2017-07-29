/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green3.evolution.game.servlet;

import com.green3.evolution.action.Action;
import com.green3.evolution.action.core.CoreActionType;
import com.green3.evolution.core.factory.CoreActionFactory;
import com.green3.evolution.core.model.User;
import com.green3.evolution.game.CoreConstants;
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
public class CoreServlet extends HttpServlet {

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
        CoreActionType operationType = CoreActionType.LOGIN;
        if (null!=operation && !operation.isEmpty()){
            operationType = CoreActionType.valueOf(operation.toUpperCase());
        }
        Action action = CoreActionFactory.createAction(operationType);
        if (null==action){
            return;
        }
        Map<String,Object> params = createParamsMap(request, operationType);
        CommonEntity model = action.execute(params);
        
        switch (operationType){
            case LOGIN: 
                String login = ((User) model).getLogin();
                HttpSession session = request.getSession();
                session.setAttribute(GameConstants.PARAM_USER_ID, login);
                response.sendRedirect("/green3-evolution/");
                break;
            default:
                response.sendRedirect("/green3-evolution/");
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

    private Map<String, Object> createParamsMap(HttpServletRequest request, CoreActionType operationType) {
        Map<String, Object> paramsMap = new HashMap<String,Object>();
        
        Object userIdParam = request.getSession().getAttribute("userId");
        if (userIdParam!=null){
            String userId = (String)userIdParam;
            paramsMap.put(GameConstants.PARAM_USER_ID, userId);
        }

        switch (operationType){
            case LOGIN:
                String login = (String) request.getParameter(CoreConstants.PARAM_USER_LOGIN);
                String password = (String) request.getParameter(CoreConstants.PARAM_USER_PASSWORD);                
                paramsMap.put(CoreConstants.PARAM_USER_LOGIN, login);
                paramsMap.put(CoreConstants.PARAM_USER_PASSWORD, password);
                break;
            default:
                break;
                
        }
        
        return paramsMap;
    }

}
