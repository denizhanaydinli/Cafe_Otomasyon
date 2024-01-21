package org.example.Main.util;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.*;

public class AdisyonExcelOlustur {
    public AdisyonExcelOlustur() {
    }

    public void toExcel(JTable table, String tfiyat, String indirim, String gfiyat) {
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
                excel.write("\t\t\t\tToplam Fiyat:\t" + tfiyat);
                excel.newLine();
                excel.write("\t\t\t\tToplam İndirim :\t" + indirim);
                excel.newLine();
                excel.write("\t\t\t\tGenel Toplam:\t" + gfiyat);
                excel.flush();
                excel.close();
            }
        } catch (IOException var12) {
            System.out.println(var12);
        }

    }
}
