package org.example.hajarsamouh.jsf;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
//import dev.langchain4j.model.embedding.onnx.bgesmallenv15.BgeSmallEnV15EmbeddingModel;
import dev.langchain4j.model.google.genai.GoogleGenAiEmbeddingModel;
import dev.langchain4j.store.embedding.CosineSimilarity;

import java.time.Duration;

public class Test4Embeddings {

    public static void main(String[] args) {

        //EmbeddingModel modeleLocal = new BgeSmallEnV15EmbeddingModel();

        EmbeddingModel modeleGoogle = GoogleGenAiEmbeddingModel.builder()
                .apiKey(System.getenv("GEMINI_API_KEY"))
                .modelName("gemini-embedding-001")
                .taskType(GoogleGenAiEmbeddingModel.TaskTypeEnum.SEMANTIC_SIMILARITY)
                .timeout(Duration.ofSeconds(10))
                .build();

        String[][] couples = {
                {"J'adore jouer au golf.", "Les golf est mon sport préféré."},   // similaires
                {"Il fait très chaud aujourd'hui.", "Il fait très froid aujourd'hui."}, // opposées
                {"Le chat dort sur le canapé.", "Un animal se repose sur le sofa."},    // même sens, mots différents
                {"Je vais chez le medecin.", "La capitale du Maroc est Rabat."},  // sans rapport
                {"Le football est un sport populaire.", "Beaucoup de gens aiment regarder le foot."},
                {"Java est un langage de programmation.", "Le café est une boisson chaude."}
        };

        for (String[] couple : couples) {
            //double simLocal = similarite(modeleLocal, couple[0], couple[1]);
            double simGoogle = similarite(modeleGoogle, couple[0], couple[1]);

            System.out.println("Phrase 1 : " + couple[0]);
            System.out.println("Phrase 2 : " + couple[1]);
            //System.out.printf("  Modèle intégré : %.4f%n", simLocal);
            System.out.printf("  Modèle Google  : %.4f%n%n", simGoogle);
        }
    }

    private static double similarite(EmbeddingModel modele, String texte1, String texte2) {
        Embedding e1 = modele.embed(texte1).content();
        Embedding e2 = modele.embed(texte2).content();
        return CosineSimilarity.between(e1, e2);
    }
}