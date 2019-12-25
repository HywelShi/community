package com.kgc.community.controller;

import com.kgc.community.dto.AccessTokenDTO;
import com.kgc.community.dto.GithubUser;
import com.kgc.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author hywel
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @RequestMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("99da32dbb64c1bd85bdd");
        accessTokenDTO.setClient_secret("53362592ca960a4cf22c480f98bfed11d58e2d38");
        accessTokenDTO.setCode(code);

        accessTokenDTO.setRedirect_uri("http://localhost:8888/callback");
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getGithubUser(accessToken);
        System.out.println(githubUser.getName());
        return "index";
    }
}
