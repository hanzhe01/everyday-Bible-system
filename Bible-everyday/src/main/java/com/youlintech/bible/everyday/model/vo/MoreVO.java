package com.youlintech.bible.everyday.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author oyhz
 */
@Data
public class MoreVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String moreUrl;
    private String backImg;
    private Long backImgId;
}
