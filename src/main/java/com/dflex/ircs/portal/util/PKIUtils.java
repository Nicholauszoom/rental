package com.dflex.ircs.portal.util;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.util.Base64;

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
	
	@Value("${app.private.key.file.path}")
	public String appPrivateKeyFilePath;
	
	public boolean verifySignature(String signature, String content, String publicKeyPass, String publicKeyAlias,
			String publicKeyFilePath,Long signatureAlg) throws Exception {

		boolean t = false;
		try {
			byte db[] = Base64.getDecoder().decode(signature.getBytes());
			
			Signature sig = null;
			if(signatureAlg.equals(Constants.SIG_ALG_SHA1)) {
				sig = Signature.getInstance("SHA1withRSA");
			} else {
				sig = Signature.getInstance("SHA256withRSA");
			}
			 
			byte[] data = content.getBytes();
			sig.initVerify(getPublicKey(publicKeyPass, publicKeyAlias, publicKeyFilePath));
			sig.update(data);
			t = sig.verify(db);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	public String createSignature(String content, String privateKeyPass, String privateKeyAlias,
			String privateKeyFilePath,Long signatureAlg) throws Exception {
		
		String resultSig = "";
		try {
			byte[] data = content.getBytes();
			Signature sig = null;
			if(signatureAlg.equals(Constants.SIG_ALG_SHA1)) {
				sig = Signature.getInstance("SHA1withRSA");
			} else {
				sig = Signature.getInstance("SHA256withRSA");
			}
			sig.initSign(getPrivateKey(privateKeyPass, privateKeyAlias, privateKeyFilePath));
			sig.update(data);
			byte[] signatureBytes = sig.sign();
	
			resultSig = Base64.getEncoder().encodeToString(signatureBytes);
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultSig;
	}

	private PrivateKey getPrivateKey(String keyPass, String keyAlias, String keyFilePath) throws Exception {

		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		FileInputStream is = new FileInputStream(keyFilePath);

		keyStore.load(is, keyPass.toCharArray());
		PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyAlias, keyPass.toCharArray());
		return privateKey;
	}

	private PublicKey getPublicKey(String keyPass, String keyAlias, String keyFilePath) throws Exception {

		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		FileInputStream is = new FileInputStream(keyFilePath);
		keyStore.load(is, keyPass.toCharArray());
		Certificate cert = keyStore.getCertificate(keyAlias);
		PublicKey publicKey = cert.getPublicKey();
		return publicKey;
	}
}
