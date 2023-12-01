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

public class MainPrepareStatement_try_with_resources {
	private static Properties propiedadesBaseDeDatos() {
		// Clase Properties()
		Properties propiedadesCredenciales = new Properties();
		propiedadesCredenciales.setProperty("user", "root"); // (Clave, Valor)
		propiedadesCredenciales.setProperty("password", ""); // (Clave, Valor)
		return propiedadesCredenciales;
	};


	public static void main(String[] args) {
		// conneccion("%1993%"); // Para generar consultas co la clausula LIKE de mysql
		// conneccion("%19_%", "%d_%"); // Para generar consultas co la clausula LIKE de mysql
		// conneccion("%19_%", "%_%a", "%_%z"); // Para generar consultas co la clausula LIKE de mysql
		conneccion("%19_%", "%_%el", "%_%z"); // Para generar consultas co la clausula LIKE de mysql
		// conneccion(1992); // Para generar consultas co la clausula LIKE de mysql
		// conneccion("1992-11-13"); // Para generar consultas cob la clausula LIKE de mysql
		// conneccion(); // Para generar consultas cob la clausula LIKE de mysql
	}



	// public static void conneccion() // no es correcto permite inyecciones SQL
	public static void conneccion(String anio, String name, String name2) // no es correcto permite inyecciones SQL
	{
		Properties propiedadesConeccion = propiedadesBaseDeDatos();
		String controladorHostBBDD = "jdbc:mysql://localhost/ejemplo";

		try (Connection conn = DriverManager.getConnection(controladorHostBBDD, propiedadesConeccion)){
			// String sql = "SELECT * FROM alumnos WHERE fecha_nac LIKE ?";
			// String sql = "SELECT * FROM alumnos WHERE fecha_nac LIKE ? and nombre like ?";
			String sql = "SELECT * FROM alumnos WHERE fecha_nac LIKE ? and nombre like ? and apellidos like ?";


			System.out.println("+++++++++++ Conexion establecida +++++++++++++++++");
			System.out.println("");

			// ************* INICIO CONSULTAS META-DATA BASE DE DATOS **************
			System.out.println("________META-DATA  BASE DE DATOS___________ ");
			System.out.println("hashCode conexion : " + conn.hashCode());
			System.out.println("Tipo  de base de datos : " + conn.getMetaData().getDatabaseProductName());
			System.out.println("Driver de la Base de datos : " + conn.getMetaData().getDriverName());
			System.out.println("Version de la Base de datos : " + conn.getMetaData().getDatabaseMajorVersion());
			System.out.println("Nombre de la Base de datos: " + conn.getCatalog());
			System.out.println("*****************************************************************");
			System.out.println("");
			System.out.println("");
			try (PreparedStatement ps = conn.prepareStatement(sql);){
				System.out.print("Consulta SQL: ");
				System.out.println(sql);

				// String anio = "1992-11-13";
				// anio = "%1993%";
				ps.setString(1, anio);
				ps.setString(2, name);
				ps.setString(3, name2);

				try (ResultSet rs = ps.executeQuery()){
					System.out.println("|_____________________ Resultado _____________________________________|");
					while (rs.next()){
						// System.out.println("resultado " + contador);
						int id = rs.getInt("id_alumno");
						String nombre = rs.getString("nombre");
						var apellido = rs.getString("apellidos");
						var fecha = rs.getDate("fecha_nac").toLocalDate();
						System.out.println("Resultado :" + id + " | " + nombre + " | " + apellido + "|" + fecha);

					}

				}
			}
		} catch(SQLException ex){
			Logger.getLogger(MainStatement_try_with_resources_2.class.getName()).log(Level.SEVERE, null, ex); // estudiar
		}

	}

}

