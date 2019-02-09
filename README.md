# XmlParser
Project contains two CommandLineRunners.< br /> 
One to generate test file< br /> 
Another to process files in specified directories< br /> 

# To Generate Xml test File
-- Set in application.properties< br /> 
xml.directory=C:\\projects\\xmlParser\\xmlDir< br /> 
xml.compute.files=false< br /> 
xml.generate.number=100000  -> Number of Records you want< br /> 
xml.generate.test.file=true< br /> 

# To process Xml files
-- Set in application.properties< br /> 
xml.directory=C:\\projects\\xmlParser\\xmlDir< br /> 
xml.compute.files=true< br /> 
xml.generate.test.file=false< br /> 
