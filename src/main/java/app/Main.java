package app;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            final JFrame application = new AppBuilder()
                    .addHomeView()
                    .addAddTaskView()
                    .addAddTaskUseCase()
                    .addCompleteTaskUseCase()
                    .addProfileView()
                    .addStudyModeView()
                    .addViewAccountUseCase()
                    .build();
            application.setSize(1100, 760);
            application.setLocationRelativeTo(null);
            application.setVisible(true);
        });
    }
}