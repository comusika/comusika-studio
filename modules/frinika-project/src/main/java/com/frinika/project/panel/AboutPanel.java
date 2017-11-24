/*
 * http://www.frinika.com
 * 
 * This file is part of Frinika.
 * 
 * Frinika is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * Frinika is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Frinika; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package com.frinika.project.panel;

import com.frinika.gui.util.BareBonesBrowserLaunch;
import com.frinika.gui.util.WindowUtils;
import com.frinika.project.dialog.VersionProperties;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.TreeSet;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 *
 * @author hajdam
 */
public class AboutPanel extends javax.swing.JPanel {

    private boolean darkMode = WindowUtils.isDarkMode();

    private WindowUtils.OkCancelListener okCancelListener;
    private JLabel light_label;
    private JLabel light_cloud1;
    private JLabel light_cloud2;
    private int cloud_width;
    private Thread animation;

    public static final char COPYRIGHT_SYMBOL = (char) 169; // The (C) Symbol

    public static final String MAIN_TITLE
            = "<html><center>"
            + "<b>Frinika Studio version " + VersionProperties.getVersion() + " </b><br>"
            + "<a href=\"http://frinika.sourceforge.net\">http://frinika.sourceforge.net</a><br><font color='#A0A0A0'><i>Build date: " + VersionProperties.getBuildDate() + "</i></font>"
            + "</html>";

    public static final String COPYRIGHT_NOTICE
            = "<html><center>"
            + "Copyright " + COPYRIGHT_SYMBOL + " " + VersionProperties.getCopyrightStart() + "-" + VersionProperties.getCopyrightEnd() + " The Frinika developers. All rights reserved<br>"
            + "This software is licensed under the GNU General Public License (GPL) version 2<br>"
            + "<a href=\"http://www.gnu.org/licenses/gpl.htm\">http://www.gnu.org/licenses/gpl.htm</a>"
            + "</html>";

    public static final String CREDITS
            = "<html>"
            + "<h2>The team behind Frinika:</h2>"
            + "Peter Johan Salomonsen - Initiative, sequencer, audiodriver, soft synths, tracker, maintenance and more<br>"
            + "Jon Aakerstrom - Audiodriver, JACK integration<br>"
            + "P.J. Leonard - Pianoroll, partview, overall GUI and sequence objects design and more<br>"
            + "Karl Helgason - RasmusDSP, flexdock, jmod integration with Frinika and more<br>"
            + "Toni (oc2pus@arcor.de) - Ant build scripts and Linux RPMs<br>"
            + "Steve Taylor - Toot integration<br>"
            + "Jens Gulden - Ghosts parts, Midi Tools menu, step recording, ctrl tools, scripting and more<br>"
            + "<br>"
            + "<b>Libraries:</b><br>"
            + "JJack Copyright " + COPYRIGHT_SYMBOL + " Jens Gulden<br>"
            + "RasmusDSP Copyright " + COPYRIGHT_SYMBOL + " Karl Helgason<br>"
            + "Toot audio foundation - Steve Taylor<br>"
            + "Tritonus Copyright " + COPYRIGHT_SYMBOL + " by Florian Bomers and Matthias Pfisterer<br>"
            + "launch4j - Cross-platform Java executable wrapper - <a href=\"http://launch4j.sourceforge.net\">http://launch4j.sourceforge.net</a><br>"
            + "jgoodies - Look and feel - <a href=\"https://looks.dev.java.net\">https://looks.dev.java.net</a><br>"
            + "flexdock - Floating and dockable windows - <a href=\"https://flexdock.dev.java.net\">https://flexdock.dev.java.net</a><br>"
            + "Java Sound MODules Library - <a href=\"http://jmod.dev.java.net\">http://jmod.dev.java.net</a><br>"
            + "Rhino JavaScript engine - <a href=\"http://www.mozilla.org/rhino/\">http://www.mozilla.org/rhino/</a><br>"
            + "LZMA SDK - <a href=\"http://www.7-zip.org/sdk.html\">http://www.7-zip.org/sdk.html</a><br>"
            + "jVorbisEnc - Zbigniew Sudnik - XIPHOPHORUS, <a href=\"http://www.xiph.org\">http://www.xiph.org</a><br>"
            + "MRJ Adapter - <a href=\"http://homepage.mac.com/sroy/mrjadapter/\">http://homepage.mac.com/sroy/mrjadapter/</a><br>"
            + "JVSTHost - <a href=\"http://github.com/mhroth/jvsthost\">http://github.com/mhroth/jvsthost</a><br>"
            + "<br>"
            + "<b>Other contributors:</b><br>"
            + "Bob Lang - Bezier synth (<a href=\"http://www.cems.uwe.ac.uk/~lrlang/BezierSynth/index.html\">http://www.cems.uwe.ac.uk/~lrlang/BezierSynth/index.html</a>)<br>"
            + "Edward H - GUI decoration patches<br>"
            + "Artur Rataj (arturrataj@gmail.com) - Pianoroll patches<br>"
            + "Thibault Aspe - French locale (<a href=\"http://thibault.aspe.free.fr\">http://thibault.aspe.free.fr</a>)<br>"
            + "<br></html>";

    private static final long serialVersionUID = 1L;

    int sel = 0;
    int ix = 0;

    public void showLicense() {
        JPanel panel = new JPanel(new BorderLayout());

        JEditorPane licenseAgreement;
        try {
            InputStream is = AboutPanel.class.getResource("/com/frinika/resources/license-gpl2.html").openStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while (is.available() > 0) {
                bos.write(is.read());
            }

            licenseAgreement = new JEditorPane();
            licenseAgreement.setContentType("text/html");
            licenseAgreement.setText(new String(bos.toByteArray()));

            licenseAgreement.addHyperlinkListener(new HyperlinkListener() {
                @Override
                public void hyperlinkUpdate(HyperlinkEvent event) {
                    if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                        BareBonesBrowserLaunch.openDesktopURL(event.getURL().toExternalForm());
                    }
                }
            });

            licenseAgreement.setEditable(false);
            JScrollPane licenseScrollPane = new JScrollPane(licenseAgreement);
            panel.add(licenseScrollPane, BorderLayout.CENTER);
            panel.setPreferredSize(new Dimension(700, 400));
            licenseScrollPane.getVerticalScrollBar().setValue(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't find license agreement.. Exiting.");
            System.exit(0);
        }

        JOptionPane.showMessageDialog(this, panel, "License",
                JOptionPane.INFORMATION_MESSAGE,
                new ImageIcon(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)));
    }

    public void whitening(Container co) {
        Component[] comps = co.getComponents();
        for (Component comp : comps) {
            if (comp instanceof JOptionPane) {
                if (!darkMode) {
                    comp.setBackground(Color.WHITE);
                }
            }
            if (comp instanceof JPanel) {
                if (!darkMode) {
                    comp.setBackground(Color.WHITE);
                }
            }
            if (comp instanceof Container) {
                whitening((Container) comp);
            }
        }
    }

    public void showCredits() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JEditorPane creditsTextArea = new JEditorPane("text/html", CREDITS);
        creditsTextArea.setEditable(false);
        creditsTextArea.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent event) {
                if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    BareBonesBrowserLaunch.openDesktopURL(event.getURL().toExternalForm());
                }
            }
        });
        creditsTextArea.setFont(creditsTextArea.getFont().deriveFont(11f).deriveFont(Font.PLAIN));

        panel.add(creditsTextArea);

        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.INFORMATION_MESSAGE);

        optionPane.setIcon(new ImageIcon(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)));
        JDialog dialog = optionPane.createDialog(this, "Credits");
        dialog.setBackground(Color.WHITE);
        whitening(dialog);

        dialog.setVisible(true);
    }

    public void showSystemInfo() {
        // Jens:
        Properties p = System.getProperties();
        String[][] ss = new String[p.size()][2];
        int i = 0;
        for (Object o : (new TreeSet(p.keySet()))) {
            String s = (String) o;
            String value = p.getProperty(s);
            ss[i][0] = s;
            ss[i][1] = value;
            i++;
        }
        JTable systemInfo = new JTable(ss, new String[]{"Entry", "Value"});
        systemInfo.setEnabled(false);

        JOptionPane.showMessageDialog(this, new JScrollPane(systemInfo), "System Info",
                JOptionPane.INFORMATION_MESSAGE,
                new ImageIcon(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)));
    }

    public AboutPanel() {
        initComponents();
        init();
    }

    private void init() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(darkMode ? Color.BLACK : Color.WHITE);
        Icon welcome = new javax.swing.ImageIcon(AboutPanel.class.getResource(darkMode ? "/frinika-dark.png" : "/frinika.png"));
        JLabel label = new JLabel(welcome);
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setVerticalTextPosition(SwingConstants.BOTTOM);
        label.setFont(label.getFont().deriveFont(Font.PLAIN));
        label.setText(MAIN_TITLE);
        label.setBorder(BorderFactory.createEmptyBorder(25, 5, 5, 5));
        panel.add(label, BorderLayout.NORTH);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        {
            JButton button = new JButton("License");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showLicense();
                }
            });
            buttonPanel.add(button);
        }

        {
            JButton button = new JButton("Credits");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showCredits();
                }
            });
            buttonPanel.add(button);
        }

        {
            JButton button = new JButton("System Info");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showSystemInfo();
                }
            });
            buttonPanel.add(button);
        }

        {
            JButton okButton = new JButton("OK");
            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    okCancelListener.okEvent();
                }
            });
            okButton.setDefaultCapable(true);
            // getRootPane().setDefaultButton(okButton);
            buttonPanel.add(okButton);
        }

        panel.add(buttonPanel, BorderLayout.CENTER);

        JPanel copyrightPanel = new JPanel();
        copyrightPanel.setOpaque(false);

        JLabel line = new JLabel(COPYRIGHT_NOTICE);
        line.setHorizontalTextPosition(SwingConstants.CENTER);
        line.setFont(line.getFont().deriveFont(10f).deriveFont(Font.PLAIN));

        copyrightPanel.add(line);

        panel.add(copyrightPanel, BorderLayout.SOUTH);

        panel.setSize(getPreferredSize().width, getPreferredSize().height);

        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        panel.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                okCancelListener.cancelEvent();
            }
        },
                stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);

        layeredPane.add(panel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        Rectangle windowSize;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        GraphicsEnvironment ge = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
        if (gc == null) {
            gc = getGraphicsConfiguration();
        }

        if (gc != null) {
            windowSize = gc.getBounds();
        } else {
            windowSize = new java.awt.Rectangle(toolkit.getScreenSize());
        }

        addAnimatedLogo(windowSize);
    }

    private void createAnimationThread() {
        animation = new Thread() {
            boolean active = true;
            Runnable gui = new Runnable() {
                @Override
                public void run() {
                    Point loc1 = light_cloud1.getLocation();
                    loc1.x -= 1;
                    if (loc1.x < -cloud_width) {
                        loc1.x += 2 * cloud_width;
                    }
                    light_cloud1.setLocation(loc1);
                    Point loc2 = light_cloud2.getLocation();
                    loc2.x -= 1;
                    if (loc2.x < -cloud_width) {
                        loc2.x += 2 * cloud_width;
                    }
                    light_cloud2.setLocation(loc2);

                    Point loc = light_label.getLocation();
                    loc.x += 3;
                    if (loc.x > 350) {
                        loc.x = -400;
                    }
                    light_label.setLocation(loc);
                    if (!isVisible()) {
                        active = false;
                    }
                }
            };

            @Override
            public void run() {
                while (active) {
                    gui.run();
                    try {
                        Thread.sleep(70);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    public void setOkCancelListener(WindowUtils.OkCancelListener okCancelListener) {
        this.okCancelListener = okCancelListener;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        layeredPane = new javax.swing.JLayeredPane();

        setLayout(new java.awt.BorderLayout());

        layeredPane.setPreferredSize(new java.awt.Dimension(470, 400));

        javax.swing.GroupLayout layeredPaneLayout = new javax.swing.GroupLayout(layeredPane);
        layeredPane.setLayout(layeredPaneLayout);
        layeredPaneLayout.setHorizontalGroup(
            layeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 524, Short.MAX_VALUE)
        );
        layeredPaneLayout.setVerticalGroup(
            layeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 412, Short.MAX_VALUE)
        );

        add(layeredPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane layeredPane;
    // End of variables declaration//GEN-END:variables

    private void addAnimatedLogo(Rectangle windowSize) {
        // Toolkit toolkit = Toolkit.getDefaultToolkit();
        GraphicsEnvironment ge = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
        if (gc == null) {
            gc = getGraphicsConfiguration();
        }

        /*if(gc != null) {
	    	windowSize = gc.getBounds();
	    } else {
	    	windowSize = new java.awt.Rectangle(toolkit.getScreenSize());
	    } */
        // Dimension size = layeredPanel.getSize();
        Icon frinika_light = new javax.swing.ImageIcon(AboutPanel.class.getResource("/com/frinika/resources/frinika_light_gradient.png"));
        light_label = new JLabel(frinika_light);
        light_label.setLocation(-400, 60);
        light_label.setSize(frinika_light.getIconWidth(), frinika_light.getIconHeight());
        layeredPane.add(light_label, javax.swing.JLayeredPane.MODAL_LAYER);

        Icon frinika_cloud = new javax.swing.ImageIcon(AboutPanel.class.getResource("/com/frinika/resources/frinika_score.png"));
        cloud_width = frinika_cloud.getIconWidth();
        light_cloud1 = new JLabel(frinika_cloud);
        light_cloud1.setLocation(cloud_width, 75);
        light_cloud1.setSize(frinika_light.getIconWidth(), frinika_light.getIconHeight());
        layeredPane.add(light_cloud1, javax.swing.JLayeredPane.MODAL_LAYER);
        light_cloud2 = new JLabel(frinika_cloud);
        light_cloud2.setLocation(0, 75);
        light_cloud2.setSize(frinika_light.getIconWidth(), frinika_light.getIconHeight());
        layeredPane.add(light_cloud2, javax.swing.JLayeredPane.MODAL_LAYER);

        Icon frinika_overscan = new javax.swing.ImageIcon(AboutPanel.class.getResource("/com/frinika/resources/frinika_overscan.png"));
        JLabel light_overscan = new JLabel(frinika_overscan);
        light_overscan.setLocation(22, 43);
        light_overscan.setSize(frinika_overscan.getIconWidth(), frinika_overscan.getIconHeight());
        layeredPane.add(light_overscan, javax.swing.JLayeredPane.POPUP_LAYER);

        createAnimationThread();
        animation.start();
    }
}
