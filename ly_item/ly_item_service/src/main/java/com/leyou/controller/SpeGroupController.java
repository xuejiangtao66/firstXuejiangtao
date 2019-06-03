package com.leyou.controller;

import com.bw.leyou.pojo.Specification;
import com.leyou.service.SpeGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/spec")
public class SpeGroupController {

    @Autowired
    private SpeGroupService speGroupService;


    @GetMapping("{cid}")
    public ResponseEntity<String> queryByCid(@PathVariable("cid") Long cid){
        Specification specification = speGroupService.queryByCid(cid);
        if(specification == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(specification.getSpecifications());

    }


}
