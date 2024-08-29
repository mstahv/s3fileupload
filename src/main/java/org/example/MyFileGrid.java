package org.example;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.vaadin.firitin.components.DynamicFileDownloader;
import org.vaadin.firitin.components.grid.VGrid;

@SpringComponent
public class MyFileGrid extends VGrid<MyFile> {
    private final S3StorageService s3StorageService;

    public MyFileGrid(S3StorageService s3StorageService) {
        super(MyFile.class);
        this.s3StorageService = s3StorageService;

        // Add a column with a dynamic file downloader
        addComponentColumn(s -> new DynamicFileDownloader(
                out -> s3StorageService.getFile(s.name()).transferTo(out)
        ).withFileNameGenerator(r -> s.name()))
                .setHeader("Download");

        updateItems();
    }

    public void updateItems() {
        setItems(s3StorageService.listFiles());
    }

}
