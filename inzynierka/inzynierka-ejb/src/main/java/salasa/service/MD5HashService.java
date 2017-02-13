package salasa.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class MD5HashService
 */
@Stateless
public class MD5HashService {

	/**
	 * Default constructor.
	 */
	public MD5HashService() {
		// TODO Auto-generated constructor stub
	}

	public String hash(String data) {
		MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
			byte[] bufor = data.getBytes();
			m.update(bufor, 0, bufor.length);
			BigInteger hash = new BigInteger(1, m.digest());
			return String.format("%1$032X", hash).toLowerCase();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
