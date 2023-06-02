# 行政支援系統EIP 開發環境設定

## 1. 運行環境

- JAVA 11
- Wildfly 26.1.0.Final / Tomcat 9(開發用)
- Keycloak 19.0.2

## 2. 設定 Profile

本機運行請使用預設Profile`internal`。

## 3. 包版相關設定檔案

設定檔案位於 `src/main/filters/*.properties`，分為內部開發`internal`(預設)、`dev`、`test`、`prod`。

## 4. 主要設定內容

Keycloak設定請依照開發環境進行替換，`/WEB-INF/keycloak-*.json` 對應各區keycloak

## 5. 連線設定

本機開發環境可使用`Tomcat`，須掛載 sqlserver jdbc driver。相關連線設定請修改`src/main/webapp/META-INF/context.xml`帳號、密碼、URL。

若是使用Wildfly，請設定JNDI名稱為`java:jboss/datasources/eipDS`。

## 6. 包版

運行以下指令進行包版：

```bash
mvn clean package -P internal
```

## 7. 登入設定

目前資料庫已有`n5000`相關帳號資料，故請先讓Keycloak使用此帳號登入。