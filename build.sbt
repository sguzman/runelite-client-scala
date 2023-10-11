val scala3Version = "3.3.1"

lazy val root = project
    .in(file("."))
    .settings(
      name := "Runelite-client",
      organization := "net.runelite",
      version := "0.1.0-SNAPSHOT",
      scalaVersion := scala3Version,
      Compile/mainClass := Some("net.runelite.client.RuneLite")
      compileOrder := CompileOrder.JavaThenScala,
      resolvers ++= Seq(
        "Typesafe" at "https://repo.typesafe.com/typesafe/releases/",
        "Java.net Maven2 Repository" at "https://download.java.net/maven/2/",
        "Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases",
        "Central Repository" at "https://repo1.maven.org/maven2/",
        "Local Libraries" at "file:///" + System
            .getProperty("user.home") + "/code/scala/runelite-scala/lib/"
      ),
      libraryDependencies ++= List(
        "org.scala-lang" % "scala3-library_3" % "3.3.1",
        "org.slf4j" % "slf4j-api" % "2.0.9",
        "ch.qos.logback" % "logback-classic" % "1.4.11",
        "net.sf.jopt-simple" % "jopt-simple" % "5.0.1",
        "com.google.guava" % "guava" % "32.1.2-jre" excludeAll (
          "com.google.code.findbugs" % "jsr305",
          "com.google.errorprone" % "error_prone_annotations",
          "com.google.j2objc" % "j2objc-annotations",
          "org.codehaus.mojo" % "animal-sniffer-annotations"
        ),
        "com.google.inject" % "guice" % "4.1.0",
        "com.google.code.gson" % "gson" % "2.10.1",
        "net.runelite.pushingpixels" % "substance" % "8.0.02",
        "net.runelite.pushingpixels" % "trident" % "1.5.00" % Runtime,
        "org.projectlombok" % "lombok" % "1.18.30" % Provided,
        "org.apache.commons" % "commons-text" % "1.2",
        "net.runelite.archive-patcher" % "archive-patcher-applier" % "1.2",
        "net.java.dev.jna" % "jna" % "5.9.0",
        "net.java.dev.jna" % "jna-platform" % "5.9.0",
        "com.google.code.findbugs" % "jsr305" % "3.0.2",
        "org.jetbrains" % "annotations" % "23.0.0" % Provided,
        "com.google.protobuf" % "protobuf-javalite" % "3.21.12",
        "net.runelite" % "rlawt" % "1.4",
        "org.lwjgl" % "lwjgl" % "3.3.3",
        "org.lwjgl" % "lwjgl-opengl" % "3.3.3",
        "org.lwjgl" % "lwjgl-opencl" % "3.3.3",
        "net.runelite" % "runelite-api" % props.projectVersion,
        "net.runelite" % "jshell" % props.projectVersion,
        "net.runelite" % "client-patch" % props.projectVersion % Runtime,
        "net.runelite.arn" % "http-api" % "1.2.12",
        "net.runelite" % "discord" % "1.4",
        "net.runelite" % "orange-extensions" % "1.1" % Provided
      )
    )

lazy val props = new {
    val projectVersion = "1.10.15-SNAPSHOT"
}
