package ma.emsi.sadik;



import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;

import java.time.Duration;

public class test1 {

        public static void main(String[] args) {
            String cle = System.getenv("GEMINI_API_KEY");

            ChatModel modele = GoogleAiGeminiChatModel.builder()
                    .apiKey(cle)
                    .modelName("gemini-2.5-flash")
                    .temperature(0.7)
                    .build();

            String reponse = modele.chat("Quelle est la capitale du Japon ?");
            System.out.println(reponse);
        }
    }


