package com.zx.springboot.demo.controller;

import com.sun.deploy.net.HttpUtils;
import com.zx.springboot.demo.entity.LoginResponse;
import com.zx.springboot.demo.util.HttpsUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class Wso2TestController {

    @RequestMapping("/login")
    public String login(String username, String password) throws Exception {

        LoginResponse loginResponse = new LoginResponse();

        Map clients = HttpsUtils.loginGetHeader("https://127.0.0.1:9443/devportal/services/configs", null);

        String firstLocation = (String) clients.get("Location");
        firstLocation = firstLocation.replace(" ", "%20");
        Map headers = HttpsUtils.loginGetHeader(firstLocation, null);
        String location = (String) headers.get("Location");
        int begin = location.indexOf("sessionDateKey=");
        int end = location.indexOf("relyingParty");

        String sessionDateKey = ((String) headers.get("Location")).substring(begin + "sessionDateKey=".length(), end-1);

        Map<String, String> requestHeader = new HashMap<>();
        requestHeader.put("Content-Type", "application/x-www-form-urlencoded");

        Map<String, String> requsetBody = new HashMap<>();
        requsetBody.put("username", username);
        requsetBody.put("password", password);
        requsetBody.put("sessionDateKey", sessionDateKey);

        Map<String, String> params1 = HttpsUtils.loginPostHeader("https://127.0.0.1:9443/commonauth", requestHeader, requsetBody);
        String commonAuthIdString =  params1.get("Set-Cookie");
        int begin2 = commonAuthIdString.indexOf("commonAuthId=");
        int end2 = commonAuthIdString.indexOf("Path");


        String commonAuthId = commonAuthIdString.substring(begin2+"commonAuthId=".length(), end2-2);

        String location1 = params1.get("Location");
        int begin4 = location1.indexOf("sessionDateKey=");

        String realSessionDateKey = location1.substring(begin4 + "sessionDateKey=".length());

        Map<String, String> authHeader = new HashMap<>();
        authHeader.put("Content-Type", "application/json");
        Map<String, String> authHeaders = HttpsUtils.loginGetHeader("https://127.0.0.1:9443/oauth2/authorize"+"?sessionDateKey="+realSessionDateKey, authHeader);
        return "";
    }



    @RequestMapping("register")
    public String register(String username, String password, String email){
        Map<String,String> header = new HashMap<>();
        header.put("Content-Type","application/x-www-form-urlencoded");
        Map<String,String> requestBody = new HashMap<>();

        requestBody.put("http://wso2.org/claims/givenname", "");
        requestBody.put("http://wso2.org/claims/lastname", "");
        requestBody.put("username", username);
        requestBody.put("password", password);
        requestBody.put("password2", password);
        requestBody.put("http://wso2.org/claims/emailaddress", email);
        requestBody.put("http://wso2.org/claims/organization", "");
        requestBody.put("http://wso2.org/claims/telephone", "");
        requestBody.put("http://wso2.org/claims/im", "");
        requestBody.put("http://wso2.org/claims/country", "");
        requestBody.put("http://wso2.org/claims/mobile", "");
        requestBody.put("http://wso2.org/claims/url", "");
        requestBody.put("isSelfRegistrationWithVerification", "true");
        requestBody.put("itenantDomain", "carbon.super");
        requestBody.put("callback", "https://localhost:9443/authenticationendpoint/login.do");


        try {
            String url = "https://127.0.0.1:9443/accountrecoveryendpoint/processregistration.do";
            String responseString = HttpsUtils.httpsFormPost(url, header, requestBody);

            if( responseString.contains("User registration completed successfully")){
                return "注册成功";
            }else {
                return "注册失败。";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }

    }

//    private String postRequest(String uri, MultiValueMap<String,String> body){
//        String resp = WebClient.builder()
//                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
//                .build()
//                .post()
//                .uri(uri)
//                .body(BodyInserters.fromFormData(body))
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
//
//        return resp;
//    }
//
//    private String getRequest(String uri){
//        String resp = WebClient.builder()
//                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
//                .build()
//                .get()
//                .uri(uri)
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
//
//        return resp;
//    }
}
