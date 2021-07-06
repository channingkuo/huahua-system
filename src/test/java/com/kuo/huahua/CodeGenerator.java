package com.kuo.huahua;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.google.common.collect.Maps;
import com.sun.javafx.PlatformUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Channing Kuo
 * @date 2021/06/28
 */
public class CodeGenerator {

	private static final String PROJECT_DIR = System.getProperty("user.dir");

	/**
	 * 代码package
	 */
	private static final String PARENT_NAME = "K-generator";

	/**
	 * 基本路径
	 */
	private static final String SRC_MAIN_JAVA = "/src/main/java/";

	/**
	 * 作者
	 */
	private static final String AUTHOR = "Channing Kuo";

	/**
	 * 是否是 rest 接口
	 */
	private static final boolean REST_CONTROLLER_STYLE = true;

	private static final String JDBC_MYSQL_URL = "jdbc:mysql://localhost:3306/cea?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false";

	private static final String JDBC_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

	private static final String JDBC_USERNAME = "root";

	private static final String JDBC_PASSWORD = "Y5wroot";

	public static void main(String[] args) {
		String moduleName = scanner("模块名（类名）");
		String tableName = scanner("表名");
		autoGenerator(moduleName, tableName);
	}

	private static void autoGenerator(String moduleName, String tableName) {
		new AutoGenerator().setGlobalConfig(getGlobalConfig()).setDataSource(getDataSourceConfig()).setPackageInfo(getPackageConfig(moduleName))
				.setStrategy(getStrategyConfig(tableName)).setCfg(getInjectionConfig(moduleName)).setTemplate(getTemplateConfig()).execute();
	}

	private static String getDateTime() {
		LocalDateTime localDate = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return localDate.format(formatter);
	}

	private static InjectionConfig getInjectionConfig(String moduleName) {
		return new InjectionConfig() {
			@Override public void initMap() {
				Map<String, Object> map = Maps.newHashMap();
				map.put("dateTime", getDateTime());
				setMap(map);
				List<FileOutConfig> fileOutConfigList = new ArrayList<>();
				// 自定义配置会被优先输出
				fileOutConfigList.add(new FileOutConfig("/templates/controller.java.vm") {
					@Override public String outputFile(TableInfo tableInfo) {
						return PROJECT_DIR + SRC_MAIN_JAVA + "com/kuo/huahua/controller/" + moduleName + "Controller" + StringPool.DOT_JAVA;
					}
				});
				fileOutConfigList.add(new FileOutConfig("/templates/entity.java.vm") {
					@Override public String outputFile(TableInfo tableInfo) {
						return PROJECT_DIR + SRC_MAIN_JAVA + "com/kuo/huahua/entity/" + moduleName + StringPool.DOT_JAVA;
					}
				});
				fileOutConfigList.add(new FileOutConfig("/templates/mapper.java.vm") {
					@Override public String outputFile(TableInfo tableInfo) {
						return PROJECT_DIR + SRC_MAIN_JAVA + "com/kuo/huahua/mapper/" + moduleName + "Mapper" + StringPool.DOT_JAVA;
					}
				});
				fileOutConfigList.add(new FileOutConfig("/templates/mapper.xml.vm") {
					@Override public String outputFile(TableInfo tableInfo) {
						return PROJECT_DIR + "/src/main/resources/mapper/" + moduleName + "Mapper" + StringPool.DOT_XML;
					}
				});
				fileOutConfigList.add(new FileOutConfig("/templates/service.java.vm") {
					@Override public String outputFile(TableInfo tableInfo) {
						return PROJECT_DIR + SRC_MAIN_JAVA + "com/kuo/huahua/service/" + "I" + moduleName + "Service" + StringPool.DOT_JAVA;
					}
				});
				fileOutConfigList.add(new FileOutConfig("/templates/serviceImpl.java.vm") {
					@Override public String outputFile(TableInfo tableInfo) {
						return PROJECT_DIR + SRC_MAIN_JAVA + "com/kuo/huahua/service/impl/" + moduleName + "ServiceImpl" + StringPool.DOT_JAVA;
					}
				});
				setFileOutConfigList(fileOutConfigList);
			}
		};
	}

	private static StrategyConfig getStrategyConfig(String tableName) {
		return new StrategyConfig().setNaming(NamingStrategy.underline_to_camel).setColumnNaming(NamingStrategy.underline_to_camel)
				.setInclude(tableName).setRestControllerStyle(REST_CONTROLLER_STYLE).setEntityBuilderModel(true).setControllerMappingHyphenStyle(true)
				.setEntityTableFieldAnnotationEnable(true);
	}

	private static PackageConfig getPackageConfig(String moduleName) {
		return new PackageConfig().setParent(PARENT_NAME).setModuleName(moduleName);
	}

	private static DataSourceConfig getDataSourceConfig() {
		return new DataSourceConfig().setUrl(JDBC_MYSQL_URL).setDriverName(JDBC_DRIVER_NAME).setUsername(JDBC_USERNAME).setPassword(JDBC_PASSWORD);
	}

	private static GlobalConfig getGlobalConfig() {
		String filePath = PROJECT_DIR + "/";
		if (PlatformUtil.isWindows()) {
			filePath = filePath.replaceAll("/+|\\\\+", "\\\\");
		} else {
			filePath = filePath.replaceAll("/+|\\\\+", "/");
		}
		return new GlobalConfig().setOutputDir(filePath).setDateType(DateType.ONLY_DATE).setIdType(IdType.AUTO).setAuthor(AUTHOR)
				.setBaseColumnList(true)
				.setFileOverride("true".equalsIgnoreCase(scanner("是否覆盖原文件(否：任意内容，是：true)"))).setBaseResultMap(true)
				.setOpen(false);
	}

	private static TemplateConfig getTemplateConfig() {
		return new TemplateConfig()
				.setController("/templates/controller.java.vm")
				.setService("/templates/service.java.vm")
				.setServiceImpl("/templates/serviceImpl.java.vm")
				.setEntity("/templates/entity.java.vm")
				.setMapper("/templates/mapper.java.vm")
				.setXml("/templates/mapper.xml.vm");
	}

	private static String scanner(String tip) {
		Scanner scanner = new Scanner(System.in);
		System.out.println(("please input " + tip + " : "));
		if (scanner.hasNext()) {
			String ipt = scanner.next();
			if (StringUtils.isNotBlank(ipt)) {
				return ipt;
			}
		}
		throw new MybatisPlusException("please input the correct " + tip + ". ");
	}
}
