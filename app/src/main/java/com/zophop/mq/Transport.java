package com.zophop.mq;

import java.io.IOException;

public interface Transport {

    void sendMessage(String message) throws IOException;
}
