# fastjava-maker
- 根据controller可生成api文档。
- 根据数据库生成各个数据表的mybatis mapper、dao、service、controller；并自动生成CURD方法。

## 使用
引用包后，访问 http://ip:port/fastjava
### MAVEN
```
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>

<dependencies>
    <dependency>
    	<groupId>com.github.shuli495</groupId>
    	<artifactId>fastjava-maker</artifactId>
    	<version>2.0.0</version>
    </dependency>
</dependencies>
```
### GRADLE
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}

dependencies {
        compile 'com.github.shuli495:fastjava:2.0.0'
}
```