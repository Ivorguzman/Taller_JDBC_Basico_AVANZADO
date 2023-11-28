package jdbc.ejemplos.ant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResultSetConScroll {


	private static Properties propiedadesBaseDeDatos() {
		// Clase Properties()
		Properties propiedadesCredenciales = new Properties();
		propiedadesCredenciales.setProperty("user", "root"); // (Clave, Valor)
		propiedadesCredenciales.setProperty("password", ""); // (Clave, Valor)
		return propiedadesCredenciales;
	};


	public static void main(String[] args) {
		conectar_ejectarConsultas();
	}


	public static void conectar_ejectarConsultas() {
		String controladorHostBBDD = "jdbc:mysql://localhost/ejemplo";
		Properties credencialesConeccion = propiedadesBaseDeDatos();

		String sql = "SELECT * FROM alumnos";
		try (Connection conn = DriverManager.getConnection(controladorHostBBDD, credencialesConeccion);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){

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
			System.out.print("Consulta SQL: ");
			System.out.println(sql);

					System.out.println(
							"|_____________________ Resultado en base de datos _____________________________________|");
					System.out.println();

					System.out.println("  rs.getRow() antes de rs.netx() ==> " + rs.getRow());
					if (rs.next()){

						do{
							int rowId = rs.getRow();
							String nombre = rs.getString("nombre");
							String apellido = rs.getString("apellidos");
							String fechaNacimiento = rs.getString("fecha_nac");
							System.out.printf("%3d %10s %13s %s\n", rowId, nombre, apellido, fechaNacimiento);

						} while (rs.next());

					}
		} catch(SQLException ex){
			Logger.getLogger(MainStatement_try_with_resources_2.class.getName()).log(Level.SEVERE, null, ex); // estudiar
		}
	}
}
