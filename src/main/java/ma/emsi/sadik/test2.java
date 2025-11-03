package ma.emsi.sadik;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;

import java.util.Map;

public class test2 {

    public static void main(String[] args) {
        String cle = System.getenv("GEMINI_API_KEY");
        if (cle == null || cle.isEmpty()) {
            System.err.println("ERREUR : La variable d'environnement 'GEMINI-API-KEY' n'est pas définie.");
            return;
        }

        ChatModel modele = GoogleAiGeminiChatModel.builder()
                .apiKey(cle)
                .modelName("gemini-2.5-flash")
                .temperature(0.7)
                .build();


        PromptTemplate template = PromptTemplate.from(
                "Traduis le texte suivant en anglais : {{texte}}"
        );


        String texteFrancais = "Bonjour ! J'apprends à utiliser LangChain4j, c'est très puissant.";


        Prompt prompt = template.apply(Map.of("texte", texteFrancais));


        System.out.println("Texte original : " + texteFrancais);
        System.out.println("-------------------------------------");
        System.out.println("Prompt envoyé au LLM : " + prompt.text());
        System.out.println("-------------------------------------");


        String reponse = modele.chat(prompt.text());

        System.out.println("Réponse (Traduction) :");
        System.out.println(reponse);
    }
}