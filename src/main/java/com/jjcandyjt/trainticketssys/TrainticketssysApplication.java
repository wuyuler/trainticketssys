package com.jjcandyjt.trainticketssys;

import com.jjcandyjt.trainticketssys.Utils.Alg;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TrainticketssysApplication {

    public static void main(String[] args) {
        Alg.init();
        SpringApplication.run(TrainticketssysApplication.class, args);
    }
}
