package com.heima.admin.controller.v1;

import com.heima.admin.service.AdChannelService;
import com.heima.model.admin.dtos.ChannelDto;
import com.heima.model.admin.pojos.AdChannel;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/channel")
@Api(value = "频道管理", tags = "channel", description = "频道管理API")
public class ChannelController{

    @Autowired
    private AdChannelService channelService;

    @PostMapping("/list")
    @ApiOperation("频道分页列表查询")
    public ResponseResult findByNameAndPage(@RequestBody ChannelDto dto){
        return channelService.findByNameAndPage(dto);
    }

    @PostMapping("/save")
    public ResponseResult save(@RequestBody AdChannel channel) {
        if(null == channel){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //参数正常
        channel.setCreatedTime(new Date());
        channelService.save(channel);

        return ResponseResult.okResult(null);
    }

    @PostMapping("/update")
    public ResponseResult update(@RequestBody AdChannel adChannel) {
        //return channelService.update(adChannel);
        if(adChannel == null || adChannel.getId() == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        boolean b = channelService.updateById(adChannel);
        if(b){
            //更新成功
            return ResponseResult.okResult(null);
        }else{
            //更新失败
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
    }

    @GetMapping("/del/{id}")
    public ResponseResult deleteById(@PathVariable("id") Integer id) {
        //return channelService.deleteById(id);
        AdChannel adChannel = channelService.getById(id);
        if(adChannel == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        if(adChannel.getStatus()){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"当前频道数据有效，无法进行删除!");
        }

        channelService.deleteById(id);
        return ResponseResult.okResult(null);
    }

    @GetMapping("/error")
    @ApiOperation("测试异常")
    public void testError(){
        int i = 1/0;
    }
}