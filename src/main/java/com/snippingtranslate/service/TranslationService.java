package com.snippingtranslate.service;

// Importa a biblioteca Dotenv para carregar variáveis de ambiente do arquivo .env
import io.github.cdimascio.dotenv.Dotenv;

// Importa a classe DeepLClient para interagir com a API de tradução da DeepL
import com.deepl.api.DeepLClient;

// Importa a classe TextResult para receber o resultado da tradução
import com.deepl.api.TextResult;

// Declaração da classe pública TranslationService, responsável por serviços de tradução
public class TranslationService {

    // Método estático que recebe um texto e retorna sua tradução para português (Brasil)
    public static String translateText(String text) {
        try {
            // Carrega as variáveis de ambiente a partir do arquivo .env
            Dotenv dotenv = Dotenv.load();

            // Obtém a chave da API da DeepL da variável de ambiente DEEPL_API_KEY
            String authKey = dotenv.get("DEEPL_API_KEY");

            // Verifica se a chave é nula ou em branco; se for, lança uma exceção
            if (authKey == null || authKey.isBlank()) {
                throw new IllegalStateException("DEEPL_API_KEY não encontrada no arquivo .env");
            }

            // Cria um cliente DeepL utilizando a chave de autenticação
            DeepLClient cliente = new DeepLClient(authKey);

            // Chama o método de tradução: texto de entrada, idioma de origem null (detecção automática) e destino "pt-BR"
            TextResult result = cliente.translateText(text, null, "pt-BR");

            // Exibe o texto traduzido no console
            System.out.println(result.getText());

            // Retorna o texto traduzido
            return result.getText();
            
        } catch (Exception e) {
            // Em caso de erro, imprime uma mensagem no console de erro e retorna null
            System.err.println("❌ Erro na tradução: " + e.getMessage());
            return null; 
        }
    }
}