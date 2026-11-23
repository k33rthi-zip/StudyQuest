package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;

public final class Y2KStyle {

    public static final Color SILVER = new Color(192, 192, 192);
    public static final Color NAVY = new Color(0, 0, 128);
    public static final Color SKY = new Color(16, 132, 208);
    public static final Color ERROR_RED = new Color(160, 0, 0);

    public static final Font UI_FONT = new Font("Tahoma", Font.PLAIN, 14);
    public static final Font LABEL_FONT = new Font("Tahoma", Font.PLAIN, 16);
    public static final Font SECTION_FONT = new Font("Tahoma", Font.BOLD, 17);
    public static final Font TITLE_FONT = new Font("Tahoma", Font.BOLD, 16);
    public static final Font STATUS_FONT = new Font("Tahoma", Font.BOLD, 16);
    public static final Font MONO_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 14);

    private static final Color LIGHT = new Color(223, 223, 223);
    private static final Color DARK = new Color(128, 128, 128);

    private Y2KStyle() {
    }

    public static Border raised() {
        return new BevelBorder(BevelBorder.RAISED, Color.WHITE, LIGHT, Color.BLACK, DARK);
    }

    public static Border sunken() {
        return new BevelBorder(BevelBorder.LOWERED, Color.WHITE, LIGHT, DARK, Color.BLACK);
    }

    /**
     * An etched group-box border with a large bold heading, like "New Task" or "Task List".
     * @param title the heading
     * @return the border
     */
    public static Border section(String title) {
        return BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                title, TitledBorder.LEFT, TitledBorder.TOP, SECTION_FONT);
    }

    public static JButton button(String text) {
        return new Y2KButton(text, 4, 18);
    }

    public static JButton compactButton(String text) {
        return new Y2KButton(text, 2, 10);
    }

    public static JLabel label(String text) {
        final JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        return label;
    }

    public static void styleInput(JTextComponent field) {
        field.setFont(UI_FONT);
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(sunken(), BorderFactory.createEmptyBorder(2, 4, 2, 4)));
    }

    /**
     * A navy-to-sky gradient title bar with the title on the left and a button on the right.
     * @param title the window title
     * @param rightButton the button shown at the right end of the bar
     * @return the title bar
     */
    public static JPanel titleBar(String title, JButton rightButton) {
        final JPanel bar = new JPanel(new BorderLayout(8, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                final Graphics2D g2 = (Graphics2D) g.create();
                g2.setPaint(new GradientPaint(0, 0, NAVY, getWidth(), 0, SKY));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        bar.setOpaque(false);
        bar.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 3));

        final JLabel text = new JLabel(title);
        text.setFont(TITLE_FONT);
        text.setForeground(Color.WHITE);
        bar.add(text, BorderLayout.CENTER);

        bar.add(rightButton, BorderLayout.EAST);
        return bar;
    }

    private static final class Y2KButton extends JButton {

        private final Border up;
        private final Border down;

        Y2KButton(String text, int vertical, int horizontal) {
            super(text);
            up = BorderFactory.createCompoundBorder(raised(),
                    BorderFactory.createEmptyBorder(vertical, horizontal, vertical, horizontal));
            down = BorderFactory.createCompoundBorder(sunken(),
                    BorderFactory.createEmptyBorder(vertical + 1, horizontal + 1, vertical - 1, horizontal - 1));
            setFont(UI_FONT);
            setBackground(SILVER);
            setForeground(Color.BLACK);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setOpaque(true);
            setBorder(up);
            getModel().addChangeListener(event -> setBorder(
                    getModel().isArmed() && getModel().isPressed() ? down : up));
        }
    }
}