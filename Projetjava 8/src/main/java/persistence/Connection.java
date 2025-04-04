package persistence;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoDatabase;

public class Connection {

    public static void main(String[] args) {
        try(MongoClient mongoClient=new MongoClient("localhost",27017)){
            MongoCredential mongoCredential=MongoCredential.createCredential("niss","ProjetJava","root123".toCharArray());
            System.out.println("Vous êtes connectez au database avec succées.");
            MongoDatabase mongoDatabase=mongoClient.getDatabase("ProjetJava");
            System.out.println("mongoCredential= "+ mongoCredential);
            System.out.println("Nom database ="+mongoDatabase.getName());
            mongoDatabase.createCollection("projet");
            mongoDatabase.createCollection("tache");
            mongoDatabase.createCollection("document");
            mongoDatabase.createCollection("seance");
            mongoDatabase.createCollection("historiqueprojet");
            mongoDatabase.createCollection("historiquetache");
            System.out.println("Collection est créer avec succées.");
        }
        catch(Exception exe){

            System.err.println("Error: " + exe.getMessage());
            exe.printStackTrace();
        }
    }
    public MongoDatabase getDatabase() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        return mongoClient.getDatabase("ProjetJava");
    }


}
