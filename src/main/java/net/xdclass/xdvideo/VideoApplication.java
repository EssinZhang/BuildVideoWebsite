package net.xdclass.xdvideo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("net.xdclass.xdvideo.mapper")
public class VideoApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoApplication.class, args);
	}
}
