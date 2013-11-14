swing-inspector
===============

Development Console for Java Swing

How to install:

1. Build with maven: mvn package
2. Setup received jar as an agent: java -javaagent:&lt;path_to_jar&gt; &lt;your other options&gt;
3. Use Swing Development Console window.

Features:
1. Stacktrace for all JComponent inheritors from a place where they were instantiated (useful when you need to
    find where this or that component was created)
2. Ability to show red border for component under mouse cursor. Helps to understand how you layout really works.