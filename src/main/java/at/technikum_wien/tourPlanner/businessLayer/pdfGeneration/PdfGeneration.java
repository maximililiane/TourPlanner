package at.technikum_wien.tourPlanner.businessLayer.pdfGeneration;

import at.technikum_wien.tourPlanner.Injector;
import at.technikum_wien.tourPlanner.configuration.Configuration;
import at.technikum_wien.tourPlanner.models.Tour;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.UnitValue;

import java.io.File;
import java.io.IOException;

public class PdfGeneration {

    //  private final String REPORT_DIRECTORY;

//    public PdfGeneration() {
//        Configuration configuration = Injector.getConfig("app.properties");
//        this.REPORT_DIRECTORY = configuration.get("report.directory");
//    }

    public static void generateTourReport(Tour tour) throws IOException {
        // check if file directory where reports should be saved exists
        File reportDirectory = new File("reports/");
        if (!reportDirectory.exists()) {
            reportDirectory.mkdir();
        }

        String pdfName = "reports/" + tour.getUid() + ".pdf";

        PdfWriter writer = new PdfWriter(pdfName);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        Paragraph tourReportHeader = new Paragraph(tour.getName() + " report")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(14)
                .setBold()
                .setFontColor(ColorConstants.RED);
        document.add(tourReportHeader);


        document.add(new Paragraph("Name: " + tour.getName()));
        document.add(new Paragraph("Description: " + tour.getDescription()));
        document.add(new Paragraph("Starting Point: " + tour.getStartingPoint()));
        document.add(new Paragraph("Destination: " + tour.getDestination()));
        document.add(new Paragraph("Transport Type: " + tour.getTransportType()));
        document.add(new Paragraph("Tour Distance: " + tour.getLength()));
        document.add(new Paragraph("Estimated Time: " + tour.getDuration()));

        Paragraph imageHeader = new Paragraph(tour.getName() + " route")
                .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN))
                .setFontSize(18)
                .setBold()
                .setFontColor(ColorConstants.GREEN);
        document.add(imageHeader);
        ImageData imageData = ImageDataFactory.create("images/" + tour.getMapImage());
        document.add(new Image(imageData));

        // TODO: add tour logs!

        document.close();

    }

//    private static Cell getHeaderCell(String s) {
//        return new Cell().add(new Paragraph(s)).setBold().setBackgroundColor(ColorConstants.GRAY);
//    }

    // TODO: implement generateSummaryReport()

}
