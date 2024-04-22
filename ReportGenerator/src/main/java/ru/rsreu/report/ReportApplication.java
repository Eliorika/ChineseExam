package ru.rsreu.report;

import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import ru.rsreu.kspbd.birt.ReportGenerator;


@SpringBootApplication
@Data
public class ReportApplication {


	public static void main(String[] args) {
		SpringApplication.run(ReportApplication.class, args);
	}


}
