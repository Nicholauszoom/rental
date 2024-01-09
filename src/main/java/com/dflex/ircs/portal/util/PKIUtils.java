package com.dflex.ircs.portal.util;

import java.security.Signature;
import java.security.SignatureException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import jakarta.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Augustino Mwageni
 * 
 * Public Key Infrastructure Implementation Utilities
 *
 */

@Component
public class PKIUtils {
	
	@Value("${app.private.key.passphrase}")
	public String appPassphrase;

	@Value("${app.private.key.alias}")
	public String appAlias;

	@Value("${app.private.key.file}")
	public String appKeyFile;
	
	@Value("${app.client.public.key.file.path}")
	public String appClientKeyFilePath;
	

	private PrivateKey getPrivateKey(String keyPass, String keyAlias, String keyFilePath) throws Exception {

		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		FileInputStream is = new FileInputStream(keyFilePath);

		keyStore.load(is, keyPass.toCharArray());
		PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyAlias, keyPass.toCharArray());
		return privateKey;
	}

	public String createSignature(String content, String privateKeyPass, String privateKeyAlias,
			String privateKeyFilePath) throws Exception {
		
		String resultSig = "";
		try {
			byte[] data = content.getBytes();
			Signature sig = Signature.getInstance("SHA1withRSA");
			sig.initSign(getPrivateKey(privateKeyPass, privateKeyAlias, privateKeyFilePath));
			sig.update(data);
			byte[] signatureBytes = sig.sign();
	
			resultSig = Base64.getEncoder().encodeToString(signatureBytes);
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultSig;
	}

	private PublicKey getPublicKey(String keyPass, String keyAlias, String keyFilePath) throws Exception {

		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		FileInputStream is = new FileInputStream(keyFilePath);
		keyStore.load(is, keyPass.toCharArray());
		Certificate cert = keyStore.getCertificate(keyAlias);
		PublicKey publicKey = cert.getPublicKey();
		return publicKey;

	}
	
	private PublicKey getPublicKey(String publicKeyString) {
		
		try {
			
			byte[] byteKey = Base64.getDecoder().decode(publicKeyString.getBytes());
			X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
			KeyFactory keyFactory = KeyFactory.getInstance("EC");

			return keyFactory.generatePublic(X509publicKey);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public boolean verifySignature(String signature, String content, String publicKeyPass, String publicKeyAlias,
			String publicKeyFilePath) throws Exception {

		boolean t = false;
		try {
			byte db[] = Base64.getDecoder().decode(signature.getBytes());
			Signature sig = Signature.getInstance("SHA1withRSA");
			byte[] data = content.getBytes();
			sig.initVerify(getPublicKey(publicKeyPass, publicKeyAlias, publicKeyFilePath));
			sig.update(data);
			t = sig.verify(db);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	public boolean verifySignature(String requestSignature, String requestData, String publicKeyString) throws SignatureException, InvalidKeyException, NoSuchAlgorithmException {
		
		boolean verifyResult = false;
		
		try {
			// Get public key
			PublicKey publicKey = getPublicKey(publicKeyString);
			System.out.println("*****************************" + publicKey);

			//decode
			byte requestSignatureBytes[] = Base64.getDecoder().decode(requestSignature.getBytes());
			Signature signature = null;
			signature = Signature.getInstance("SHA256withECDSA");
			
			//Verify
			byte[] requestDataBytes = requestData.getBytes();
			signature.initVerify(publicKey);
			signature.update(requestDataBytes);
			verifyResult = signature.verify(requestSignatureBytes);
			System.out.println("verifyResult*****************************" + verifyResult);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return verifyResult;
	}

	
	public String createSignature(String requestData, String privateKeyString) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
		
		Signature signature = null; 
		String resultSignature = "";
		
		byte[] privateKeyBytes = privateKeyString.getBytes();
		// decode 
		privateKeyBytes = toDecodedBase64ByteArray(privateKeyBytes);
		
		// get private key
		KeyFactory keyFactory = KeyFactory.getInstance("EC");
		KeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
	
		// Sign
		byte[] data = requestData.getBytes();
		signature = Signature.getInstance("SHA256withECDSA");
		signature.initSign(privateKey);
		signature.update(data);
		byte[] signatureBytes = signature.sign();
		resultSignature = Base64.getEncoder().encodeToString(signatureBytes);
		
		return resultSignature;
	}

	private static byte[] toByteArray(File file) throws IOException {
		
		try (FileInputStream in = new FileInputStream(file); FileChannel channel = in.getChannel()) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			channel.transferTo(0, channel.size(), Channels.newChannel(out));
			return out.toByteArray();
		}
	}

	private static byte[] toDecodedBase64ByteArray(byte[] base64EncodedByteArray) {
		return DatatypeConverter.parseBase64Binary(new String(base64EncodedByteArray, Charset.forName("UTF-8")));
	}
}
