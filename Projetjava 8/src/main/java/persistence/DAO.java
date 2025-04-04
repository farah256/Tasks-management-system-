package persistence;

import java.util.List;

public interface DAO<T> {
    // Opérations CRUD génériques

    // Créer un nouvel objet dans la base de données
    boolean create(T obj);

    // Lire un objet de la base de données par son identifiant
    T read(String id);

    // Mettre à jour un objet dans la base de données
    void update(T obj,String id);

    // Supprimer un objet de la base de données par son identifiant
    void delete(String id);

    // Lire tous les objets de la base de données
    List<T> getAll();


    
}

