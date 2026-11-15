package org.example.hajarsamouh.jsf;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;
import dev.langchain4j.model.output.TokenUsage;

public class Test2Couts {
    public static void main(String[] args) {
        ChatModel model = GoogleGenAiChatModel.builder()
                .apiKey(System.getenv("GEMINI_API_KEY"))
                .modelName("gemini-3.5-flash-lite")
                .temperature(0.7)
                .build();

        ChatResponse reponse = model.chat(UserMessage.from("Je dois partir dans 2 jours à Paris ; j'y resterai 3 jours. Est-ce que je dois mettre un parapluie dans mes valises ?"));
        System.out.println("Réponse : " + reponse.aiMessage().text());

        // Nombres de tokens
        TokenUsage usage = reponse.tokenUsage();
        int entree = usage.inputTokenCount();
        int sortieVisible = usage.outputTokenCount();
        int total = usage.totalTokenCount();
        int sortieFacturee = total - entree;

        System.out.println("Tokens en entrée  : " + entree);
        System.out.println("Tokens en sortie  : " + sortieVisible);
        System.out.println("Tokens au total   : " + total);
        System.out.println("Tokens de réflexion (déduits) : " + (total - entree - sortieVisible));

        // Calcul du coût
        final double PRIX_ENTREE = 0.30;  // $ par million de tokens
        final double PRIX_SORTIE = 2.50;  // $ par million de tokens (réflexion comprise)

        double coutNaif = (entree * PRIX_ENTREE + sortieVisible * PRIX_SORTIE) / 1_000_000;
        double coutReel = (entree * PRIX_ENTREE + sortieFacturee * PRIX_SORTIE) / 1_000_000;

        System.out.printf("Coût naïf : $%.7f -> %.0f requêtes pour 1 $%n", coutNaif, 1 / coutNaif);
        System.out.printf("Coût réel : $%.7f -> %.0f requêtes pour 1 $%n", coutReel, 1 / coutReel);
    }
}