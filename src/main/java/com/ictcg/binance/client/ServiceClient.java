package com.ictcg.binance.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class ServiceClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${binance.base.url}")
    private String WS_BASE_URL;

    @Value("${binance.symbol.bitcoin}")
    private String symbol;

    @Value("${binance.stream.trade}")
    private String stream;

    private MessageHandler handler = new MessageHandler() {
        @Override
        public void handleMessage(String message) {
            //do whatever you need with the message from binance
            logger.info(message);
        }
    };

    public void subscribeToBinance() {
        String URL = WS_BASE_URL + symbol.toLowerCase() + stream;
        new WSClient().subscribe(URL, handler);
    }

}
