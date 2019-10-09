package com.adanyc.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.adanyc.model.Empleado;
import com.adanyc.service.IEmpleadoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping(value = "/empleados/api/v1/")
public class EmpleadoRestController {
	@Autowired
	private IEmpleadoService empleadoService;

	// ======
	// LISTAR
	// ======
	// 1ra forma
	@RequestMapping(value = "/listarTodos")
	public ResponseEntity<List<Empleado>> listarTodos() {
		List<Empleado> lista = empleadoService.listarTodos();
		return new ResponseEntity<List<Empleado>>(lista, HttpStatus.OK);
	}

	@RequestMapping(path = "/listarTodosXML", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Empleado>> listarTodosXML() {
		List<Empleado> lista = empleadoService.listarTodos();
		return new ResponseEntity<List<Empleado>>(lista, HttpStatus.OK);
	}

	// 2da forma
	@RequestMapping(value = "/listarTodos2")
	public ResponseEntity<String> listarTodos2() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode onResult = objectMapper.createObjectNode();
		Integer codigoError = 0;
		String mensajeError = "Operacion exitosa";

		try {
			List<Empleado> listaEmpleados = empleadoService.listarTodos();

			// 1ra forma: convertir la lista a array
			// =====================================
			onResult.putPOJO("empleados", listaEmpleados.toArray());

			// 1da forma: recorrer la lista e ir agregando al array
			// ====================================================
			// ArrayNode anEmpleado = objectMapper.createArrayNode();
			// for (Empleado objEmpleado : listaEmpleados) {
			// ObjectNode onEmpleado = objectMapper.createObjectNode();
			// onEmpleado.put("primerNombre", objEmpleado.getPrimerNombre());
			// onEmpleado.put("segundoNombre", objEmpleado.getSegundoNombre());
			// onEmpleado.put("apellidoPaterno", objEmpleado.getApellidoPaterno());
			// onEmpleado.put("apellidoMaterno", objEmpleado.getApellidoMaterno());
			// onEmpleado.put("sexo", objEmpleado.getSexo());
			// anEmpleado.add(onEmpleado);
			// }

		} catch (Exception e) {
			codigoError = 1;
			mensajeError = "" + e;
		}

		ObjectNode onMensaje = objectMapper.createObjectNode();
		onMensaje.put("codigo", codigoError);
		onMensaje.put("mensaje", mensajeError);
		onResult.putPOJO("objMensaje", onMensaje);

		ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
		String strJsonResult = objectWriter.writeValueAsString(onResult);

		return new ResponseEntity<String>(strJsonResult, HttpStatus.OK);
	}

	// 3ra forma
	@RequestMapping(value = "/listarTodos3")
	public List<Empleado> listarTodos3() {
		try {
			return empleadoService.listarTodos();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio un error", e);
		}
	}

	// 4ta forma
	@RequestMapping(value = "/listarTodos4")
	public List<Empleado> listarTodos4() {
		return empleadoService.listarTodos();
	}

	// ======
	// BUSCAR
	// ======
	@GetMapping("/buscarPorCodigo/{codigo}")
	public ResponseEntity<Empleado> buscarPorCodigo(@PathVariable(value = "codigo") int codigo) {
		// 1ra forma
		// =========
		Optional<Empleado> optEmpleado = empleadoService.buscarPorCodigo(codigo);
		if (optEmpleado.isPresent()) {
			return ResponseEntity.ok(optEmpleado.get());
			// return new ResponseEntity<Empleado>(optEmpleado.get(), HttpStatus.OK); //
			// otra forma
		} else {
			return ResponseEntity.notFound().build();
			// return new ResponseEntity<Empleado>(HttpStatus.NOT_FOUND); // otra forma
		}

		// 2da forma: Haciendo uso de un Recurso
		// =====================================
		// return empleadoService.buscarPorCodigo(codigo).orElseThrow(() -> new
		// ResourceNotFoundException("Empleado", "codigo", codigo));

		// 3ra forma: Devolviendo un objeto vacio
		// ======================================
		// ObjectMapper objectMapper = new ObjectMapper(); ObjectNode onResult =
		// objectMapper.createObjectNode(); Optional<Empleado> optEmpleado =
		// empleadoService.buscarPorCodigo(codigo); if (optEmpleado.isPresent()) {
		// onResult.putPOJO("empleado", optEmpleado.get()); } else {
		// onResult.putPOJO("empleado", objectMapper.createObjectNode()); } ObjectWriter
		// objectWriter = objectMapper.writerWithDefaultPrettyPrinter(); String
		// strJsonResult = objectWriter.writeValueAsString(onResult); return new
		// ResponseEntity<String>(strJsonResult, HttpStatus.OK);
	}

	//
	// =============================================================

	@PostMapping(path = "/buscarPorNombre")
	public ResponseEntity<List<Empleado>> buscarPorNombre(@RequestBody String jsonEntrada) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jnJsonEntrada = objectMapper.readTree(jsonEntrada);
		String nombre = jnJsonEntrada.path("nombre").asText();
		return ResponseEntity.ok(empleadoService.buscarPorNombre(nombre));
	}

	@PostMapping(path = "/insertar")
	public ResponseEntity<Integer> insertar(@RequestBody Empleado empleado) {
		int result = empleadoService.insertar(empleado);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping(path = "/actualizar")
	public ResponseEntity<Integer> actualizar(@RequestBody Empleado empleado) {
		int result = empleadoService.actualizar(empleado);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(value = "/eliminar/{codigo}")
	public ResponseEntity<Integer> eliminar(@PathVariable int codigo) {
		Optional<Empleado> optEmpleado = empleadoService.buscarPorCodigo(codigo);
		int result = 0; // 0=okay, 1=error
		if (optEmpleado.isPresent()) {
			result = empleadoService.eliminar(codigo);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(1, HttpStatus.NOT_FOUND);
		}
	}

}
