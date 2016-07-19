package com.smorales.javalab.business.files;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class ZipperTest {

    private Path tempDir = Paths.get("../java-gradle");

    private Zipper zipper;

    @Before
    public void setUp() {
        zipper = new Zipper();
    }

    @After
    public void tearDown() {
        Path zipFile = Paths.get(tempDir + File.separator + Zipper.ZIP_FILENAME);
        if (zipFile.toFile().exists()) {
            zipFile.toFile().delete();
        }
    }

    @Test
    public void testCreateZipFromFolder() {
        //act
        byte[] zip = zipper.createZipFromFolder(tempDir);

        assertThat(zip).isNotEmpty();
    }

}