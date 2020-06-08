package com.test.community.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.test.community.Dto.AccessTokenDto;
import com.test.community.Dto.GiteeUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GiteeProvider {
    public String getAccessToken(AccessTokenDto accessTokenDto) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDto));
        Request request = new Request.Builder()
                .url("https://gitee.com/oauth/token?grant_type=authorization_code&code=" + accessTokenDto.getCode() +
                        "&client_id=" + accessTokenDto.getClient_id() +
                        "&redirect_uri=" + accessTokenDto.getRedirect_url() +
                        "&client_secret=" + accessTokenDto.getClient_secret())
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String str = response.body().string();
            JSONObject jsonObject = JSON.parseObject(str);
            return jsonObject.getString("access_token");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GiteeUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://gitee.com/api/v5/user?access_token=" + accessToken)
                .build();
        try {
            Response response  = client.newCall(request).execute();
            String str = response.body().string();
            GiteeUser giteeUser=JSON.parseObject(str, GiteeUser.class);
            return  giteeUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
