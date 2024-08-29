package org.example;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.firitin.components.upload.UploadFileHandler;

import java.io.InputStream;

@Route
public class MainView extends VerticalLayout {

    public MainView(S3StorageService s3StorageService, MyFileGrid myFileGrid) {
        add(new H1("S3 file upload example"));
        add(new Paragraph("This is a simple example of uploading files to S3 using Vaadin Flow."));

        // UploadFileHandler saves no tempfiles, but streams the file directly to the S3 storage
        UploadFileHandler uploadFileHandler = new UploadFileHandler((InputStream content, UploadFileHandler.FileDetails metaData) -> {
            s3StorageService.uploadFile(metaData.fileName(), content);
            return () -> {
                // This is called when the upload is done, in "UI thread"
                myFileGrid.updateItems();
                Notification.show("File uploaded: " + metaData.fileName());
            };
        })
        // suggest camera and hide DD area by uncommenting the following lines
        // .withAcceptedFileTypes("image/*;capture=camera")
        // .withDragAndDrop(false)
        ;

        add(uploadFileHandler);
        add(myFileGrid);

    }

}
