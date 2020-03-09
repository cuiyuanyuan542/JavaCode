package com.komhart.translate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.AmazonTranslateClient;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;

@Controller
public class TranslateController{

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @PostMapping("/translate")
    @ResponseBody
    public String translate(String source){
         // Create credentials using a provider chain. For more information, see
        // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html
        AWSCredentialsProvider credentialsProvider = DefaultAWSCredentialsProviderChain.getInstance();

        AmazonTranslate translate = AmazonTranslateClient.builder()
                .withCredentials(new AWSStaticCredentialsProvider(credentialsProvider.getCredentials()))
                .withRegion("ap-northeast-2")
                .build();

        TranslateTextRequest request = new TranslateTextRequest()
                .withText(source)
                .withSourceLanguageCode("en")
                .withTargetLanguageCode("zh");
        TranslateTextResult result  = translate.translateText(request);
        System.out.println("The result after translating is "+result.getTranslatedText());
        return result.getTranslatedText();
    }

    @PostMapping("/tran/{lan}")
    @ResponseBody
    public String changeLanguage(String source,@PathVariable("lan") String lan){

        System.out.println(lan);
        AWSCredentialsProvider credentialsProvider = DefaultAWSCredentialsProviderChain.getInstance();

        AmazonTranslate translate = AmazonTranslateClient.builder()
                .withCredentials(new AWSStaticCredentialsProvider(credentialsProvider.getCredentials()))
                .withRegion("ap-northeast-2")
                .build();

        TranslateTextRequest request = new TranslateTextRequest()
                .withText(source)
                .withSourceLanguageCode("en")
                .withTargetLanguageCode(lan);
                
        TranslateTextResult result  = translate.translateText(request);
        System.out.println("The result after translating is "+result.getTranslatedText());
        return result.getTranslatedText();

    }
}