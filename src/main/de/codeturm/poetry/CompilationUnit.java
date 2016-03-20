package de.codeturm.poetry;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.List;

public class CompilationUnit extends Annotatable<CompilationUnit> {

  public List<TypeDeclaration<?>> declarations = new ArrayList<>();
  public String packageName = "";

  public CompilationUnit(String packageName) {
    this.packageName = packageName;
  }

  /**
   * {@linkplain http://docs.oracle.com/javase/specs/jls/se8/html/jls-7.html}
   */
  public void print(JavaPrinter printer) {
    printer.context.compilationUnit = this;
    // [PackageDeclaration]
    if (!packageName.isEmpty()) {
      super.printAnnotations(printer, ElementType.PACKAGE);
      printer.newline("package %s;", packageName);
      printer.newline("");
    }
    // {ImportDeclaration}
    // {TypeDeclaration}
    for (TypeDeclaration<?> type : declarations) {
      type.print(printer);
      printer.newline("");
    }
  }

  @Override
  public String toString() {
    return toString(new JavaPrinter());
  }

  public String toString(JavaPrinter printer) {
    print(printer);
    return String.join(printer.linesDelimiter, printer.lines);
  }

}