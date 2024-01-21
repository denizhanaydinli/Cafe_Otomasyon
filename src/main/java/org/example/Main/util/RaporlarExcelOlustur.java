package org.example.Main.util;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.*;

public class RaporlarExcelOlustur {
    public RaporlarExcelOlustur() {
    }

    public void toExcel(JTable table, String tfiyat) {
        try {
            new ParaFormati();
            TableModel model = table.getModel();
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(0);
            fc.showOpenDialog(table);
            File file = fc.getSelectedFile();
            if (!file.isFile()) {
                BufferedWriter excel = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file + ".xls"), "windows-1254"));
                excel.newLine();
                excel.newLine();

                int i;
                for(i = 0; i < model.getColumnCount(); ++i) {
                    excel.write(model.getColumnName(i) + "\t");
                }

                excel.newLine();

                for(i = 0; i < model.getRowCount(); ++i) {
                    for(int j = 0; j < model.getColumnCount(); ++j) {
                        excel.write(model.getValueAt(i, j).toString() + "\t");
                    }

                    excel.newLine();
                }

                excel.newLine();
                excel.newLine();
                excel.write("\t\tToplam Fiyat:\t" + tfiyat);
                excel.newLine();
                excel.flush();
            }
        } catch (IOException var10) {
            System.out.println(var10);
        }

    }
}
