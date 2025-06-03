package pokerGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

/**
 * 
 * Requirements to run this class: Gson library (should already be in the buildpath and the Minstral AI installed
 * @author Patrick Christmas, check works cited, lots of lifted code used
 * @version May 30 2025 
 */
public class LLMClient {

    /** 
     *  endpoint where LM Studio is listening for completions
     *  
     *  IMPORTANT!!!!!! To Mrs. Kelly: If you are running this on YOUR Computer, you have to but your own ENDPOINT link here!!!
     **/
    private static final String ENDPOINT = "http://127.0.0.1:1234/v1/chat/completions";

    /**
     * sends a user prompt to the local LLM and retrieves the response
     * @param prompt the text prompt to send to the language model
     * @return the reply, or an error message if something goes wrong
     */
    public static String getChatResponse(String prompt) {
        try {
            // Setup HTTP connection
            HttpURLConnection conn = (HttpURLConnection) new URL(ENDPOINT).openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            // Construct user message
            JsonObject userMessage = new JsonObject();
            userMessage.addProperty("role", "user");
            userMessage.addProperty("content", prompt);

            // Add message to array
            JsonArray messages = new JsonArray();
            messages.add(userMessage);

            // Build full payload
            JsonObject payload = new JsonObject();
            payload.addProperty("model", "mistralai/mistral-7b-instruct-v0.3");
            payload.add("messages", messages);

            // Send request payload
            try (OutputStream os = conn.getOutputStream()) {
                os.write(payload.toString().getBytes(StandardCharsets.UTF_8));
            }

            // Read full response from the server
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            // log the payload for debugging
            System.out.println("SENDING PAYLOAD TO LLM:\n" + payload.toString());

            // parse LLM response using Gson
            JsonObject json = JsonParser.parseString(response.toString()).getAsJsonObject();
            JsonArray choices = json.getAsJsonArray("choices");

            if (choices != null && choices.size() > 0) {
                JsonObject messageObj = choices.get(0).getAsJsonObject().getAsJsonObject("message");
                return messageObj.get("content").getAsString().trim();
            } else {
                return "ERROR: No choices returned from LLM.";
            }

        } catch (IOException | JsonParseException e) {
            return "ERROR: " + e.getMessage();
        }
    }

    /**
     * Returns a string describing the purpose of this utility.
     *
     * @return description of LLMClient class
     */
    @Override
    public String toString() {
        return "LLMClient[Utility for sending prompts to LM Studio (Mistral) and parsing responses]";
    }
}