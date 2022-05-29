package at.technikum_wien.tourPlanner.businessLayer.pdfGeneration;

import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.models.TourLog;
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

import java.util.List;
import java.io.File;
import java.io.IOException;

public class PdfGeneration {



    public static void generateTourReport(Tour tour) throws IOException {
        // check if file directory where reports should be saved exists
        File reportDirectory = new File("reports/");
        if (!reportDirectory.exists()) {
            reportDirectory.mkdir();
        }

        String pdfName = "reports/" + tour.getUid() + "-report.pdf";

        PdfWriter writer = new PdfWriter(pdfName);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // header
        Paragraph tourReportHeader = new Paragraph(tour.getName() + " Report")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(18)
                .setBold()
                .setFontColor(ColorConstants.BLUE);
        document.add(tourReportHeader);

        // attributes
        document.add(new Paragraph("Name: " + tour.getName()));
        document.add(new Paragraph("Description: " + tour.getDescription()));
        document.add(new Paragraph("Starting Point: " + tour.getStartingPoint()));
        document.add(new Paragraph("Destination: " + tour.getDestination()));
        document.add(new Paragraph("Transport Type: " + tour.getTransportType().name()));
        document.add(new Paragraph("Tour Distance: " + tour.getLength()));
        document.add(new Paragraph("Estimated Time: " + tour.getDuration()));

        // log table
        Paragraph tourLogsHeader = new Paragraph(tour.getName() + " Logs")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(18)
                .setBold()
                .setFontColor(ColorConstants.BLUE);
        document.add(tourLogsHeader);

        if (tour.getLogs().isEmpty()) {
            document.add(new Paragraph("This tour has no logs."));
        } else {
            Table table = new Table(UnitValue.createPercentArray(5)).useAllAvailableWidth();
            table.addHeaderCell(getHeaderCell("Date"));
            table.addHeaderCell(getHeaderCell("Comment"));
            table.addHeaderCell(getHeaderCell("Difficulty"));
            table.addHeaderCell(getHeaderCell("Total time"));
            table.addHeaderCell(getHeaderCell("Rating"));
            table.setFontSize(14).setBackgroundColor(ColorConstants.WHITE);
            for (TourLog log : tour.getLogs()) {
                table.addCell(log.getDate().toString());
                table.addCell(log.getComment());
                table.addCell(Integer.toString(log.getDifficulty()));
                table.addCell(log.getTotalTime().toString());
                table.addCell(Integer.toString(log.getRating()));
            }
            document.add(table);

        }

        document.add(new AreaBreak());

        // tour map image
        Paragraph imageHeader = new Paragraph(tour.getName() + " Route")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(18)
                .setBold()
                .setFontColor(ColorConstants.GREEN);
        document.add(imageHeader);
        ImageData imageData = ImageDataFactory.create("images/" + tour.getMapImage());
        document.add(new Image(imageData));

        document.close();

    }

    public static void generateSummaryReport(Tour tour) throws IOException {
        // check if file directory where reports should be saved exists
        File reportDirectory = new File("reports/");
        if (!reportDirectory.exists()) {
            reportDirectory.mkdir();
        }

        String pdfName = "reports/" + tour.getUid() + "-summary-report.pdf";

        PdfWriter writer = new PdfWriter(pdfName);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // header
        Paragraph summaryReportHeader = new Paragraph(tour.getName() + " Summary Report")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(18)
                .setBold()
                .setFontColor(ColorConstants.BLUE);
        document.add(summaryReportHeader);

        if (tour.getLogs().isEmpty()) {
            // tour has no logs so no data can be generated
            document.add(new Paragraph(tour.getName() + " has no logs. No data can be generated."));
        } else {
            // tour log data
            String[] averages = calculateAverages(tour.getLogs());
            document.add(new Paragraph("Average Time: " + averages[0]));
            document.add(new Paragraph("Average Difficulty: " + averages[1]));
            document.add(new Paragraph("Average Rating: " + averages[2]));
        }

        document.close();
    }

    private static Cell getHeaderCell(String s) {
        return new Cell().add(new Paragraph(s)).setBold().setBackgroundColor(ColorConstants.LIGHT_GRAY);
    }

    // [0] stores average time, [1] stores average difficulty, [3] stores average rating
    private static String[] calculateAverages(List<TourLog> tourLogs) {
        String[] returnArray = new String[3];

        int averageDifficulty = 0;
        int averageTime = 0;
        float averageRating = 0;

        for (TourLog log : tourLogs) {
            // average difficulty
            averageDifficulty += log.getDifficulty();

            // average time difficulty
            String[] values = log.getTotalTime().toString().split(":");
            int hours = Integer.parseInt(values[0]);
            int minutes = Integer.parseInt(values[1]);

            averageTime += ((hours * 60) + minutes);

            averageRating += log.getRating();

        }

        averageDifficulty = averageDifficulty / tourLogs.size();
        averageTime = averageTime / tourLogs.size();
        averageRating = averageRating / tourLogs.size();

        // parse averageTime from minutes back into hh:mm format
        int hours = averageTime / 60;
        int minutes = averageTime % 60;
        String returnTime = (hours + ":" + minutes);

        returnArray[0] = returnTime;
        returnArray[1] = Integer.toString(averageDifficulty);
        returnArray[2] = Float.toString(averageRating).substring(0, 3); // return float in format X.X

        return returnArray;
    }

}
