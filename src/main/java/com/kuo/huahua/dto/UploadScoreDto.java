package com.kuo.huahua.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Channing Kuo
 * @date 2021/7/3
 */
@Data
public class UploadScoreDto {

	private String term;

	private String className;

	private String name;

	private String kind;

	private String testName;

	private Float score;
}
