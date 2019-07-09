# IAM7 V2 (ACE V11) Setup

## the toolkit plugin:
- download iam7_toolkit.zip
- unpack the file
- copy the jar to the tools/plugins directory (C:\IBM\ACE\11.0.0.4\tools\plugins on my system)

## the connector file: 
- download IseriesImpl.par
- copy it to a directory of your choice (C:\IBM\ACE\par on my system)

## update node.conf.yaml (C:\ProgramData\IBM\MQSI\components\node1 on my system - "node1" is the name of my ACE Server)
- change the lil path:
        lilPath: 'C:\IBM\ACE\par'                 # A list of paths from where User-defined node LIL/JAR files are loaded. (multiple directories are separated by platform path separator)

## copy the XSL files from this zip file to C:\ProgramData\IBM\MQSI\XSL
- xsl.zip

## restart your node

## example App and Policy project - update this to your needs:
- iam7_demo.zip
