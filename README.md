# Java Compiler

## About
Java compiler with lexic, syntax, semanthic analysis and bytecode generation on Java Virtual Machine.
<br>
<br>
Lexic analysis is done with JFlex library, symbols are defined in mjlexer.lex.
<br>
<br>
Syntax analysis is done with Ast Cup, rules are written in mjparser.cup.
<br>
<br>
Semanthic analysis is done in SemanticPass.java through visit methods using custom SymbolTable library.
<br>
<br>
After semanthic pass bytecode is generated in CodeGenerator.java with MJ-runtime library.
<br>
<br>
## Run and tests
Running is done in build.xml file and tests in test folder.
<br>
<br>
Delete, lexerGen, parserGen, repackage and compile targets are for building before running tests.
<br>
<br>
After build, MJParserTest.java runs all 4 phases and generates program object file. Program code is written in program.mj file.
<br>
<br>
To run object file on JVM run disasm and runObj targets.
