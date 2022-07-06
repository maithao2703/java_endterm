package helper;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class SecurityHelper {
	public static KeyPair generateKeyPairRSA() {
		KeyPairGenerator generator;
		KeyPair pair = null;
		try {
			generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(2048);
			pair = generator.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return pair;
	}

	public static String encryptMessage(PublicKey publicKey, String message) {
		Cipher encryptCipher;
		String encodedMessage = "";
		try {
			encryptCipher = Cipher.getInstance("RSA");
			encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] secretMessageBytes = message.getBytes(StandardCharsets.UTF_8);
			byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);
			encodedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}

		return encodedMessage;
	}

	public static String decryptMessage(PrivateKey privateKey, String encryptedMessage) {
		byte[] encryptedMessageBytes = Base64.getDecoder().decode(encryptedMessage);
		Cipher decryptCipher;
		String decryptedMessage = "";
		try {
			decryptCipher = Cipher.getInstance("RSA");
			decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] decryptedMessageBytes = decryptCipher.doFinal(encryptedMessageBytes);
			decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		
		return decryptedMessage;
	}
}
