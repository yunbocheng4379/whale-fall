package com.sea.whale.controller;

import com.sea.whale.entity.dto.WhaleFallDTO;
import com.sea.whale.entity.vo.R;
import com.sea.whale.operatelog.OperateLog;
import com.sea.whale.operatelog.ParsingParamHandler;
import com.sea.whale.service.IWhaleFallService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Whale
 * @since 2022-08-24 21:20:00
 */
@RestController
@RequestMapping("/whaleFall")
public class WhaleFallController {

    private final IWhaleFallService iWhaleFallService;

    public WhaleFallController(IWhaleFallService iWhaleFallService){
        this.iWhaleFallService = iWhaleFallService;
    }

    /**
     * 测试后端是否联通接口
     */
    @GetMapping("/testWhale")
    public R testRedis(@Param("id") Integer id) {
        System.out.println(id);
        return id != null ? R.ok().data("data", "系统联通性测试成功") : R.error("系统连通性测试失败");
    }

    /**
     * 测试一些功能的接口
     */
    @PostMapping("/whale")
    public R whale(@Valid @RequestBody WhaleFallDTO whaleFallDTO) {
        System.out.println(whaleFallDTO.getId());
        System.out.println(whaleFallDTO.getWhaleName());
        return R.ok().data("data", iWhaleFallService.testException(whaleFallDTO.getId()));
    }

    /**
     * 查询一个鲸鱼的接口
     */
    @PostMapping("/queryWhale")
    public R queryWhale(@Valid @RequestBody WhaleFallDTO whaleFallDTO) {
        return R.ok().data("data", iWhaleFallService.queryWhale(whaleFallDTO));
    }

    /**
     * 新增一个鲸鱼的接口
     */
    @PostMapping("/addWhale")
    @OperateLog(logResource = "鲸鱼", operateName = "新增一个种类",handler = ParsingParamHandler.class)
    public R addWhale(@Valid @RequestBody WhaleFallDTO whaleFallDTO) {
        System.out.println(whaleFallDTO.getId());
        System.out.println(whaleFallDTO.getWhaleName());
        return R.ok().data("data", iWhaleFallService.addWhale(whaleFallDTO));
    }

    /**
     * 删除一个鲸鱼的接口
     */
    @PostMapping("/delWhale")
    @OperateLog(logResource = "鲸鱼", operateName = "删除一个种类", handler = ParsingParamHandler.class)
    public R delWhale(@Valid @RequestBody WhaleFallDTO whaleFallDTO) {
        return R.ok().data("data", iWhaleFallService.delWhale(whaleFallDTO));
    }

    /**
     * 删除一个鲸鱼的接口
     */
    @PostMapping("/updateWhale")
    @OperateLog(logResource = "鲸鱼", operateName = "修改一个种类", handler = ParsingParamHandler.class)
    public R updateWhale(@Valid @RequestBody WhaleFallDTO whaleFallDTO) {
        return R.ok().data("data", iWhaleFallService.updateWhale(whaleFallDTO));
    }
}
