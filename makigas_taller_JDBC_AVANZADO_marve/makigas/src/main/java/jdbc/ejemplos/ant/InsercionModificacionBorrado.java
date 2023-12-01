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
import jdbc.ejemplos.ant.Alumnos;

public class InsercionModificacionBorrado {




	private static Properties propiedadesBaseDeDatos() {
		// Clase Properties()
		Properties propiedadesCredenciales = new Properties();
		propiedadesCredenciales.setProperty("user", "root"); // (Clave, Valor)
		propiedadesCredenciales.setProperty("password", ""); // (Clave, Valor)
		return propiedadesCredenciales;
	};


	public static void main(String[] args) {

		conneccion(); // Para generar consultas co la clausula LIKE de mysql
		// inserciones();
	}

	public static void conneccion() {
		Properties credencialesConeccion = propiedadesBaseDeDatos();
		String controladorHostBBDD = "jdbc:mysql://localhost/ejemplo";

		try (Connection conn = DriverManager.getConnection(controladorHostBBDD, credencialesConeccion)){
			System.out.println("+++++++++++ Conexion establecida +++++++++++++++++");
			System.out.println("");

			// ************* INICIO CONSULTAS SQL SIMPLES **************
			try (Statement stmt = conn.createStatement();){
				String sql1 = "INSERT INTO alumnos (nombre,apellidos,fecha_nac) VALUES ('Ivor','Guzmán','1976-04-01') ,('Alexander','Zamabrano','1977-04-01')";
				String sql2 = "DELETE FROM alumnos WHERE id_alumno > 5 and id_alumno < 17";
				String sql3 = "DELETE FROM alumnos WHERE id_alumno > 5 and id_alumno";
				String sql4 = "UPDATE  alumnos SET fecha_nac='1976-04-01' WHERE id_alumno=19";



				
				var filasAfectadas = stmt.executeUpdate(sql3);
				System.out.println(
						"|_____________________ Resultado en base de datos _____________________________________|");
				System.out.println();
				// System.out.println("Resultado :" + filasAfectadas + " fila/as afectada/as ");
			} catch(SQLException ex){
				Logger.getLogger(MainStatement_try_with_resources_2.class.getName()).log(Level.SEVERE, null, ex); // estudiar
			}

			// ************* FIN CONSULTAS SQL SIMPLES **************




			// ************* INICIO CONSULTAS SQL PREPARADAS **************
			
			
			Alumnos[] alumno = new Alumnos[] {

					new Alumnos("Pedro", "Perensejo", "1999-04-01"), new Alumnos("Luis", "Tecla", "2000-05-01"),
					new Alumnos("Ana", "Jimena", "2003-04-01"),

			};
			var sqlPreparada = "INSERT INTO alumnos (nombre,apellidos,fecha_nac) VALUES (?, ?, ?) ";

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
			System.out.print("Consulta SQL: ");
			System.out.println(sqlPreparada);
			// ************* FIN CONSULTAS SQL PREPARADAS **************

			try (PreparedStatement pStmt = conn.prepareStatement(sqlPreparada)){
				for (Alumnos alumnos : alumno){
					pStmt.setString(1, alumnos.getNombre());
					pStmt.setString(2, alumnos.getApellido());
					pStmt.setString(3, alumnos.getFec_nacimiento());
					var resultado = pStmt.executeUpdate();
					System.out.println("Resultado :" + resultado + " fila/as afectada/as ");

				}


			} catch(Exception ex){
				Logger.getLogger(MainStatement_try_with_resources_2.class.getName()).log(Level.SEVERE, null, ex); // estudiar
			}
			// ************* INICIO CONSULTAS SQL PREPARADAS **************

		} catch(SQLException ex){
			Logger.getLogger(MainStatement_try_with_resources_2.class.getName()).log(Level.SEVERE, null, ex); // estudiar
		}
	}

}



