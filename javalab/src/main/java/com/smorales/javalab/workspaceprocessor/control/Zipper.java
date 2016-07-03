package com.smorales.javalab.workspaceprocessor.control;

import com.smorales.javalab.workspaceprocessor.boundary.NotRunnableCodeException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipper {

    static final String ZIP_FILENAME = "project.zip";

    public byte[] createZipFromFolder(Path folder) {
        List<Path> fileList = this.generateFileList(folder);
        Path pathToZip = this.zipIt(folder, fileList);
        return zipAsBytes(pathToZip);
    }

    /**
     * Traverse a directory and get all files, and add the file into fileList
     *
     * @param path file or directory
     */
    private List<Path> generateFileList(Path path) {
        try {
            return Files.walk(path)
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new NotRunnableCodeException("can not walk through files", e);
        }
    }

    private byte[] zipAsBytes(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new NotRunnableCodeException("can not read zip file");
        }
    }

    private Path zipIt(Path folder, List<Path> fileList) {
        try {
            String zipFilename = folder + File.separator + ZIP_FILENAME;
            ZipOutputStream zOut = new ZipOutputStream(new FileOutputStream(zipFilename));

            for (Path f : fileList) {
                ZipEntry zipEntry = new ZipEntry(f.toString());
                zOut.putNextEntry(zipEntry);

                FileInputStream fIn = new FileInputStream(f.toString());

                int len;
                byte[] buffer = new byte[1024];
                while ((len = fIn.read(buffer)) > 0) {
                    zOut.write(buffer, 0, len);
                }

                fIn.close();
            }

            zOut.closeEntry();
            zOut.close();

            return Paths.get(zipFilename);
        } catch (IOException ex) {
            throw new NotRunnableCodeException("can not create zip file");
        }
    }


}
