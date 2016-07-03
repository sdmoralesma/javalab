package com.smorales.javalab.workspaceprocessor.control;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class ZipperTest {

    private Path tempDir = Paths.get("/home/sergio/IdeaProjects/javalab/java-gradle");

    private Zipper zipper;
    private FileManager fileManager;

    @Before
    public void setUp() {
        zipper = new Zipper();
        fileManager = new FileManager();
        fileManager.tracer = mock(Logger.class);
    }

    @After
    public void tearDown() {
        Path zipFile = Paths.get("/home/sergio/IdeaProjects/javalab/java-gradle/project.zip");
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