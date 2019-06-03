package com.leyou.service;

import com.bw.leyou.pojo.SpeGroup;
import com.bw.leyou.pojo.Specification;
import com.leyou.exception.ExceptionEnum;
import com.leyou.exception.LyException;
import com.leyou.mapper.SpeGropMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpeGroupService {
    @Autowired
    private SpeGropMapper speGropMapper;

    public Specification queryByCid(Long cid) {

        return speGropMapper.selectByPrimaryKey(cid);
    }
}
