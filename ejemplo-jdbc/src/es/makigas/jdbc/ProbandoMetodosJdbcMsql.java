package es.makigas.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.sun.tools.javac.Main;

public class ProbandoMetodosJdbcMsql
{

	Connection conexion;

	public ProbandoMetodosJdbcMsql() throws SQLException
	{
		try
		{
			this.conectar();
			this.consulta();
		} finally
		{

		}

		/*
		 * // Metodo 1 para saver los drivers Instalados
		 * DriverManager.drivers().forEach(driver-> System.out.println(driver.toString()));
		 * // Metodo 2 para saver los drivers Instalados
		 * for (Driver d : DriverManager.drivers().toList())
		 * {
		 * System.out.println(d.toString());
		 * }
		 */

		/*
		 * System.out.print(this.conexion.getMetaData().getDatabaseProductName() + " ");
		 * System.out.println(this.conexion.getMetaData().getDatabaseMajorVersion());
		 * System.out.println(this.conexion.getMetaData().getDatabaseMinorVersion());
		 * System.out.println(this.conexion.getMetaData().getDriverName());
		 * System.out.println(this.conexion.getMetaData().getDefaultTransactionIsolation());
		 * System.out.println(this.conexion.getMetaData().getCatalogSeparator());
		 * System.out.println(this.conexion.getMetaData().getCatalogTerm());
		 * System.out.println(this.conexion.getClass());
		 * System.out.println(this.conexion.getAutoCommit());
		 * System.out.println(this.conexion.getClientInfo());
		 * System.out.println(this.conexion.getSchema());
		 * System.out.println(this.conexion.getNetworkTimeout());
		 * System.out.println(this.conexion.getNetworkTimeout());
		 * System.out.println(this.conexion.getCatalog()); // ejemplo
		 */




	}// ____________ FIN CONTRUCTOR:_____________


	private void conectar()
	{
		String url = "jdbc:mysql://localhost/ejemplo";
		String usuario = "root";
		String clave = "";

		try
		{
			this.conexion = DriverManager.getConnection(url, usuario, clave);
			System.out.println("************ Conección establecida *************");
			System.out.println(this.conexion.getClass());
			System.out.print(this.conexion.getMetaData().getDatabaseProductName() + " ");
			System.out.println(this.conexion.getMetaData().getDatabaseMajorVersion());
			System.out.println(this.conexion.getMetaData().getDriverName());
			System.out.println("AutoCommit: " + this.conexion.getAutoCommit());
			System.out.println("Nombre Base de Datos: " + this.conexion.getCatalog());
			System.out.println("**************************************************");
			System.out.println("");
		} catch(SQLException ex)
		{
			System.out.println("Fallo conexion");
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);

		}

	}


	private void consulta() throws SQLException
	{
		String consulta1 = "SELECT * from alumnos ;";
		try
		{
			// FORMATO DE IMPRESION CONSULTA Basica
			Statement sentencia = this.conexion.createStatement();// java.sql
			ResultSet conjuntoResultados = sentencia.executeQuery(consulta1);// estructura de datos tipo cursor
			System.out.println("conjuntoResultados===>" + conjuntoResultados);



			// FORMATO DE IMPRESION ENCABEZADO Basica
			System.out.printf("%s\t%s\t%s\t%s\n", "Id Alumno", "nombreAlumno", "apellidoAlumnos", "fechaNacimiento");
			System.out.printf("%s\n", "______________________________________________________________");

			while (conjuntoResultados.next())
			{

				int idAlumno = conjuntoResultados.getInt("id_alumno");
				String nombreAlunno = conjuntoResultados.getString("nombre");
				String apellidoAlumno = conjuntoResultados.getString("apellidos");
				Date nacimientoAlumno = conjuntoResultados.getDate("fecha_nac");
				System.out.printf("%5d\t%16s\t%10s\t %s\n", idAlumno, nombreAlunno, apellidoAlumno, nacimientoAlumno);
			}
			System.out.printf("%s\n", "________________________________________________________________");
			conjuntoResultados.close();
			sentencia.close();
		} finally
		{
			this.conexion.close();
		}



	}

	public static void main(String[] args) throws SQLException
	{
		new ProbandoMetodosJdbcMsql();
	}


}
