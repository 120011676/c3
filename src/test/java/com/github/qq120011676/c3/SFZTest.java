package com.github.qq120011676.c3;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.nio.file.Paths;

public class SFZTest {
    public static void main(String[] args) throws TesseractException {
        String projectPath = System.getProperty("user.dir");
        String filepath = Paths.get(projectPath,
                        "src",
                        "test",
                        "resources")
                .toString();
        ITesseract iTesseract = new Tesseract();
        iTesseract.setLanguage("chi_sim");
        iTesseract.setDatapath(filepath);
        String result = iTesseract.doOCR(new File("C:\\Users\\12001\\Downloads\\QQ图片20231016171405.png"));
        System.out.println(result);
    }
}
