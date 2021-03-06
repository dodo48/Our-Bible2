/*
 *
 * Copyright (c) 2007, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.border.*;
import javax.swing.colorchooser.*;
import javax.swing.filechooser.*;
import javax.accessibility.*;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import java.io.*;
import java.applet.*;
import java.net.*;
import java.sql.SQLException;

/**
 * Internal Frames Demo
 *
 * @author Jeff Dinkins
 */
public class InternalFrameDemo extends DemoModule {
    int windowCount = 0;
    JDesktopPane desktop = null;

    ImageIcon icon1, icon2, icon3, icon4;
    ImageIcon smIcon1, smIcon2, smIcon3, smIcon4;

    public Integer FIRST_FRAME_LAYER  = new Integer(1);
    public Integer DEMO_FRAME_LAYER   = new Integer(2);
    public Integer PALETTE_LAYER     = new Integer(3);

    public int FRAME0_X        = 15;
    public int FRAME0_Y        = 280;

    public int FRAME0_WIDTH    = 320;
    public int FRAME0_HEIGHT   = 230;

    public int FRAME_WIDTH     = 225;
    public int FRAME_HEIGHT    = 150;

    public int PALETTE_X      = 375;
    public int PALETTE_Y      = 20;

    public int PALETTE_WIDTH  = 260;
    public int PALETTE_HEIGHT = 260;

    JCheckBox windowResizable   = null;
    JCheckBox windowClosable    = null;
    JCheckBox windowIconifiable = null;
    JCheckBox windowMaximizable = null;

    JTextField windowTitleField = null;
    JLabel windowTitleLabel = null;


    /**
     * main method allows us to run as a standalone demo.
    public static void main(String[] args) {
        InternalFrameDemo demo = new InternalFrameDemo(null);
        demo.mainImpl();
    }
     */

    /**
     * InternalFrameDemo Constructor
     */
    public InternalFrameDemo(SwingSet2 swingset) {
        super(swingset, "InternalFrameDemo", "toolbar/JDesktop.gif");

        // preload all the icons we need for this demo
        icon1 = createImageIcon("ImageClub/misc/fish.gif", getString("InternalFrameDemo.fish"));
        icon2 = createImageIcon("ImageClub/misc/moon.gif", getString("InternalFrameDemo.moon"));
        icon3 = createImageIcon("ImageClub/misc/sun.gif",  getString("InternalFrameDemo.sun"));
        icon4 = createImageIcon("ImageClub/misc/cab.gif",  getString("InternalFrameDemo.cab"));

        smIcon1 = createImageIcon("ImageClub/misc/fish_small.gif", getString("InternalFrameDemo.fish"));
        smIcon2 = createImageIcon("ImageClub/misc/moon_small.gif", getString("InternalFrameDemo.moon"));
        smIcon3 = createImageIcon("ImageClub/misc/sun_small.gif",  getString("InternalFrameDemo.sun"));
        smIcon4 = createImageIcon("ImageClub/misc/cab_small.gif",  getString("InternalFrameDemo.cab"));

        // Create the desktop pane
        desktop = new JDesktopPane();
        getDemoPanel().add(desktop, BorderLayout.CENTER);

        // Create the "frame maker" palette
        createInternalFramePalette();

        // Create an initial internal frame to show
        JInternalFrame frame1 = createInternalFrame(icon1, FIRST_FRAME_LAYER, 1, 1);
        frame1.setBounds(FRAME0_X, FRAME0_Y, FRAME0_WIDTH, FRAME0_HEIGHT);

        // Create four more starter windows
        //createInternalFrame(icon1, DEMO_FRAME_LAYER, FRAME_WIDTH, FRAME_HEIGHT);
        createInternalFrame(icon3, DEMO_FRAME_LAYER, FRAME_WIDTH, FRAME_HEIGHT);
        createInternalFrame(icon4, DEMO_FRAME_LAYER, FRAME_WIDTH, FRAME_HEIGHT);
        createInternalFrame(icon2, DEMO_FRAME_LAYER, FRAME_WIDTH, FRAME_HEIGHT);
        createMyInternalFrame(icon2, DEMO_FRAME_LAYER, FRAME_WIDTH, FRAME_HEIGHT);
        
        // ����� ������ ����
        DBase FullBooksList = new DBase("db//FullBooksList.SQLite3", "����� ������ ����");
        try {
        	FullBooksList.ConnDBase();
        	FullBooksList.ReadFullBooksList(); 
        	AllBookDescription.showAllBook();
        	FullBooksList.CloseDB();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        
        

       
    }



    /**
     * Create an internal frame and add a scrollable imageicon to it
     */
    public JInternalFrame createInternalFrame(Icon icon, Integer layer, int width, int height) {
        JInternalFrame jif = new JInternalFrame();

        if(!windowTitleField.getText().equals(getString("InternalFrameDemo.frame_label"))) {
            jif.setTitle(windowTitleField.getText() + "  ");
        } else {
            jif = new JInternalFrame(getString("InternalFrameDemo.frame_label") + " " + windowCount + "  ");
        }

        // set properties
        jif.setClosable(windowClosable.isSelected());
        jif.setMaximizable(windowMaximizable.isSelected());
        jif.setIconifiable(windowIconifiable.isSelected());
        jif.setResizable(windowResizable.isSelected());

        jif.setBounds(20*(windowCount%10), 20*(windowCount%10), width, height);
        jif.setContentPane(new ImageScroller(this, icon, 0, windowCount));

        windowCount++;

        desktop.add(jif, layer);

        // Set this internal frame to be selected

        try {
            jif.setSelected(true);
        } catch (java.beans.PropertyVetoException e2) {
        }

        jif.show();

        return jif;
    }

    /**
     * Create an internal frame and add a scrollable imageicon to it
     */
    public JInternalFrame createMyInternalFrame(Icon icon, Integer layer, int width, int height) {
        JInternalFrame jif = new JInternalFrame();
        
        // ��������� ������+
        JToolBar jifToolBar = new JToolBar();
        jif.add(jifToolBar, BorderLayout.NORTH);        
        // ��������� ������-         
        
		JEditorPane editorPane = new JEditorPane();
		getContentPane().add(editorPane, BorderLayout.CENTER);
        editorPane.setContentType("text/html");
                
        String MyHTML =
		"<head> <title>������ 1</title> </head> "+
		 "<body> <H1>������!</H1> <P>��� ���������� ������ HTML-���������.</P> " +
		 "<P>���� *.html-���� ����� ���� ������������ ������  � � Notepad, � � Netscape. " +
		"������� ��������� � Notepad, ������ ������� ������ " +
		 " Reload ('�������������') � Netscape, ����� �������  ��� ��������� �������������� � HTML-���������.</P>"+
		 "</body>";
        
        
        
        // DBase base = new DBase("G:\\DV\\����\\RST+.SQLite3", "�����������");
        DBase base = new DBase("db//RST+.SQLite3", "�����������");
        try {
    		base.ConnDBase();
    		MyHTML = base.GetChapterText(10, 1, 1);
    		base.CloseDB();            	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        MyHTML = "" + MyHTML;
        
        editorPane.setText(MyHTML);
        
        editorPane.setEditable(false);
        //jif.getContentPane().add(editorPane);
        
        JScrollPane scroller = new JScrollPane(); 
        JViewport vp = scroller.getViewport(); 
        vp.add(editorPane); 
        jif.add(scroller, BorderLayout.CENTER);
		
        // ���������� ����� �� ������.
        editorPane.addHyperlinkListener(new HyperlinkListener() {
        	public void hyperlinkUpdate(HyperlinkEvent e) {
        		if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
        			String url = e.getURL().toString();
        			
        			// ������� �� ������.
        			if (url.indexOf("crossref") != -1) {
        				url = url.substring(15);
        				int slashPos = url.indexOf("/"); 
        				if (slashPos != -1) {
        					int bookNumber = Integer.parseInt(url.substring(0, slashPos));
        					
        					int slashPos2 = url.indexOf("/", slashPos + 1);
        					if (slashPos2 != -1) {
        						System.out.println(url.substring(slashPos + 1, slashPos2 - 1));
        						int chapterNumber = Integer.parseInt(url.substring(slashPos + 1, slashPos2));
        						int verseNumber = Integer.parseInt(url.substring(slashPos2 + 1));

        						try {
        				    		base.ConnDBase();
        				    		editorPane.setText(base.GetChapterText(bookNumber, chapterNumber, verseNumber));
        				    		base.CloseDB();
        				    		editorPane.setCaretPosition(base.caretPosition); //����
        				    		System.out.println(base.caretPosition);
        						} catch (ClassNotFoundException e2) {
        							e2.printStackTrace();
        						} catch (SQLException e2) {
        							e2.printStackTrace();
        						}
        						System.out.println("" + bookNumber + " - " + chapterNumber + " - " + verseNumber);
        					}
        				}
        				
        			}
        			
        			/*
	        		if (e.getURL().toString().equals("http://���")) {
	        			// ����� �������� ����� ������ ����� ��� �������� � ���� ����� � ������������������ �� ���������� �����.
	        			//editorPane.setText("���� �������������");
	        			editorPane.setCaretPosition(2300);
	        		}
	        		*/
        		}
        	}
        });
                

        /*
        if(!windowTitleField.getText().equals(getString("InternalFrameDemo.frame_label"))) {
            jif.setTitle(windowTitleField.getText() + "  ");
        } else {
            jif = new JInternalFrame(getString("InternalFrameDemo.frame_label") + " " + windowCount + "  ");
        }
        */

        jif.setTitle("��� ����!");
        
        // set properties
        jif.setClosable(windowClosable.isSelected());
        jif.setMaximizable(windowMaximizable.isSelected());
        jif.setIconifiable(windowIconifiable.isSelected());
        jif.setResizable(windowResizable.isSelected());

        int koef = 10;
        
        jif.setBounds(koef*(windowCount%10), koef*(windowCount%10), width*koef/5, height*koef/5);
        //jif.setContentPane(new ImageScroller(this, icon, 0, windowCount));
        
        windowCount++;

        desktop.add(jif, layer);
        
        
        

        // Set this internal frame to be selected

        try {
            jif.setSelected(true);
        } catch (java.beans.PropertyVetoException e2) {
        }

        jif.show();

        return jif;
    }
   
    
    
    public JInternalFrame createInternalFramePalette() {
        JInternalFrame palette = new JInternalFrame(
            getString("InternalFrameDemo.palette_label")
        );
        palette.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
        palette.getContentPane().setLayout(new BorderLayout());
        palette.setBounds(PALETTE_X, PALETTE_Y, PALETTE_WIDTH, PALETTE_HEIGHT);
        palette.setResizable(true);
        palette.setIconifiable(true);
        desktop.add(palette, PALETTE_LAYER);

        // *************************************
        // * Create create frame maker buttons *
        // *************************************
        JButton b1 = new JButton(smIcon1);
        JButton b2 = new JButton(smIcon2);
        JButton b3 = new JButton(smIcon3);
        JButton b4 = new JButton(smIcon4);

        // add frame maker actions
        b1.addActionListener(new ShowFrameAction(this, icon1));
        b2.addActionListener(new ShowFrameAction(this, icon2));
        b3.addActionListener(new ShowFrameAction(this, icon3));
        b4.addActionListener(new ShowFrameAction(this, icon4));

        // add frame maker buttons to panel
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JPanel buttons1 = new JPanel();
        buttons1.setLayout(new BoxLayout(buttons1, BoxLayout.X_AXIS));

        JPanel buttons2 = new JPanel();
        buttons2.setLayout(new BoxLayout(buttons2, BoxLayout.X_AXIS));

        buttons1.add(b1);
        buttons1.add(Box.createRigidArea(HGAP15));
        buttons1.add(b2);

        buttons2.add(b3);
        buttons2.add(Box.createRigidArea(HGAP15));
        buttons2.add(b4);

        p.add(Box.createRigidArea(VGAP10));
        p.add(buttons1);
        p.add(Box.createRigidArea(VGAP15));
        p.add(buttons2);
        p.add(Box.createRigidArea(VGAP10));

        palette.getContentPane().add(p, BorderLayout.NORTH);

        // ************************************
        // * Create frame property checkboxes *
        // ************************************
        p = new JPanel() {
            Insets insets = new Insets(10,15,10,5);
            public Insets getInsets() {
                return insets;
            }
        };
        p.setLayout(new GridLayout(1,2));


        Box box = new Box(BoxLayout.Y_AXIS);
        windowResizable   = new JCheckBox(getString("InternalFrameDemo.resizable_label"), true);
        windowIconifiable = new JCheckBox(getString("InternalFrameDemo.iconifiable_label"), true);

        box.add(Box.createGlue());
        box.add(windowResizable);
        box.add(windowIconifiable);
        box.add(Box.createGlue());
        p.add(box);

        box = new Box(BoxLayout.Y_AXIS);
        windowClosable    = new JCheckBox(getString("InternalFrameDemo.closable_label"), true);
        windowMaximizable = new JCheckBox(getString("InternalFrameDemo.maximizable_label"), true);

        box.add(Box.createGlue());
        box.add(windowClosable);
        box.add(windowMaximizable);
        box.add(Box.createGlue());
        p.add(box);

        palette.getContentPane().add(p, BorderLayout.CENTER);


        // ************************************
        // *   Create Frame title textfield   *
        // ************************************
        p = new JPanel() {
            Insets insets = new Insets(0,0,10,0);
            public Insets getInsets() {
                return insets;
            }
        };

        windowTitleField = new JTextField(getString("InternalFrameDemo.frame_label"));
        windowTitleLabel = new JLabel(getString("InternalFrameDemo.title_text_field_label"));

        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        p.add(Box.createRigidArea(HGAP5));
        p.add(windowTitleLabel, BorderLayout.WEST);
        p.add(Box.createRigidArea(HGAP5));
        p.add(windowTitleField, BorderLayout.CENTER);
        p.add(Box.createRigidArea(HGAP5));

        palette.getContentPane().add(p, BorderLayout.SOUTH);

        palette.show();

        return palette;
    }


    class ShowFrameAction extends AbstractAction {
        InternalFrameDemo demo;
        Icon icon;


        public ShowFrameAction(InternalFrameDemo demo, Icon icon) {
            this.demo = demo;
            this.icon = icon;
        }

        public void actionPerformed(ActionEvent e) {
            demo.createInternalFrame(icon,
                                     getDemoFrameLayer(),
                                     getFrameWidth(),
                                     getFrameHeight()
            );
        }
    }

    public int getFrameWidth() {
        return FRAME_WIDTH;
    }

    public int getFrameHeight() {
        return FRAME_HEIGHT;
    }

    public Integer getDemoFrameLayer() {
        return DEMO_FRAME_LAYER;
    }
    
    class ImageScroller extends JScrollPane {

        public ImageScroller(InternalFrameDemo demo, Icon icon, int layer, int count) {
            super();
            JPanel p = new JPanel();
            p.setBackground(Color.white);
            p.setLayout(new BorderLayout() );

            p.add(new JLabel(icon), BorderLayout.CENTER);

            getViewport().add(p);
            getHorizontalScrollBar().setUnitIncrement(10);
            getVerticalScrollBar().setUnitIncrement(10);
        }

        public Dimension getMinimumSize() {
            return new Dimension(25, 25);
        }

    }

    void updateDragEnabled(boolean dragEnabled) {
        windowTitleField.setDragEnabled(dragEnabled);
    }

}
