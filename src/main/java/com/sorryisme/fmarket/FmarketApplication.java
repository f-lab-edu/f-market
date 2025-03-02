package com.sorryisme.fmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.config.EnableElasticsearchAuditing;

@SpringBootApplication
public class FmarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(FmarketApplication.class, args);
    }

}
