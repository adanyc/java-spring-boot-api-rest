package com.adanyc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApiRestApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApiRestApplication.class, args);
	}
}

// ==================
// Pruebas en Consola
// ==================
/*
 * @SpringBootApplication public class DemoApiRestApplication implements
 * CommandLineRunner {
 * 
 * private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
 * 
 * @Autowired private EmpleadoRepository empleadoRepository;
 * 
 * public static void main(String[] args) {
 * SpringApplication.run(DemoApiRestApplication.class, args); }
 * 
 * @Override public void run(String... args) throws Exception { List<Empleado>
 * lista = empleadoRepository.findAll(); for (Empleado empleado : lista) {
 * LOGGER.info(empleado.getPrimerNombre()); } } }
 */
