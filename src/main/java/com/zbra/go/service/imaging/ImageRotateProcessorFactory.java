package com.zbra.go.service.imaging;

public interface ImageRotateProcessorFactory {

    ImageRotateProcessor getImageRotateProcessor(int orientationTag);

}
