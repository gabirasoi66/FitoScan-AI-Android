# FitoScan–AI Android

Proiect Android pentru FitoScan–AI V1.3, pregătit pentru build automat în GitHub Actions.

## Build APK în GitHub

Workflow: `.github/workflows/android-build.yml`

La fiecare push pe `main` sau rulare manuală, GitHub Actions:
1. descarcă proiectul;
2. configurează JDK 17 și Gradle;
3. execută `:app:assembleDebug`;
4. publică `app-debug.apk` ca artifact cu numele `FitoScan-AI-debug-apk`.

## Instalare pe telefon

După ce workflow-ul s-a terminat cu succes, descarcă artifactul `FitoScan-AI-debug-apk`, extrage ZIP-ul și instalează `app-debug.apk` pe Android.

## Important

Aplicația Android împachetează interfața FitoScan–AI. Pentru analiza AI reală, backend-ul FitoScan trebuie să ruleze pe un server HTTPS și cheia OpenAI trebuie păstrată numai pe server, nu în aplicația Android.
