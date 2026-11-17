# GitHubAndroidClient

Мобильное клиентское приложение для GitHub, написанное на Kotlin с использованием современного стека Android-разработки. Позволяет аутентифицироваться с помощью Personal Access Token и просматривать репозитории пользователя, их детальную информацию и README-файлы.

## Возможности

- **Аутентификация** — вход через GitHub Personal Access Token с валидацией через GitHub API
- **Список репозиториев** — просмотр всех репозиториев аутентифицированного пользователя с отображением имени, языка и описания
- **Детальная информация** — просмотр полного описания репозитория: лицензия, звёзды, форки, наблюдатели, ссылка на репозиторий
- **README** — загрузка и отображение содержимого README-файла репозитория
- **Локализация** — поддержка русского и английского языков
- **Тёмная тема** — стилизация в духе GitHub Dark

## Стек технологий

| Категория | Технология |
|---|---|
| **Язык** | Kotlin 2.2.10 |
| **UI** | Jetpack Compose + Material 3 |
| **Навигация** | Navigation Compose 2.10.2 |
| **DI** | Koin 4.2.0 |
| **Сеть** | Ktor Client 3.6.1 (OkHttp engine) |
| **Сериализация** | kotlinx-serialization-json 3.6.1 |
| **Хранение** | DataStore Preferences 1.3.0 |
| **Сборка** | Gradle 9.4.1 (Kotlin DSL) |
| **Min SDK** | Android 7.0 (API 24) |
| **Target SDK** | Android 15 (API 37) |

## Архитектура

Проект использует гибридную архитектуру **MVVM (Model-View-ViewModel)**:

- **View Layer** — чистые Compose-экраны (`@Composable`), без Fragment-ов. Навигация через `NavHost` в рамках единственной `MainActivity`.
- **ViewModel Layer** — `ViewModel`-классы с `StateFlow` для состояния и `SharedFlow` для событий. Состояние инкапсулировано через sealed-интерфейсы (`Loading` / `Loaded` / `Error` / `Empty`).
- **Data Layer** — единый `AppRepository`, обёртка над DataStore для токена, DTO-модели с `@Serializable`.
- **Dependency Injection** — Koin 4.2.0, модуль определён в `diModule.kt`.

```
presentation/          ← Compose-экраны + ViewModels
data/                  ← Repository, Storage, DTO-модели
di/                    ← Koin-модуль
ui/theme/              ← Тема Compose (цвета, типографика)
```

## Структура проекта

```
app/
├── src/main/
│   ├── java/com/example/githubandroidclient/
│   │   ├── MyApplication.kt          ← Toplevel Application (Koin)
│   │   ├── presentation/
│   │   │   ├── MainActivity.kt
│   │   │   ├── AuthScreen.kt + AuthViewModel.kt
│   │   │   ├── RepositoriesListScreen.kt + RepositoriesListViewModel.kt
│   │   │   ├── DetailInfoScreen.kt + RepositoryInfoViewModel.kt
│   │   │   └── reusable UI components (TopBar, RowItem, IconItem...)
│   │   ├── data/
│   │   │   ├── AppRepository.kt
│   │   │   ├── KeyValueStorage.kt
│   │   │   └── model/ (Repo, Owner, License, UserInfo, ReadmeResponse)
│   │   ├── di/diModule.kt
│   │   └── ui/theme/
│   └── res/                          ← Drawables, Strings, Themes
├── src/test/
└── src/androidTest/
```

## Запуск проекта

### Требования

- **Android Studio** (Hedgehog или новее)
- **JDK 11+** (настраивается через Gradle toolchains)
- **Эмулятор** с API 24+ или физическое устройство

### Шаги

1. Клонируйте репозиторий:
   ```bash
   git clone <repository-url>
   cd GitHubAndroidClient
   ```

2. Откройте проект в Android Studio.

3. Синхронизируйте Gradle-зависимости (Android Studio предложит автоматически).

4. Запустите приложение на эмуляторе или устройстве кнопкой **Run** (Shift+F10) или:
   ```bash
   ./gradlew assembleDebug
   ```

## Как пользоваться

1. **Аутентификация** — создайте [GitHub Personal Access Token](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens) с scope `repo` и вставьте его в поле ввода на экране входа.
2. **Список репозиториев** — после успешной аутентификации отобразятся ваши репозитории.
3. **Детали репозитория** — нажмите на репозиторий для просмотра подробной информации и содержимого README.
4. **Выход** — кнопка выхода в верхней панели очистит токен и вернёт на экран аутентификации.

## GitHub API

| Параметр | Значение |
|---|---|
| **Base URL** | `https://api.github.com` |
| **API Version Header** | `X-GitHub-Api-Version: 2026-03-10` |
| **Accept Header** | `application/vnd.github+json` |
| **Auth** | Bearer Token |

### Используемые эндпоинты

- `GET /user` — валидация токена
- `GET /user/repos` — список репозиториев
- `GET /repos/{owner}/{repo}` — информация о репозитории
- `GET /repos/{owner}/{repo}/readme` — содержимое README

## Локализация

- 🇬🇧 English (по умолчанию)
- 🇷🇺 Русский (`values-ru-rRU`)

## Сборка

```bash
# Debug APK
./gradlew assembleDebug

# Release APK (с R8-оптимизациями)
./gradlew assembleRelease

# Запуск тестов
./gradlew test
./gradlew connectedAndroidTest
```

## Лицензия

Данный проект открыт для изучения и модификации.