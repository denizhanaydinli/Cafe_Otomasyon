package org.example.Main.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

public class ParaFormati {
    public ParaFormati() {
    }

    public String ParayaCevir(BigDecimal amount) {
        NumberFormat formatter = new DecimalFormat("#,##0.00");
        String para = formatter.format(amount);
        return para;
    }

    public String GeriCevir(String amount) {
        amount = amount.replace(".", "");
        amount = amount.replace(',', '.');
        return amount;
    }

    public static void main(String[] args) throws ParseException {
        ParaFormati pf = new ParaFormati();
        pf.GeriCevir("2,5");
    }
}
