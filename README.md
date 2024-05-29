# Football Scorers API

Tento projekt poskytuje API pro nejlepší střelce ve fotbale za roky 2016-2020. API umožňuje přístup k datům o nejlepších střelcích v různých fotbalových ligách a soutěžích.

## Obsah

- [Instalace](#instalace)
- [Použití](#použití)


## Instalace

1. **Klonování repozitáře:**

   ```bash
   https://github.com/Deatrix09/Football-scorers-API.git
   cd Football-scorers-API
   
## Nastavení PostgreSQL databáze s Dockerem

Postupujte podle následujících kroků pro nastavení databáze PostgreSQL pomocí Dockeru a konfiguraci vaší aplikace pro připojení k ní.

### Krok 1: Vytvoření a spuštění Docker kontejneru pro PostgreSQL

Spusťte následující příkaz pro vytvoření a spuštění Docker kontejneru pro PostgreSQL:

```bash
   docker run --name football-scorers-db -e POSTGRES_PASSWORD=yourpassword -e POSTGRES_USER=youruser -e POSTGRES_DB=footballdb -p 5432:5432 -d postgres
```

Nahraďte yourpassword a youruser vaším zvoleným heslem a uživatelským jménem pro PostgreSQL.

### Krok 2: Aktualizace application.properties
Otevřete soubor src/main/resources/application.properties ve vašem projektu a aktualizujte nastavení připojení k databázi následovně:

```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/footballdb
spring.datasource.username=youruser
spring.datasource.password=yourpassword
```

   
