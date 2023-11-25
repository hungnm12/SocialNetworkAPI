//package com.example.Social.Network.API.security;
//
//import com.example.Social.Network.API.Constant.ResponseCode;
//import com.example.Social.Network.API.Constant.ResponseMessage;
//import com.example.Social.Network.API.Model.Entity.User;
//import com.example.Social.Network.API.Model.ResDto.GeneralResponse;
//import com.example.Social.Network.API.Repository.UserRepo;
//import com.example.Social.Network.API.Service.Impl.JwtService;
//import com.example.Social.Network.API.utils.JwtUtils;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.core.parameters.P;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//
//@Configuration
//public class TokenInterceptor implements HandlerInterceptor {
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    @Autowired
//    private JwtService jwtService;
//
//    @Autowired
//    private UserRepo userRepo;
//    @Override
//    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
//        String token = request.getParameter(
//                "token");
////        v
//        if(token != null )
//        {
//            User user= JwtUtils.getUserFromToken(jwtService,userRepo,token);
//            if(user == null)
//            {
//                GeneralResponse generalResponse = new GeneralResponse(ResponseCode.USER_NOT_VALIDATED, ResponseMessage.USER_NOT_VALIDATED, "User is not valid");
//                String responseJson = objectMapper.writeValueAsString(generalResponse);
//                response.setContentType("application/json");
//                response.getWriter().write(responseJson);
//                response.setStatus(HttpServletResponse.SC_OK);
//            }
//            else
//            if (!jwtService.isTokenValid(token, user)) {
//                // Create and return custom GeneralResponse
//                GeneralResponse generalResponse = new GeneralResponse(ResponseCode.TOKEN_INVALID, ResponseMessage.TOKEN_INVALID, "Token is not valid");
//                String responseJson = objectMapper.writeValueAsString(generalResponse);
//                response.setContentType("application/json");
//                response.getWriter().write(responseJson);
//                response.setStatus(HttpServletResponse.SC_OK);
//                return false;
//            }
//
////            return new GeneralResponse(ResponseCode.USER_NOT_VALIDATED, ResponseMessage.USER_NOT_VALIDATED,"The user is not valid");
//        }
//
//
//
//        return true;
//    }
//
//
////    @Override
////    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
////        // This method is called after the handler method is invoked
////    }
////
////    @Override
////    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
////        // This method is called after the response has been sent to the client
////    }
//}