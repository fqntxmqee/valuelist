/**
 * Copyright (c) 2003 held jointly by the individual authors.            
 *                                                                          
 * This library is free software; you can redistribute it and/or modify it    
 * under the terms of the GNU Lesser General Public License as published      
 * by the Free Software Foundation; either version 2.1 of the License, or 
 * (at your option) any later version.                                            
 *                                                                            
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; with out even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU Lesser General Public License for more details.                                                  
 *                                                                           
 * You should have received a copy of the GNU Lesser General Public License   
 * along with this library;  if not, write to the Free Software Foundation,   
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.              
 *                                                                            
 * > http://www.gnu.org/copyleft/lesser.html                                  
 * > http://www.opensource.org/licenses/lgpl-license.php
 */
package net.mlw.vlh.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.SwingUtilities;

import net.mlw.vlh.Errors;
import net.mlw.vlh.ErrorsException;
import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListHandler;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.swing.support.TableSorter;

/**
 * @author Matthew L. Wilson
 * @version $Revision: 1.8 $ $Date: 2005/08/30 18:54:25 $
 */
public class ValueListHelper implements ActionListener
{
   public static final String ACTION_COMMAND_RESET = "reset";

   public static final String ACTION_COMMAND_FIRST = "first";

   public static final String ACTION_COMMAND_PREVIOUS = "previous";

   public static final String ACTION_COMMAND_NEXT = "next";

   public static final String ACTION_COMMAND_LAST = "last";

   public static final String ACTION_COMMAND_SORT = "sort";

   private boolean useInvokeLatter = false;

   private int pagingPage = 1;

   private ValueListHandler vlh;

   private String name;

   private ValueListTableModel tableModel;

   private PagingComponent pagingComponent;

   private TableSorter tableSorter;

   private ValueListInfo info = new ValueListInfo();

   private List filterRetrievers = new ArrayList();

   private int pagingNumberPer = Integer.MAX_VALUE;

   private ActionListener errorListener;

   public ValueListHelper(ValueListHandler vlh, String name)
   {
      this(vlh, name, false);
   }

   public ValueListHelper(ValueListHandler vlh, String name, boolean useInvokeLatter)
   {
      this.vlh = vlh;
      this.name = name;
      this.useInvokeLatter = useInvokeLatter;
   }

   public void setPagingComponent(PagingComponent pagingComponent)
   {
      this.pagingComponent = pagingComponent;
      pagingComponent.addActionListener(this);
   }

   public void setValueListTableModel(ValueListTableModel tableModel)
   {
      this.tableModel = tableModel;
   }

   protected void getNewValueList()
   {
      if (useInvokeLatter)
      {
         SwingUtilities.invokeLater(valueListRunnable);
      }
      else
      {
         valueListRunnable.run();
      }
   }

   /**
    * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
    */
   public void actionPerformed(ActionEvent event)
   {
      if (event != null)
      {
         String command = event.getActionCommand();
         if (command != null)
         {
            if (ACTION_COMMAND_RESET.equals(command))
            {
               for (Iterator iter = filterRetrievers.iterator(); iter.hasNext();)
               {
                  FilterRetriever element = (FilterRetriever) iter.next();
                  element.reset();
               }
               return;
            }
            else if (ACTION_COMMAND_FIRST.equals(command))
            {
               info.setPagingPage(1);
            }
            else if (ACTION_COMMAND_PREVIOUS.equals(command))
            {
               info.setPagingPage(info.getPagingPage() - 1);
            }
            else if (ACTION_COMMAND_NEXT.equals(command))
            {
               info.setPagingPage(info.getPagingPage() + 1);
            }
            else if (ACTION_COMMAND_LAST.equals(command))
            {
               info.setPagingPage(info.getTotalNumberOfPages());
            }
            else if (ACTION_COMMAND_SORT.equals(command))
            {
               if (tableSorter != null)
               {
                  info.resetSorting();

                  Iterator iter = tableSorter.getSortingColumns().iterator();
                  if (iter.hasNext())
                  {
                     TableSorter.Directive directive = (TableSorter.Directive) iter.next();
                     info.setPrimarySortColumn(tableModel.getSortPropertyName(directive.getColumn()));
                     info.setPrimarySortDirection(directive.getDirection() == 1 ? ValueListInfo.ASCENDING : ValueListInfo.DESCENDING);

                     info.getFilters().put(ValueListInfo.SORT_COLUMN + "1", tableModel.getSortPropertyName(directive.getColumn()));
                     info.getFilters().put(ValueListInfo.SORT_DIRECTION + "1", directive.getDirection() == 1 ? "asc" : "desc");
                  }

                  for (int i = 2; iter.hasNext(); i++)
                  {
                     TableSorter.Directive directive = (TableSorter.Directive) iter.next();
                     info.getFilters().put(ValueListInfo.SORT_COLUMN + i, tableModel.getSortPropertyName(directive.getColumn()));
                     info.getFilters().put(ValueListInfo.SORT_DIRECTION + i, directive.getDirection() == 1 ? "asc" : "desc");
                  }
               }

            }
            else
            {
               Errors errors = new Errors();
               for (Iterator iter = filterRetrievers.iterator(); iter.hasNext();)
               {
                  FilterRetriever element = (FilterRetriever) iter.next();
                  String key = element.getFilterKey();
                  Object value = element.getFilterValue(errors);
                  info.getFilters().put(key, value);
               }

               if (errors.hasErrors())
               {
                  if (errorListener != null)
                  {
                     errorListener.actionPerformed(new ActionEvent(errors, 0, "errors"));
                     return;
                  }
                  else
                  {
                     throw new ErrorsException(errors);
                  }
               }

               info.setPagingPage(1);
            }

            info.getFilters().put("command", command);
            getNewValueList();
            info.getFilters().remove("command");
         }
      }

   }

   public void addFilterRetriever(FilterRetriever filterRetriever)
   {
      filterRetrievers.add(filterRetriever);
   }

   /**
    * @param tableSorter
    *           The tableSorter to set.
    */
   public void setTableSorter(TableSorter tableSorter)
   {
      tableSorter.addActionListener(this);
      this.tableSorter = tableSorter;
   }

   /**
    * @param pagingNumberPer The pagingNumberPer to set.
    */
   public void setPagingNumberPer(int pagingNumberPer)
   {
      info.setPagingNumberPer(pagingNumberPer);
   }

   protected Runnable valueListRunnable = new Runnable()
   {
      public synchronized void run()
      {
         try
         {
            ValueList valueList = vlh.getValueList(name, info);

            if (pagingComponent != null)
            {
               pagingComponent.setPagingInfo(valueList.getValueListInfo());
            }

            tableModel.setValueList(valueList);
         }
         catch (Throwable e)
         {
            if (errorListener != null)
            {
               errorListener.actionPerformed(new ActionEvent(e, 0, "error"));
            }
            else
            {
               e.printStackTrace();
            }
         }
      }
   };

   /**
    * @param errorListener The errorListener to set.
    */
   public void setErrorListener(ActionListener errorListener)
   {
      this.errorListener = errorListener;
   }
}