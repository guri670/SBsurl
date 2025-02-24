package com.koreait.short_url_project.domain.surl.surl.controller;

import com.koreait.short_url_project.domain.member.member.entity.Member;
import com.koreait.short_url_project.domain.surl.surl.entity.Surl;
import com.koreait.short_url_project.domain.surl.surl.service.SurlService;
import com.koreait.short_url_project.global.exceptions.GlobalException;
import com.koreait.short_url_project.global.rq.Rq.Rq;
import com.koreait.short_url_project.global.rsData.RsData;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ShortUrlController {

    private final Rq rq;
    private final SurlService surlService;

    @GetMapping("/add")
    @ResponseBody
    public RsData<Surl> add(String body, String url) {
        Member member = rq.getMember(); // 현재 브라우저로 로그인 한 회원 정보
        System.out.println("before get id");
        member.getId();
        System.out.println("after get id");
        System.out.println("before get username");
        member.getUsername();
        System.out.println("after get username");

        return surlService.add(member, body, url);


    }

    @GetMapping("/s/{body}/**")
    @ResponseBody
    public RsData<Surl> add(
            @PathVariable String body,
            HttpServletRequest req
    ) {
        Member member = rq.getMember();

        String url = req.getRequestURI();

        if (req.getQueryString() != null) {
            url += "?" + req.getQueryString();
        }

        String[] urlBits = url.split("/", 4);

        url = urlBits[3];
        return surlService.add(member, body, url);

    }

    @GetMapping("/g/{id}")
    public String go(
            @PathVariable long id
    ) {
        Surl surl = surlService.findById(id).orElseThrow(GlobalException.E404::new);

        surl.increaseCount();
        surlService.increaseCount(surl);
        return "redirect:" + surl.getUrl();
    }

    @GetMapping("/all")
    @ResponseBody
    public List<Surl> getAll() {
        return surlService.findAll();
    }
}


