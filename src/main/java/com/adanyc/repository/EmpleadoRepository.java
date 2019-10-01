package com.adanyc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.adanyc.model.Empleado;

@Repository
public class EmpleadoRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	class EmpleadoRowMapper implements RowMapper<Empleado> {
		@Override
		public Empleado mapRow(ResultSet rs, int rowNum) throws SQLException {
			Empleado empleado = new Empleado();
			empleado.setCodigo(rs.getInt("codigo"));
			empleado.setPrimerNombre(rs.getString("primer_nombre"));
			empleado.setSegundoNombre(rs.getString("segundo_nombre"));
			empleado.setApellidoPaterno(rs.getString("apellido_paterno"));
			empleado.setApellidoMaterno(rs.getString("apellido_materno"));
			empleado.setSexo(rs.getString("sexo"));
			return empleado;
		}
	}

	public List<Empleado> listarTodos() {
		String query = "SELECT codigo,primer_nombre,segundo_nombre,apellido_paterno,apellido_materno,sexo FROM empleado";
		List<Empleado> lista = jdbcTemplate.query(query, new EmpleadoRowMapper());
		return lista;
	}

	public List<Empleado> buscarPorNombre(String nombre) {
		String query = "SELECT * FROM empleado WHERE UPPER(TRIM(primer_nombre)) LIKE UPPER(TRIM('" + nombre + "%'))";
		List<Empleado> lista = jdbcTemplate.query(query, new EmpleadoRowMapper());
		return lista;
	}

	public Optional<Empleado> buscarPorCodigo(int codigo) {
		String query = "SELECT * FROM empleado WHERE codigo=?";
		try {
			return jdbcTemplate.queryForObject(query, new Object[] { codigo },
					(rs, rowNum) -> Optional.of(new Empleado(rs.getInt("codigo"), rs.getString("primer_nombre"),
							rs.getString("segundo_nombre"), rs.getString("apellido_paterno"),
							rs.getString("apellido_materno"), rs.getString("sexo"))));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public int insertar(Empleado empleado) {
		String query = "INSERT INTO empleado (primer_nombre, segundo_nombre, apellido_paterno, apellido_materno, sexo) VALUES (?, ?, ?, ?, ?)";
		return jdbcTemplate.update(query, new Object[] { empleado.getPrimerNombre(), empleado.getSegundoNombre(),
				empleado.getApellidoPaterno(), empleado.getApellidoMaterno(), empleado.getSexo() });
	}

	public int eliminar(int codigo) {
		return jdbcTemplate.update("DELETE FROM empleado WHERE codigo=?", new Object[] { codigo });
	}

	public int actualizar(Empleado empleado) {
		String query = "UPDATE empleado SET primer_nombre = ?, segundo_nombre = ?, apellido_paterno = ?, apellido_materno = ?, sexo=? WHERE codigo = ?";
		return jdbcTemplate.update(query,
				new Object[] { empleado.getPrimerNombre(), empleado.getSegundoNombre(), empleado.getApellidoPaterno(),
						empleado.getApellidoMaterno(), empleado.getSexo(), empleado.getCodigo() });
	}
}
