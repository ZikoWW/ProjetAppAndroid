
## 📱 Application Séries TV Populaires

Cette application Android affiche une liste de séries TV populaires en consommant l’API publique [Episodate](https://www.episodate.com/api). Elle est développée avec **Jetpack Compose** en suivant l’architecture **MVVM** et en intégrant des bibliothèques modernes comme **Retrofit**, **Coil** et **Dagger-Hilt**.

---

### 🚀 Fonctionnalités

- Affichage des séries sous forme de **grille** avec :
  - L’image de la série (chargée avec Coil)
  - Le titre de la série
- **Chargement infini** : scroll bas déclenche le chargement des séries suivantes
- Gestion des **états de chargement**

---

### 🏗️ Architecture utilisée

- **MVVM (Model - View - ViewModel)** pour séparer les responsabilités :
  - `Model` : Représente les données des séries (JSON → data class)
  - `Repository` : Récupère les données via l'API
  - `ViewModel` : Gère l'état de l'application
  - `View` : Affiche les données via Jetpack Compose
- **Dagger-Hilt** pour l’injection de dépendances
- **Coroutines** pour les appels réseau asynchrones

---

### 🧰 Technologies et bibliothèques

| Outil / Bibliothèque | Rôle                     |
| -------------------- | ------------------------ |
| **Jetpack Compose**  | UI déclarative           |
| **Retrofit**         | Appels API               |
| **Coil**             | Chargement des images    |
| **Dagger-Hilt**      | Injection de dépendances |
| **Coroutines**       | Programmation asynchrone |
| **ViewModel**        | Gestion d’état           |

---

### ⚙️ Installation et exécution

1. Cloner le projet :
   ```bash
   git clone https://github.com/ton-projet/series-tv-app.git
   ```
2. Ouvrir le projet avec **Android Studio**
3. Lancer l’application sur un émulateur ou un appareil réel (API 21+)

---

### 📀 Décisions architecturales

- Utilisation de **LazyVerticalGrid** pour afficher les séries de manière optimisée.
- Mise en place d’un **repository** pour centraliser les appels réseau.
- Séparation stricte des couches pour faciliter la maintenance et les tests.
- **Pagination simple** via le paramètre `page` de l’API.

---

### ✅ État de progression

- [x] Configuration initiale du projet
- [x] Intégration de Retrofit et Coil
- [x] Mise en place de MVVM
- [x] Affichage de la grille des séries
- [x] Gestion des états de chargement

---

### 🥮 Prochaines améliorations (si souhaité)

- Ajout d’une page de détails pour chaque série
- Mise en cache locale (Room)
- Ajout de tests unitaires
