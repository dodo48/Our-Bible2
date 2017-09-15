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
import java.io.File.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.border.*;
import javax.swing.colorchooser.*;
import javax.swing.filechooser.*;
import javax.accessibility.*;
import javax.swing.JFileChooser.*;


import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import java.io.*;
import java.applet.*;
import java.net.*;

/**
 * List Demo. This demo shows that it is not
 * always necessary to have an array of objects
 * as big as the size of the list stored.
 *
 * Indeed, in this example, there is no array
 * kept for the list data, rather it is generated
 * on the fly as only those elements are needed.
 *
 * @author Jeff Dinkins
 */
public class ListDemo extends DemoModule {
    JList list;

    JPanel prefixList;

    JPanel suffixList;

    Action prefixAction;
    Action suffixAction;

    //GeneratedListModel listModel;

    Vector checkboxes = new Vector();


    /**
     * ListDemo Constructor
     */
    public ListDemo(SwingSet2 swingset) {
    	
    	
        super(swingset, "ListDemo", "toolbar/JList.gif");

        //loadImages();
        
        setLayout(new BorderLayout());

        JLabel description = new JLabel(getString("ListDemo.description"));
        getDemoPanel().add(description, BorderLayout.NORTH);

        JButton addBook = new JButton("Добавить");
        getDemoPanel().add(addBook, BorderLayout.WEST);

        JButton renBook = new JButton("Изменить");
        getDemoPanel().add(renBook, BorderLayout.CENTER);
        
        JButton delBook = new JButton("Удалить");
        getDemoPanel().add(delBook, BorderLayout.EAST);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        centerPanel.add(Box.createRigidArea(HGAP10));

        
        getDemoPanel().add(centerPanel, BorderLayout.SOUTH);
 
        
        
    }
    
    
    
    
    
    
    
}
