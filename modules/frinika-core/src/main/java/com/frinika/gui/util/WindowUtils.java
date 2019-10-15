package com.frinika.gui.util;

import com.bulenkov.darcula.DarculaLaf;
import com.bulenkov.darcula.DarculaLookAndFeelInfo;
import com.frinika.gui.util.handler.OkCancelService;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.plastic.theme.SkyBlue;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalTheme;
import javax.swing.text.JTextComponent;

/**
 * Utility static methods usable for windows and dialogs.
 *
 * @author hajdam
 */
@ParametersAreNonnullByDefault
public class WindowUtils {

    private static boolean darkMode = false;

    private static final int BUTTON_CLICK_TIME = 150;

    private WindowUtils() {
    }

    @Nonnull
    public static WindowHeaderPanel addHeaderPanel(Window window, Class<?> resourceClass, ResourceBundle resourceBundle) {
        URL iconUrl = resourceClass.getResource(resourceBundle.getString("header.icon"));
        Icon headerIcon = iconUrl != null ? new ImageIcon(iconUrl) : null;
        return addHeaderPanel(window, resourceBundle.getString("header.title"), resourceBundle.getString("header.description"), headerIcon);
    }

    @Nonnull
    public static WindowHeaderPanel addHeaderPanel(Window window, String headerTitle, String headerDescription, @Nullable Icon headerIcon) {
        WindowHeaderPanel headerPanel = new WindowHeaderPanel();
        headerPanel.setTitle(headerTitle);
        headerPanel.setDescription(headerDescription);
        if (headerIcon != null) {
            headerPanel.setIcon(headerIcon);
        }
        if (window instanceof WindowHeaderPanel.WindowHeaderDecorationProvider) {
            ((WindowHeaderPanel.WindowHeaderDecorationProvider) window).setHeaderDecoration(headerPanel);
        } else {
            Frame frame = getFrame(window);
            if (frame instanceof WindowHeaderPanel.WindowHeaderDecorationProvider) {
                ((WindowHeaderPanel.WindowHeaderDecorationProvider) frame).setHeaderDecoration(headerPanel);
            }
        }
        int height = window.getHeight() + headerPanel.getPreferredSize().height;
        ((JDialog) window).getContentPane().add(headerPanel, java.awt.BorderLayout.PAGE_START);
        window.setSize(window.getWidth(), height);
        return headerPanel;
    }

    public static void switchLookAndFeel(SupportedLaf selectedLaf) {
        switch (selectedLaf) {
            case DEFAULT: {
                try {
                    UIManager.setLookAndFeel(PlasticXPLookAndFeel.class.getCanonicalName());
                    MetalTheme currentTheme = PlasticXPLookAndFeel.getCurrentTheme();
                    if (!(currentTheme instanceof SkyBlue)) {
                        PlasticXPLookAndFeel.setCurrentTheme(PlasticXPLookAndFeel.getPlasticTheme());
                        UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
                    }
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    try {
                        PlasticXPLookAndFeel plasticXPLookAndFeel = new PlasticXPLookAndFeel();
                        UIManager.setLookAndFeel(plasticXPLookAndFeel);
                    } catch (UnsupportedLookAndFeelException ex2) {
                        Logger.getLogger(WindowUtils.class.getName()).log(Level.SEVERE, null, ex2);
                    }
                }
                WindowUtils.setDarkMode(false);
                break;
            }
            case DARCULA: {
                try {
                    // Workaround for https://github.com/bulenkov/iconloader/issues/14
                    javax.swing.UIManager.getFont("Label.font");

                    UIManager.setLookAndFeel(DarculaLaf.class.getCanonicalName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    try {
                        UIManager.installLookAndFeel(new DarculaLookAndFeelInfo());
                        UIManager.setLookAndFeel(new DarculaLaf());
                    } catch (UnsupportedLookAndFeelException ex2) {
                        Logger.getLogger(WindowUtils.class.getName()).log(Level.SEVERE, null, ex2);
                    }
                }
                WindowUtils.setDarkMode(true);
                break;
            }
        }
    }

    public static void invokeWindow(final Window window) {
        java.awt.EventQueue.invokeLater(() -> {
            if (window instanceof JDialog) {
                ((JDialog) window).setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            }

            window.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            window.setVisible(true);
        });
    }

    @Nonnull
    public static DialogWrapper createDialog(final Component component, Component parent, Dialog.ModalityType modalityType) {
        JDialog dialog = new JDialog(getWindow(parent), modalityType);
        initWindowByComponent(dialog, component);
        return new DialogWrapper() {
            @Override
            public void show() {
                dialog.setVisible(true);
            }

            @Override
            public void showCentered(@Nullable Component component) {
                center(component);
                show();
            }

            @Override
            public void close() {
                closeWindow(dialog);
            }

            @Override
            public void dispose() {
                dialog.dispose();
            }

            @Override
            public Window getWindow() {
                return dialog;
            }

            @Override
            public Container getParent() {
                return dialog.getParent();
            }

            @Override
            public void center(@Nullable Component component) {
                if (component == null) {
                    center();
                } else {
                    dialog.setLocationRelativeTo(component);
                }
            }

            @Override
            public void center() {
                dialog.setLocationByPlatform(true);
            }
        };
    }

    @Nonnull
    public static JDialog createDialog(final Component component) {
        JDialog dialog = new JDialog();
        initWindowByComponent(dialog, component);
        return dialog;
    }

    public static void invokeDialog(final Component component) {
        JDialog dialog = createDialog(component);
        invokeWindow(dialog);
    }

    public static void initWindow(Window window) {
//        if (window.getParent() instanceof XBEditorFrame) {
//            window.setIconImage(((XBEditorFrame) window.getParent()).getMainFrameManagement().getFrameIcon());
//        }
    }

    public static void initWindowByComponent(Window window, final Component component) {
        Dimension size = component.getPreferredSize();
        window.add(component, BorderLayout.CENTER);
        window.setSize(size.width + 8, size.height + 24);
        if (component instanceof OkCancelService) {
            assignGlobalKeyListener(window, ((OkCancelService) component).getOkCancelListener());
        }
    }

    public static void closeWindow(Window window) {
        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
    }

    @Nonnull
    public static JDialog createBasicDialog() {
        JDialog dialog = new JDialog(new javax.swing.JFrame(), true);
        dialog.setSize(640, 480);
        dialog.setLocationByPlatform(true);
        return dialog;
    }

    /**
     * Find frame component for given component.
     *
     * @param component instantiated component
     * @return frame instance if found
     */
    @Nullable
    public static Frame getFrame(Component component) {
        Component parentComponent = SwingUtilities.getWindowAncestor(component);
        while (!(parentComponent == null || parentComponent instanceof Frame)) {
            parentComponent = parentComponent.getParent();
        }
        return (Frame) parentComponent;
    }

    @Nullable
    public static Window getWindow(Component component) {
        return SwingUtilities.getWindowAncestor(component);
    }

    /**
     * Assign ESCAPE/ENTER key for all focusable components recursively.
     *
     * @param component target component
     * @param closeButton button which will be used for closing operation
     */
    public static void assignGlobalKeyListener(Container component, final JButton closeButton) {
        assignGlobalKeyListener(component, closeButton, closeButton);
    }

    /**
     * Assign ESCAPE/ENTER key for all focusable components recursively.
     *
     * @param component target component
     * @param okButton button which will be used for default ENTER
     * @param cancelButton button which will be used for closing operation
     */
    public static void assignGlobalKeyListener(Container component, final JButton okButton, final JButton cancelButton) {
        assignGlobalKeyListener(component, new OkCancelListener() {
            @Override
            public void okEvent() {
                doButtonClick(okButton);
            }

            @Override
            public void cancelEvent() {
                doButtonClick(cancelButton);
            }
        });
    }

    /**
     * Assign ESCAPE/ENTER key for all focusable components recursively.
     *
     * @param component target component
     * @param listener ok and cancel event listener
     */
    public static void assignGlobalKeyListener(Container component, final OkCancelListener listener) {
        JRootPane rootPane = SwingUtilities.getRootPane(component);
        final String ESC_CANCEL = "esc-cancel";
        final String ENTER_OK = "enter-ok";
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), ESC_CANCEL);
        rootPane.getActionMap().put(ESC_CANCEL, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (listener == null) {
                    return;
                }

                boolean performCancelAction = true;

                Window window = SwingUtilities.getWindowAncestor(event.getSource() instanceof JRootPane ? (JRootPane) event.getSource() : rootPane);
                if (window != null) {
                    Component focusOwner = window.getFocusOwner();
                    if (focusOwner instanceof JComboBox) {
                        performCancelAction = !((JComboBox) focusOwner).isPopupVisible();
                    } else if (focusOwner instanceof JRootPane) {
                        // Ignore in popup menus
                        // performCancelAction = false;
                    }
                }

                if (performCancelAction) {
                    listener.cancelEvent();
                }
            }
        });

        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), ENTER_OK);
        rootPane.getActionMap().put(ENTER_OK, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (listener == null) {
                    return;
                }

                boolean performOkAction = true;

                Window window = SwingUtilities.getWindowAncestor(event.getSource() instanceof JRootPane ? (JRootPane) event.getSource() : rootPane);
                if (window != null) {
                    Component focusOwner = window.getFocusOwner();
                    if (focusOwner instanceof JTextComponent) {
                        performOkAction = !((JTextComponent) focusOwner).isEditable();
                    }
                }

                if (performOkAction) {
                    listener.okEvent();
                }
            }
        });
    }

    public static boolean isDarkMode() {
        return darkMode;
    }

    public static void setDarkMode(boolean darkMode) {
        WindowUtils.darkMode = darkMode;
    }

    /**
     * Performs visually visible click on the button component.
     *
     * @param button button component
     */
    public static void doButtonClick(JButton button) {
        button.doClick(BUTTON_CLICK_TIME);
    }

    /**
     * Creates panel for given main and control panel.
     *
     * @param mainPanel main panel
     * @param controlPanel control panel
     * @return panel
     */
    @Nonnull
    public static JPanel createDialogPanel(JPanel mainPanel, JPanel controlPanel) {
        JPanel dialogPanel;
        if (controlPanel instanceof OkCancelService) {
            dialogPanel = new DialogPanel((OkCancelService) controlPanel);
        } else {
            dialogPanel = new JPanel(new BorderLayout());
        }
        dialogPanel.add(mainPanel, BorderLayout.CENTER);
        dialogPanel.add(controlPanel, BorderLayout.SOUTH);
        Dimension mainPreferredSize = mainPanel.getPreferredSize();
        Dimension controlPreferredSize = controlPanel.getPreferredSize();
        dialogPanel.setPreferredSize(new Dimension(mainPreferredSize.width, mainPreferredSize.height + controlPreferredSize.height));
        return dialogPanel;
    }

    @ParametersAreNonnullByDefault
    private static final class DialogPanel extends JPanel implements OkCancelService {

        private final OkCancelService okCancelService;

        public DialogPanel(OkCancelService okCancelService) {
            super(new BorderLayout());
            this.okCancelService = okCancelService;
        }

        @Nonnull
        @Override
        public OkCancelListener getOkCancelListener() {
            return okCancelService.getOkCancelListener();
        }
    }

    public static void setWindowCenterPosition(Window window) {
        setWindowCenterPosition(window, 0);
    }

    public static void setWindowCenterPosition(Window window, int screen) {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] allDevices = env.getScreenDevices();
        int topLeftX, topLeftY, screenX, screenY, windowPosX, windowPosY;

        if (screen < allDevices.length && screen > -1) {
            Rectangle screenRectangle = allDevices[screen].getDefaultConfiguration().getBounds();
            topLeftX = screenRectangle.x;
            topLeftY = screenRectangle.y;

            screenX = screenRectangle.width;
            screenY = screenRectangle.height;
        } else {
            Rectangle screenRectangle = allDevices[0].getDefaultConfiguration().getBounds();
            topLeftX = screenRectangle.x;
            topLeftY = screenRectangle.y;

            screenX = screenRectangle.width;
            screenY = screenRectangle.height;
        }

        windowPosX = ((screenX - window.getWidth()) / 2) + topLeftX;
        windowPosY = ((screenY - window.getHeight()) / 2) + topLeftY;

        window.setLocation(windowPosX, windowPosY);
    }

    public static void centerWindowOnWindow(Window window, Window relativeWindow) {
        int centerPosX, centerPosY, windowPosX, windowPosY;

        Rectangle bounds = relativeWindow.getBounds();
        centerPosX = bounds.x + (bounds.width / 2);
        centerPosY = bounds.y + (bounds.height / 2);

        windowPosX = centerPosX - (window.getWidth() / 2);
        windowPosY = centerPosY - (window.getHeight() / 2);

        window.setLocation(windowPosX, windowPosY);
    }

    @ParametersAreNonnullByDefault
    public interface DialogWrapper {

        void show();

        void showCentered(@Nullable Component window);

        void close();

        void dispose();

        @Nonnull
        Window getWindow();

        @Nonnull
        Container getParent();

        void center(@Nullable Component window);

        void center();
    }
}
