package Search;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MyFileHandler {

    public static ArrayList<Search.Document> ReadFromXML(String path) {
        ArrayList<Search.Document> documents = new ArrayList<>();
        documents.add(new Search.Document("ab0", ""));

        File file = new File(path);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        Document doc = null;

        try {
            db = dbf.newDocumentBuilder();
            doc = db.parse(file);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        doc.getDocumentElement().normalize();

        NodeList docs = doc.getElementsByTagName("doc");
        int i = 0;
        for (int tmp = 0; tmp < docs.getLength(); tmp++) {
            Node node = docs.item(tmp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                NodeList abstracts = eElement.getElementsByTagName("abstract");
                if (null != abstracts.item(i)) {
                    String body = abstracts.item(i).getTextContent();
                    Search.Document document = new Search.Document("ab" + i, body);
                    documents.add(document);
                } else {
                    Search.Document document = new Search.Document("ab" + i, "");
                    documents.add(document);
                }
            }
        }
        return documents;
    }

    public static HashMap<String, PostingList> ReadFromFile(String path) {
        HashMap<String, PostingList> table = null;
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            objectInputStream = new ObjectInputStream(fileInputStream);
            table = (HashMap<String, PostingList>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File not Found!");
        } catch (IOException e) {
            System.out.println("Exception in reading from file!");
        } catch (ClassNotFoundException e) {
            System.out.println("Exception in reading class not found!");
        } finally {
            try {
                fileInputStream.close();
                objectInputStream.close();
            } catch (IOException e) {
                System.out.println("Exception in closing file objects(Reading)");
            }
        }
        return table;
    }

    public static void WriteToFile(InvertedIndex index, String path) {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(path);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(index.getTable());
            System.out.println("File created!");
        } catch (IOException e) {
            System.out.println("Exception in writing to file!");
        } finally {
            try {
                fileOutputStream.close();
                objectOutputStream.close();
            } catch (IOException e) {
                System.out.println("Exception in closing file objects(Writing)");
            }
        }
    }
}
