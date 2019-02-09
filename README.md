# XmlParser
* Project contains two CommandLineRunners. 
* One to generate test file 
* Another to process files in specified directories 

# To Generate Xml test File set in application.properties
```
xml.directory=C:\\projects\\xmlParser\\xmlDir 
xml.compute.files=false 
xml.generate.number=100000  -> Number of Records you want 
xml.generate.test.file=true 
```

# To process Xml files set in application.properties
```
xml.directory=C:\\projects\\xmlParser\\xmlDir 
xml.compute.files=true 
xml.generate.test.file=false 
```
