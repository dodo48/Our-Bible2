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
import java.sql.SQLException;

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

	public ListDemo(SwingSet2 swingset) {
		
		super(swingset, "ListDemo", "toolbar/JList.gif");

        setLayout(new BorderLayout());
     
       DefaultListModel listAllBook;
       
       JLabel description = new JLabel(getString("ListDemo.description"));
       getDemoPanel().add(description, BorderLayout.NORTH);

       JButton addBook = new JButton("Добавить");
       getDemoPanel().add(addBook, BorderLayout.WEST);

       JButton renBook = new JButton("Изменить");
       getDemoPanel().add(renBook, BorderLayout.CENTER);
       
       JButton delBook = new JButton("Удалить");
       getDemoPanel().add(delBook, BorderLayout.EAST);
              
       listAllBook = new DefaultListModel();
       
       listAllBook.add(0, "Первый элемент");
       
       /*
       final JCheckBox cb = (JCheckBox) listAllBook.add(0, new JCheckBox(listAllBook));
       checkboxes.addElement(cb);
       cb.setSelected(selected);
       cb.addActionListener(prefixAction);
       if(selected) {
           listModel.addPrefix(prefix);
       }
       cb.addFocusListener(listFocusListener);       
   		*/
       
       ArrayList<BookDescription> allBook =  AllBookDescription.getAllBookDescription();
	
       for(BookDescription str: allBook) {
  		
   			listAllBook.add(0, str.IDBook);
   		}
       
   		JList listAllBookDescription = new JList(listAllBook);
   		getDemoPanel().add(listAllBookDescription, BorderLayout.SOUTH);
   		
   		getDemoPanel().add(new JScrollPane(listAllBookDescription));
   		
   		//listAllBookDescription.add(new JScrollPane());
   		
   		addBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBook(evt);
            }

			private void addBook(ActionEvent evt) {
				
				JFileChooser dialog = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("SQLite", "SQLite3");
				String descriptionBook = "";
				
				dialog.setFileFilter(filter);
				int ret = dialog.showDialog(null, "Открыть файл");  
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = dialog.getSelectedFile();
                    System.out.println("Выбранный файл: " + file.getName());
                    
                    // Проверка на совпадение
                    if(AllBookDescription.compBook(file.getName())) {
                    	
                    	AllBookDescription.addBook("1", file.getName(), "");
                    	listAllBook.add(0, file.getName());
                    	
                    	// Отрыть выбранную базу для чтения полного названия
                        DBase discretionaryBook = new DBase("db//" + file.getName(), "Произвольная книга");
                        try {
                        	discretionaryBook.ConnDBase();
                        	descriptionBook = discretionaryBook.ReadDescription(); 
                        	System.out.println("Название книги: " + descriptionBook);
                        	
                        	AllBookDescription.addBook("1", file.getName(), descriptionBook);
                        	listAllBook.add(0, descriptionBook);

                        	
                        	discretionaryBook.CloseDB();
                		} catch (ClassNotFoundException e) {
                			e.printStackTrace();
                		} catch (SQLException e) {
                			e.printStackTrace();
                		}
                    	
                        
                    	
                    	
                        // Общий список книг
                    	
                        DBase FullBooksList = new DBase("db//FullBooksList.SQLite3", "Общий список книг");
                        try {
                        	FullBooksList.ConnDBase();
                        	FullBooksList.SaveInfo(file.getName(), descriptionBook);  
                        	FullBooksList.CloseDB();
                		} catch (ClassNotFoundException e) {
                			e.printStackTrace();
                		} catch (SQLException e) {
                			e.printStackTrace();
                		}
                    	

                    }
                    else {
                    	
                    JOptionPane.showMessageDialog(null, "Данный объект присутствует в списке", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    
                    }
                }
				
			}
        });        
        
	}
}
