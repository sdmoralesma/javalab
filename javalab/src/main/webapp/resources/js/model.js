/**
 * Model
 */
var model = {
    "console": "Welcome to javalab v0.1 !\r\n$ java -version > \"1.8.0_45\" Java HotSpot(TM) 64-Bit Server VM",
    "libraries": [
        {
            "name": "apache commons lang",
            "version": "3.4",
            "link": "http://commons.apache.org/proper/commons-lang/",
            "checked": false,
            "visible": true,
            "jar": "org/apache/commons/commons-lang3/3.4/commons-lang3-3.4.jar"
        },
        {
            "name": "hamcrest core",
            "version": "1.3",
            "link": "https://code.google.com/p/hamcrest/wiki/Tutorial",
            "checked": true,
            "visible": false,
            "jar": "org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar"
        },
        {
            "name": "joda time",
            "version": "2.8",
            "link": "http://www.joda.org/joda-time",
            "checked": false,
            "visible": true,
            "jar": "joda-time/joda-time/2.8/joda-time-2.8.jar"
        },
        {
            "name": "junit",
            "version": "4.12",
            "link": "http://junit.org/",
            "checked": true,
            "visible": false,
            "jar": "junit/junit/4.12/junit-4.12.jar"
        },
        {
            "name": "google guava",
            "version": "18.0",
            "link": "https://code.google.com/p/guava-libraries",
            "checked": false,
            "visible": true,
            "jar": "com/google/guava/guava/18.0/guava-18.0.jar"
        }
    ],

    "treedata": [
        {
            "id": 1,
            "name": "src/main/java/",
            "type": "folder",
            "children": [
                {
                    "id": 11,
                    "name": "com.company.project",
                    "type": "folder",
                    "children": [
                        {
                            "id": 111,
                            "name": "HelloWorld.java",
                            "type": "file",
                            "code": "package com.company.project;\r\n\r\npublic class HelloWorld {\r\n\r\n\tpublic static void main(String[] args) {\r\n\t\tHelloWorld greeter = new HelloWorld();\r\n\t\tSystem.out.println(greeter.sayHello());\r\n\t}\r\n\r\n\tpublic String sayHello() {\r\n\t\treturn \"hello world!\";\r\n\t}\r\n}",
                            "cursor": "",
                            "children": []
                        }
                    ]
                }
            ]
        },
        {
            "id": 2,
            "name": "src/test/java/",
            "type": "folder",
            "children": [
                {
                    "id": 21,
                    "name": "com.company.project",
                    "type": "folder",
                    "children": [
                        {
                            "id": 211,
                            "name": "HelloWorldTest.java",
                            "type": "file",
                            "code": "package com.company.project;\r\n\r\nimport org.junit.*;\r\n\r\npublic class HelloWorldTest {\r\n\r\n\tprivate HelloWorld greeter;\r\n\r\n\t@Before\r\n\tpublic void setUp() {\r\n\t\tgreeter = new HelloWorld();\r\n\t}\r\n\r\n\t@Test\r\n\tpublic void testHello() {\r\n\t\tAssert.assertEquals(\"hello world!\", greeter.sayHello());\r\n\t}\r\n}",
                            "cursor": "",
                            "children": []
                        }
                    ]
                }
            ]
        }
    ],
    "runnableNode": {"id": undefined, "mainClass": false, "testClass": false}
};

//Tree Model
var root = new TreeModel().parse({
    "id": 0,
    "children": model.treedata
});