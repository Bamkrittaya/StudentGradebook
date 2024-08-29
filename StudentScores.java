import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
public class StudentScores {
 
    private static final int NUM_SUBJECTS = 5;
    private static final String[] SUBJECT_NAMES = {"Maths", "Science", "English", "Economy", "History"};
    private List<String> studentNames;
    private List<int[]> studentScores;
 
    public static void main(String[] args) {
        StudentScores studentScores = new StudentScores();
        studentScores.run();
    }
 
 
    public void run() {
        studentNames = new ArrayList<>();
        studentScores = new ArrayList<>();
 
        // Create the table model
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Allow editing only for score columns
                return column >= 1 && column <= NUM_SUBJECTS;
            }
        };
        model.addColumn("Name");
        for (String subject : SUBJECT_NAMES) {
            model.addColumn(subject);
        }
        model.addColumn("Average");
 
        JTable table = new JTable(model);
 
        // Set preferred width for the student names column
        table.getColumnModel().getColumn(0).setPreferredWidth(200); // Adjust the width as needed
 
        // Create a cell editor for score columns
        DefaultCellEditor scoreCellEditor = new DefaultCellEditor(new JTextField()) {
            @Override
            public boolean stopCellEditing() {
                boolean result = super.stopCellEditing();
 
                // Update the average when editing is complete
                int row = table.getEditingRow();
                int column = table.getEditingColumn();
                if (column >= 1 && column <= NUM_SUBJECTS) {
                    int[] scores = new int[NUM_SUBJECTS];
                    for (int j = 0; j < NUM_SUBJECTS; j++) {
                        scores[j] = (int) table.getValueAt(row, j + 1);
                    }
                    double average = calculateAverage(scores);
                    table.setValueAt(String.format("%.2f", average), row, NUM_SUBJECTS + 1);
                }
 
                return result;
            }
        };
 
        // Set the cell editor for score columns
        for (int column = 1; column <= NUM_SUBJECTS; column++) {
            table.getColumnModel().getColumn(column).setCellEditor(scoreCellEditor);
        }
 
        // Create a scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(table);
 
        // Add the scroll pane to a panel
        JPanel panel = new JPanel();
        panel.add(scrollPane);
 
        // Create a dialog with the panel
        JDialog dialog = new JDialog();
        dialog.setContentPane(panel);
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.pack();
        dialog.setVisible(true);
 
        // Prompt for student data
        int studentCount = 0;
        while (true) {
            String studentName = JOptionPane.showInputDialog(null, "Enter the name of student " + (studentCount + 1) + ":");
            if (studentName == null) {
                // User clicked cancel or closed the dialog
                break;
            }
 
            int[] scores = new int[NUM_SUBJECTS];
            // Re-enter the scores
            for (int j = 0; j < NUM_SUBJECTS; j++) {
                String scoreInput;
                int score = 0; // Initialize score variable
                boolean isScoreValid = false;
                do {
                    scoreInput = JOptionPane.showInputDialog(null, "Enter the score for " + SUBJECT_NAMES[j] + ":");
                    if (scoreInput == null) {
                        // User clicked cancel or closed the dialog
                        return;
                    }
 
                    try {
                        score = Integer.parseInt(scoreInput);
                        if (score >= 0 && score <= 100) {
                            isScoreValid = true;
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid score. Please enter a score between 0 and 100.");
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid score.");
                    }
                } while (!isScoreValid);
                scores[j] = score;
            }
            // Add student data to the table
            Object[] rowData = new Object[NUM_SUBJECTS + 2];
            rowData[0] = studentName;
            for (int j = 0; j < NUM_SUBJECTS; j++) {
                rowData[j + 1] = scores[j];
            }
            double average = calculateAverage(scores);
            rowData[NUM_SUBJECTS + 1] = String.format("%.2f", average);
            model.addRow(rowData);
 
            // Confirm the entered scores
            int confirmResult = JOptionPane.showConfirmDialog(null, "Are the scores correct?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirmResult == JOptionPane.YES_OPTION) {
                studentNames.add(studentName);
                studentScores.add(scores);
                studentCount++;
                // Write data to file
                
                
            } else if (confirmResult == JOptionPane.NO_OPTION) {
                // Handle incorrect scores
                boolean reenterScores = true;
                while (reenterScores) {
                    String[] options = new String[NUM_SUBJECTS + 2];
                    options[0] = "Name";
                    for (int i = 0; i < NUM_SUBJECTS; i++) {
                        options[i + 1] = SUBJECT_NAMES[i];
                    }
                    options[NUM_SUBJECTS + 1] = "Cancel";
 
                    int inputOption = JOptionPane.showOptionDialog(null, "Which part would you like to re-enter?", "Incorrect Input",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
 
                    switch (inputOption) {
                        case 0:
                            // Re-enter the student name
                            studentName = JOptionPane.showInputDialog(null, "Enter the corrected name for student " + (studentCount + 1) + ":");
                            if (studentName == null) {
                                // User clicked cancel or closed the dialog
                                return;
                            }
                            break;
                        case NUM_SUBJECTS + 1:
                            // Cancel adding the student
                            reenterScores = false;
                            break;
                        default:
                            // Re-enter the score for the selected subject
                            int selectedSubjectIndex = inputOption - 1;
                            String scoreInput;
                            int score = 0; // Initialize score variable
                            boolean isScoreValid = false;
                            do {
                                scoreInput = JOptionPane.showInputDialog(null, "Enter the corrected score for " + SUBJECT_NAMES[selectedSubjectIndex] + ":");
                                if (scoreInput == null) {
                                    // User clicked cancel or closed the dialog
                                    return;
                                }
 
                                try {
                                    score = Integer.parseInt(scoreInput);
                                    if (score >= 0 && score <= 100) {
                                        isScoreValid = true;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Invalid score. Please enter a score between 0 and 100.");
                                    }
                                } catch (NumberFormatException e) {
                                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid score.");
                                }
                            } while (!isScoreValid);
 
                            // Update the selected score
                            scores[selectedSubjectIndex] = score;
                            break;
                    }
 
                    // Remove the last added row
                    model.removeRow(model.getRowCount() - 1);
 
                    // Add student data to the table
                    rowData = new Object[NUM_SUBJECTS + 2];
                    rowData[0] = studentName;
                    for (int j = 0; j < NUM_SUBJECTS; j++) {
                        rowData[j + 1] = scores[j];
                    }
                    double averageScore = calculateAverage(scores);
                    rowData[NUM_SUBJECTS + 1] = String.format("%.2f", averageScore);
 
                    model.addRow(rowData);
 
                    // If scores are re-entered, confirm again
                    if (inputOption != NUM_SUBJECTS + 1) {
                        confirmResult = JOptionPane.showConfirmDialog(null, "Are the scores correct now?", "Confirmation", JOptionPane.YES_NO_OPTION);
                        if (confirmResult == JOptionPane.YES_OPTION) {
                            studentNames.add(studentName);
                            studentScores.add(scores);
                            studentCount++;
                            
                            // Write data to file
                            
                            
                            reenterScores = false; // Exit the loop
                        }
                    }
                }
             
            }
            // Ask if the user wants to add another student
            int addAnother = JOptionPane.showConfirmDialog(null, "Do you want to add another student?", "Add Student", JOptionPane.YES_NO_OPTION);
            
            if (addAnother == JOptionPane.NO_OPTION)
                break;                      
            }
        for (int i = 0; i < studentNames.size(); i++) {
            writeToFile(studentNames.get(i), calculateAverage(studentScores.get(i)));
        }
    
    }
 
    private void writeToFile(String name, double average) {
        try {
            FileWriter writer = new FileWriter("grades.txt", true); // Append to the file instead of overwriting it
            writer.write(name + "'s GPA = " + String.format("%.2f", average) + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private double calculateAverage(int[] scores) {
        int sum = 0;
        for (int score : scores) {
            sum += score;
        }
        return (double) sum / scores.length;
    }
}
