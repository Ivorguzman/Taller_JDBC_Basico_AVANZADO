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

public class TransaccionesInsercionModificacionBorrado {


	private static Properties propiedadesBaseDeDatos() {
		// Clase Properties()
		Properties propiedadesCredenciales = new Properties();
		propiedadesCredenciales.setProperty("user", "root"); // (Clave, Valor)
		propiedadesCredenciales.setProperty("password", ""); // (Clave, Valor)
		return propiedadesCredenciales;
	};


	public static void main(String[] args) {
		conectar_ejectarConsultas(); // Para generar consultas co la clausula LIKE de mysql
		// inserciones();
	}


	public static void conectar_ejectarConsultas() {
		Properties credencialesConeccion = propiedadesBaseDeDatos();
		String controladorHostBBDD = "jdbc:mysql://localhost/ejemplo";

		try (Connection conn = DriverManager.getConnection(controladorHostBBDD, credencialesConeccion)){
			conn.setAutoCommit(false); // Desactivando el commit automatico
			// ************* INICIO CONSULTAS SQL SIMPLES ************** //
			try (Statement stmt = conn.createStatement();){

				/*
				 * String sql = "DELETE FROM alumnos WHERE id_alumno > 5 and id_alumno";
				 * var filasAfectadas = stmt.executeUpdate(sql);
				 */


				System.out.println(
						"|_____________________ Resultado en base de datos _____________________________________|");
				// System.out.println("Resultado :" + filasAfectadas + " fila/as afectada/as ");
			} catch(SQLException ex){
				Logger.getLogger(MainStatement_try_with_resources_2.class.getName()).log(Level.SEVERE, null, ex); // estudiar
			}

			// ************* FIN CONSULTAS SQL SIMPLES ************** //



			
			
			

			// ************* INICIO CONSULTAS SQL PREPARADAS ************** //
			var sqlPreparada = "INSERT INTO alumnos (nombre,apellidos,fecha_nac) VALUES (?, ?, ?) ";
			try (PreparedStatement pStmt = conn.prepareStatement(sqlPreparada);){

				Alumnos[] alumno = new Alumnos[] {
						new Alumnos("Pedro", "Perensejo", "1999-04-01"), new Alumnos("Luis", "Tecla", "2000-05-01"),
						new Alumnos("Ana", "Guzmán", "2003-04-01"),
				};
				/*
				 * for (Alumnos alumnos : alumno){
				 * pStmt.setString(1, alumnos.getNombre());
				 * pStmt.setString(2, alumnos.getApellido());
				 * pStmt.setString(3, alumnos.getFec_nacimiento());
				 * var resultado = pStmt.executeUpdate(); // Varios Updates()
				 * System.out.println("Resultado :" + resultado + " fila/as afectada/as ");
				 * }
				 * conn.commit();
				 */

				var sqlPreparada2 = "SELECT * FROM alumnos WHERE nombre LIKE ? ";
				try (PreparedStatement pStmt2 = conn.prepareStatement(sqlPreparada2);){

					String consulta = "%_%_";
					pStmt2.setString(1, consulta);

					try (ResultSet resultSet = pStmt2.executeQuery();){
						System.out.println(
								"|_____________________ Información de tabla alumnos_____________________________________|");
						
						  while (resultSet.next()){
						  // System.out.println("registro");
						  int id = resultSet.getInt("id_alumno");
						  String nombre = resultSet.getString("nombre");
						  var apellido = resultSet.getString("apellidos");
						  var fecha = resultSet.getDate("fecha_nac").toLocalDate();
						  System.out.println("Resultado :" + id + " | " + nombre + " | " + apellido + "|" + fecha);
						  }
						 

					} catch(SQLException ex){


						Logger.getLogger(MainStatement_try_with_resources_2.class.getName()).log(Level.SEVERE, null,
								ex); // estudiar
					}
				} catch(SQLException ex){
					
					Logger.getLogger(MainStatement_try_with_resources_2.class.getName()).log(Level.SEVERE, null, ex); // estudiar
				}
			} catch(SQLException ex3){


				System.err.println("ERROR: " + ex3.getMessage());
				if (conn != null){
					System.out.println("Rollback");
					try{
						// deshace todos los cambios realizados en los datos
						conn.rollback();
					} catch(SQLException ex1){
						System.err.println("No se pudo deshacer" + ex1.getMessage());
					}
				}

				/*
				 * conn.rollback();
				 * System.out.println("=== conn.rollback() ===");
				 * Logger.getLogger(MainStatement_try_with_resources_2.class.getName()).log(Level.SEVERE, null, ex3); // estudiar
				 */
			}
			// ************* FIN CONSULTAS SQL PREPARADAS **************


		} catch(SQLException ex){
			Logger.getLogger(MainStatement_try_with_resources_2.class.getName()).log(Level.SEVERE, null, ex); // estudiar
		}
	}

}





