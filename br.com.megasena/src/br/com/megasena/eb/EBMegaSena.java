package br.com.megasena.eb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import types.TONrosSorteados;
import utilidades.ManipulaData;
import utilidades.ManipulaNumeros;
import br.com.megasena.jdbc.ConnectionFactory;

public class EBMegaSena {

	public void importaDados(TONrosSorteados[] toNrosSorteados) {
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.createConnection();

			String sql = "INSERT INTO nros_sorteados VALUES (?,?,?)";

			for (int i = 0; i < toNrosSorteados.length; i++) {
				PreparedStatement ps = conexao.prepareStatement(sql);
				ps.setInt(1, toNrosSorteados[i].getNro());
				ps.setDate(2, ManipulaData.formataData(toNrosSorteados[i].getData()));
				ps.setString(3, toNrosSorteados[i].getSequencia());
				ps.execute();
			}

			conexao.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	public void insereNrosSorteados(TONrosSorteados toNrosSorteados) {
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.createConnection();

			String sql = "INSERT INTO nros_sorteados (nro, data,sequencia) VALUES (?,?,?)";

			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setInt(1, toNrosSorteados.getNro());
			ps.setDate(2, ManipulaData.formataData(toNrosSorteados.getData()));
			ps.setString(3, toNrosSorteados.getSequencia());
			ps.execute();

			conexao.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}

	}
	
	public void insereNrosApostados(TONrosSorteados toNrosSorteados) {
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.createConnection();

			String sql = "INSERT INTO nros_apostados (data,sequencia,sorteio) VALUES (?,?,?)";

			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setDate(1, ManipulaData.formataData(toNrosSorteados.getData()));
			ps.setString(2, toNrosSorteados.getSequencia());
			ps.setInt(3, toNrosSorteados.getNro());
			ps.execute();

			conexao.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}

	}
	
	public TONrosSorteados getNroApostados(String sequencia) {
		try {
			//int nros[] = ManipulaNumeros.converteSeqEmConj(sequencia);			
			//int nrosOrdenados[] = ManipulaNumeros.ordenaNumeros(nros);
					
			
		//	String sequenciaOrdenada = nrosOrdenados[0] + "-" + nrosOrdenados[1] + "-" + nrosOrdenados[2] + "-" + nrosOrdenados[3] + "-" + nrosOrdenados[4] + "-" + nrosOrdenados[5];

			Connection conexao = null;
			conexao = ConnectionFactory.createConnection();

			String sql = "SELECT * FROM nros_apostados WHERE sequencia = ?";

			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, sequencia);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				TONrosSorteados toNrosSorteados = new TONrosSorteados(rs.getInt("sorteio"),
						rs.getString("data"), rs.getString("sequencia"));

				conexao.close();
				return toNrosSorteados;
			} else {
				conexao.close();
				return null;
			}

			

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		return null;
	}
	
	
	public TONrosSorteados[] listaNrosApostados(int sorteio) {
		try {
		
			Connection conexao = null;
			conexao = ConnectionFactory.createConnection();

			String sql = "SELECT * FROM nros_apostados WHERE 1=1 ";
			
			if(sorteio != 0){
				sql = sql.concat("AND sorteio = ?");
			}

			PreparedStatement ps = conexao.prepareStatement(sql);
			
			if(sorteio != 0){
				ps.setInt(1, sorteio);
			}
			
			ArrayList<TONrosSorteados> arrayNrosApostados = new ArrayList<TONrosSorteados>();

			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int[] numerosConjunto = ManipulaNumeros.converteSeqEmConj(rs.getString("sequencia"));
				TONrosSorteados toNrosSorteados = new TONrosSorteados(rs.getInt("sorteio"),ManipulaData.dataSqlParaDataString(rs.getDate("data")), rs.getString("sequencia"), numerosConjunto);
				arrayNrosApostados.add(toNrosSorteados);
			}
			
			if (arrayNrosApostados.isEmpty()) {				
				conexao.close();
				return null;
			} else {
				
				conexao.close();
				TONrosSorteados[] toNrosSorteados = (TONrosSorteados[]) arrayNrosApostados
						.toArray(new TONrosSorteados[arrayNrosApostados.size()]);
				return toNrosSorteados;
			}


		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		} 

		return null;
	}

	public TONrosSorteados getNrosSorteados(int nro) {
		try {

			Connection conexao = null;
			conexao = ConnectionFactory.createConnection();

			String sql = "SELECT * FROM nros_sorteados WHERE nro = ?";

			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setInt(1, nro);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				TONrosSorteados toNrosSorteados = new TONrosSorteados(rs.getInt("nro"),
						rs.getString("data"), rs.getString("sequencia"), ManipulaNumeros.converteSeqEmConj( rs.getString("sequencia")));

				conexao.close();
				return toNrosSorteados;
			}

			conexao.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		return null;
	}

	public TONrosSorteados[] listaNrosSorteados() {
		try {
			ArrayList<TONrosSorteados> listaTONrosSorteados = new ArrayList<TONrosSorteados>();
			Connection conexao = null;
			conexao = ConnectionFactory.createConnection();
			
			String sql = "SELECT * FROM nros_sorteados";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int[] numerosConjunto = ManipulaNumeros
						.converteSeqEmConj(rs.getString("sequencia"));

				TONrosSorteados toNrosSorteados = new TONrosSorteados();
				toNrosSorteados.setNro(rs.getInt("nro"));
				toNrosSorteados.setData(ManipulaData.dataSqlParaDataString(rs.getDate("data")));
				toNrosSorteados.setSequencia(rs.getString("sequencia"));
				toNrosSorteados.setNumerosConjunto(numerosConjunto);
						
				listaTONrosSorteados.add(toNrosSorteados);
			}

			conexao.close();
			if (listaTONrosSorteados.isEmpty()) {
				return null;
			} else {

				TONrosSorteados[] toNrosSorteados = (TONrosSorteados[]) listaTONrosSorteados
						.toArray(new TONrosSorteados[listaTONrosSorteados.size()]);

				return toNrosSorteados;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		return null;

	}

/*	public static void main(String[] args) {
		TONrosSorteados[] toNrosSorteados = new EBMegaSena().listaNrosSorteados();

		for (int i = 0; i < toNrosSorteados.length; i++) {
			System.out.println(toNrosSorteados[i].getNro());
		}

	}*/

}
