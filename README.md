# Cudgel
Cudgel project on JavaFX

# Config
	applicationId "stan.cudgel"
	versionCode 1701170348
	versionName "0.004"

# Build information
## build
```
javac -sourcepath ./src/main/java -d bin -classpath lib/* ./src/main/java/stan/cudgel/App.java
```

## build css
```
javafxpackager -createbss -srcdir ./src/main/css -outdir bin/css -srcfiles cudgel.css;media/musicplayer.css -v
```

## copy res
### Windows
```
xcopy .\src\main\res .\bin\res /E
```

## run
```
java -classpath lib/*;bin stan.cudgel.App
```
