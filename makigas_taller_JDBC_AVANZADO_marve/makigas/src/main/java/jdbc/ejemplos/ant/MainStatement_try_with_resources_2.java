package jdbc.ejemplos.ant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainStatement_try_with_resources_2
{

	public static void main(String[] args)
	{
		// conneccion("'1993-02-18'");
		conneccion("'1993%'"); // Para generar consultas co la clausula LIKE de mysql

	}


	private static Properties propiedadesBaseDeDatos()
	{
		// Clase Properties()
		Properties propiedadesCredenciales = new Properties();
		propiedadesCredenciales.setProperty("user", "root"); // (Clave, Valor)
		propiedadesCredenciales.setProperty("password", ""); // (Clave, Valor)
		return propiedadesCredenciales;

	};




	// public static void conneccion(String anio) // no es correcto permite inyecciones SQL
	public static void conneccion(String anio) // no es correcto permite inyecciones SQL


	{
		String controladorHostBBDD = "jdbc:mysql://localhost/ejemplo";
		Properties propiedadesConeccion = propiedadesBaseDeDatos();


		// String cSql = "Select * from alumnos where fecha_nac LIKE '1993%'";
		String cSql = "Select * from alumnos where fecha_nac LIKE " + anio;
		// String cSql = "Select * from alumnos where fecha_nac = " + anio;

		try (Connection conn = DriverManager.getConnection(controladorHostBBDD, propiedadesConeccion);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(cSql)) // (try -with-resources)

		{

			System.out.println("+++++++++++ Conexion establecida +++++++++++++++++");
			System.out.println("");

			System.out.println("hashCode conexion : " + conn.hashCode());
			System.out.println("Tipo  de base de datos : " + conn.getMetaData().getDatabaseProductName());
			System.out.println("Driver de la Base de datos : " + conn.getMetaData().getDriverName());
			System.out.println("Version de la Base de datos : " + conn.getMetaData().getDatabaseMajorVersion());
			System.out.println("Nombre de la Base de datos: " + conn.getCatalog());
			System.out.println("*****************************************************************");
			System.out.println("");
			System.out.println("");
			System.out.print("   Consulta SQL: ");
			System.out.println(cSql);
			System.out.println("|_____________________ Resultado _____________________________________|");
			while (rs.next())
			{
				// System.out.println("resultado " + contador);


				System.out.print(rs.getInt("id_alumno") + " ");
				System.out.print(rs.getString("nombre") + " ");
				System.out.print(rs.getString("apellidos") + " ");
				System.out.println(rs.getDate("fecha_nac").toLocalDate());

				/*
				 * int id= rs.getInt("id_alumno");
				 * String nombre = rs.getString("nombre");
				 * var apellido =rs.getString("apellidos");
				 * var fecha =rs.getDate("fecha_nac");
				 */


				/*
				 * int id = rs.getInt(1);
				 * String nombre = rs.getString(2);
				 * var apellido = rs.getString(3);
				 * var fecha = rs.getDate(4);
				 * System.out.println("Resultado :" + id + " | " + nombre + " | " + fecha);
				 */

			}
		} catch(SQLException ex)
		{
			Logger.getLogger(MainStatement_try_with_resources_2.class.getName()).log(Level.SEVERE, null, ex); // estudiar
		}

	}
}

