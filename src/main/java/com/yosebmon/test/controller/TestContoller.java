package com.yosebmon.test.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yosebmon.test.entity.MemberEntity;
import com.yosebmon.test.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestContoller {

    private final MemberRepository memberRepository;

    @GetMapping("/test")
    public String test(){
        List<MemberEntity> memberEntities = memberRepository.findAll();
        memberEntities.forEach(s->{
            log.info(s.toString());
        });
        return "success";
    }
}
