#1 IAM7 V2 (ACE V11) Setup

1.  the toolkit plugin:
    - download iam7_toolkit.zip
    - unpack the file
    - copy the jar to the tools/plugins directory (C:\IBM\ACE\11.0.0.4\tools\plugins on my system)


2.  the connector file: 
    - download IseriesImpl.par
    - copy it to a directory of your choice (C:\IBM\ACE\par on my system)


3. update node.conf.yaml (C:\ProgramData\IBM\MQSI\components\node1 on my system - "node1" is the name of my ACE Server)
    - change the lil path:
        lilPath: 'C:\IBM\ACE\par'                 # A list of paths from where User-defined node LIL/JAR files are loaded. (multiple directories are separated by platform path separator)


4. copy the XSL files from this zip file to C:\ProgramData\IBM\MQSI\XSL
    - xsl.zip


5. restart your node


6. example App and Policy project - update this to your needs:
    - iam7_demo.zip
