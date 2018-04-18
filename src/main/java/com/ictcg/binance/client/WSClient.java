package com.ictcg.binance.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.net.URI;

@ClientEndpoint
public class WSClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void subscribe(String url) {
        subscribe(url, null);
    }

    public void subscribe(String url, MessageHandler handler) {
        try {
            final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI(url));

            // add listener
            if (handler == null) {
                //clientEndPoint.addMessageHandler(message -> logger.info(message));
            } else {
                clientEndPoint.addMessageHandler(handler);
            }
            //clientEndPoint.sendMessage("{}");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}