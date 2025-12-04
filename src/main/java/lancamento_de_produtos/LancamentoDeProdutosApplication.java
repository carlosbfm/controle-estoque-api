package lancamento_de_produtos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LancamentoDeProdutosApplication {

	public static void main(String[] args) {
		SpringApplication.run(LancamentoDeProdutosApplication.class, args);
	}

}
