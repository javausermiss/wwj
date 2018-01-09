package com.fh.controller.wwjapp;

import com.fh.entity.system.RunImage;
import com.fh.service.system.runimage.RunImageManager;
import com.fh.util.wwjUtil.RespStatus;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/app")
public class RunImgController {
    @Resource(name="runimageService")
    private RunImageManager runimageService;

    @RequestMapping(value = "/getRunImage", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getRunImage(){
        try{
            List<RunImage> list = runimageService.getRunImageList();
            Map<String,Object> map = new HashMap<>();
            map.put("runImage",list);
            return RespStatus.successs().element("data",map);

        }catch (Exception e){
            e.printStackTrace();
            return RespStatus.fail();
        }

    }


}
