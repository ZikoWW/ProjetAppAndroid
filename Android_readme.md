# 📱 TV Series App - Android

Une application Android moderne pour découvrir et rechercher des séries TV populaires, développée avec Jetpack Compose et l'architecture MVVM.

<div align="center">

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)

</div>

## ✨ Fonctionnalités

- 🎬 **Affichage des séries populaires** en grille responsive
- 🔍 **Recherche en temps réel** avec debounce optimisé
- 📱 **Pagination automatique** pour une navigation fluide
- 🎨 **Interface moderne** avec Material Design 3
- 🌙 **Mode sombre/clair** adaptatif
- ⚡ **Performance optimisée** avec cache d'images
- 🛠️ **Gestion d'erreurs robuste** avec retry automatique

## 🚀 Aperçu

### Écran principal
- Grille responsive de séries TV
- Barre de recherche intelligente
- Chargement automatique du contenu

### Fonctionnalités avancées
- Recherche instantanée (minimum 2 caractères)
- Pagination transparente
- Messages d'erreur contextuels
- Interface adaptative jour/nuit

## 🏗️ Architecture

L'application suit l'architecture **MVVM (Model-View-ViewModel)** recommandée par Google :

```
📦 Architecture
├── 🎯 Model - Classes de données et modèles
├── 👁️ View - Interface utilisateur (Jetpack Compose)
├── 🧠 ViewModel - Logique métier et gestion des états
└── 🔄 Repository - Couche d'accès aux données
```

### Structure du projet

```
app/src/main/java/com/example/apptvseries/
├── 📱 MainActivity.kt
├── 🏠 TvSeriesApplication.kt
├── 📊 model/
│   └── TvShowModels.kt
├── 🌐 network/
│   └── TvShowApiService.kt
├── 🗃️ repository/
│   └── TvShowRepository.kt
├── 🧠 viewmodel/
│   └── TvShowViewModel.kt
└── 🎨 ui/
    ├── TvShowsScreenWithSearch.kt
    ├── components/
    ├── state/
    └── theme/
```

## 🛠️ Technologies utilisées

### Langage et plateforme
- **Kotlin** - Langage principal
- **Android SDK** (Min API 24, Target API 34)

### Interface utilisateur
- **Jetpack Compose** - UI toolkit déclaratif moderne
- **Material Design 3** - Design system cohérent
- **Coil** - Chargement d'images performant

### Architecture et données
- **ViewModel** - Gestion des états UI
- **StateFlow** - Programmation réactive
- **Coroutines** - Gestion de l'asynchrone
- **Retrofit** - Client HTTP pour API REST

### API
- **EpisoDate API** - Base de données de séries TV
  - Endpoint : `https://www.episodate.com/api/`
  - Fonctionnalités : Séries populaires, recherche

## 📋 Prérequis

- **Android Studio** Arctic Fox ou supérieur
- **JDK 17** ou supérieur
- **Android SDK** avec API Level 24+
- **Connexion internet** pour les données

## ⚡ Installation

### 1. Cloner le projet
```bash
git clone https://github.com/ZikoWW/ProjetAppAndroid.git
cd tv-series-app
```

### 2. Ouvrir dans Android Studio
- Lancez Android Studio
- Sélectionnez "Open an existing project"
- Naviguez vers le dossier du projet

### 3. Synchroniser les dépendances
```bash
# Android Studio le fera automatiquement
# Ou via la ligne de commande :
./gradlew build
```

### 4. Lancer l'application
- Connectez un appareil Android ou lancez un émulateur
- Cliquez sur le bouton "Run" (▶️)

## 🎯 Utilisation

### Navigation principale
1. **Découverte** - Parcourez les séries populaires
2. **Recherche** - Tapez le nom d'une série dans la barre de recherche
3. **Pagination** - Scrollez pour charger plus de contenu automatiquement

### Fonctionnalités de recherche
- Tapez **minimum 2 caractères** pour déclencher la recherche
- La recherche se lance **automatiquement après 800ms** d'inactivité
- Utilisez le **bouton X** pour effacer la recherche

### Gestion des erreurs
- **Messages contextuels** en cas de problème de connexion
- **Bouton "Réessayer"** pour relancer les requêtes
- **Indicateurs visuels** pendant les chargements

## ⚙️ Configuration

### Personnalisation du thème
Modifiez `ui/theme/Theme.kt` pour personnaliser les couleurs :

```kotlin
val ElectricBlue = Color(0xFF3B82F6)  // Couleur principale
val NetflixRed = Color(0xFFE50914)    // Couleur secondaire
val CinemaGold = Color(0xFFFFBF00)    // Couleur d'accent
```

### Paramètres de l'API
Configuration dans `TvShowViewModel.kt` :

```kotlin
private const val ITEMS_PER_PAGE = 12      // Séries par page
private const val SEARCH_DEBOUNCE = 800L   // Délai de recherche (ms)
private const val MIN_SEARCH_LENGTH = 2    // Caractères minimum pour recherche
```

## 📊 Performance

### Optimisations implémentées
- **Debounce de recherche** (800ms) pour réduire les appels API
- **Pagination intelligente** avec chargement anticipé
- **Cache d'images** automatique avec Coil
- **Limitation à 12 séries** par page pour la fluidité

### Métriques
- 📱 **Taille de l'APK** : ~8-12 MB
- ⚡ **Temps de démarrage** : <2 secondes
- 🖼️ **Chargement des images** : Cache persistant
- 📡 **Appels API** : Optimisés avec retry automatique

## 🧪 Tests

### Lancer les tests
```bash
# Tests unitaires
./gradlew test

# Tests d'instrumentation
./gradlew connectedAndroidTest
```

### Types de tests
- **Tests unitaires** - ViewModel et Repository
- **Tests d'interface** - Composants Compose
- **Tests d'intégration** - Flux complets

## 🐛 Résolution de problèmes

### Problèmes courants

#### Erreur de compilation JVM
```bash
# Solution : Vérifiez votre version JDK
java -version
# Doit être JDK 17
```

#### Erreur de synchronisation Gradle
```bash
# Solution : Nettoyer et reconstruire
./gradlew clean
./gradlew build
```

#### Problème de chargement des images
- Vérifiez votre connexion internet
- Les images sont mises en cache automatiquement

#### Recherche lente
- Normal : délai de 800ms pour optimiser les performances
- Assurez-vous d'avoir une connexion stable

### Logs de débogage
Activez les logs dans `TvShowRepository.kt` :

```kotlin
private val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}
```

## 🤝 Contribution

Les contributions sont les bienvenues ! Voici comment contribuer :

### 1. Fork le projet
```bash
git fork https://github.com/votre-username/tv-series-app.git
```

### 2. Créer une branche
```bash
git checkout -b feature/nouvelle-fonctionnalite
```

### 3. Commiter les changements
```bash
git commit -m "Ajout: nouvelle fonctionnalité"
```

### 4. Pousser la branche
```bash
git push origin feature/nouvelle-fonctionnalite
```

### 5. Ouvrir une Pull Request

## 🗺️ Roadmap

### Version 2.0 (À venir)
- [ ] 📺 Écran de détail des séries
- [ ] ⭐ Système de favoris
- [ ] 📤 Partage de séries
- [ ] 🔔 Notifications pour nouvelles saisons

### Version 2.1
- [ ] 💾 Cache local avec Room
- [ ] 📱 Mode hors ligne
- [ ] 🌍 Support multi-langue
- [ ] 📊 Analytics d'utilisation

### Version 3.0
- [ ] 🎮 Interface tablet optimisée
- [ ] 🔐 Authentification utilisateur
- [ ] ☁️ Synchronisation cloud
- [ ] 🤖 Recommandations IA

## 📄 Licence

Ce projet est sous licence MIT. Voir le fichier [LICENSE](LICENSE) pour plus de détails.

```
MIT License

Copyright (c) 2025 TV Series App

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software...
```

## 👥 Auteurs

- **Votre Nom** - *Développeur principal* - [@votre-github](https://github.com/votre-username)

## 🙏 Remerciements

- **EpisoDate** pour l'API de séries TV
- **Google** pour Jetpack Compose et Android
- **Square** pour Retrofit
- **Coil** team pour le chargement d'images
- **Material Design** team pour le design system

## 📞 Support

### Obtenir de l'aide
- 📧 **Email** : votre.email@example.com
- 🐛 **Issues** : [GitHub Issues](https://github.com/votre-username/tv-series-app/issues)
- 📖 **Documentation** : [Wiki du projet](https://github.com/votre-username/tv-series-app/wiki)

### FAQ

**Q : L'app fonctionne-t-elle hors ligne ?**  
R : Actuellement non, une connexion internet est requise. Le mode hors ligne est prévu pour la v2.1.

**Q : Puis-je contribuer au projet ?**  
R : Absolument ! Consultez la section [Contribution](#-contribution) pour commencer.

**Q : Quelle est la taille minimale d'écran supportée ?**  
R : L'app est optimisée pour les écrans de 4.7" et plus (API 24+).

---

<div align="center">

**⭐ N'oubliez pas de mettre une étoile si ce projet vous a plu ! ⭐**

[![GitHub stars](https://img.shields.io/github/stars/votre-username/tv-series-app.svg?style=social&label=Star)](https://github.com/votre-username/tv-series-app/stargazers)

</div>