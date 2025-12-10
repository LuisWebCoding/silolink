# 🌾 SiloLink

Sistema de gerenciamento de silos agrícolas.

## 📁 Estrutura

- **frontend/** - Aplicação React
- **backend/** - API Spring Boot

## 🚀 Como Rodar

### Frontend
```bash
cd silo-link
npm install
npm run dev

### Backend
Vá na pasta resources e logo em seguida entre no arquivo application.properties
nas linhas 6 e 7:
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

modifique o password e o username para o que está sendo utilizando na máquina local.

Após esse processo com frontend já iniciado:
cd Backend-CodingII-main
mvn clean install
mvn spring-boot:run
