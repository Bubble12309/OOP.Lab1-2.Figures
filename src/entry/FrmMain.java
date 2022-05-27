package entry;

import drawable.Drawable;
import drawable.DrawableList;
import figures.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

import javax.swing.event.MouseInputListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FrmMain extends JFrame {
    //-------------------------------------------------------------------------------------------
    //-----------------------------------------Main menu-----------------------------------------
    //-------------------------------------------------------------------------------------------
    protected JMenuBar menuBar;
    protected JMenu fileMenu;
    protected JMenuItem saveFileItem;
    protected JMenu helpMenu;
    protected JMenuItem helpMenuItem;
    protected JMenu canvasMenu;
    protected JMenuItem removeMenuItem;
    protected JMenuItem clearMenuItem;

    //-------------------------------------------------------------------------------------------
    //------------------------------------Horizontal toolbar-------------------------------------
    //-------------------------------------------------------------------------------------------
    protected ButtonGroup bgFigures;
    protected JToolBar toolBarFigures;
    protected JFigureButton buttonCircle;
    protected JFigureButton buttonRectangle;
    protected JFigureButton buttonSquare;
    protected JFigureButton buttonEllipse;
    protected JFigureButton buttonLine;
    protected JFigureButton buttonPolygon;
    protected JFigureButton buttonPolyline;

    //-------------------------------------------------------------------------------------------
    //------------------------------------Vertical toolbar---------------------------------------
    //-------------------------------------------------------------------------------------------
    protected JToolBar toolBarPalette;

    protected JLabel labelColorCaption;
    protected JPanel panelColor;
    protected JButton buttonColorChooser;
    protected JPanel colorExample;

    protected JLabel labelConstraints;
    protected JPanel panelConstraints;
    protected JLabel labelH;
    protected JPanel panelH;
    protected JLabel labelW;
    protected JPanel panelW;
    protected JFormattedTextField widthField;
    protected JFormattedTextField heightField;
    protected JButton buttonConstraintsConfirm;

    protected int stdPenWidth;
    protected JPanel panelPenWidth;
    protected JLabel labelPenWidthCaption;
    protected JPanel panelPenWidthTextField;
    protected JLabel labelPenWidth;
    protected JFormattedTextField penWidthField;

    //-------------------------------------------------------------------------------------------
    //---------------------------------------Drawing controls------------------------------------
    //-------------------------------------------------------------------------------------------
    protected JPanel gArea;
    protected JScrollPane gAreaScroll;
    protected GCanvas gCanvas;

    //-------------------------------------------------------------------------------------------
    //------------------------------------Menu actions-------------------------------------------
    //-------------------------------------------------------------------------------------------
    protected Action saveAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser savePicDialog = new JFileChooser();
            FileNameExtensionFilter filterPNG = new FileNameExtensionFilter(".png image", "png");
            FileNameExtensionFilter filterJPG = new FileNameExtensionFilter(".jpg image", "jpg");
            savePicDialog.addChoosableFileFilter(filterPNG);
            savePicDialog.addChoosableFileFilter(filterJPG);
            savePicDialog.setFileFilter(filterPNG);
            int returnValue = savePicDialog.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                try {
                    if (savePicDialog.getFileFilter() == filterPNG) {
                        ImageIO.write(gCanvas.getBufferedImage(), "png", new File(savePicDialog.getSelectedFile().getAbsolutePath() + ".png"));
                    } else if (savePicDialog.getFileFilter() == filterJPG) {
                        ImageIO.write(gCanvas.getBufferedImage(), "jpg", new File(savePicDialog.getSelectedFile().getAbsolutePath() + ".jpg"));
                    } else
                        ImageIO.write(gCanvas.getBufferedImage(), "png", new File(savePicDialog.getSelectedFile().getAbsolutePath() + ".png"));
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "The IOException raised", "IO exception", JOptionPane.ERROR_MESSAGE);
                } catch (AWTException ae) {
                    JOptionPane.showMessageDialog(null, "Error raised while saving image", "AWT error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    };

    protected Action helpAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Nope", "Someone asked for help?", JOptionPane.WARNING_MESSAGE);
        }
    };

    protected Action clearCanvasAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            gCanvas.getDrawableList().clear();
            gCanvas.setSample(null);
            gCanvas.repaint(gCanvas.getBounds());
        }
    };

    protected Action removeFigureAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (gCanvas.getSample() == null) {
                DrawableList figures = gCanvas.getDrawableList();
                if (!figures.isEmpty()) {
                    figures.removeLast();
                }
            } else {
                gCanvas.setSample(null);
            }
            gCanvas.repaint(gCanvas.getBounds());
        }
    };

    //-------------------------------------------------------------------------------------------
    //----------------------------------JFigureButtons action------------------------------
    //-------------------------------------------------------------------------------------------
    protected Action buttonFigureClick = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFigureButton b = (JFigureButton) e.getSource();
            gCanvas.setMode(b.getMode());
        }
    };

    //-------------------------------------------------------------------------------------------
    //--------------------------------------Tools actions----------------------------------------
    //-------------------------------------------------------------------------------------------
    protected MouseListener toolBarToggleClick = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                bgFigures.clearSelection();
                gCanvas.setMode(JFigureButton.NONE);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    };

    protected Action buttonConstraintsConfirmClick = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int width = Integer.parseInt(widthField.getText());
                int height = Integer.parseInt(heightField.getText());
                Dimension dim = new Dimension(Math.max(width, 0), Math.max(height, 0));
                gCanvas.setMinimumSize(dim);
                gCanvas.setPreferredSize(dim);
                gCanvas.setMaximumSize(dim);
                gArea.setPreferredSize(dim);
                gArea.revalidate();
                gCanvas.revalidate();
            } catch (NumberFormatException nfe) {
                widthField.setText(String.valueOf(gCanvas.getWidth()));
                heightField.setText(String.valueOf(gCanvas.getHeight()));
                JOptionPane.showMessageDialog(null, "Impossible to set dimensions", "Lexical error", JOptionPane.ERROR_MESSAGE);
            }
        }
    };

    protected KeyAdapter textFieldValidOnly = new KeyAdapter() {
        private static final int maxLength = 4;

        @Override
        public void keyTyped(KeyEvent e) {
            super.keyTyped(e);
            char c = e.getKeyChar();
            if ((c < '0') || (c > '9') || ((JTextField) e.getSource()).getText().length() >= maxLength) {
                e.consume();
            }
        }
    };

    protected Action buttonColorChooserClick = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Color res = JColorChooser.showDialog(null, "Choose color", gCanvas.getCurrColor());
            if (res != null) {
                colorExample.setBackground(res);
                gCanvas.setCurrColor(res);
                gCanvas.setSample(null);
                gCanvas.repaint(gCanvas.getBounds());
            }
        }
    };

    protected MouseInputListener gCanvasClick = new MouseInputListener() {
        @Override
        public void mouseDragged(MouseEvent e) {
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            Drawable sample = gCanvas.getSample();
            if (sample != null) {
                sample.recalculate(e.getPoint());
                gCanvas.repaint(gCanvas.getBounds());
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (gCanvas.getMode() != JFigureButton.NONE) {
                int mb = e.getButton();
                Point p = e.getPoint();
                switch (mb) {
                    case MouseEvent.BUTTON1 -> {
                        if (gCanvas.getSample() == null) {
                            int penWidth;
                            try {
                                penWidth = Integer.parseInt(penWidthField.getText());
                            } catch (NumberFormatException nfe) {
                                penWidth = stdPenWidth;
                                penWidthField.setText(String.valueOf(stdPenWidth));
                            }
                            Figure sample = FigureFactory.getInitInstance(gCanvas.getMode(), gCanvas.getCurrColor(), penWidth, p, p);
                            gCanvas.setSample(sample);
                            gCanvas.repaint(gCanvas.getBounds());
                        } else {
                            gCanvas.getSample().addPoint(p);
                            gCanvas.repaint(gCanvas.getBounds());
                        }
                    }
                    case MouseEvent.BUTTON3 -> {
                        Drawable sample = gCanvas.getSample();

                        if (sample != null && sample.ready()) {
                            gCanvas.getDrawableList().add(gCanvas.getSample());
                            gCanvas.setSample(null);
                            gCanvas.repaint(gCanvas.getBounds());
                        }
                    }
                    case MouseEvent.BUTTON2 -> {
                        gCanvas.setSample(null);
                        gCanvas.repaint(gCanvas.getBounds());
                    }
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    };

    FrmMain() {
        Dimension stdCanvasDimension = new Dimension(800, 600);
        Dimension stdButtonDimension = new Dimension(45, 45);
        Color stdInitColor = new Color(0, 0, 0);
        int stdColorExampleSize = 35;
        int stdToolBarPaletteWidth = 140;
        int stdPenWidth = 5;
        //-------------------------------------------------------------------------------------------
        //--------------------------------------Setting frame params---------------------------------
        //-------------------------------------------------------------------------------------------
        setTitle("OOP Lab 1 and 2");
        setResizable(true);
        setPreferredSize(new Dimension(900, 600)); //
        setMinimumSize(new Dimension(stdToolBarPaletteWidth + 20, 100));
        setDefaultCloseOperation(super.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0, 0));
        setIconImage(Toolkit.getDefaultToolkit().getImage("res/images/formicon.png"));

        //-------------------------------------------------------------------------------------------
        //------------------------------------------Main menu----------------------------------------
        //-------------------------------------------------------------------------------------------
        menuBar = new JMenuBar();
        menuBar.setBackground(new Color(240, 240, 240));
        setJMenuBar(menuBar);

        fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        saveFileItem = new JMenuItem(saveAction);
        saveFileItem.setText("Save");
        fileMenu.add(saveFileItem);

        canvasMenu = new JMenu("Canvas");
        menuBar.add(canvasMenu);

        removeMenuItem = new JMenuItem(removeFigureAction);
        removeMenuItem.setText("Remove last item");
        canvasMenu.add(removeMenuItem);

        clearMenuItem = new JMenuItem(clearCanvasAction);
        clearMenuItem.setText("Clear");
        canvasMenu.add(clearMenuItem);

        helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);

        helpMenuItem = new JMenuItem(helpAction);
        helpMenuItem.setText("Help me");
        helpMenu.add(helpMenuItem);
        //-------------------------------------------------------------------------------------------
        //----------------------------------Horizontal bar initialization----------------------------
        //-------------------------------------------------------------------------------------------
        toolBarFigures = new JToolBar(JToolBar.HORIZONTAL);
        toolBarFigures.setFloatable(false);
        toolBarFigures.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 0));
        toolBarFigures.setBackground(new Color(210, 210, 210));
        toolBarFigures.addMouseListener(toolBarToggleClick);

        add(toolBarFigures, BorderLayout.NORTH);

        bgFigures = new ButtonGroup();

        buttonCircle = new JFigureButton(JFigureButton.CIRCLE);
        buttonCircle.setIcon(new ImageIcon("res/images/circle.png"));
        buttonCircle.setPreferredSize(stdButtonDimension);
        buttonCircle.addActionListener(buttonFigureClick);
        bgFigures.add(buttonCircle);
        toolBarFigures.add(buttonCircle);

        buttonEllipse = new JFigureButton(JFigureButton.ELLIPSE);
        buttonEllipse.setIcon(new ImageIcon("res/images/ellipse.png"));
        buttonEllipse.setPreferredSize(stdButtonDimension);
        buttonEllipse.addActionListener(buttonFigureClick);
        bgFigures.add(buttonEllipse);
        toolBarFigures.add(buttonEllipse);

        toolBarFigures.addSeparator(new Dimension(10, 35));

        buttonRectangle = new JFigureButton(JFigureButton.RECTANGLE);
        buttonRectangle.setIcon(new ImageIcon("res/images/rectangle.png"));
        buttonRectangle.setPreferredSize(stdButtonDimension);
        buttonRectangle.addActionListener(buttonFigureClick);
        bgFigures.add(buttonRectangle);
        toolBarFigures.add(buttonRectangle);

        buttonSquare = new JFigureButton(JFigureButton.SQUARE);
        buttonSquare.setIcon(new ImageIcon("res/images/square.png"));
        buttonSquare.setPreferredSize(stdButtonDimension);
        buttonSquare.addActionListener(buttonFigureClick);
        bgFigures.add(buttonSquare);
        toolBarFigures.add(buttonSquare);

        toolBarFigures.addSeparator(new Dimension(10, 35));

        buttonLine = new JFigureButton(JFigureButton.LINE);
        buttonLine.setIcon(new ImageIcon("res/images/line.png"));
        buttonLine.setPreferredSize(stdButtonDimension);
        buttonLine.addActionListener(buttonFigureClick);
        bgFigures.add(buttonLine);
        toolBarFigures.add(buttonLine);

        toolBarFigures.addSeparator(new Dimension(10, 35));
        buttonPolygon = new JFigureButton(JFigureButton.POLYGON);
        buttonPolygon.setIcon(new ImageIcon("res/images/polygon.png"));
        buttonPolygon.setPreferredSize(stdButtonDimension);
        buttonPolygon.addActionListener(buttonFigureClick);
        bgFigures.add(buttonPolygon);
        toolBarFigures.add(buttonPolygon);

        buttonPolyline = new JFigureButton(JFigureButton.POLYLINE);
        buttonPolyline.setIcon(new ImageIcon("res/images/polyline.png"));
        buttonPolyline.setPreferredSize(stdButtonDimension);
        buttonPolyline.addActionListener(buttonFigureClick);
        bgFigures.add(buttonPolyline);
        toolBarFigures.add(buttonPolyline);
        //-------------------------------------------------------------------------------------------
        //---------------------------------Vertical toolbar initialization-----------------------------
        //-------------------------------------------------------------------------------------------
        toolBarPalette = new JToolBar();
        toolBarPalette.setFloatable(false);
        toolBarPalette.setPreferredSize(new Dimension(stdToolBarPaletteWidth, 0));
        toolBarPalette.setLayout(null); //layout absolute
        toolBarPalette.setBackground(new Color(210, 210, 210));
        toolBarPalette.addMouseListener(toolBarToggleClick);
        add(toolBarPalette, BorderLayout.WEST);

        panelColor = new JPanel();
        panelColor.setBounds(0, 6, stdToolBarPaletteWidth, 70);
        panelColor.setLayout(null);
        panelColor.setBackground(new Color(230, 230, 230));
        toolBarPalette.add(panelColor);

        labelColorCaption = new JLabel("Color");
        labelColorCaption.setBounds(0, 1, panelColor.getWidth(), labelColorCaption.getFont().getSize());
        labelColorCaption.setHorizontalAlignment(JLabel.CENTER);
        panelColor.add(labelColorCaption);

        colorExample = new JPanel();
        colorExample.setBackground(stdInitColor);
        colorExample.setBounds(10, labelColorCaption.getHeight() + 11, stdColorExampleSize, stdColorExampleSize);
        panelColor.add(colorExample);

        buttonColorChooser = new JButton(buttonColorChooserClick);
        buttonColorChooser.setText("...");
        buttonColorChooser.setBounds(colorExample.getX() + colorExample.getWidth() + 10, colorExample.getY() + 5, 75, 25);
        panelColor.add(buttonColorChooser);

        panelConstraints = new JPanel();
        var pcLayout = new BoxLayout(panelConstraints, BoxLayout.Y_AXIS);
        panelConstraints.setLayout(pcLayout);
        panelConstraints.setBackground(new Color(230, 230, 230));
        panelConstraints.setBounds(0, panelColor.getY() + panelColor.getHeight() + 6, stdToolBarPaletteWidth, 100);
        toolBarPalette.add(panelConstraints);

        labelConstraints = new JLabel("Dimensions");
        labelConstraints.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        labelConstraints.setHorizontalAlignment(JLabel.CENTER);
        panelConstraints.add(labelConstraints);

        panelW = new JPanel();
        panelW.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelConstraints.add(panelW);

        labelW = new JLabel("W:");
        labelW.setPreferredSize(new Dimension(20, labelW.getFont().getSize()));
        panelW.add(labelW);

        widthField = new JFormattedTextField(Integer.toString(stdCanvasDimension.width));
        widthField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        widthField.setPreferredSize(new Dimension(60, widthField.getFont().getSize() + 5));
        widthField.addKeyListener(textFieldValidOnly);
        panelW.add(widthField);

        panelH = new JPanel();
        panelH.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelConstraints.add(panelH);

        labelH = new JLabel("H:");
        labelH.setPreferredSize(new Dimension(20, labelW.getFont().getSize()));
        panelH.add(labelH);

        heightField = new JFormattedTextField(Integer.toString(stdCanvasDimension.height));
        heightField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        heightField.setPreferredSize(new Dimension(60, heightField.getFont().getSize() + 5));
        heightField.addKeyListener(textFieldValidOnly);
        panelH.add(heightField);

        buttonConstraintsConfirm = new JButton(buttonConstraintsConfirmClick);
        buttonConstraintsConfirm.setText("Apply");
        buttonConstraintsConfirm.setAlignmentX(JButton.CENTER_ALIGNMENT);
        panelConstraints.add(buttonConstraintsConfirm);

        panelPenWidth = new JPanel();
        panelPenWidth.setLayout(new BoxLayout(panelPenWidth, BoxLayout.Y_AXIS));
        panelPenWidth.setBounds(0, panelConstraints.getY() + panelConstraints.getHeight() + 6, stdToolBarPaletteWidth, 45);
        panelPenWidth.setBackground(new Color(230, 230, 230));

        labelPenWidthCaption = new JLabel("Pen width");
        labelPenWidthCaption.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        labelPenWidthCaption.setHorizontalAlignment(JLabel.CENTER);
        panelPenWidth.add(labelPenWidthCaption);

        panelPenWidthTextField = new JPanel();
        panelPenWidthTextField.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelPenWidth.add(panelPenWidthTextField);

        labelPenWidth = new JLabel("PW:");
        labelPenWidth.setPreferredSize(new Dimension(30, labelW.getFont().getSize()));
        panelPenWidthTextField.add(labelPenWidth);

        penWidthField = new JFormattedTextField();
        penWidthField.setText(String.valueOf(stdPenWidth));
        penWidthField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        penWidthField.setPreferredSize(new Dimension(90, penWidthField.getFont().getSize() + 5));
        penWidthField.addKeyListener(textFieldValidOnly);
        panelPenWidthTextField.add(penWidthField);

        toolBarPalette.add(panelPenWidth);
        //-------------------------------------------------------------------------------------------
        //-----------------------------------Canvas initialization-----------------------------------
        //-------------------------------------------------------------------------------------------
        gArea = new JPanel();
        gArea.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        gArea.setIgnoreRepaint(false);
        gArea.setBackground(new Color(98, 91, 91));

        gAreaScroll = new JScrollPane(gArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(gAreaScroll, BorderLayout.CENTER);

        gCanvas = new GCanvas();
        gCanvas.setMinimumSize(stdCanvasDimension);
        gCanvas.setMaximumSize(stdCanvasDimension);
        gCanvas.setPreferredSize(stdCanvasDimension);
        gCanvas.setIgnoreRepaint(false);
        gCanvas.setBackground(new Color(255, 255, 255));
        gCanvas.setCurrColor(stdInitColor);
        gCanvas.addMouseListener(gCanvasClick);
        gCanvas.addMouseMotionListener(gCanvasClick);
        gCanvas.setBorder(null);
        gArea.setPreferredSize(gCanvas.getPreferredSize());
        gArea.add(gCanvas);
        //-----------------------------------------------------------------------------------
        this.pack();
    }
}