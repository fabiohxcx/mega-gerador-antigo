package br.com.megasena.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactoryLogin {
	//DriverManager.registerDriver(new com.mysql.jdbc.Driver()); 
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			System.out.println("ERRO");
			e.printStackTrace();
		}
	}

	public static Connection createConnection() throws SQLException {
		String stringDeConexao = "jdbc:mysql://localhost:3306/secure_login";
		String usuario = "root";
		String senha = "root";

		Connection conexao = null;

		try {
			conexao = DriverManager.getConnection(stringDeConexao, usuario, senha);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Falha na conexão com Banco de Dados!");
		} catch (Exception e) {
			System.out.println("Problema de conexao: verifique se o banco de dados esta conectado");
			e.printStackTrace();
		}
		return conexao;
	}
}
