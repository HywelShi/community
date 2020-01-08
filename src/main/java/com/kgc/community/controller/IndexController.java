package com.kgc.community.controller;

import com.kgc.community.dto.PaginationDTO;
import com.kgc.community.dto.QuestionDTO;
import com.kgc.community.mapper.QuestionMapper;
import com.kgc.community.mapper.UserMapper;
import com.kgc.community.model.Question;
import com.kgc.community.model.User;
import com.kgc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.soap.SOAPMessageHandlers;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author hywel
 */
@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionService questionService;

    /**
     * 访问主页获取页面用户cookie中存入的token
     *
     * @return
     */
    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(value = "page",defaultValue = "1") Integer page,
                        @RequestParam(value = "size",defaultValue = "5") Integer size) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.selectByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        //登录成功主页显示所有的问题
        PaginationDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination", pagination);
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/login";
    }
}
