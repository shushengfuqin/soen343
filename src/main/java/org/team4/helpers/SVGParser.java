package org.team4.helpers;

public class SVGParser {

    String filePath;

    public SVGParser(String filePath) {
        setFilePath(filePath);
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}

