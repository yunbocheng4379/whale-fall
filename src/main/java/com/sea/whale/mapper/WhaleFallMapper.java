package com.sea.whale.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sea.whale.entity.dto.WhaleFallDTO;
import com.sea.whale.entity.vo.WhaleFallVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Whale
 * @since 2022-08-24 21:20:00
 */
@Repository
public interface WhaleFallMapper extends BaseMapper<WhaleFallVO> {

//  使用xml方式写SQL，@Options不会生效，必须使用 @Insert 这种方式才行
    // @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
//    @Insert("        INSERT INTO whale_fall (whale_pwd, whale_name, whale_phone, whale_email, whale_account)\n" +
//            "        VALUES (#{whaleFallDTO.whalePwd},#{whaleFallDTO.whaleName}, #{whaleFallDTO.whalePhone}, #{whaleFallDTO.whaleEmail},\n" +
//            "                #{whaleFallDTO.whaleAccount})\n")
    Integer addWhaleFall(@Param("whaleFallDTO") WhaleFallDTO whaleFallDTO);

    Integer updateWhale(@Param("whaleFallDTO") WhaleFallDTO whaleFallDTO);

}
