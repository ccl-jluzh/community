package life.majiang.community.controller;

import life.majiang.community.dto.AccessTokenDto;
import life.majiang.community.dto.GithubUserDto;
import life.majiang.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("&{github.client.id}")
    private String clintId;
    @Value("&{github.client.secret}")
    private String clintSecret;
    @Value("&{github.redirect.url}")
    private String redirectUrl;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) {
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        accessTokenDto.setRedirect_url(redirectUrl);
        accessTokenDto.setClient_id(clintId);
        accessTokenDto.setClient_secret(clintSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        //  GithubUserDto user = githubProvider.getUser(accessToken);
        //  System.out.println(user.getName());
        return "index";
    }
}
