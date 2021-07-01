package com.transferwise.acorn.webhook;

public class WebhookRequestInvalid extends RuntimeException {
    public WebhookRequestInvalid() {
        super("Consumed webhook request has invalid signature");
    }
}
