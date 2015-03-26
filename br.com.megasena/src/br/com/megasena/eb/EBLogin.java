package br.com.megasena.eb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import types.TOLogin;
import br.com.megasena.jdbc.ConnectionFactoryLogin;

public class EBLogin {
	public boolean existeLogin(String email) {
		Connection conexao = null;
		boolean conectado = false;

		try {
			conexao = ConnectionFactoryLogin.createConnection();
			if (conexao != null) {
				conectado = true;
			}
			String sql = "SELECT 1 FROM members WHERE email = ?";

			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();

			boolean existe = rs.next();

			return existe;

		} catch (Exception e2) {
			e2.printStackTrace();
		} finally {
			try {
				if (conectado)
					conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public void incluirLogin(TOLogin toLogin) {
		Connection conexao = null;
		boolean conectado = false;

		try {
			if (!existeLogin(toLogin.getEmail())) {

				conexao = ConnectionFactoryLogin.createConnection();
				conectado = true;
				String sql = "INSERT INTO members (email,password) VALUES (?,?)";

				PreparedStatement ps = conexao.prepareStatement(sql);
				ps.setString(1, toLogin.getEmail());
				ps.setString(2, toLogin.getSenha());

				ps.execute();
			} else {
				System.out.println("Já existe um login com este e-mail");
			}

		} catch (Exception e2) {
			e2.printStackTrace();
		} finally {
			try {
				if (conectado)
					conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void excluirLogin(String email) {
		Connection conexao = null;
		boolean conectado = false;

		try {
			conexao = ConnectionFactoryLogin.createConnection();
			conectado = true;
			String sql = "DELETE FROM members WHERE email = ?";

			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, email);
			ps.execute();

		} catch (Exception e2) {
			e2.printStackTrace();
		} finally {
			try {
				if (conectado)
					conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public TOLogin getLogin(String email) throws SQLException {
		Connection conexao = null;
		boolean conectado = false;
		try {
			conexao = ConnectionFactoryLogin.createConnection();
			conectado = true;
			
			if(conexao == null){
				return null;
			}

			String sql = "SELECT email, password FROM members WHERE email = ?";

			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				TOLogin toLogin = new TOLogin(rs.getString("email"), rs.getString("password"));
				return toLogin;
			} else {
				return null;
			}

		}finally {
			try {
				if (conectado)
					conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		System.out.println(new EBLogin().existeLogin("hideki"));
		TOLogin toLogin;
		try {
			toLogin = new EBLogin().getLogin("hideki.fabio@gmail.com");
			System.out.println(toLogin.getEmail() + "-" + toLogin.getSenha());
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
