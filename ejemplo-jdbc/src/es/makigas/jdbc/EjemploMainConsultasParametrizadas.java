package es.makigas.jdbc;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverAction;

public class EjemploMainConsultasParametrizadas
{


	private Connection conexion = null;


	// ____________ CONTRUCTOR:_____________
	public EjemploMainConsultasParametrizadas() throws SQLException
	{
		try
		{
			this.conectar();
			this.transaccion();
			this.consulta();
		} finally
		{

			this.cerra();
		}
	}// ____________ FIN CONTRUCTOR:_____________




	private void conectar() throws SQLException
	{
		String url = "jdbc:mysql://localhost/ejemplo";
		String usuario = "root";
		String clave = "";
		this.conexion = DriverManager.getConnection(url, usuario, clave);
		this.conexion.setAutoCommit(false); // Indicando a JDBC que se ponga en modo trasacciónes
		System.out.println("Conección establecida");

	}

	private void transaccion() throws SQLException
	{
		final String PROFESOR = "INSERT INTO  profesores(id_profesor, nombre, apellidos) VALUES (?,?,?)";
		final String ASIGNATURA = "INSERT INTOd asignaturas(id_asignatura,nombre,profesor) VALUES(?,?,?);";
		PreparedStatement profesor = null;
		PreparedStatement asignsturas = null;

		try
		{
			profesor = this.conexion.prepareStatement(PROFESOR);
			profesor.setInt(1, 6);
			profesor.setString(2, "Juan");
			profesor.setString(3, "Binba");
			profesor.executeUpdate();

			asignsturas = this.conexion.prepareStatement(ASIGNATURA);
			asignsturas.setInt(1, 11);
			asignsturas.setString(2, "Teoria de Grafos");
			asignsturas.setInt(3, 5);
			asignsturas.executeUpdate();

			this.conexion.commit();
			System.out.println("commit ejecutado");


		} catch(SQLException ex)
		{
			this.conexion.rollback();
			System.out.println("rollback ejecutado");

			ex.printStackTrace();

		} finally
		{
			if (profesor != null)
			{
				profesor.close();
			}
			if (asignsturas != null)
			{
				asignsturas.close();
			}
		}

	}


	private void consulta() throws SQLException
	{
		String consultaJoin2 = "SELECT asignaturas.id_asignatura, asignaturas.nombre , profesores.nombre, profesores.apellidos,profesores.id_profesor  \r\n"
				+ "  FROM asignaturas,profesores WHERE id_profesor= 10";

		/*
		 * String consultaJoin = "SELECT asignaturas.id_asignatura, asignaturas.nombre , profesores.nombre, profesores.apellidos,profesores.id_profesor \r\n"
		 * + "  FROM asignaturas,profesores\r\n" + "  WHERE  asignaturas.profesor=profesores.id_profesor;";
		 */

		Statement statement = this.conexion.createStatement();
		ResultSet set = statement.executeQuery(consultaJoin2); // SE CREAN CONSULTRAS con Join




		System.out.printf("%s\n",
				"_________________________________________________________________________________________________________________");
		System.out.printf("%8s\t%16s\t%24s\t%20s\t%10s\n", "Id Asignatura", "Asignatura", "Nombre Profesor",
				"Apellido Profesor", "Id profesor");
		System.out.printf("%s\n",
				"_________________________________________________________________________________________________________________");


		while (set.next())
		{
			// FORMATO DE IMPRESION CONSULTA 2
			int id_asignatura = set.getInt("asignaturas.id_asignatura");
			String asignatura = set.getString("asignaturas.nombre");
			
			String nombreProfesor = set.getString("profesores.nombre");
			String apellidoProfesor = set.getString("profesores.apellidos");
			int id_profesor = set.getInt("profesores.id_profesor");
			
			System.out.printf("%5d\t%30s\t%18s\t%23s\t%15d\n",
					id_asignatura, asignatura, nombreProfesor,
					apellidoProfesor, id_profesor);
		}
		System.out.printf("%s\n",
				"__________________________________________________________________________________________________________________");
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
			new EjemploMainConsultasParametrizadas();
		} catch(SQLException ex)
		{
			ex.printStackTrace();
		}
	}




}

