package es.makigas.jdbc;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EjemploMainConsultasComplejas
{
	private Connection conexion = null;

	public EjemploMainConsultasComplejas() throws SQLException
	{
		try
		{
			this.conectar();
			this.consulta();

		} finally
		{

			this.cerra();
		}
	}// ____________ FIN CONTRUCTOR:_____________

	private void conectar() throws SQLException
	{
		String url = "jdbc:mysql://localhost:3306/ejemplo";
		String usuario = "root";
		String clave = "";
		this.conexion = DriverManager.getConnection(url, usuario, clave);
		System.out.println("Conección establecida");

	}

	/*
	 * -- SELECT nombreDeColumna1, nombreDeColumna2, …n
	 * -- FROM tabla1
	 * -- INNER JOIN tabla2
	 * -- WHERE tabla1.nombreDeColumna = tabla2.nombreDeColumna
	 */

	private void consulta() throws SQLException
	{
		String consultaJoin = "SELECT asignaturas.id_asignatura, asignaturas.nombre , profesores.nombre, profesores.apellidos \r\n"
				+ "  FROM asignaturas,profesores\r\n" + "  WHERE  asignaturas.profesor=profesores.id_profesor;";


		Statement statement = this.conexion.createStatement();
		ResultSet set = statement.executeQuery(consultaJoin); // SE CREAN CONSULTRAS con Join




		System.out.printf("%s\n",
				"________________________________________________________________________________________________");
		System.out.printf("%8s\t%16s\t%24s\t%20s\n", "Id Asignatura", "Asignatura", "Nombre Profesor",
				"Apellido Profesor");
		System.out.printf("%s\n",
				"________________________________________________________________________________________________");


		while (set.next())
		{
			// FORMATO DE IMPRESION CONSULTA 2
			int Id_asignatura = set.getInt("asignaturas.id_asignatura");
			String asignatura = set.getString("asignaturas.nombre");
			String nombreProfesor = set.getString("profesores.nombre");
			String apellidoProfesor = set.getString("profesores.apellidos");
			System.out.printf("%5d\t%30s\t%18s\t%23s\n", Id_asignatura, asignatura, nombreProfesor, apellidoProfesor);
		}
		System.out.printf("%s\n",
				"________________________________________________________________________________________________");
		set.close();
		statement.close();
	}

	private void cerra() throws SQLException
	{
		if (this.conexion != null)
		{
			this.conexion.close();
		}
	}

	public static void main(String[] args)
	{
		try
		{
			new EjemploMainConsultasComplejas();
		} catch(SQLException ex)
		{
			ex.printStackTrace();
		}
	}

}
