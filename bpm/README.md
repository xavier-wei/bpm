# BPM 開發設定
## 1. 環境部署 -dev
- java 11
- 後端：load `bpm/pom.xml`
- 前端：
```bash
cd bpm
npm ci
```
## 2. 環境啟動
- 後端：run SpringBootApplication
- 前端：
```bash
cd bpm
npm start
```
## 2. 正式環境打包
```bash
cd bpm
mvn package -P prod,no-liquibase
```
