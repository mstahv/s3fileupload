package org.example;

import java.util.Date;

/**
 * A simple DTO, not to make the UI dependent on the S3 SDK
 */
public record MyFile(String name, long size, Date lastModified) {
}
