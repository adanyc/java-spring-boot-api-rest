package com.adanyc.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adanyc.model.Empleado;
import com.adanyc.service.IEmpleadoService;

@Controller
public class EmpleadoController {

	@Autowired
	IEmpleadoService empleadoService;

	@RequestMapping(path = { "/index", "/" })
	public String index(Model model) {
		model.addAttribute("listaEmpleados", empleadoService.listarTodos());
		return "index";
	}

	@PostMapping(path = "/buscarPorNombre")
	public String buscarPorNombre(String nombre, Model model) {
		model.addAttribute("listaEmpleados", empleadoService.buscarPorNombre(nombre));
		return "index";
	}

	@PostMapping(path = "/insertar")
	public String insertar(Empleado empleado, Model model) {
		empleadoService.insertar(empleado);
		return "redirect:/";
	}

	@GetMapping(path = "/eliminar/{codigo}")
	public String eliminar(@PathVariable int codigo, Model model) {
		empleadoService.eliminar(codigo);
		return "redirect:/";
	}

	@GetMapping("/buscarPorCodigo/{codigo}")
	public String buscarPorCodigo(@PathVariable("codigo") int codigo, Model model) {
		if (codigo != 0) {
			Optional<Empleado> empleado = empleadoService.buscarPorCodigo(codigo);
			if (empleado.isPresent()) {
				model.addAttribute("empleado", empleado.get());
			}
		} else {
			model.addAttribute("empleado", new Empleado());
		}
		return "guardar";
	}

	@PostMapping("/guardar")
	public String guardar(Empleado empleado, Model model) {
		if (empleado.getCodigo() == 0) {
			empleadoService.insertar(empleado);
		} else {
			empleadoService.actualizar(empleado);
		}
		return "redirect:/";
	}

}
