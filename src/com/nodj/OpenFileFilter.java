package com.nodj;

import java.io.File;
import javax.swing.filechooser.*;

public class OpenFileFilter extends FileFilter {

    String description;
    String fileExt;

    public OpenFileFilter(String extension) {
        fileExt = extension;
        description = extension;
    }

    @Override
    public boolean accept(File f) {
        if (f.isDirectory())
            return true;
        return (f.getName().toLowerCase().endsWith(fileExt));
    }

    @Override
    public String getDescription() {
        return description;
    }
}