package com.atguigu.gulimall.ware.config;
//import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement//开启事务
//@MapperScan("com.atguigu.gulimall.ware")
public class MybatisConfig {
    /**
     * 引入分页插件
     */
//    @Bean
//    public PaginationInterceptor getPagination(){
//        PaginationInterceptor paginationInterceptor
//                =new PaginationInterceptor();
//        //设置请求的页面大于最大页操作 true 跳回首页 false据徐请求 默认 false
//        //paginationInterceptor.setOverFlow(false);
//        paginationInterceptor.setOverflow(true);
//        //设置最大单页限制数 500 -1不受限制
//        //paginationInterceptor.setLimit(500);
//        paginationInterceptor.setLimit(100);
//        return paginationInterceptor;
//    }
//
    /**
     * 3.4X版本之后
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {

        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
// 设置请求的页面大于最大页操作 true 跳回首页 false 继续请求 默认 false
        paginationInnerInterceptor.setOverflow(true);
// 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInnerInterceptor.setMaxLimit(100L);

        mybatisPlusInterceptor.addInnerInterceptor(paginationInnerInterceptor);

        return mybatisPlusInterceptor;
    }
}
