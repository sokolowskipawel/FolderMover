package com.sokolowski;

import java.util.Map;

public interface Request {

    void handleRequest(Map<String, String> data);
}
