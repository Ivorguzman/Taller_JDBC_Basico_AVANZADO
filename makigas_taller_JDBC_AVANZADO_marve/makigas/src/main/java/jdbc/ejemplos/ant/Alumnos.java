package jdbc.ejemplos.ant;

import java.sql.Date;

public class Alumnos
{

	private String nombre;
	private String apellido;
	private String fec_nacimiento;


	public Alumnos(String nombre, String apellido, String fec_nacimiento)
	{
		// this.setNombre(nombre); // Otra forma
		this.nombre = nombre;
		this.apellido = apellido;
		this.fec_nacimiento = fec_nacimiento;
	}



	public String getNombre() {
		return this.nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getApellido() {
		return this.apellido;
	}



	public void setApellido(String apellido) {
		this.apellido = apellido;
	}



	public String getFec_nacimiento() {
		return this.fec_nacimiento;
	}



	public void setFec_nacimiento(String fec_nacimiento) {
		this.fec_nacimiento = fec_nacimiento;
	}

}


