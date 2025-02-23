package com.sea.whale.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sea.whale.entity.dto.WhaleFallDTO;
import com.sea.whale.entity.vo.WhaleFallVO;
import com.sea.whale.enums.ResultEnum;
import com.sea.whale.exception.AppException;
import com.sea.whale.mapper.WhaleFallMapper;
import com.sea.whale.service.IWhaleFallService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Whale
 * @since 2022-08-24 21:20:00
 */
@Service
public class WhaleFallServiceImpl extends ServiceImpl<WhaleFallMapper, WhaleFallVO> implements IWhaleFallService {

    private final WhaleFallMapper whaleFallMapper;

    public WhaleFallServiceImpl(WhaleFallMapper whaleFallMapper){
        this.whaleFallMapper = whaleFallMapper;
    }

    /**
     * 测试一些功能的接口
     */
    @Override
    public WhaleFallVO testException(Integer id) {
        if (id == 1) throw new AppException(ResultEnum.ARGS_VALID_ERROR);
        //return whaleFallMapper.selectById(id);
        return null;
    }

    /**
     * 新增一个鲸鱼的接口
     */
    @Override
    public Integer addWhale(WhaleFallDTO whaleFallDTO){
        return whaleFallMapper.addWhaleFall(whaleFallDTO);
    }

    /**
     * 查询一个鲸鱼的接口
     */
    @Override
    public WhaleFallVO queryWhale(WhaleFallDTO whaleFallDTO) {
        return whaleFallMapper.selectById(whaleFallDTO.getId());
    }

    /**
     * 删除一个鲸鱼的接口
     */
    @Override
    public Boolean delWhale(WhaleFallDTO whaleFallDTO) {
        return whaleFallMapper.deleteById(whaleFallDTO.getId()) > 0;
    }

    @Override
    public Boolean updateWhale(WhaleFallDTO whaleFallDTO) {
        return whaleFallMapper.updateWhale(whaleFallDTO) > 0;
    }

}
