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
    private int ID;
    private String Name;
    private String Version;
    private String Class;
    private String Number;
    private String fileName;
    private String FileNote;
    dbElement(String[] fields, int ID){
        this.ID = ID;
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
        this.FileNote = FileNote;
    }

    public int getID() { return ID;}

    public void setID(int ID) { this.ID = ID; }
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
    private HashMap<String, List<dbElement>> HashMap = new HashMap<String, List<dbElement>>();
    private List<dbElement> whole = new ArrayList<dbElement>();

    static {
        db = new File(path, "db.csv");
    }

    public Manager() {
        try {
            loadCSV();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<dbElement> loadCSV() throws Exception {
        int ID = 0;
        //Start reading from line number 2 (line numbers start from zero)
        //setWhole(null);
        if (!db.exists()) {
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
                //System.out.println(nextLine);
                getWhole().add(new dbElement(nextLine, 0));
            }
        }
        Collections.sort(getWhole(), new dbElementComparator());
        for(dbElement e : getWhole())
            e.setID(ID++);
        /*
        System.out.println(getWhole().size());
        for(int i = 0 ; i < getWhole().size(); i++){
            System.out.println(getWhole().get(i).getFileName());
        }
        */
        return getWhole();
    }

    public static void initializationDir() {
        if (!path.exists()) {
            path.mkdir();
        }
    }

    public List<dbElement> getWhole() {
        return this.whole;
    }

    public void setWhole(List<dbElement> newWhole) {
        this.whole = newWhole;
    }


    public static void saveObj(Figura fig) throws java.io.IOException {
        File file = new File(path, fig.getFinalName() + "." + fig.getClass().getName() + ".obj");
        System.out.println(fig.getClass().getName());
        if (!file.exists())
            file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(fig);
        createCSV();
        CSVWriter writer = new CSVWriter(new FileWriter(db, true));
        System.out.println(fig.getDescription().getName());
        String fields;
        if (fig.getClass() == Composite.class)
            fields = String.valueOf(fig.getNFigure());
        else {
            fields = String.valueOf(fig.getNLati());
        }
        String[] newRecord = {
                fig.getName(),
                String.valueOf(fig.getVersione()),
                String.valueOf(fig.getClass().getName()),
                String.valueOf(fields),
                String.valueOf(file.getName()),
                fig.getDescription().getName()
        };
        writer.writeNext(newRecord);
        writer.close();
    }

    public Figura loadObj(int i) throws java.io.IOException {
        File file = new File(path, whole.get(i).getFileName());
        Figura fig;
        if (!file.exists()) return null;
        else {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            try {
                switch (whole.get(i).getC()) { //dai che posso farlo, tutti gli id sono in rigoroso ordine numerico di come è stato, appunnto, inserito nel whole
                    case "Polygon":
                        fig = (Polygon) ois.readObject();
                        break;
                    case "Circle":
                        fig = (Circle) ois.readObject();
                        break;
                    case "Composite":
                        fig = (Composite) ois.readObject();
                        break;
                    default:
                        fig = (Figura) ois.readObject();
                }
                return fig;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    public static int countFigure() {
        initializationDir();
        return path.listFiles().length;
    }


    public String[][] loadModelDB(List<dbElement> toModel) {
        if (toModel == null) toModel = getLastVersionFigure();
        try {
            //ciao = Manager.loadObj(fail);
            //loadCSV();
            int nFigure = toModel.size();
            if (!toModel.isEmpty()) {
                String[][] modelGrid = new String[nFigure][7];
                int k = 0;
                for (dbElement dbe : toModel) {
                    modelGrid[k][0] = String.valueOf(dbe.getID());
                    modelGrid[k][1] = dbe.getName();
                    modelGrid[k][2] = dbe.getVersion();
                    modelGrid[k][3] = dbe.getC();
                    modelGrid[k][4] = dbe.getNumber();
                    modelGrid[k][5] = dbe.getFileName();
                    modelGrid[k][6] = dbe.getFileNote();
                    k++;
                }
                return modelGrid;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void rmvFig(int i) {
        File file = new File(path, getWhole().get(i).getFileName());
        file.delete();
        db.delete();
        whole.remove(i);
        saveWhole();
    }

    public void saveWhole() {
        try {
            createCSV();
            for (dbElement k : getWhole()) {
                CSVWriter writer = new CSVWriter(new FileWriter(db, true));
                String[] newRecord = {k.getName(), k.getVersion(), k.getClass().getName(), k.getNumber(), k.getFileName(), k.getFileNote()};
                writer.writeNext(newRecord);
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFileDescription(int i) {
        File fileDescription = new File(path, whole.get(i).getFileNote());
        return getDescription(fileDescription);
    }

    public static String getDescription(File readFile) {
        if (readFile.exists()) {
            BufferedReader buffR = null;
            Reader inputReader = null;
            try {
                buffR = new BufferedReader((new FileReader(readFile)));
                StringBuilder stringB = new StringBuilder();
                String line = buffR.readLine();
                while (line != null) {
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
                } catch (Exception e) {
                }
            }
        }
        return null;
    }


    public List<dbElement> getLastVersionFigure() {
        Float last = 0.0f;
        List<dbElement> groupedByName = new ArrayList<dbElement>();
        groupedByName.add(whole.get(0));
        int k = 0;
        for (dbElement e : whole) {
            if (e.getName().equals(groupedByName.get(k).getName())) {
                if (Float.valueOf(e.getVersion()) > Float.valueOf(groupedByName.get(k).getVersion())) {
                    groupedByName.set(k, e);
                }
            } else {
                k++; // posso farlo perché già ordinati per nome
                groupedByName.add(e);
            }
        }
        return groupedByName;
    }

    public List<dbElement> getListName(int i) {
        dbElement name = getWhole().get(i);
        List<dbElement> ListByName = new ArrayList<dbElement>();
        for (dbElement e : whole) {
            if (name.getName().equals(e.getName())) {
                ListByName.add(e);
            }
        }
        return ListByName;
    }


    public static void createCSV(){
        try {
            if (!db.exists()) {
                db.createNewFile();
                CSVWriter writer = new CSVWriter(new FileWriter(db, true));
                String[] newRecord = {"Name", "Version", "Class", "Number of Fields", "File Name", "Note File"};
                writer.writeNext(newRecord);
                writer.close();
            }
        } catch (IOException  e){
            e.printStackTrace();
        }
    }
}

