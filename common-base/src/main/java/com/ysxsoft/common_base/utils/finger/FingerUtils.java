package com.ysxsoft.common_base.utils.finger;

import android.annotation.TargetApi;
import android.app.Activity;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import com.bumptech.glide.Glide;
import com.umeng.socialize.sina.helper.MD5;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.ECGenParameterSpec;

/**
 * create by Sincerly on 2019/3/19 0019
 * 指纹验证
 **/
public class FingerUtils {
    FingerprintManager manager;
    private Activity activity;
    private String KEY_NAME="my_key";
    public static FingerUtils utils = null;

    public static FingerUtils getInstance() {
        synchronized (utils) {
            utils = new FingerUtils();
        }
        return utils;
    }

    public FingerUtils init(Activity activity) {
        this.activity = activity;
        return this;
    }

    public void initSignature(){
        try {
            Signature signature=Signature.getInstance("RSA");
            KeyStore keyStore=KeyStore.getInstance("");
            keyStore.load(null);
            PrivateKey key= (PrivateKey) keyStore.getKey(KEY_NAME,null);
            signature.initSign(key);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public KeyPairGenerator createKeyPair(){
        try {
            KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(new KeyGenParameterSpec.Builder(KEY_NAME,KeyProperties.PURPOSE_SIGN)
                    .setDigests(KeyProperties.DIGEST_SHA256)
                    .setAlgorithmParameterSpec(new ECGenParameterSpec("secp256r1"))
                    .setUserAuthenticationRequired(true)
                    .build());
            keyPairGenerator.generateKeyPair();
            return keyPairGenerator;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }
}
///////////////////////////////////////////////////////////////////////////
// 流程
///////////////////////////////////////////////////////////////////////////
//1.创建密钥对 KeyPairGenerator