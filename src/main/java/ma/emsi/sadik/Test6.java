package ma.emsi.sadik;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.service.AiServices;

import java.time.Duration;
import java.util.Scanner;

public class Test6 {

    interface AssistantMeteo {
        String chat(String userMessage);
    }

    public static void main(String[] args) {
        String llmKey = System.getenv("GEMINI_API_KEY");
        if (llmKey == null || llmKey.isEmpty()) {
            System.err.println("ERREUR : La variable d'environnement 'GEMINI_API_KEY' n'est pas définie.");
            return;
        }

        // 2. Création du ChatModel avec le logging activé
        ChatModel model = GoogleAiGeminiChatModel.builder()
                .apiKey(llmKey)
                .modelName("gemini-2.5-flash")
                .temperature(0.3)
                .timeout(Duration.ofSeconds(60))
                .logRequests(true)
                .logResponses(true)
                .build();

        AssistantMeteo assistant =
                AiServices.builder(AssistantMeteo.class)
                        .chatModel(model)
                        .tools(new MeteoTool())
                        .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                        .build();

        System.out.println("Assistant Météo prêt. (tapez 'exit' pour quitter)");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\nVous : ");
            String userMessage = scanner.nextLine();

            if ("exit".equalsIgnoreCase(userMessage.trim())) {
                System.out.println("Au revoir !");
                break;
            }

            String assistantResponse = assistant.chat(userMessage);
            System.out.println("Assistant : " + assistantResponse);
        }
        scanner.close();
    }
}
