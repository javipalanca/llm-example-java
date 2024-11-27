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
import java.util.Scanner;

public class ConvBot {

        public static void main(String[] args) {
            String endpoint = "https://api.poligpt.upv.es";
            String apiKey = "your-api-key";
            String model = "llama3.2:3b";
            OpenAIClient client = new OpenAIClientBuilder().endpoint(endpoint)
                    .credential(new AzureKeyCredential(apiKey)).buildClient();
            List<ChatRequestMessage> chatMessages = new ArrayList<>();
            chatMessages.add(new ChatRequestSystemMessage("Eres un ayudante servicial."));
            Scanner scanner = new Scanner(System.in);
            System.out.println("Bot conversacional iniciado. Escribe 'exit' para salir.");

            while (true) {
                System.out.print("Tú: ");
                String userInput = scanner.nextLine();
                if (userInput.equalsIgnoreCase("exit")) { System.out.println("Adiós."); break; }
                chatMessages.add(new ChatRequestUserMessage(userInput));
                ChatCompletionsOptions options = new ChatCompletionsOptions(chatMessages);
                ChatCompletions chatCompletions = client.getChatCompletions(model, options);
                ChatChoice choice = chatCompletions.getChoices().get(0);
                String assistantResponse = choice.getMessage().getContent();
                System.out.println("Asistente: " + assistantResponse); // Añadir la respuesta al historial
                ChatRequestMessage assistantMessage = new ChatRequestAssistantMessage(assistantResponse);

                // Añadir la respuesta al historial
                chatMessages.add(assistantMessage);

            }
            scanner.close();
        }
    }

