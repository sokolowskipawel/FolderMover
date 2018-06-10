package com.sokolowski;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class DirectoryNotExist extends IOException {
    // wypisuje jakich ścieżek brakuje


    public DirectoryNotExist(String message, List patchNotExist) {
        super(message);
        for (Object object : patchNotExist) {
            System.out.println(this.getMessage() + object.toString());
        }
    }
}
