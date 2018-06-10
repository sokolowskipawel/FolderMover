package com.sokolowski;

//odczyt pliku z danymi o przenoszeniu
// klucz - nazwa pliku, dane ścieżka do przeniesienia

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;

public class FolderMover {
    private String folderPath;  //ścieżka do folderu z plikami
    private String filePath;    //ścieżka do pliku z danymi
    private Request request;
    //

    public FolderMover(String folderPath, String filePath, Request request) {
        this.folderPath = folderPath;
        this.filePath = filePath;
        this.request = request;
    }

    public Map readData() throws IOException {

        File dataFile = new File(filePath);
        BufferedReader fileReader = new BufferedReader(new FileReader(dataFile));
        String line;
        Map<String, String> data = new HashMap<>();   // mapa z plikami i ich ścieżkami

        while ((line = fileReader.readLine()) != null) {
            if (line.contains(";")) {
                data.put(line.substring(0, line.indexOf(";")), line.substring(line.indexOf(";") + 1, line.length()));
            } else {
                throw new IOException("Błędny format danych wejściowych");
            }
        }
        return data;
    }

    public List validateDirectories(Map<String, String> data) {

        List patchNotExist = new LinkedList<String>(); // lista wszystkich ścieżek których nie ma
        File folder;

        //sprawdzenie czy ścieżka istnieje i zwrócenie wszystkich nieistniejących
        for (Map.Entry dataFileFolder : data.entrySet()) {
            folder = new File((String) dataFileFolder.getValue());
            if (!folder.exists()) {
                patchNotExist.add(dataFileFolder.getValue());
            }
        }
        return patchNotExist;
    }

    public void moveToFolders() throws DirectoryNotExist, IOException {

        Map<String, String> data = new HashMap<>();
        List patchNotExist;     //lista z nieistniejącymi folderam

        //odczyt pliku z mapowaniem folderów
        try {
            data = readData();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        //sprawdzenie czy wszystkie ścieżki docelowe istnieją,nie ma ścieżki to wyjątek DirectoryNotExist
        patchNotExist = validateDirectories(data);
        if (patchNotExist.size() == 0) {
            request.handleRequest(data);
        } else {
            throw new DirectoryNotExist("Nie znaleziony folder ", patchNotExist);
        }
    }

    public static void main(String[] args) throws IOException {

        Request request = new AnalyzeRequest();
        FolderMover folderMover = new FolderMover("c:\\temp\\", "c:\\temp\\filestomove.txt", request);
        List patchNotExist;
        Map<String, String> data = new HashMap<>();

        try {
            folderMover.moveToFolders();
        } catch (IOException e) {
        }
    }
}
