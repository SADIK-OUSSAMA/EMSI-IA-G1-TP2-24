package ma.emsi.sadik;


import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.googleai.GoogleAiEmbeddingModel;
import dev.langchain4j.model.googleai.GoogleAiEmbeddingModel.TaskType;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.CosineSimilarity;

import java.time.Duration;

public class test3 {

    public static void main(String[] args) {
        String cle = System.getenv("GEMINI_API_KEY");
        if (cle == null || cle.isEmpty()) {
            System.err.println("ERREUR : La variable d'environnement 'GEMINI-API-KEY' n'est pas définie.");
            return;
        }

        try {
            EmbeddingModel modele = GoogleAiEmbeddingModel.builder()
                    .apiKey(cle)
                    .modelName("gemini-embedding-001")
                    .taskType(TaskType.SEMANTIC_SIMILARITY)
                    .outputDimensionality(300)
                    .timeout(Duration.ofSeconds(2))
                    .build();

            System.out.println("Modèle d'embedding créé. Test des similarités...");


            calculateAndPrintSimilarity(modele,
                    "Bonjour, comment allez-vous ?",
                    "Salut, quoi de neuf ?");

            calculateAndPrintSimilarity(modele,
                    "J'adore la programmation orientée objet.",
                    "Mon supermarché est fermé le dimanche.");

            calculateAndPrintSimilarity(modele,
                    "Paris est une ville magnifique au printemps.",
                    "La capitale de la France est connue pour sa tour Eiffel.");

        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de l'exécution :");
            e.printStackTrace();
            System.err.println("\nNOTE : Si l'erreur est 'model not found', vérifiez la 'Note Critique' dans la réponse.");
        }
    }


    private static void calculateAndPrintSimilarity(EmbeddingModel model, String s1, String s2) {
        System.out.println("-------------------------------------");
        System.out.println("Phrase 1 : " + s1);
        System.out.println("Phrase 2 : " + s2);

        Response<Embedding> reponse1 = model.embed(s1);
        Response<Embedding> reponse2 = model.embed(s2);

        Embedding emb1 = reponse1.content();
        Embedding emb2 = reponse2.content();

        double similarite = CosineSimilarity.between(emb1, emb2);

        System.out.printf(" Similarité Cosinus : %.4f%n", similarite);
    }
}

