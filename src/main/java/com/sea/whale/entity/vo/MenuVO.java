package com.sea.whale.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author chengyunbo
 * @since 2025-03-04 10:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuVO implements Serializable {

    private static final long serialVersionUID = 202L;

    private Integer id;

    private String text;

    private Integer menuId;

    private String route;

    private List<MenuVO> children;

}
