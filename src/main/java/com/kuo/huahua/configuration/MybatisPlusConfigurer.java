//package com.kuo.huahua.configuration;
//
//import com.baomidou.mybatisplus.annotation.DbType;
//import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
//import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.mapper.MapperScannerConfigurer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.core.io.support.ResourcePatternResolver;
//
//import javax.sql.DataSource;
//
///**
// * @author Channing Kuo
// * @date 2021/06/28
// */
//@Configuration public class MybatisPlusConfigurer {
//
//	@Bean public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws Exception {
//		MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
//		factoryBean.setDataSource(dataSource);
//		factoryBean.setTypeAliasesPackage("com.kuo.huahua.entity");
//		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//		factoryBean.setMapperLocations(resolver.getResources("classpath*:/mapper/**/*.xml"));
//		return factoryBean.getObject();
//	}
//
//	@Bean public MapperScannerConfigurer mapperScannerConfigurer() {
//		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
//		mapperScannerConfigurer.setBasePackage("com.kuo.huahua.mapper");
//		return mapperScannerConfigurer;
//	}
//
//	@Bean public org.apache.ibatis.session.Configuration getConfiguration() {
//		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
//		// 返回集映射Map时，null值不忽略
//		configuration.setCallSettersOnNulls(true);
//		return configuration;
//	}
//
//	@Bean public MybatisPlusInterceptor mybatisPlusInterceptor() {
//		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//		PaginationInnerInterceptor innerInterceptor = new PaginationInnerInterceptor();
//		innerInterceptor.setDbType(DbType.MYSQL);
//		innerInterceptor.setOverflow(true);
//		interceptor.addInnerInterceptor(innerInterceptor);
//		// 防止全表更新与删除
//		interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
//		return interceptor;
//	}
//
//	//	@Bean
//	//	public ConfigurationCustomizer configurationCustomizer() {
//	//		return mybatisConfiguration -> mybatisConfiguration.setUseDeprecatedExecutor(false);
//	//	}
//
//	//	@Bean
//	//	public String sqlInterceptor(SqlSessionFactory sqlSessionFactory) {
//	//		SqlAuthenticationInterceptor sqlAuthenticationInterceptor = new SqlAuthenticationInterceptor();
//	//		sqlSessionFactory.getConfiguration().addInterceptor(sqlAuthenticationInterceptor);
//	//		return "interceptor";
//	//	}
//}
