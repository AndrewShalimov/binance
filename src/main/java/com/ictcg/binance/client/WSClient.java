package com.ictcg.binance.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.net.URI;

@ClientEndpoint
public class WSClient {

    private static Object waitLock = new Object();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @OnMessage
    public void onMessage(String message) {
        logger.info("Received msg: {}", message);
    }

    public WSClient() {
        logger.info("created");

    }

    private void wait4TerminateSignal() {
        synchronized (waitLock) {
            try {
                waitLock.wait();
            } catch (InterruptedException e) {
            }
        }
    }

    public void connect_(String url) {
        WebSocketContainer container = null;//
        Session session = null;
        try {
            container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(WSClient.class, URI.create(url));
            wait4TerminateSignal();
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

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