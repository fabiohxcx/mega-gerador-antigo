import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;

public class teste {

	public static void main(String[] args) {
		try {
			String senhaAntiga = "Fábio";

			MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
			byte messageDigest[];

			messageDigest = algorithm.digest(senhaAntiga.getBytes("UTF-8"));

			StringBuilder hexString = new StringBuilder();
			for (byte b : messageDigest) {
				hexString.append(String.format("%02X", 0xFF & b));
			}
			String senha = hexString.toString();

			System.out.println(senha);
			
			System.out.println(DigestUtils.sha256Hex(senhaAntiga));
			

		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
