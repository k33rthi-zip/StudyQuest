package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;

public final class Y2KStyle {
    public static final Color SILVER = new Color(192, 192, 192);
    public static final Color TEAL = new Color(0, 128, 128);
    public static final Color NAVY = new Color(0, 0, 128);
    public static final Color SKY = new Color(16, 132, 208);
    public static final Color ERROR_RED = new Color(160, 0, 0);

    public static final Font UI_FONT = new Font("Tahoma", Font.PLAIN, 12);
    public static final Font BOLD_FONT = UI_FONT.deriveFont(Font.BOLD);
    public static final Font MONO_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 12);

    private static final Color LIGHT = new Color(223, 223, 223);
    private static final Color DARK = new Color(128, 128, 128);

    private Y2KStyle() { }

    public static Border raised() {
        return new BevelBorder(BevelBorder.RAISED, Color.WHITE, LIGHT, Color.BLACK, DARK);
    }

    public static Border sunken() {
        return new BevelBorder(BevelBorder.LOWERED, Color.WHITE, LIGHT, DARK, Color.BLACK);
    }

    public static JButton button(String text) { return new Y2KButton(text); }

    public static JLabel label(String text) {
        final JLabel label = new JLabel(text);
        label.setFont(UI_FONT);
        return label;
    }

    public static void styleInput(JTextComponent field) {
        field.setFont(UI_FONT);
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(sunken(), BorderFactory.createEmptyBorder(2, 4, 2, 4)));
    }

    public static JPanel titleBar(String title) {
        final JPanel bar = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                final Graphics2D g2 = (Graphics2D) g.create();
                g2.setPaint(new GradientPaint(0, 0, NAVY, getWidth(), 0, SKY));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        bar.setOpaque(false);
        bar.setBorder(BorderFactory.createEmptyBorder(3, 6, 3, 3));

        final JLabel text = new JLabel(title);
        text.setFont(BOLD_FONT);
        text.setForeground(Color.WHITE);
        bar.add(text, BorderLayout.CENTER);

        final JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 2, 0));
        buttons.setOpaque(false);
        for (String glyph : new String[] {"_", "o", "X"}) {
            final JLabel box = new JLabel(glyph, SwingConstants.CENTER);
            box.setFont(BOLD_FONT);
            box.setOpaque(true);
            box.setBackground(SILVER);
            box.setBorder(raised());
            box.setPreferredSize(new Dimension(18, 16));
            buttons.add(box);
        }
        bar.add(buttons, BorderLayout.EAST);
        return bar;
    }

    private static final class Y2KButton extends JButton {
        private final Border up = BorderFactory.createCompoundBorder(raised(),
                BorderFactory.createEmptyBorder(3, 16, 3, 16));
        private final Border down = BorderFactory.createCompoundBorder(sunken(),
                BorderFactory.createEmptyBorder(4, 17, 2, 15));

        Y2KButton(String text) {
            super(text);
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