//package metier.service;
//
//import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
//import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
//import com.google.api.client.auth.oauth2.Credential;
//import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
//import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
//import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
//import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.Collections;
//
//public class Authentification {
//
//    private static final String CLIENT_SECRET_FILE_PATH = "client_secret_187820675770-ij3mc5s572jfuvvk2hn22vfhc1c8bmq4.apps.googleusercontent.com.json";
//    private static final String APPLICATION_NAME = "Projet Java";
//    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
//    private static final String[] SCOPES = {"https://www.googleapis.com/auth/calendar"};
//
//    private static GoogleClientSecrets clientSecrets;
//    private static HttpTransport httpTransport;
//    private static AuthorizationCodeFlow flow;
//
//    public static Credential authenticate() throws Exception {
//        httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//        clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(new FileInputStream(CLIENT_SECRET_FILE_PATH)));
//
//        flow = new GoogleAuthorizationCodeFlow.Builder(
//                httpTransport, JSON_FACTORY, clientSecrets, Collections.singletonList(SCOPES[0]))
//                .setDataStoreFactory(new GoogleDataStoreFactory())
//                .build();
//
//        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
//        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
//    }
//
//    public static void main(String[] args) throws IOException {
//        try {
//            Credential credential = authenticate();
//            // Utilisez le credential pour accéder au calendrier Google
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
