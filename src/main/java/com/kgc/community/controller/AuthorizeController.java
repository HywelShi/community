package com.kgc.community.controller;

import com.kgc.community.dto.AccessTokenDTO;
import com.kgc.community.dto.GithubUser;
import com.kgc.community.mapper.UserMapper;
import com.kgc.community.model.User;
import com.kgc.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author hywel
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client_id}")
    private String clientId;
    @Value("${github.client_secret}")
    private String clientSecret;
    @Value("${github.redirect_uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request, HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getGithubUser(accessToken);
        if(githubUser != null && githubUser.getId() != null){
            String token = null;
            //判断用户是否已经授权登录过 如果没有则插入数据库 如果有则直接登录
            User user = userMapper.selectByName(githubUser.getName());
            if(user==null){
                user = new User();
                token= UUID.randomUUID().toString();
                user.setToken(token);
                user.setName(githubUser.getName());
                user.setAccountId(String.valueOf(githubUser.getId()));
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(System.currentTimeMillis());
                user.setAvatarUrl(githubUser.getAvatar_url());
                userMapper.insert(user);
            }
            //登录成功 写入cookie和session
            Cookie cookie = new Cookie("token", user.getToken());
            //设置cookie过期时间 单位秒 600秒后自动清除
            cookie.setMaxAge(600);
            response.addCookie(cookie);
            //request.getSession().setAttribute("githubUser",githubUser);
            //重定向返回主页面
            return "redirect:/";
        }else{
            //登录失败 重新登录
            //重定向返回主页面
            return "redirect:/";
        }
    }
}
