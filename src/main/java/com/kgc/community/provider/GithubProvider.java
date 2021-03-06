package com.kgc.community.provider;

import com.alibaba.fastjson.JSON;
import com.kgc.community.dto.AccessTokenDTO;
import com.kgc.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author hywel
 * 利用okhttp将github授权后的code发送回去
 * @Component利用IOC将其放入spring容器统一管理
 */
@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            //access_token=cf6e978b09a9533a9c9430fe13e5df1270457d1e&scope=user&token_type=bearer
            //先按照&截取再按照=截取 得到access_token
            System.out.println(string);
            String access_token = string.split("&")[0].split("=")[1];
            System.out.println(access_token);
            return access_token;
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }


    public GithubUser getGithubUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
