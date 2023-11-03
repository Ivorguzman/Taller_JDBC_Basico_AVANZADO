package es.makigas.jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.sql.Connection;

public class EjemploMainConsultasBasicas
{

	private Connection conexion = null;


	// ____________ CONTRUCTOR:_____________
	public EjemploMainConsultasBasicas() throws SQLException
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




	private void consulta() throws SQLException
	{
		String consultaBasica = "SELECT  id_alumno,nombre,apellidos from alumnos";


		// FORMATO DE IMPRESION CONSULTA Basica
		Statement statement = this.conexion.createStatement();
		ResultSet set = statement.executeQuery(consultaBasica); // SE CREAN CONSULTRAS SIMPLES



		// FORMATO DE IMPRESION encabezados Basica
		System.out.printf("%s\t%s\t%s\n", "Id Alumno", "nombreAlumno", "apellidoAlumnos");



		System.out.printf("%s\n", "________________________________________________");




		while (set.next())
		{

			int idAlumno = set.getInt("id_alumno");
			String nombreAlunno = set.getString("nombre");
			String apellidoAlumno = set.getString("apellidos");
			System.out.printf("%5d\t%16s\t%10s\n", idAlumno, nombreAlunno, apellidoAlumno);


		}
		System.out.printf("%s\n", "__________________________________________________");
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
			new EjemploMainConsultasBasicas();
		} catch(SQLException ex)
		{
			ex.printStackTrace();
		}
	}


















	/*
	 * EJEMPLO DE CONECIÓN SENCILLA
	 * public static void main(String[] args)
	 * {
	 * Connection conexion = null;
	 * String url = "jdbc:mysql://localhost:3306/ejemplo";
	 * String usuario = "root";
	 * String clave = "";
	 * try // establecer conexion
	 * {
	 * conexion = DriverManager.getConnection(url, usuario, clave);
	 * System.out.println("Conección establecida");
	 * } catch(SQLException sql)
	 * {
	 * sql.printStackTrace();
	 * } finally
	 * {
	 * if (conexion != null)
	 * {
	 * try
	 * {
	 * conexion.close();
	 * } catch(SQLException sql)
	 * {
	 * sql.printStackTrace();
	 * }
	 * }
	 * }
	 * }
	 */


}
