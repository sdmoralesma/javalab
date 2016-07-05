package com.smorales.javalab.workspaceprocessor.boundary;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BuildrTest {


    @Test
    public void convertTestDependencyToBuildr() throws Exception {
        String gradleDep = "testCompile 'junit:junit:4.12'";
        String buildrDep = new Buildr().convertDependencyToBuildr(gradleDep);
        assertThat(buildrDep).isEqualTo("test.with 'junit:junit:jar:4.12'");
    }

    @Test
    public void convertCompileDependencyToBuildr() throws Exception {
        String gradleDep = "compile 'junit:junit:4.12'";
        String buildrDep = new Buildr().convertDependencyToBuildr(gradleDep);
        assertThat(buildrDep).isEqualTo("compile.with 'junit:junit:jar:4.12'");
    }

}