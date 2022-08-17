package life.majiang.community.provider;

import com.alibaba.fastjson.JSON;
import life.majiang.community.dto.AccessTokenDto;
import life.majiang.community.dto.GithubUserDto;
import okhttp3.*;
import org.springframework.stereotype.Component;
import java.io.IOException;

/*
 * @description:
 * @author: ccl
 * @date: 2022/8/17 15:26
 **/
@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDto accessTokenDto) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDto));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token=string.split("&")[0].split("=")[1];
            System.out.println(string);
            return string;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUserDto getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://github.com/user?accesstoken=" + accessToken)
                .build();
        try {
                Response response = client.newCall(request).execute();
                String string = response.body().string();
                GithubUserDto githubUserDto=JSON.parseObject(string,GithubUserDto.class);
                return githubUserDto;
        }
        catch (IOException e) {

        }
        return null;
    }
}


