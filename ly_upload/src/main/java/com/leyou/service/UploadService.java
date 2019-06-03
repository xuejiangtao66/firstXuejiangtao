package com.leyou.service;

import com.leyou.exception.ExceptionEnum;
import com.leyou.exception.LyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class UploadService {

    private static final List<String> ALLOW_TYPE =  Arrays.asList("image/png","image/jpeg","image/jpg");

    public String uploadImg(MultipartFile file) {
        //校验文件类型
        if(!ALLOW_TYPE.contains(file.getContentType())){
                throw new  LyException(ExceptionEnum.INVALID_FILE_TYPE);
        }


        //校验文件内容
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            if(image == null){
                throw new  LyException(ExceptionEnum.INVALID_FILE_TYPE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            //文件路径
       File dest = new File("G://dianshangtu//img",file.getOriginalFilename());
       //上传文件
            file.transferTo(dest);
            return "image.leyou.com/" + file.getOriginalFilename();
        } catch (IOException e) {
            log.info("文件上传失败:{}",e);
            throw new LyException(ExceptionEnum.UPLOAD_FAIL);
        }
    }


}
