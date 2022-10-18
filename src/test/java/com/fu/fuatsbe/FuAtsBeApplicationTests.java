package com.fu.fuatsbe;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@RequiredArgsConstructor
class FuAtsBeApplicationTests {

    // @Test
    // void addAccount() {
    // try (PDDocument document = new PDDocument()) {

    // File file = new File(
    // "https:\\firebasestorage.googleapis.com/v0/b/ats-storage-44e44.appspot.com/o/candidate-CV%2FNguyen-Huu-Duong_CV-Eng.pdf?alt=media&token=2f5e4932-0643-495c-a032-8b6759f53bc1&fbclid=IwAR2KM0JrkRL2l9LMSB0LhV-F1lg-Vouzk9ukEFQRs4s0zDccxuu4sRXul78");
    // // Reading the pdf file
    // PDDocument loadDocument = Loader.loadPDF(file);

    // // Get the number of pages
    // System.out.println("Number of pages in the pdf :" +
    // loadDocument.getNumberOfPages());

    // // Strip the text from a particular page
    // PDFTextStripper textStripper = new PDFTextStripper();
    // // Lets read page 1
    // textStripper.setStartPage(1);
    // textStripper.setEndPage(1);
    // System.out.println("Text in the pdf >>> " +
    // textStripper.getText(loadDocument));
    // } catch (IOException ie) {
    // ie.printStackTrace();
    // }
    // }

    // @Test
    // void convertString() {
    // String str = "123.asd567";
    // System.out.println(str.replaceAll("[^0-9]", ""));
    // }

}
