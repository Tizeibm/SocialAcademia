# ![Maven](https://img.shields.io/badge/Maven-FFB300?style=flat&logo=apachemaven&logoColor=white) ![Java](https://img.shields.io/badge/Java-007396?style=flat&logo=java&logoColor=white) ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=flat&logo=spring&logoColor=white) ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-336791?style=flat&logo=postgresql&logoColor=white)

# SocialAcademia

## Description du projet
SocialAcademia est une API REST dédiée à un réseau social académique. Ce projet permet aux utilisateurs d'interagir entre eux, de partager des opportunités, des publications, ainsi que de gérer des profils d'étudiants et de professeurs. Grâce à son architecture modulaire, il facilite l'intégration de nouvelles fonctionnalités et l'extension de ses capacités.

### Fonctionnalités clés
- Gestion des profils d'utilisateurs (étudiants et professeurs)
- Partage de publications et de commentaires
- Système de notifications
- Gestion des opportunités académiques
- Suivi des interactions entre utilisateurs (likes, follows)

## Tech Stack

| Technologie       | Description                       |
|-------------------|-----------------------------------|
| ![Java](https://img.shields.io/badge/Java-007396?style=flat&logo=java&logoColor=white) | Langage de programmation principal |
| ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=flat&logo=spring&logoColor=white) | Framework pour le développement d'applications Java |
| ![Maven](https://img.shields.io/badge/Maven-FFB300?style=flat&logo=apachemaven&logoColor=white) | Outil de gestion de projet et d'automatisation |
| ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-336791?style=flat&logo=postgresql&logoColor=white) | Système de gestion de base de données relationnelle |

## Instructions d'installation

### Prérequis
- Java 11 ou supérieur
- Maven
- PostgreSQL

### Étapes d'installation
1. Clonez le dépôt :
   ```bash
   git clone https://github.com/Tizeibm/SocialAcademia.git
   cd SocialAcademia
   ```

2. Configurez votre base de données PostgreSQL et créez un utilisateur avec les droits nécessaires.

3. Modifiez le fichier `src/main/resources/application.properties` pour configurer les paramètres de connexion à la base de données :
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/votre_base_de_donnees
   spring.datasource.username=votre_utilisateur
   spring.datasource.password=Votre_Mot_de_Passe
   ```

4. Compilez et exécutez le projet :
   ```bash
   ./mvnw spring-boot:run
   ```

## Utilisation

Une fois le serveur démarré, l'API est accessible à l'adresse `http://localhost:8080`. Vous pouvez utiliser des outils comme Postman ou curl pour tester les différentes endpoints de l'API.

### Exemples d'utilisation
- **Créer un nouvel utilisateur :**
  ```http
  POST /api/users
  Content-Type: application/json

  {
      "nom": "John Doe",
      "email": "john.doe@example.com",
      "role": "etudiant"
  }
  ```

- **Obtenir tous les utilisateurs :**
  ```http
  GET /api/users
  ```

## Structure du projet

Voici un aperçu de la structure du projet :

```
SocialAcademia/
├── SocialAcademia/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/example/SocialAcademia/
│   │   │   │       ├── controller/              # Contrôleurs de l'API
│   │   │   │       ├── model/                   # Modèles de données
│   │   │   │       ├── repository/               # Interfaces pour l'accès aux données
│   │   │   │       ├── security/                 # Configuration de la sécurité
│   │   │   │       └── service/                  # Services métier
│   │   │   └── resources/
│   │   │       └── application.properties         # Fichier de configuration
│   │   └── test/
│   │       └── java/                             # Tests unitaires
│   ├── pom.xml                                    # Fichier de configuration Maven
```

### Explication des dossiers et fichiers principaux
- **controller/** : Contient les classes qui gèrent les requêtes HTTP et les réponses.
- **model/** : Contient les classes qui représentent les entités de l'application.
- **repository/** : Contient les interfaces pour l'accès aux données, généralement en utilisant Spring Data JPA.
- **security/** : Contient la configuration de la sécurité et de l'authentification.
- **service/** : Contient la logique métier de l'application.

## Contribuer

Les contributions sont les bienvenues ! Pour contribuer, veuillez suivre ces étapes :
1. Forkez le projet.
2. Créez une branche pour votre fonctionnalité (`git checkout -b feature/ma-fonctionnalite`).
3. Commitez vos changements (`git commit -m 'Ajout d\'une nouvelle fonctionnalité'`).
4. Poussez votre branche (`git push origin feature/ma-fonctionnalite`).
5. Ouvrez une Pull Request.

Merci de respecter les bonnes pratiques de codage et d'écrire des tests pour toute nouvelle fonctionnalité.
