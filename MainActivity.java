package com.example.bhargav_2.itextdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    File dest;
    Button btn;
    PdfPTable table;
    PdfPCell cell1,cell2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dest=new File(getCacheDir(),"statement.pdf");

        btn=(Button)findViewById(R.id.genPdfBtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPDF();

                Intent i=new Intent(MainActivity.this,PdfConfirmActivity.class);
                startActivity(i);
            }
        });

    }

    protected void createPDF(){
        Document document=new Document();
        //location to save
        try {
            PdfWriter.getInstance(document,new FileOutputStream(dest));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //open to write
        document.open();

        //setting up doc
        document.setPageSize(PageSize.A4);
        document.addCreationDate();
        document.addAuthor("Android");
        document.addCreator("Android");

        BaseColor mColorAccent=new BaseColor(0,153,204,255);
        float mHeadingFontSize=20.0f;
        float mValueFontSize=26.0f;

        // LINE SEPARATOR
        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));

        String[] statementDetails={"Account Number","Transaction type","Effective Date","Process Date","Principal",
                "Interest","Miscellaneous fees","Total Payment","Principal before payment","Payment method"};

        String[] statementValues={"123412341234","One-Time Payment","07/11/17","07/11/17","$200.00","$40.00","$0.00",
                "$249.00","$18,000.00","Chas...4578"};

        Chunk amountPaidChunk=new Chunk("$249");
        Paragraph amountPaidPara=new Paragraph(amountPaidChunk);
        amountPaidPara.setAlignment(Element.ALIGN_CENTER);
        Chunk paymentDateStrChunk=new Chunk("Payment made on 07/11/17");
        Paragraph paymentDateStrPara=new Paragraph(paymentDateStrChunk);
        paymentDateStrPara.setAlignment(Element.ALIGN_CENTER);
        try {
            document.add(amountPaidPara);
            document.add(paymentDateStrPara);
            document.add(new Paragraph(""));
            document.add(new Chunk(lineSeparator));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        try {
            table=new PdfPTable(2); //Two columns
            for(int i=0;i<statementDetails.length;i++){
                document.add(new Paragraph(""));
                cell1=new PdfPCell(Phrase.getInstance(statementDetails[i]));
                table.addCell(cell1);
                cell2=new PdfPCell(Phrase.getInstance(statementValues[i]));
                table.addCell(cell2);

//                document.add(new Paragraph(""));
//                document.add(new Chunk(lineSeparator));
//                document.add(new Paragraph(""));
//                Chunk keyChunk=new Chunk(statementDetails[i]);
//                Chunk valueChunk=new Chunk(statementValues[i]);
//                Paragraph para=new Paragraph();
//                para.add(keyChunk);
//                para.add(valueChunk);
//                document.add(para);

            }
            document.add(table);
        }catch (Exception e){
            e.printStackTrace();
        }

        document.close();

    }
}
