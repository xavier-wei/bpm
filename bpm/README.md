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
- 如果只需要打包到target，執行mvn clean package -P prod即可
```bash

Move-Item target\bpm.war -Destination "C:\wildfly-2\standalone\deployments"
Remove-Item "C:\wildfly-2\standalone\deployments\bpm.war"
mvn clean package -P prod
```

```bash
echo "start"
echo "stop"
```
