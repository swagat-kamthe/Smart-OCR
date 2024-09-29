package com.example.Gemini;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Controller
public class TestController {

    // Ensure the path is correct and accessible
    private static final String UPLOAD_DIR = "D:/spring/demo/src/main/resources/static/uploads/";

    @PostMapping("/finally")
    @ResponseBody
    public ModelAndView handleFormSubmission(@RequestParam("file2")  MultipartFile file) {
    	System.out.println("Hello hi");
    	
    	if (file.isEmpty()) {
    		String extractedText = "";
    		ModelAndView modelAndView = new ModelAndView("processText");
        	modelAndView.addObject("extractedText", extractedText);
            return modelAndView;
        }

        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
            	String extractedText = "";
            	ModelAndView modelAndView = new ModelAndView("processText");
            	modelAndView.addObject("extractedText", extractedText);
                return modelAndView;
            }
            System.out.println("1");
            String extractedText = extractTextFromImage(image);
            System.out.println(extractedText);
        	ModelAndView modelAndView = new ModelAndView("processText");
        	modelAndView.addObject("extractedText", extractedText);
            return modelAndView;
    	
    }catch(IOException ie){
    	String extractedText = "";
    	ModelAndView modelAndView = new ModelAndView("processText");
    	modelAndView.addObject("extractedText", extractedText);
        return modelAndView;
    	}
    }
    
    private String extractTextFromImage(BufferedImage image) {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("src/main/resources/templates/tessdata"); // Path to tessdata directory
        tesseract.setLanguage("mar+eng"); // Set language to English

        try {
            return tesseract.doOCR(image);
        } catch (TesseractException e) {
            e.printStackTrace();
            return "Error while extracting text";
        }
    }
}
