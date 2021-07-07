package com.transferwise.acorn.webhook;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class VerifyWebhookRequest {
    private final PublicKey publicKey;

    public void payload(String payload, String signature) {
        boolean signatureValid;
        try {
            Signature sign = Signature.getInstance("SHA256WithRSA");
            sign.initVerify(publicKey);
            sign.update(payload.getBytes());

            byte[] signatureBytes = Base64.getDecoder().decode(signature);

            signatureValid = sign.verify(signatureBytes);
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
            throw new WebhookRequestInvalid();
        }
        if (!signatureValid) {
            throw new WebhookRequestInvalid();
        }
    }
}
