package edu.najah.cap.ex;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CanNotWriteFileException extends Throwable {
    public CanNotWriteFileException(String message) {
        super(message);
    }
}
