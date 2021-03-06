package com.ictcg.binance.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.net.URI;

@ClientEndpoint
public class WebsocketClientEndpoint {

    private Session userSession = null;
    private MessageHandler messageHandler;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public WebsocketClientEndpoint(URI endpointURI) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @OnOpen
    public void onOpen(Session userSession) {
        logger.info("Opening websocket");
        this.userSession = userSession;
    }


    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        logger.info("closing websocket");
        this.userSession = null;
    }


    @OnMessage
    public void onMessage(String message) {
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
        }
    }


    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }


    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }

}