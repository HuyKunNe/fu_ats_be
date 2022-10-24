package com.fu.fuatsbe;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@RequiredArgsConstructor
class FuAtsBeApplicationTests {

    @Test
    void addAccount() {
        try (PDDocument document = new PDDocument()) {

            File file = new File("E://CN9//Capstone//Nguyen-Huu-Duong_CV-Eng.pdf");
            // Reading the pdf file
            PDDocument loadDocument = Loader.loadPDF(file);

            // Get the number of pages
            System.out.println("Number of pages in the pdf :" +
                    loadDocument.getNumberOfPages());

            // Strip the text from a particular page
            PDFTextStripper textStripper = new PDFTextStripper();
            // Lets read page 1
            textStripper.setStartPage(1);
            textStripper.setEndPage(1);
            System.out.println("Text in the pdf >>> " +
                    textStripper.getText(loadDocument));
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    // @Test
    // void convertString() {
    // String str = "123.asd567";
    // System.out.println(str.replaceAll("[^0-9]", ""));
    // }

}
