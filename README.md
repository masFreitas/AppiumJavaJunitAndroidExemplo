# Appium + Java + JUnit 5 + Allure (Maven) — Exemplo (Android)

Este repositório demonstra uma base de automação **mobile Android** usando **Appium**, **Java**, **JUnit 5** e **Allure Report**, com arquitetura **POM (Page Object Model)** e build via **Maven**.

O projeto inclui:
- DriverFactory (criação do driver e capabilities)
- Page Objects + Selectors por feature
- Screenshots no `@AfterEach` anexadas no Allure
- Geração de relatório Allure a partir de `target\allure-results`

---

## Sumário
- [Estrutura do projeto](#estrutura-do-projeto)
- [Pré-requisitos](#pré-requisitos)
- [Setup do ambiente (Windows)](#setup-do-ambiente-windows)
- [Como identificar o device (adb devices)](#como-identificar-o-device-adb-devices)
- [Como iniciar o Appium via CLI](#como-iniciar-o-appium-via-cli)
- [Como executar os testes](#como-executar-os-testes)
- [Como gerar e abrir o Allure Report](#como-gerar-e-abrir-o-allure-report)
- [Allure Steps (passos) nos testes](#allure-steps-passos-nos-testes)
- [Troubleshooting](#troubleshooting)

---

## Estrutura do projeto

- `pom.xml`  
  Build Maven (dependências, versões, Surefire).

- `src\test\java\core`  
  Infra do framework:
  - `DriverFactory`: cria o `AndroidDriver` e contém utilitários (ex.: screenshot).
  - `BaseTest`: setup/teardown (`@AfterEach` anexando screenshot no Allure).
  - `BasePage`: helpers de interação com UI (quando aplicável).

- `src\test\java\pages`  
  Page Objects agrupados por feature/tela (ex.: `login`, `clipboard`).

- `src\test\java\test`  
  Classes de teste (ex.: `LoginTest`, `ClipboardTest`).

- `src\test\resources`  
  - `allure.properties`: define o diretório de resultados (`target\allure-results`)
  - `TheApp.apk`: APK de exemplo (se optar por instalar via capability `app`)

- `target\allure-results\`  
  Saída do Allure após a execução dos testes.

- `allure-report\`  
  Relatório HTML gerado via Allure CLI.

## DriverFactory: o que é e como funciona

O `DriverFactory` é a classe responsável por **criar, configurar e encerrar** o driver do Appium (no caso, o `AndroidDriver`). Em uma automação mobile, o driver é o objeto que faz a ponte entre o seu código de teste e o dispositivo/emulador, permitindo enviar comandos (taps, digitação, leitura de elementos, navegação, etc.).

Neste projeto, o `DriverFactory` centraliza:

1. **Criação do driver**: define as *capabilities/options* e inicializa a sessão com o Appium Server.
2. **Configuração de screenshots**: define quando os prints são salvos.
3. **Encerramento do driver**: garante que a sessão seja finalizada ao fim do teste (evita sessão “presa” e problemas em execuções subsequentes).

### Onde ele é usado

Os testes (ex.: `LoginTest`) normalmente não chamam `DriverFactory` diretamente. Quem costuma orquestrar isso é o `BasePage`:

- Nas Pages, cria o driver (`DriverFactory.getDriver()`),
- no `@AfterEach` do `BaseTest`, registra artefatos (ex.: screenshot) e encerra a sessão (`DriverFactory.tearDown()`).

---

### Entendendo as opções/capabilities dentro do DriverFactory

No Appium 2, a sessão é criada a partir de **capabilities** (ou “options”), que são pares `chave → valor` informando:

- qual plataforma,
- qual motor de automação,
- qual device,
- qual app,
- e comportamentos desejados (ex.: reset, permissões, etc.).

Exemplo (simplificado) do que você tem hoje:

```java
BaseOptions options = new BaseOptions()
    .amend("platformName", "Android")
    .amend("automationName", "UIAutomator2")
    .amend("deviceName", "RX8R808B8TL")
    .amend("appPackage", "com.appiumpro.the_app")
    .amend("appActivity", "com.appiumpro.the_app.MainActivity")
    .amend("noReset", false);
```

### `platformName = "Android"`

Define a **plataforma** que será automatizada.  
Valores comuns: `Android` ou `iOS`.

---

### `automationName = "UIAutomator2"`

Define o **driver/motor de automação** usado no Android.  
`UIAutomator2` é o padrão recomendado para Android no Appium 2, pois oferece boa compatibilidade com Android moderno e suporte amplo a interações.

---

### `deviceName`

Identifica o alvo (device/emulador). Em execuções com **device físico via USB**, esse valor costuma ser o **serial** que aparece no `adb devices`.

Para descobrir o serial:

```bash
adb devices
```
Saída comum:
```txt
List of devices attached
RX8R808B8TL    device
```
Nesse caso, o deviceName é RX8R808B8TL.

### `appPackage` e `appActivity`
Usados quando o app já está instalado no device/emulador.

- appPackage: pacote Android do app (equivalente ao applicationId).
- appActivity: activity principal/inicial (ou a tela que você quer abrir).

Esses valores dizem ao Appium: “inicie o app X e abra a activity Y”.

### Alternativa: `app` (APK local)

Se você quiser que o Appium instale o APK local automaticamente, você usa:
```java
.amend("app", System.getProperty("user.dir") + "/src/test/resources/TheApp.apk")
```

Nesse modo, você geralmente não precisa setar appPackage/appActivity manualmente (depende do app e da estratégia), porque o Appium pode derivar isso a partir do APK.

### `noReset`
Controla se o Appium deve “limpar” ou preservar dados do app entre execuções.

- noReset = true
Mantém o estado do app (não limpa dados/cache). Útil para testes que não devem reinstalar ou perder login.

- noReset = false
Faz reset de estado em cenários onde você quer começar sempre “do zero”.
Em projetos reais, isso ajuda a reduzir flakiness em suites que dependem de estado previsível.

> Dica prática: se estiver ficando lento porque reinstala/limpa tudo sempre, testar noReset=true pode acelerar.

### Appium Server URL
```java
URL serverUrl = new URL("http://127.0.0.1:4723");
driver = new AndroidDriver(serverUrl, options);
```
Esse é o endereço do **Appium Server**.

Antes de rodar os testes, o Appium precisa estar rodando no terminal:
```cmd
appium
```
Se o Appium não estiver no ar, o driver não consegue iniciar a sessão.

### Encerramento do driver (tearDown)
```java
public static void tearDown() {
    if (driver != null) {
        driver.quit();
        driver = null;
    }
}
```
- quit() encerra a sessão no Appium Server e libera recursos.
- driver = null garante que, no próximo teste, um novo driver seja criado de forma limpa.

Isso evita:

- “sessão fantasma” no Appium,
- falhas intermitentes entre testes,
- consumo desnecessário de recursos no device/emulador.

---

## BasePage: o que é e como funciona

A `BasePage` é a classe base para todos os **Page Objects** do projeto. O objetivo dela é concentrar comportamentos comuns de interação com a UI e acesso ao driver, evitando duplicação de código nos Page Objects (ex.: `LoginPage`, `ClipboardPage`, etc.).

Em um projeto com POM, normalmente a `BasePage` centraliza:

- **Acesso ao driver** (obtido via `DriverFactory.getDriver()` ou injeção).
- **Interações comuns**: `click`, `sendKeys`, `getText`, `isDisplayed`.
- **Espera e sincronização**: métodos com *explicit waits* (`WebDriverWait`) e/ou utilitários para aguardar elementos.
- **Logs e evidências**: pontos únicos para anexar screenshots/steps (se aplicável).

---

## BaseTest: o que é e como funciona

A `BaseTest` é a classe base para as classes de teste (ex.: `LoginTest`, `ClipboardTest`). Ela organiza o **ciclo de vida do teste** e concentra comportamentos “cross-cutting”, como:

- Encerrar a sessão e anexar evidências no `@AfterEach`
- Preparar dados comuns, navegação inicial, configuração de ambiente, etc.

### Exemplo típico de responsabilidades
- `@AfterEach`: tirar screenshot, anexar no Allure, finalizar driver

> Se seus testes estendem `BaseTest`, você garante que todas as classes herdam esse setup/teardown automaticamente.

---

## LoginSelector: o que é e como funciona

O `LoginSelector` (ou `LoginSelectors`) é o arquivo que centraliza os **locators** da tela de login.  
Ele existe para separar claramente:

- **“Onde está o elemento”** (selector/locator)
- **“O que fazer com o elemento”** (métodos da página, dentro de `LoginPage`)

Esse padrão melhora a manutenção: quando um ID/XPath muda, você altera em um lugar só, sem quebrar toda a Page.

### O que geralmente fica no LoginSelector
- Identificadores (`By.id`, `By.xpath`, `MobileBy.AccessibilityId`, etc.)
- Constantes para textos/ids reutilizados
- Nomes consistentes por elemento (ex.: `USERNAME_INPUT`, `PASSWORD_INPUT`, `LOGIN_BUTTON`)

### Exemplo de estrutura (conceitual)
```java
public class LoginSelector {
    public static final By USERNAME_INPUT = By.id("...");
    public static final By PASSWORD_INPUT = By.id("...");
    public static final By LOGIN_BUTTON = By.id("...");
}
```
> O ideal é que LoginSelector não tenha lógica (sem métodos de ação), apenas “mapa” da UI.

--- 

## LoginPage: o que é e como funciona
O ```LoginPage``` é o **Page Object** da tela de login. Ele encapsula as interações de alto nível e oferece uma API mais “negócio” para os testes.

Em vez de o teste fazer ```driver.findElement(...).click()``` diretamente, ele chama:
- ```loginPage.fillUsername(...)```
- ```loginPage.fillPassword(...)```
- ```loginPage.clickLogin()```

Isso traz benefícios:
- os testes ficam mais legíveis e próximos do comportamento do usuário
- mudanças na UI afetam só o Page Object / Selector
- facilita reutilização das ações em múltiplos cenários

### O que geralmente fica no LoginPage

- Métodos de ação: preencher usuário/senha, clicar em login, abrir menu de login, etc.
- Métodos de verificação: checar se existe um texto, se um elemento está visível, etc.
- Reutilização dos locators do LoginSelector

### Exemplo de uso no teste
```java
    @Test
    public void loginSuccessfully() throws MalformedURLException {
        accessLoginMenu();
        loginPage.fillUsername("alice");
        loginPage.fillPassword("mypassword");
        loginPage.clickLogin();
        Assertions.assertTrue(loginPage.existElementByText("Secret Area"));
    }
```

--- 

## Pré-requisitos

### Obrigatórios
- [**Java 21** JDK](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- [**Maven**](https://maven.apache.org/download.cgi)
- [**Node.js** (para instalar Appium via npm)](https://nodejs.org/en/download/current)
- [**Appium Server** (executado via CLI)](https://appium.io/docs/en/2.0/quickstart/install/)
- [**Appium UiAutomator2 Driver** (Para executar testes no Android)](https://appium.io/docs/en/2.0/quickstart/uiauto2-driver/)
- [**Android Studio** (para gerenciar SDKs/emuladores)](https://developer.android.com/studio?hl=pt-br)
- **Android SDK Platform Tools** (Instalar através do Android Studio)
- [**Allure Commandline** (para gerar/visualizar o report)](https://allurereport.org/docs/v3/install/)

---

## Setup do ambiente (Windows)

### 1) Instalar Appium (global) e o driver UiAutomator2 (Appium 2)
No **PowerShell** (como usuário normal):

```powershell
npm i -g appium
appium -v
appium driver install uiautomator2
```

> Observação: o projeto usa `automationName=UIAutomator2`, então o driver UiAutomator2 deve estar instalado no Appium.

### 2) Garantir ADB no PATH
Verifique se o comando `adb` está disponível:

```powershell
adb version
```

Se não estiver, adicione o caminho do Android SDK **platform-tools** no PATH.
Exemplo comum (ajuste conforme sua instalação):
- `%LOCALAPPDATA%\Android\Sdk\platform-tools`

Depois, feche e reabra o terminal e valide novamente `adb version`.

### 3) Preparar o dispositivo Android
- Ative **Opções do desenvolvedor**
- Ative **Depuração USB**
- Conecte via USB e confirme o prompt “Permitir depuração USB” (chave RSA)

OU

### 4) Utilizar emulador
- Criar um Virtual Device no Android Studio
---

## Como identificar o device (adb devices)

Com o dispositivo conectado (ou emulador aberto), execute:

```powershell
adb devices
```

Exemplo de saída:

```text
List of devices attached
RX8R808B8TL     device
```

O valor da **primeira coluna** (no exemplo, `RX8R808B8TL`) é o identificador do dispositivo (serial/UDID).  
Você deve usar esse valor no `DriverFactory`, hoje configurado em:

```java
.amend("deviceName", "RX8R808B8TL")
```

Se o `adb devices` mostrar `unauthorized`, desbloqueie o celular e aceite o prompt de autorização.

---

## Como iniciar o Appium via CLI

Antes de rodar os testes, o **Appium Server precisa estar rodando** em um terminal separado.

Na raiz do projeto (ou em qualquer pasta), execute:

```powershell
appium
```

Deixe esse terminal aberto durante a execução dos testes.

### Validar rapidamente o servidor
Em outro terminal, você pode validar o status do servidor:

```powershell
curl http://127.0.0.1:4723/status
```

> Observação: este projeto inicializa o driver com `http://127.0.0.1:4723` (sem `/wd/hub`), que é o padrão do Appium 2.  
> Se você estiver usando Appium 1 por algum motivo, normalmente o endpoint inclui `/wd/hub` — ajuste o `DriverFactory` conforme necessário.

---

## Como executar os testes

Na raiz do projeto:

### Baixar dependências e compilar
```powershell
mvn clean install
```

### Rodar todos os testes
```powershell
mvn test
```

### Rodar uma classe específica
```powershell
mvn -Dtest=LoginTest test
```

### Rodar um método específico
```powershell
mvn -Dtest=LoginTest#loginSuccessfully test
```

---

## Como gerar e abrir o Allure Report

Após rodar os testes, os resultados ficam em:

- `target\allure-results\`

### Abrir relatório temporário (recomendado)
```powershell
allure serve target\allure-results
```

### Gerar relatório persistente
```powershell
allure generate target\allure-results -o target\allure-report
allure open target\allure-report
```

---

## Allure Steps (passos) nos testes

O Allure **não cria Steps automaticamente** com base nos métodos que você chama.
Para exibir “passos” no relatório, você precisa instrumentar o código de teste/page objects:

### `@Step` no Page Object / BaseTest
Exemplo:

```java
import io.qameta.allure.Step;

@Step("Preencher usuário: {username}")
public void fillUsername(String username) { ... }
```

---

## Troubleshooting

### `adb devices` não lista o device
- Troque cabo/porta USB (precisa ser cabo com dados)
- Verifique se a depuração USB está ativa
- Confirme que o ADB está no PATH (`adb version`)

### `adb devices` mostra `unauthorized`
- Desbloqueie o device e aceite a permissão de depuração USB
- Em Opções do desenvolvedor, revogue autorizações USB e reconecte

### Erro de conexão com Appium
- Confirme que o Appium está rodando no CLI e na porta 4723
- Valide `curl http://127.0.0.1:4723/status`
- Verifique se o driver UiAutomator2 foi instalado (`appium driver list --installed`)

### O app não abre / activity não encontrada
- Revise `appPackage` e `appActivity` no `DriverFactory`
- Se preferir usar o APK local, configure a capability `app` apontando para o arquivo (`TheApp.apk`) e ajuste conforme seu cenário

---
