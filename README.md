# FitoScan–AI Android

Stadiul codului sursă: **V1.11.1 – Corectare interfață**, conform [commitului din 8 octombrie 2026](https://github.com/gabirasoi66/FitoScan-AI-Android/commit/7dcc0369f93f0d807efb3de027e2ae74908a1f93).
Versiunea proiectului și versiunea pachetului Android sunt distincte. În `app/build.gradle.kts`, pachetul are `versionName = "1.0"` și `versionCode = 1`.

## Scanarea pe telefon

În ecranul **Scanare plantă**, puteți adăuga până la patru fotografii în total. În V1.11.1, selectorul din galerie preia câte o fotografie la fiecare deschidere și ignoră un URI deja adăugat. Puteți verifica previzualizările și elimina fotografiile individual sau pe toate odată.

Codul include un flux de captură prin cameră. Configurarea completă a acestuia necesită declararea providerului asociat autorității de tip `<applicationId>.fileprovider`, absentă din manifestul acestei versiuni.

## Analiza AI

Aplicația pregătește fotografiile pe dispozitiv cu `ImagePreprocessor`, construiește cererea prin `AnalysisContract` și o trimite prin `ApiTransport` către backend-ul FitoScan, configurat în `NetworkConfig`. Selectarea fișierelor locale, previzualizarea și eliminarea lor funcționează independent de serviciul de analiză. Diagnosticul AI necesită o conexiune la internet și un backend HTTPS funcțional. Cheia furnizorului AI și celelalte secrete de serviciu se păstrează exclusiv pe server.

## Compilarea și instalarea APK-ului

Proiectul configurează `minSdk = 26` și `compileSdk = 35`. Cu o instalare Gradle compatibilă cu pluginul Android al proiectului și Android SDK 35, comanda de compilare din rădăcina proiectului este:

```sh
gradle :app:assembleDebug
```

Dacă buildul reușește, găsiți APK-ul de test în `app/build/outputs/apk/debug/app-debug.apk`. Instalați acest fișier pe un telefon sau emulator Android compatibil. În revizia documentată lipsesc Gradle Wrapper (`gradlew`) și workflow-ul `.github/workflows/android-build.yml` menționat anterior.
