package br.com.megasena.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	//DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			System.out.println("ERRO");
			e.printStackTrace();
		}
	}

	public static Connection createConnection() {
		String stringDeConexao = "jdbc:mysql://localhost:3306/megasena";
		String usuario = "root";
		String senha = "root";

		Connection conexao = null;

		try {
			conexao = DriverManager.getConnection(stringDeConexao, usuario, senha);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Problema de conexao: verifique se o banco de dados esta conectado");
			e.printStackTrace();
		}
		return conexao;
	}
}