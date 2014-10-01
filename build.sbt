name := "play-spring-hystrix"

version := "1.0-SNAPSHOT"

EclipseKeys.withSource := true

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "com.netflix.hystrix" % "hystrix-core"     % "1.3.18",
  "com.netflix.hystrix" % "hystrix-javanica" % "1.3.18",
  "com.netflix.hystrix" % "hystrix-metrics-event-stream" % "1.3.18",
  "org.springframework" % "spring-context"   % "4.1.0.RELEASE",
  "org.springframework" % "spring-aop"       % "4.1.0.RELEASE",
  "org.springframework" % "spring-test" 	 % "4.1.0.RELEASE" % "test"  
)     


play.Project.playJavaSettings
