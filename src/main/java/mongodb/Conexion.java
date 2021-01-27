package mongodb;

import com.mongodb.MongoClientURI;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONObject;

public class Conexion {

    private MongoClientURI Conexion;

    public Conexion() {
        Conexion = getConexion();
    }

    public static MongoClientURI getConexion() {
        //mongodb+srv://admin:100Alexis@cluster0.qhag3.mongodb.net/biblioteca?retryWrites=true&w=majority
        //mongodb://localhost
        MongoClientURI uri = new MongoClientURI(
                "mongodb://localhost");
        return uri;
    }

    public String agregarLibro(String nombre, String autor, String fechaPublicacion,
            String editorial, String isbn, int total) {
        MongoCollection coleccion = getCollection(this.Conexion, "biblioteca", "libro");

        System.out.println("Nombre : " + nombre);
        System.out.println("Autor : " + autor);
        System.out.println("fecha : " + fechaPublicacion);
        System.out.println("editorial : " + editorial);
        System.out.println("isbn : " + isbn);
        System.out.println("total : " + total);

        JSONObject libro = new JSONObject();
        libro.put("nombre", nombre);
        libro.put("autor", autor);
        libro.put("fechaPublicacion", fechaPublicacion);
        libro.put("editorial", editorial);
        libro.put("idbn", isbn);
        libro.put("total", total);
        System.out.println("fin json");
        coleccion.insertOne(new Document("nombre", nombre).append("autor", autor).append("fechaPublicacion", fechaPublicacion)
                .append("editorial", editorial).append("idbn", isbn).append("total", total));
        return libro.toString();
    }

    public String leerLibro() {
        MongoCollection coleccion = getCollection(this.Conexion, "biblioteca", "libro");

        Iterable<Document> doc = coleccion.find();

        JSONObject libro = new JSONObject();

        for (Document d : doc) {
            libro.put(d.getObjectId("_id").toHexString(), d);
        }
             
        return libro.toString();
    }

    public String eliminarLibro(String id) {
        MongoCollection coleccion = getCollection(this.Conexion, "biblioteca", "libro");

        Document findDocument = new Document("_id", new ObjectId(id));
        Iterable<Document> docFind = coleccion.find(findDocument);
        JSONObject libro = new JSONObject();

        for (Document d : docFind) {
            libro.put(d.getObjectId("_id").toHexString(), d);
        }

        coleccion.findOneAndDelete(findDocument);
        return libro.toString();
    }

    public String actualizarLibro(String id, String nombre, String autor, String fecha,
            String editorial, String isbn, int total) {
        MongoCollection coleccion = getCollection(this.Conexion, "biblioteca", "libro");

        Document findDocument = new Document("_id", new ObjectId(id));

        Iterable<Document> docFind = coleccion.find(findDocument);
        JSONObject libro = new JSONObject();

        for (Document d : docFind) {

            libro.put("_id", d.getObjectId("_id").toHexString());
            libro.put("nombre", nombre);
            libro.put("autor", autor);
            libro.put("fecha", fecha);
            libro.put("editorial", editorial);
            libro.put("isbn", isbn);
            libro.put("total", total);
        }

        coleccion.findOneAndUpdate(findDocument, new Document("$set", new Document("nombre", nombre).append("autor", autor)
                .append("fecha", fecha).append("editorial", editorial).append("isbn", isbn).append("total", total)));
        return libro.toString();
    }

    private MongoCollection getCollection(MongoClientURI conexion, String db, String collection) {
        MongoClientURI uri = conexion;
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase(db);
        MongoCollection coleccion = database.getCollection(collection);

        return coleccion;
    }

}
