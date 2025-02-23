package com.sea.whale.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sea.whale.entity.dto.WhaleFallDTO;
import com.sea.whale.entity.vo.WhaleFallVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Whale
 * @since 2022-08-24 21:20:00
 */
public interface IWhaleFallService extends IService<WhaleFallVO> {

    WhaleFallVO testException(Integer id);

    Integer addWhale(WhaleFallDTO whaleFallDTO);

    WhaleFallVO queryWhale(WhaleFallDTO whaleFallDTO);

    Boolean delWhale(WhaleFallDTO whaleFallDTO);

    Boolean updateWhale(WhaleFallDTO whaleFallDTO);


}
