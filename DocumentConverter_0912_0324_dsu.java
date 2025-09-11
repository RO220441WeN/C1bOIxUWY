// 代码生成时间: 2025-09-12 03:24:25
package com.example.playframework.converter;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.OutputStream;

/**
 * DocumentConverter provides functionality to convert documents from one format to another.
 * It currently supports conversion from Word to PDF.
 */
public class DocumentConverter extends Controller {

    private static final String CONVERTED_FILE_PREFIX = "converted_";
    private static final String TEMP_ZIP_FILE_NAME = "temp.zip";
    private static final String WORD_TO_PDF = "wordToPdf";
    private static final String TEMP_DIRECTORY = "temp";
    private static final String TEMP_FILE_EXTENSION = ".docx";
    private static final String TARGET_FILE_EXTENSION = ".pdf";

    /**
     * Converts a Word document to a PDF file.
     *
     * @param http request containing the uploaded Word document.
     * @return a Result object with the converted PDF file.
     */
    public static Result convertWordToPdf(Http.MultipartFormData<File> request) {
        Http.MultipartFormData.FilePart<File> filePart = request.getFile(WORD_TO_PDF);
        if (filePart == null) {
            return badRequest("Missing file part");
        }
        File wordFile = filePart.getFile();
        try {
            File tempFile = createTempFile(wordFile);
            File pdfFile = convert(wordFile, tempFile);

            response().setContentType("application/pdf");
            return ok(new FileInputStream(pdfFile));
        } catch (IOException | DocumentException e) {
            return internalServerError(e.getMessage());
        }
    }

    /**
     * Creates a temporary file and copies the uploaded file into it.
     *
     * @param uploadedFile The uploaded Word file.
     * @return A File object representing the temporary file.
     * @throws IOException If an I/O error occurs.
     */
    private static File createTempFile(File uploadedFile) throws IOException {
        File tempDir = new File(TEMP_DIRECTORY);
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
        File tempFile = File.createTempFile(CONVERTED_FILE_PREFIX, TEMP_FILE_EXTENSION, tempDir);
        try (FileInputStream fis = new FileInputStream(uploadedFile);
             FileOutputStream fos = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[8 * 1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
        return tempFile;
    }

    /**
     * Converts the Word document to a PDF file.
     *
     * @param wordFile The Word document to convert.
     * @param tempFile The temporary file to write the PDF to.
     * @return A File object representing the PDF file.
     * @throws IOException If an I/O error occurs.
     * @throws DocumentException If a document conversion error occurs.      */
    private static File convert(File wordFile, File tempFile) throws IOException, DocumentException {
        try (XWPFDocument document = new XWPFDocument(new FileInputStream(wordFile));
             PdfWriter writer = new PdfWriter(new FileOutputStream(tempFile))) {
            PdfOptions options = PdfOptions.create();
            PdfConverter.getInstance().convert(document, writer, options);
        }
        return tempFile;
    }
}
