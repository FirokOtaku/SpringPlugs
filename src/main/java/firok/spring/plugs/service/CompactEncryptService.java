package firok.spring.plugs.service;

import firok.spring.plugs.config.EncryptConfig;
import firok.topaz.general.Encrypts;
import firok.topaz.resource.Files;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@ConditionalOnBean(EncryptConfig.class)
@Configuration
public class CompactEncryptService
{
	@Autowired
	EncryptConfig config;

	private RSAPublicKey publicKey;
	private RSAPrivateKey privateKey;

	@SneakyThrows
	public String[] enc(String value)
	{
		var len = value.length();
		var ret = new String[len / 64 + 1];
		for(int step = 0; step * 64 <= len; step++)
		{
			var piece = value.substring(step * 64, Math.min(len, (step + 1) * 64));
			var bytes = Encrypts.encodeRSA(piece, publicKey).getBytes(StandardCharsets.UTF_8);
			ret[step] = Encrypts.encodeBase64_B2S(bytes);
		}
		return ret;
	}

	@SneakyThrows
	public String dec(String[] values)
	{
		var ret = new StringBuilder();

		for(var value : values)
		{
			var bytes = Encrypts.decodeBase64_S2B(value);
			var str = Encrypts.decodeRSA(new String(bytes), privateKey);
			ret.append(str);
		}

		return ret.toString();
	}

	static final KeyPairGenerator kpg;
	static final KeyFactory kf;
	static
	{
		try
		{
			kpg = KeyPairGenerator.getInstance("RSA");
			kf = KeyFactory.getInstance("RSA");
		}
		catch (NoSuchAlgorithmException e)
		{
			throw new RuntimeException("找不到 RSA 算法实现", e);
		}
	}

	@SneakyThrows
	@PostConstruct
	void postConstruct()
	{
		var keyValuePublic = config.getKeyValuePublic();
		var keyValuePrivate = config.getKeyValuePrivate();
		var pathPublicKey = config.getPathPublicKey();
		var pathPrivateKey = config.getPathPrivateKey();

		if(!keyValuePublic.isEmpty() && !keyValuePrivate.isEmpty())
		{
			publicKey = readPublic(Encrypts.decodeBase64_S2B(keyValuePublic));
			privateKey = readPrivate(Encrypts.decodeBase64_S2B(keyValuePrivate));
		}
		else if(Files.checkExist(pathPrivateKey, true) && Files.checkExist(pathPublicKey, true))
		{
			try(var ifs = new FileInputStream(pathPublicKey))
			{
				publicKey = readPublic(ifs.readAllBytes());
			}
			try(var ifs = new FileInputStream(pathPrivateKey))
			{
				privateKey = readPrivate(ifs.readAllBytes());
			}
		}
		else
		{
			var kp = kpg.generateKeyPair();
			publicKey = (RSAPublicKey) kp.getPublic();
			privateKey = (RSAPrivateKey) kp.getPrivate();
			try(var ofs = new FileOutputStream(pathPublicKey))
			{
				ofs.write(publicKey.getEncoded());
				ofs.flush();
			}
			try(var ofs = new FileOutputStream(pathPrivateKey))
			{
				ofs.write(privateKey.getEncoded());
				ofs.flush();
			}
		}
	}

	@SneakyThrows
	static RSAPrivateKey readPrivate(byte[] buffer)
	{
		var key = new PKCS8EncodedKeySpec(buffer);
		return (RSAPrivateKey) kf.generatePrivate(key);
	}

	@SneakyThrows
	static RSAPublicKey readPublic(byte[] buffer)
	{
		var key = new X509EncodedKeySpec(buffer);
		return (RSAPublicKey) kf.generatePublic(key);
	}
}
