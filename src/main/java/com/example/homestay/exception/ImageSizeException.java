package com.example.homestay.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageSizeException extends RuntimeException {
  private ErrorCode errorCode;
  private String imageName;
  private long maxSize;  // in bytes
  private long actualSize; // in bytes

  public ImageSizeException(ErrorCode errorCode, String imageName, long maxSize, long actualSize) {
    super(formatMessage(imageName, maxSize, actualSize));
    this.errorCode = errorCode;
    this.imageName = imageName;
    this.maxSize = maxSize;
    this.actualSize = actualSize;
  }

  private static String formatMessage(String imageName, long maxSize, long actualSize) {
    return String.format("Image: %s, Max Size: %.2f MB, Actual Size: %.2f MB",
            imageName,
            bytesToMegabytes(maxSize),
            bytesToMegabytes(actualSize));
  }

  private static double bytesToMegabytes(long bytes) {
    return bytes / (1024.0 * 1024.0); // Convert bytes to MB
  }
}
