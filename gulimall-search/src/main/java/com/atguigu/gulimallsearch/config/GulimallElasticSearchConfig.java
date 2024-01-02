package com.atguigu.gulimallsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.HttpAsyncResponseConsumerFactory;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
* 1.导入依赖
*
* 2.编写配置
*
* */
@Configuration
public class GulimallElasticSearchConfig {

    public static final RequestOptions COMON_OPTIONS;

    @Bean
    public RestHighLevelClient esRestClient(){
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("43.139.29.111", 9200, "http")
                ));

        return client;
    }

//    private static final RequestOptions COMON_OPTIONS;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
//        builder.addHeader("Authorization","Bearer " + TOKEN);
//        builder.setHttpAsyncResponseConsumerFactory(
//        new HttpAsyncResponseConsumerFactory
//                .HeapBufferedResponseConsumerFactory(30 * 1024* 1024 *1024));
        COMON_OPTIONS = builder.build();
    }

}
