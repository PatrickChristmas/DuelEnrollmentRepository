package pokerGame;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * ChatPanel allows the user to select an AI player and talk to it
 * It has a dropdown to select the AI, a scrollable text area for the conversation, and an input field with a send button to communicate.
 * @author Patrick Christmas, significant inspiration from DJ-Raven's Github
 * @version June 1 2025
 */
public class ChatPanel extends JPanel {
    private JTextArea chatArea;
    private JTextField inputField;
    private JComboBox<AIPlayer> aiSelector;

    /**
     * constructs the ChatPanel with the given list of AIPlayer opponents
     * @param aiPlayers the list of AI players the user can chat with
     */
    public ChatPanel(ArrayList<AIPlayer> aiPlayers) {
        setLayout(new BorderLayout());

        // chat display
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        // aI Selector
        aiSelector = new JComboBox<>(aiPlayers.toArray(new AIPlayer[0]));
        if (!aiPlayers.isEmpty()) {
            aiSelector.setSelectedIndex(0); // ensure selection match
        }
        aiSelector.addActionListener(e -> {
            AIPlayer selectedAI = (AIPlayer) aiSelector.getSelectedItem();
            if (selectedAI != null) {
                chatArea.append("\nNow chatting with: " + selectedAI.getName() + "\n");
            }
        });

        aiSelector.setPreferredSize(new Dimension(200, 30));
        JPanel selectorPanel = new JPanel();
        selectorPanel.add(new JLabel("Talk to:"));
        selectorPanel.add(aiSelector);
        add(selectorPanel, BorderLayout.NORTH);

        // Input panel with text field and send button
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        JButton sendButton = new JButton("Send");

        sendButton.addActionListener((ActionEvent e) -> {
            String userInput = inputField.getText().trim();
            if (!userInput.isEmpty()) {
                AIPlayer selectedAI = (AIPlayer) aiSelector.getSelectedItem();
                System.out.println("ACTUAL SELECTED AI IN CHAT: " + selectedAI.getName());
                if (selectedAI != null) {
                    chatArea.append("You: " + userInput + "\n");

                    System.out.println("Selected AI: " + selectedAI.getName());
                    String prompt = GamePromptBuilder.buildChatPrompt(selectedAI, userInput);
                    System.out.println("FULL CHAT PROMPT SENT:\n" + prompt);

                    String response = LLMClient.getChatResponse(prompt);
                    chatArea.append(selectedAI.getName() + ": " + response + "\n");
                }

                inputField.setText("");
            }
        });

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);
    }

    /**
     * Returns a string representation of the ChatPanel for debugging purposes.
     *
     * @return basic summary including selected AI and chat field state
     */
    @Override
    public String toString() {
        AIPlayer selected = (AIPlayer) aiSelector.getSelectedItem();
        String name = (selected != null) ? selected.getName() : "None";
        return "ChatPanel[Current AI: " + name + ", Chat Lines: " + chatArea.getLineCount() + "]";
    }
}
