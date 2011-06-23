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
package net.mlw.vlh.swing.support;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 * @author Matthew L. Wilson
 * @version $Revision: 1.1 $ $Date: 2005/03/16 12:16:23 $
 */
public class DelegateToServiceTableSorter extends AbstractTableModel
{
   protected TableModel tableModel;

   public static final int DESCENDING = -1;

   public static final int NOT_SORTED = 0;

   public static final int ASCENDING = 1;

   private static Directive EMPTY_DIRECTIVE = new Directive(-1, NOT_SORTED);

   private JTableHeader tableHeader;

   private MouseListener mouseListener;

   private TableModelListener tableModelListener;

   private List sortingColumns = new ArrayList();

   public DelegateToServiceTableSorter()
   {
      this.mouseListener = new MouseHandler();
      this.tableModelListener = new TableModelHandler();
   }

   public DelegateToServiceTableSorter(TableModel tableModel)
   {
      this();
      setTableModel(tableModel);
   }

   public DelegateToServiceTableSorter(TableModel tableModel, JTableHeader tableHeader)
   {
      this();
      setTableHeader(tableHeader);
      setTableModel(tableModel);
   }

   private void clearSortingState()
   {
   }

   public TableModel getTableModel()
   {
      return tableModel;
   }

   public void setTableModel(TableModel tableModel)
   {
      if (this.tableModel != null)
      {
         this.tableModel.removeTableModelListener(tableModelListener);
      }

      this.tableModel = tableModel;
      if (this.tableModel != null)
      {
         this.tableModel.addTableModelListener(tableModelListener);
      }

      clearSortingState();
      fireTableStructureChanged();
   }

   public JTableHeader getTableHeader()
   {
      return tableHeader;
   }

   public void setTableHeader(JTableHeader tableHeader)
   {
      if (this.tableHeader != null)
      {
         this.tableHeader.removeMouseListener(mouseListener);
         TableCellRenderer defaultRenderer = this.tableHeader.getDefaultRenderer();
         if (defaultRenderer instanceof SortableHeaderRenderer)
         {
            this.tableHeader.setDefaultRenderer(((SortableHeaderRenderer) defaultRenderer).tableCellRenderer);
         }
      }
      this.tableHeader = tableHeader;
      if (this.tableHeader != null)
      {
         this.tableHeader.addMouseListener(mouseListener);
         this.tableHeader.setDefaultRenderer(new SortableHeaderRenderer(this.tableHeader.getDefaultRenderer()));
      }
   }

   public boolean isSorting()
   {
      return sortingColumns.size() != 0;
   }

   private Directive getDirective(int column)
   {
      for (int i = 0; i < sortingColumns.size(); i++)
      {
         Directive directive = (Directive) sortingColumns.get(i);
         if (directive.column == column)
         {
            return directive;
         }
      }
      return EMPTY_DIRECTIVE;
   }

   public int getSortingStatus(int column)
   {
      return getDirective(column).direction;
   }

   private void sortingStatusChanged()
   {
      clearSortingState();
      fireTableDataChanged();
      if (tableHeader != null)
      {
         tableHeader.repaint();
      }
   }

   public void setSortingStatus(int column, int status)
   {
      Directive directive = getDirective(column);
      if (directive != EMPTY_DIRECTIVE)
      {
         sortingColumns.remove(directive);
      }
      if (status != NOT_SORTED)
      {
         sortingColumns.add(new Directive(column, status));
      }
      sortingStatusChanged();
   }

   protected Icon getHeaderRendererIcon(int column, int size)
   {
      Directive directive = getDirective(column);
      if (directive == EMPTY_DIRECTIVE)
      {
         return null;
      }
      return new Arrow(directive.direction == DESCENDING, size, sortingColumns.indexOf(directive));
   }

   private void cancelSorting()
   {
      sortingColumns.clear();
      sortingStatusChanged();
   }

   // TableModel interface methods

   public int getRowCount()
   {
      return (tableModel == null) ? 0 : tableModel.getRowCount();
   }

   public int getColumnCount()
   {
      return (tableModel == null) ? 0 : tableModel.getColumnCount();
   }

   public String getColumnName(int column)
   {
      return tableModel.getColumnName(column);
   }

   public Class getColumnClass(int column)
   {
      return tableModel.getColumnClass(column);
   }

   public boolean isCellEditable(int row, int column)
   {
      return tableModel.isCellEditable(row, column);
   }

   public Object getValueAt(int row, int column)
   {
      return tableModel.getValueAt(row, column);
   }

   public void setValueAt(Object aValue, int row, int column)
   {
      tableModel.setValueAt(aValue, row, column);
   }

   private ActionListener actionListener;

   public void addActionListener(ActionListener actionListener)
   {
      this.actionListener = actionListener;
   }

   /**
    * @return Returns the sortingColumns.
    */
   public List getSortingColumns()
   {
      return sortingColumns;
   }

   // Helper classes

   private class TableModelHandler implements TableModelListener
   {
      public void tableChanged(TableModelEvent e)
      {
         // If we're not sorting by anything, just pass the event along.
         if (!isSorting())
         {
            clearSortingState();
            fireTableChanged(e);
            return;
         }

         // If the table structure has changed, cancel the sorting; the
         // sorting columns may have been either moved or deleted from
         // the model.
         if (e.getFirstRow() == TableModelEvent.HEADER_ROW)
         {
            cancelSorting();
            fireTableChanged(e);
            return;
         }

         // Something has happened to the data that may have invalidated the row order.
         clearSortingState();
         fireTableDataChanged();
         return;
      }
   }

   private class MouseHandler extends MouseAdapter
   {
      public void mouseClicked(MouseEvent e)
      {
         JTableHeader h = (JTableHeader) e.getSource();
         TableColumnModel columnModel = h.getColumnModel();
         int viewColumn = columnModel.getColumnIndexAtX(e.getX());
         int column = columnModel.getColumn(viewColumn).getModelIndex();
         if (column != -1)
         {
            int status = getSortingStatus(column);
            if (!e.isControlDown())
            {
               cancelSorting();
            }
            // Cycle the sorting states through {NOT_SORTED, ASCENDING, DESCENDING} or
            // {NOT_SORTED, DESCENDING, ASCENDING} depending on whether shift is pressed.
            status = status + (e.isShiftDown() ? -1 : 1);
            status = (status + 4) % 3 - 1; // signed mod, returning {-1, 0, 1}
            setSortingStatus(column, status);
            if (actionListener != null)
            {
               actionListener.actionPerformed(new ActionEvent(e.getSource(), e.getID(), "sort", e.getModifiers()));
            }
         }
      }
   }

   private static class Arrow implements Icon
   {
      private boolean descending;

      private int size;

      private int priority;

      public Arrow(boolean descending, int size, int priority)
      {
         this.descending = descending;
         this.size = size;
         this.priority = priority;
      }

      public void paintIcon(Component c, Graphics g, int x, int y)
      {
         Color color = c == null ? Color.GRAY : c.getBackground();
         // In a compound sort, make each succesive triangle 20%
         // smaller than the previous one.
         int dx = (int) (size / 2 * Math.pow(0.8, priority));
         int dy = descending ? dx : -dx;
         // Align icon (roughly) with font baseline.
         y = y + 5 * size / 6 + (descending ? -dy : 0);
         int shift = descending ? 1 : -1;
         g.translate(x, y);

         // Right diagonal.
         g.setColor(color.darker());
         g.drawLine(dx / 2, dy, 0, 0);
         g.drawLine(dx / 2, dy + shift, 0, shift);

         // Left diagonal.
         g.setColor(color.brighter());
         g.drawLine(dx / 2, dy, dx, 0);
         g.drawLine(dx / 2, dy + shift, dx, shift);

         // Horizontal line.
         if (descending)
         {
            g.setColor(color.darker().darker());
         }
         else
         {
            g.setColor(color.brighter().brighter());
         }
         g.drawLine(dx, 0, 0, 0);

         g.setColor(color);
         g.translate(-x, -y);
      }

      public int getIconWidth()
      {
         return size;
      }

      public int getIconHeight()
      {
         return size;
      }
   }

   private class SortableHeaderRenderer implements TableCellRenderer
   {
      private TableCellRenderer tableCellRenderer;

      public SortableHeaderRenderer(TableCellRenderer tableCellRenderer)
      {
         this.tableCellRenderer = tableCellRenderer;
      }

      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column)
      {
         Component c = tableCellRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
         if (c instanceof JLabel)
         {
            JLabel l = (JLabel) c;
            l.setHorizontalTextPosition(JLabel.LEFT);
            int modelColumn = table.convertColumnIndexToModel(column);
            l.setIcon(getHeaderRendererIcon(modelColumn, l.getFont().getSize()));
         }
         return c;
      }
   }

   public static class Directive
   {
      private int column;

      private int direction;

      public Directive(int column, int direction)
      {
         this.column = column;
         this.direction = direction;
      }

      /**
       * @return Returns the column.
       */
      public int getColumn()
      {
         return column;
      }

      /**
       * @return Returns the direction.
       */
      public int getDirection()
      {
         return direction;
      }
   }
}