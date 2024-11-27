package com.example.llm;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.ChatChoice;
import com.azure.ai.openai.models.ChatCompletions;
import com.azure.ai.openai.models.ChatCompletionsOptions;
import com.azure.ai.openai.models.ChatRequestAssistantMessage;
import com.azure.ai.openai.models.ChatRequestMessage;
import com.azure.ai.openai.models.ChatRequestSystemMessage;
import com.azure.ai.openai.models.ChatRequestUserMessage;
import com.azure.ai.openai.models.ChatResponseMessage;
import com.azure.core.credential.AzureKeyCredential;

import java.util.ArrayList;
import java.util.List;

public class LLMTest {

    public static void main(String[] args) {
        String endpoint = "https://api.poligpt.upv.es";
        String apiKey = "your-api-key";
        String model = "llama3.2:3b";

        OpenAIClient client = new OpenAIClientBuilder()
                .endpoint(endpoint)
                .credential(new AzureKeyCredential(apiKey))
                .buildClient();

        List<ChatRequestMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatRequestSystemMessage("Eres un ayudante servicial. Hablarás como un pirata."));
        //chatMessages.add(new ChatRequestUserMessage("Can you help me?"));
        //chatMessages.add(new ChatRequestAssistantMessage("Of course, me hearty! What can I do for ye?"));
        chatMessages.add(new ChatRequestUserMessage("¿Cuál es la mejor manera de adiestrar a un loro?"));

        ChatCompletions chatCompletions = client.getChatCompletions(model, new ChatCompletionsOptions(chatMessages));

        System.out.printf("Model ID=%s is created at %s.%n", chatCompletions.getId(), chatCompletions.getCreatedAt());
        for (ChatChoice choice : chatCompletions.getChoices()) {
            ChatResponseMessage message = choice.getMessage();
            System.out.printf("Index: %d, Chat Role: %s.%n", choice.getIndex(), message.getRole());
            System.out.println("Message:");
            System.out.println(message.getContent());
        }
    }
}
