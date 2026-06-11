# SnipTranslate

**Capture trechos da tela, extraia texto automaticamente e traduza para o português — tudo em poucos cliques.**

SnippingTranslate é uma ferramenta open-source em Java criada para agilizar o fluxo de trabalho de quem precisa ler e compreender rapidamente conteúdos em inglês. Basta selecionar uma região da tela, e o texto é extraído via OCR e traduzido automaticamente. Ideal para estudantes, pesquisadores, desenvolvedores e qualquer pessoa que lide com documentação, artigos ou interfaces em inglês.

> ⚠️ **Status:** MVP em desenvolvimento ativo. Funcionalidades principais implementadas, mas melhorias de UI, performance e configuração estão em andamento.

---

## Visão Geral

O projeto nasceu da necessidade de traduzir trechos de tela sem interromper o fluxo de trabalho. Com o SnippingTranslate, você:

1. Abre a ferramenta (ícone na bandeja do sistema).
2. Aperta o atalho para iniciar a captura de tela.
3. Arrasta para selecionar a região desejada.
4. Aguarda o processamento (OCR + tradução).
5. Recebe o texto traduzido em uma overlay.

Tudo isso sem janelas poluídas ou configurações complicadas — pelo menos essa é a visão final. No estado atual (MVP), algumas partes ainda são manuais e a interface é mínima.

---

## Como Funciona

```
[Seleção da região] → [OCR com Tesseract] → [Tradução com DeepL] → [Resultado]
```

1. **Captura**: A tela inteira é congelada em uma sobreposição transparente. Você arrasta para selecionar a área de interesse.
2. **OCR**: A imagem recortada é enviada ao Tesseract (via `tess4j`), que extrai o texto em inglês.
3. **Tradução**: O texto extraído é enviado à API da DeepL (requer chave) e retorna a tradução em português brasileiro.
4. **Resultado**: O texto é exibido em uma overlay com opção de fechar e copiar a tradução.

---

## Tecnologias

| Tecnologia | Motivo da escolha

| **Tesseract 4 (via tess4j)** | Motor OCR open-source mais preciso e consolidado. A biblioteca `tess4j` simplifica a integração Java. |

| **Maven** | Gerenciamento de dependências simplificado e padrão no ecossistema Java. |

> _Nota:_ O Tesseract requer arquivos de dados de idioma (`tessdata`), que atualmente são resolvidos dinamicamente dentro do projeto (não é mais um caminho fixo). A instalação do Tesseract no sistema ainda é necessária, mas planejamos empacotá-la junto com o instalador.

---

## Requisitos de Sistema

- **Java Runtime (JRE) 17** ou superior – [Baixar aqui](https://adoptium.net/)

- **Conexão com internet** – necessária apenas para a API de tradução
- **Sistema operacional**: Windows 10/11 (testado)

---

## Instalação e Configuração

Baixe ou clone o repositório:

git clone https://github.com/daniloxn/SnipTranslate.git

ou acesse:

https://github.com/daniloxn/SnipTranslate

### 2. Configure a chave da API DeepL

1. Crie uma conta gratuita em [DeepL API Free](https://www.deepl.com/pt-BR/translator) e vá na aba de [API](https://www.deepl.com/pt-BR/your-account/keys) e obtenha sua chave de autenticação.
2. Assim que abrir o aplicativo pela primeira vez, coloque sua api-key.

### 3. Execute o JAR

Abra o terminal na pasta do arquivo e execute:

```bash
java -jar SnipTranslate.jar
```

ou apenas dando dois click no arquivo SnipTranslate.jar.

> 💡 **Dica**: Você pode criar um atalho na área de trabalho ou adicionar o JAR à inicialização automática do Windows.

---

## Como Usar

1. **Inicie a aplicação** – Um ícone aparecerá na bandeja do sistema (próximo ao relógio).
2. **Pressione o atalho** – A tela será coberta por uma camada semitransparente.
3. **Selecione a região** – Pressione e arraste o mouse para definir a área a ser capturada.
4. **Solte o mouse** – O processamento inicia automaticamente.
5. **Aguarde** – O texto extraído e traduzido será exibido na overlay.

> 📸 _Em breve: screenshot demonstrativa do fluxo._

---

## Estrutura do Projeto

```
SnippingTranslate/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── snippingtranslate/
│       │           ├── AppLauncher.java  # Ponto de entrada
│       │           ├── Main.java
│       │           ├── background/
│       │           │   ├── HotKeyListener.java
│       │           ├── config/
│       │           │   ├── ConfigManager.java
│       │           │   ├── LanguageConfig.java
│       │           │   ├── ThemeConstants.java
│       │           ├── icons/
│       │           │   ├── icon.png
│       │           │   ├── icon.ico
│       │           ├── screen/
│       │           │   ├── ApiKeySetupDialog.java
│       │           │   ├── FloatingPanel.java
│       │           │   ├── SettingsPanel.java
│       │           ├── service/
│       │           │   ├── ConfigService.java
│       │           │   ├── ScreenCaptureService.java
│       │           │   ├── OCRService.java
│       │           │   └── TranslationService.java
│       │           └── ui/
│       │               └── TrayIconManager.java
│       └── resources/
│           └── tessdata/                      # Dados de idioma do Tesseract
│               └── eng.traineddata
├── pom.xml                                    # Configuração Maven
├── .env                                       # Chave da API (não versionado)
├── README.md
├── SnipTranslate-1.0.jar
└── main.bat                                   # Inicializador improvisado para testes


```

---

## Limitações Atuais (MVP)

- **Performance**: Imagens grandes ou com muito texto podem demorar alguns segundos para processar.
- **Feedback**: Não há barra de progresso ou notificação quando o processamento termina (apenas console).
- **Configuração**: A chave da DeepL precisa ser manualmente colocada em um arquivo `.env`. Futuramente teremos uma janela de configuração. ✅
- **Tesseract**: Ainda depende de instalação separada no sistema. Planejamos empacotá-lo em versões futuras. ✅
- **Idiomas**: Suporte apenas para inglês → português. Expansão planejada. ✅

---

## Roadmap 🗺️

| Etapa | Status

| Ícone na bandeja do sistema | ✔️ Concluído | ✅

| Caminho dinâmico do tessdata | ✔️ Concluído | ✅

| **Melhorias futuras**

| Empacotar Tesseract no instalador .exe | 🔄 Planejado |

| Aprimoramento visual (UI/UX) | 🔄 Planejado | ✅

---

## Troubleshooting

**"A janela de captura não aparece"**
→ Certifique-se de estar usando um ambiente gráfico com suporte a janelas sem decoração (quase todos os sistemas desktop modernos). No Windows, execute com privilégios normais.

**"Performance muito lenta"**
→ Reduza a área de captura. Imagens grandes exigem mais processamento do Tesseract.

---

## Licença

Este projeto é distribuído sob a licença [MIT](LICENSE) (ou outra a definir). Sinta-se livre para usar, modificar e distribuir, desde que mantenha os créditos.

---

## Autor

Desenvolvido por [Danilo](https://github.com/seu-usuario) — entre em contato para dúvidas, sugestões ou contribuições.

---

**SnippingTranslate — tradução de tela ao alcance de um arrasto.**
