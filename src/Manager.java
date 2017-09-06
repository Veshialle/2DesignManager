/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.*;
import java.util.*;


class dbElement{
    private String Name;
    private String Version;
    private String Class;
    private String Number;
    private String fileName;
    private String Description;
    dbElement(String[] fields){
        this.Name = fields[0];
        this.Version = fields[1];
        this.Class = fields[2];
        this.Number = fields[3];
        this.fileName = fields[4];
        this.Description = fields[5];
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
    public String getDescription(){
        return Description;
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

    public void setDescription(String description) {
        Description = description;
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

    static {
        db = new File(path, "db.csv");
    }

    public Manager() {
    }

    public static void initializationDir() {
        if (!path.exists()) {
            path.mkdir();
        }
    }
    public static void saveObj(Figura fig) throws java.io.IOException {
        File file = new File(path, fig.getFinalName() + "." + fig.getClass().getName()  + ".obj");

        if (!file.exists())
            file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(fig);

    }

    public static Figura loadObj(File file) throws java.io.IOException{
        file = new File("save/", "gianconsquaqquero.1.0.Polygon.obj");
        Figura fig;
        if(!file.exists()) return null;
        else {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            System.out.println(ois.getClass());

            String fileName = file.getName();
            String[] tokens = fileName.split(".");
            for (String t : tokens)
                System.out.println(t);
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

    public static List<dbElement> loadCSV() throws Exception
    {
        //Start reading from line number 2 (line numbers start from zero)
        if(!db.exists()) return null;
        CSVReader reader = new CSVReaderBuilder(new FileReader(db)).build();
        //Read CSV line by line and use the string array as you want
        String[] nextLine;
        List<dbElement> whole = new ArrayList<dbElement>();
        while ((nextLine = reader.readNext()) != null) {
            if (nextLine != null) {
                //Verifying the read data here
                System.out.println(Arrays.toString(nextLine));
                whole.add(new dbElement(nextLine));
            }
        }
        Collections.sort(whole, new dbElementComparator());
        return whole;
    }
}
