package utilidades;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Criptografia {
	public static String sha256Hex(String valor) {
		try {
			MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
			byte messageDigest[];

			messageDigest = algorithm.digest(valor.getBytes("UTF-8"));

			StringBuilder hexString = new StringBuilder();
			for (byte b : messageDigest) {
				hexString.append(String.format("%02X", 0xFF & b));
			}

			return hexString.toString();
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void main(String[] args) {
		System.out.println(Criptografia.sha256Hex("123"));
	}
}
