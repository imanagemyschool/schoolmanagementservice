import com.typesafe.sbt.SbtNativePackager.autoImport._

name := "schoolmanagementservice"


version := {
    val f = file(baseDirectory.value.getPath + "/conf" + "/version.conf")
    println("Version file value = " + f.getAbsolutePath)
    val version = IO.read(f)
    val pattern = "app.version ?= ?\"(.+)\"".r
    pattern.findAllIn(version).matchData.map(m => m.group(1)).toList.head
}

val sVersion = "2.11.7"

scalaVersion := sVersion

lazy val root = (project in file(".")).enablePlugins(PlayScala).enablePlugins(SbtNativePackager)

val amazonWebServicesSDK = "com.amazonaws" % "aws-java-sdk" % "1.9.33"
val anorm24       = "com.typesafe.play" %% "anorm" % "2.4.0"
val slick         = "com.typesafe.slick" %% "slick" % "3.1.1"
val mysqlDriver   = "mysql" % "mysql-connector-java" % "5.1.35"
val scalaJodaTime = "com.github.nscala-time" %% "nscala-time" % "2.0.0"
val jodaTime      = "joda-time" % "joda-time" % "2.7"
val jodaConvert   = "org.joda" % "joda-convert" % "1.7"
val hikariCP = "com.edulify" %% "play-hikaricp" % "1.5.1" //hikari coonection pool
val jodaSlick = "com.github.tototoshi" %% "slick-joda-mapper" % "2.2.0" //Slick joda-time mapper
val slickHikariCP = "com.typesafe.slick" %% "slick-hikaricp" % "3.1.0"
val commonsIO     = "commons-io" % "commons-io" % "2.4"
val commonsNet    = "commons-net" % "commons-net" % "3.3"
val json4sJackson = "org.json4s" %% "json4s-jackson" % "3.4.1"

libraryDependencies ++= Seq(amazonWebServicesSDK, jdbc, anorm24, evolutions, slick, jodaSlick, filters, slickHikariCP, jodaConvert, cache, javaJdbc, scalaJodaTime, jodaTime, mysqlDriver, ws, hikariCP, commonsIO, commonsNet, json4sJackson)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  


routesGenerator := InjectedRoutesGenerator

val lowerCaseProjName = "SchoolManagementService".toLowerCase

name in Linux := lowerCaseProjName

name in Debian := lowerCaseProjName

name in Windows := "SchoolManagementService"

maintainer in Linux := "imanagemyschool"

maintainer in Universal := "imanagemyschool"

packageSummary := "Account service package"

packageDescription := "SchoolManagementService redistributable debian package"

debianControlScriptsDirectory <<= sourceDirectory apply (_ / "debian")

bashScriptExtraDefines += """
                            |addJava -Dlogger.resource=application-logger.xml
                            |addJava -XX:ErrorFile=/var/log/java/java_error%p.log
                            |addJava -XX:+HeapDumpOnOutOfMemoryError
                            |addJava -XX:HeapDumpPath=/var/log/java/oom
                            |""".stripMargin
