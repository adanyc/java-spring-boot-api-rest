package com.adanyc.service;

import java.util.List;
import java.util.Optional;

import com.adanyc.model.Empleado;

public interface IEmpleadoService {
	public abstract List<Empleado> listarTodos();

	public abstract List<Empleado> buscarPorNombre(String nombre);

	public abstract Optional<Empleado> buscarPorCodigo(int codigo);

	public abstract int insertar(Empleado empleado);

	public abstract int eliminar(int codigo);

	public abstract int actualizar(Empleado empleado);
}
