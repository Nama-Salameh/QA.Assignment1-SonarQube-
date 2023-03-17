package edu.najah.cap.ex;

import java.nio.file.FileSystemException;

public class EditorException extends FileSystemException {

    public EditorException(String message) {
        super(message);
    }
}
