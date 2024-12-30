/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package e.commerce;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class FAQs {

    public static void load() {
       JFrame frame = new JFrame("FAQs about Online Shopping");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setResizable(false);

        // Centering the frame on the screen
        frame.setLocationRelativeTo(null);

        // Creating the panel that will hold the FAQs
        JPanel faqPanel = new JPanel();
        faqPanel.setLayout(new BoxLayout(faqPanel, BoxLayout.Y_AXIS));

        // Adding FAQs to the panel
        faqPanel.add(createFAQ("Q1: How do I place an order?", "A1: To place an order,Click on product and then place order or add items to your cart and proceed to checkout ."));
        
        
        faqPanel.add(createFAQ("Q2: How do I contact customer service?", "A5: You can contact customer service via email "));
    faqPanel.add(createFAQ("Q3: Is my personal information secure?", "A6: Yes, we protect your personal information."));
        faqPanel.add(createFAQ("Q4: How do I change my account information?", "A7: You can change your account information in the 'Manage Account' section."));
        faqPanel.add(createFAQ("Q5: What should I do if I receive a damaged item?", "A9: Contact customer service immediately to resolve the issue."));
        faqPanel.add(createFAQ("Q6: How do I leave a review?", "A10: You can leave a review on the My Reviews page after logging in."));

        // Adding the panel to a scroll pane
        JScrollPane scrollPane = new JScrollPane(faqPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Adding the scroll pane to the frame
        frame.getContentPane().add(scrollPane);
        frame.setVisible(true);
    }

    private static JPanel createFAQ(String question, String answer) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel questionLabel = new JLabel("<html><b>" + question + "</b></html>");
        JLabel answerLabel = new JLabel("<html>" + answer + "</html>");
        panel.add(questionLabel);
        panel.add(answerLabel);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return panel;
    }
}
