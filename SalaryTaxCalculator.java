import javax.swing.*;
import java.awt.*;

public class SalaryTaxCalculator extends JFrame {

    private static final double TAX_THRESHOLD = 7000.0;
    private static final double TAX_RATE = 0.25;

    private final JComboBox<String> companyBox;
    private final JTextField salaryField;
    private final JTextArea reportArea;

    public SalaryTaxCalculator() {
        setTitle("Salary Tax Calculator");
        setSize(500, 480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel companyLabel = new JLabel("Select Company:");
        companyLabel.setBounds(30, 30, 150, 30);
        add(companyLabel);

        // Edit this list to match your assignment's companies
        companyBox = new JComboBox<>(new String[]{"RBI", "Company B", "Company C"});
        companyBox.setBounds(200, 30, 250, 30);
        add(companyBox);

        JLabel salaryLabel = new JLabel("Enter Salary:");
        salaryLabel.setBounds(30, 80, 150, 30);
        add(salaryLabel);

        salaryField = new JTextField();
        salaryField.setBounds(200, 80, 250, 30);
        add(salaryField);

        JButton calcButton = new JButton("Calculate");
        calcButton.setBounds(50, 130, 150, 40);
        add(calcButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(250, 130, 150, 40);
        add(clearButton);

        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        JScrollPane scroll = new JScrollPane(reportArea);
        scroll.setBounds(30, 200, 420, 220);
        add(scroll);

        calcButton.addActionListener(e -> calculate());
        clearButton.addActionListener(e -> clear());
    }

    private void calculate() {
        String company = (String) companyBox.getSelectedItem();
        String input = salaryField.getText().trim().replace(',', '.');

        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a salary.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double salary;
        try {
            salary = Double.parseDouble(input);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Salary must be a valid number.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (salary < 0) {
            JOptionPane.showMessageDialog(this, "Salary cannot be negative.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Less than R7000 = no tax; otherwise 25%
        double tax = (salary < TAX_THRESHOLD) ? 0.0 : salary * TAX_RATE;
        double total = salary - tax;

        reportArea.setText(
                "******** SALARY REPORT ********\n\n" +
                "Company : " + company + "\n" +
                String.format("Salary  : R%.2f%n", salary) +
                String.format("Tax     : R%.2f%n", tax) +
                "--------------------------------\n" +
                String.format("Total Salary After Tax : R%.2f%n", total));
    }

    private void clear() {
        companyBox.setSelectedIndex(0);
        salaryField.setText("");
        reportArea.setText("");
        salaryField.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SalaryTaxCalculator().setVisible(true));
    }
}
