package com.sokolowski;

import java.util.Map;

public class AnalyzeRequest implements Request {



    @Override
    public void handleRequest(Map<String, String> data) {
          for (Map.Entry dataFileFolder : data.entrySet()) {
              System.out.println("Plik " + dataFileFolder.getKey() + " zostanie przeniesiony " + dataFileFolder.getValue());
          }
    }



}
