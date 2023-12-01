package jdbc.ejemplos.ant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainStatement_try_with_resources_1
{
	private static Properties propiedadesBaseDeDatos()
	{

		// Clase Properties()
		Properties propiedad = new Properties();
		propiedad.setProperty("user", "root"); // (Clave, Valor)
		propiedad.setProperty("password", ""); // (Clave, Valor)
		return propiedad;

	}


	public static void main(String[] args)

	{
		/*
		 **************** METODO 1 conexion con : (try -with-resources) **********************
		 * La declaración try -with-resources es una declaración try que declara uno o más recursos .
		 * Y un recurso es un objeto que debe cerrarse una vez
		 * finalizado el programa. La declaración try -with-resources garantiza
		 * que cada recurso se cierre al final de la declaración.
		 * // conectando con try-with-resources
		 * try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:/ejemplo", "root", ""))
		 * {
		 * System.out.println("Conexion establecida");
		 * } catch(SQLException ex)
		 * {
		 * ex.printStackTrace();
		 * }
		 */




		// **** METODO 2 conexion con (try -with-resources), (Properties) y SQLException con un logger ****

		Properties propiedades = propiedadesBaseDeDatos();
		var sql = "Select * from alumnos";
		try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:/escuela", propiedades);
				Statement stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery(sql))
		{
			// ************* INICIO CONSULTAS META-DATA BASE DE DATOS **************
			System.out.println("+++++++++++ Conexion establecida +++++++++++++++++");
			System.out.println("");
			System.out.println("________META-DATA  BASE DE DATOS___________ ");
			System.out.println("hashCode conexion : " + c.hashCode());
			System.out.println("Tipo  de base de datos : " + c.getMetaData().getDatabaseProductName());
			System.out.println("Driver de la Base de datos : " + c.getMetaData().getDriverName());
			System.out.println("Version de la Base de datos : " + c.getMetaData().getDatabaseMajorVersion());
			System.out.println("Nombre de la Base de datos: " + c.getCatalog());
			System.out.print("Consulta SQL: ");
			System.out.println(sql);
			System.out.println("");
			System.out.println("");

					while (rs.next())
					{
						// System.out.println("resultado " + contador);
						/*
						 * System.out.print(rs.getInt("id_alumno") + " ");
						 * System.out.print(rs.getString("nombre") + " ");
						 * System.out.print(rs.getString("apellidos") + " ");
						 * System.out.println(rs.getDate("fecha_nac"));
						 */
						


						/*
						 * int id= rs.getInt("id_alumno");
						 * String nombre = rs.getString("nombre");
						 * var apellido =rs.getString("apellidos");
						 * var fecha =rs.getDate("fecha_nac");
						 */


						int id = rs.getInt(1);
						String nombre = rs.getString(2);
						var apellido = rs.getString(3);
						var fecha = rs.getDate(4);

						System.out.println("Resultado :" + id + " | " + nombre + " | " + fecha);
					}
				} catch(SQLException ex)
				{
					Logger.getLogger(MainStatement_try_with_resources_1.class.getName()).log(Level.SEVERE, null, ex); // estudiar
				}
			}

	}


				
			
		


















