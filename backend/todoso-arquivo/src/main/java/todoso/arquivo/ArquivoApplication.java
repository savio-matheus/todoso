package todoso.arquivo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import todoso.arquivo.servico.ArquivoPropriedades;
import todoso.arquivo.servico.ArquivoServico;

@SpringBootApplication
@EnableConfigurationProperties(ArquivoPropriedades.class)
public class ArquivoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArquivoApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedOrigins("*")
					.allowedMethods("GET", "POST","PUT", "PATCH", "DELETE");
			}
		};
	}
	
	@Bean
	CommandLineRunner init(ArquivoServico arquivoServico) {
		return (args) -> {
			arquivoServico.iniciar();
		};
	}

}
