package pet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan("pet.dao")
public class PetApplication {

	/**
	 * 程序启动入口
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(PetApplication.class, args);
	}
}
