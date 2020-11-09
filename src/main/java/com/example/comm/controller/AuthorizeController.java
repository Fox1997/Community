package com.example.comm.controller;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.example.comm.Provider.GithubProvider;
import com.example.comm.dto.AccessTokenDto;
import com.example.comm.dto.GithubUser;
import com.example.comm.model.User;
import com.example.comm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Controller
@PropertySource({"classpath:application.properties"})
public class AuthorizeController {

    @Autowired(required = false)
    private GithubProvider githubProvider;

    @Autowired(required = false)
    private UserService userService;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;


    @RequestMapping("/hello")
    @ResponseBody
    public Map<String, Object> hello() {
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "world");
        return map;
    }


    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response)
    {
        System.out.printf("11111111111111111hello");
        AccessTokenDto accessTokenDto =new AccessTokenDto();
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(redirectUri);
        accessTokenDto.setState(state);
//        try {
//            System.out.println("qqqqqqqqqqqqqqqqqqqqqqqq");
//            String accessToken = githubProvider.getAccessToken(accessTokenDto);
//            System.out.println(accessToken);
//            GithubUser githubUser = githubProvider.getUser(accessToken);
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.printf(accessToken);
        if (githubUser != null && githubUser.getId() != null)
        {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userService.createOrUpdate(user);
            System.out.println(user);
            response.addCookie(new Cookie("token",token));
            return "redirect:/";

        }else{
            return "redirect:/";

        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie =new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";

    }
}
