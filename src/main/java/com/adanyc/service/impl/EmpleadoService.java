package com.adanyc.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adanyc.model.Empleado;
import com.adanyc.repository.EmpleadoRepository;
import com.adanyc.service.IEmpleadoService;

@Service
public class EmpleadoService implements IEmpleadoService {

	@Autowired
	EmpleadoRepository empleadoRepository;

	@Override
	public List<Empleado> listarTodos() {
		return empleadoRepository.listarTodos();
	}

	@Override
	public Optional<Empleado> buscarPorCodigo(int codigo) {
		return empleadoRepository.buscarPorCodigo(codigo);
	}

	@Override
	public int insertar(Empleado empleado) {
		return empleadoRepository.insertar(empleado);
	}

	@Override
	public int eliminar(int codigo) {
		return empleadoRepository.eliminar(codigo);
	}

	@Override
	public int actualizar(Empleado empleado) {
		return empleadoRepository.actualizar(empleado);
	}

	@Override
	public List<Empleado> buscarPorNombre(String nombre) {
		return empleadoRepository.buscarPorNombre(nombre);
	}

}
