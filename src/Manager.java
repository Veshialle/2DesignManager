/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
import com.opencsv.*;

import java.io.*;
import java.util.*;


class dbElement{
    private String Name;
    private String Version;
    private String Class;
    private String Number;
    private String fileName;
    private String FileNote;
    dbElement(String[] fields){
        this.Name = fields[0];
        this.Version = fields[1];
        this.Class = fields[2];
        this.Number = fields[3];
        this.fileName = fields[4];
        this.FileNote = fields[5];
    }
    public String getName(){
        return Name;
    }
    public String getVersion(){
        return Version;
    }
    public String getC(){
        return Class;
    }
    public String getFileNote(){
        return FileNote;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public void setClass(String aClass) {
        Class = aClass;
    }

    public void setFileNote(String FileNote) {
        FileNote = FileNote;
    }
}

class dbElementComparator implements  Comparator<dbElement>{
    public dbElementComparator(){

    }
    public int compare(dbElement dbE1, dbElement dbE2){
        if(dbE1.getName().compareTo(dbE2.getName()) != 0) {
            if (dbE1.getName().compareTo(dbE2.getName()) > 0)
                return 1;
            else if (dbE1.getName().compareTo(dbE2.getName()) < 0)
                return -1;
        } else if(dbE1.getVersion().compareTo(dbE2.getVersion()) != 0) {
            if (dbE1.getVersion().compareTo(dbE2.getVersion()) > 0)
                return 1;
            else if(dbE1.getVersion().compareTo(dbE2.getVersion()) < 0)
                return -1;
        }
        return 0;
    }

    public boolean equals(dbElement dbe){
        return this.equals(dbe);
    }
}





public class Manager {
    static private String fileName;
    static private String pathName = "save/";
    static private File path = new File(pathName);
    static private File db;
    private List<dbElement> whole = new ArrayList<dbElement>();

    static {
        db = new File(path, "db.csv");
    }

    public Manager(){
        try {
            loadCSV();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public List<dbElement> loadCSV() throws Exception{
        //Start reading from line number 2 (line numbers start from zero)
        if(!db.exists()) {
            saveWhole();
            return null;
        }
        CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
        CSVReader reader = new CSVReaderBuilder(new FileReader(db)).withSkipLines(1).build();
        //CSVReader reader = new CSVReaderBuilder(new FileReader(db)).build();
        //Read CSV line by line and use the string array as you want
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            if (nextLine != null) {
                //Verifying the read data here
                getWhole().add(new dbElement(nextLine));
            }
        }
        Collections.sort(getWhole(), new dbElementComparator());
        return getWhole();
    }

    public static void initializationDir() {
        if (!path.exists()) {
            path.mkdir();
        }
    }

    public List<dbElement> getWhole(){
        return this.whole;
    }

    public void setWhole(List<dbElement> newWhole){
        this.whole = newWhole;
    }


    public static void saveObj(Figura fig) throws java.io.IOException {
        File file = new File(path, fig.getFinalName() + "." + fig.getClass().getName()  + ".obj");

        if (!file.exists())
            file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(fig);

        CSVWriter writer = new CSVWriter(new FileWriter(db, true));
        System.out.println(fig.getDescription().getName());
        String [] newRecord = {
                fig.getName(),
                String.valueOf(fig.getVersione()),
                String.valueOf(fig.getClass()),
                String.valueOf(fig.getNLati()),
                String.valueOf(file.getName()),
                fig.getDescription().getName()
        };
        writer.writeNext(newRecord);
        writer.close();
    }

    public Figura loadObj(int i) throws java.io.IOException{
        File file = new File(path, whole.get(i).getFileName());
        Figura fig;
        if(!file.exists()) return null;
        else {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            String fileName = file.getName();
            String[] tokens = fileName.split(".");
            try {
                switch(tokens[3]){
                    case "Polygon":
                        fig = (Polygon)ois.readObject();
                        break;
                    case "Circle":
                        fig = (Circle)ois.readObject();
                        break;
                    case "Composite":
                        fig = (Composite)ois.readObject();
                        break;
                    default:
                        fig = (Figura)ois.readObject();
                }
                return fig;
            } catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }
    }

    public static int countFigure() {
        initializationDir();
        return path.listFiles().length;
    }




    public String[][] loadModelDB() {
        try {
            //ciao = Manager.loadObj(fail);
            setWhole(loadCSV());
            int nFigure = whole.size();
            if (!whole.isEmpty()) {
                String[][] modelGrid = new String[nFigure][6];
                int k = 0;
                for (dbElement dbe : whole) {
                    modelGrid[k][0] = dbe.getName();
                    modelGrid[k][1] = dbe.getVersion();
                    modelGrid[k][2] = dbe.getC();
                    modelGrid[k][3] = dbe.getNumber();
                    modelGrid[k][4] = dbe.getFileName();
                    modelGrid[k][5] = dbe.getFileNote();
                }
                return modelGrid;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void rmvFig(int i){
        File file = new File(path, getWhole().get(i).getFileName());
        file.delete();
        db.delete();
        whole.remove(i);
        saveWhole();
    }

    public void saveWhole() {
        try {
            if(!db.exists()) {
                db.createNewFile();
                CSVWriter writer = new CSVWriter(new FileWriter(db, true));
                String[] newRecord = {"Name","Version","Class","Number of Fields","File Name","Note File"};
                writer.writeNext(newRecord);
                writer.close();
            }
            for(dbElement k : getWhole()){
                CSVWriter writer = new CSVWriter(new FileWriter(db, true));
                String[] newRecord = {k.getName(), k.getVersion(), k.getClass().getCanonicalName(), k.getNumber(), k.getFileName(), k.getFileNote()};
                writer.writeNext(newRecord);
                writer.close();
            }
            } catch (IOException e){
                e.printStackTrace();
        }
    }
    public String getStringDescription(int i){
        File fileDescription = new File(path, whole.get(i).getFileNote());
        BufferedReader buffR = null;
        Reader inputReader = null;
        try {
            System.out.println(whole.size());
            System.out.println(fileDescription.getName());
            buffR = new BufferedReader((new FileReader(fileDescription)));
            StringBuilder stringB = new StringBuilder();
            String line = buffR.readLine();
            while(line != null){
                stringB.append(line);
                stringB.append(System.lineSeparator());
                line = buffR.readLine();
            }
            return stringB.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                buffR.close();
            } catch (Exception e){ }
        }
        return null;
    }
}