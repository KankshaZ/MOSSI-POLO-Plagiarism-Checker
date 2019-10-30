import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Arrays; 

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.visitor.Visitable;

public class PreProcessing {
    public static void main(String[] args) throws Exception {

        String fileLocation = "files/TestFile.java";
        String  newFileLocation =  fileLocation.replace("files", "testedFiles");
        File newFile = new File(newFileLocation);
        File directory = new File(String.valueOf("testedFiles"));
        if(!directory.exists())
            directory.mkdir();
        if(!(newFile.exists()))
        {
            try{
                newFile.createNewFile();
            }catch (Exception e){}
        }
        FileInputStream fileInputStream = new FileInputStream(fileLocation);

        // configuring JavaParser to not handle comments at all 
        // https://stackoverflow.com/questions/50258148/javaparser-doesnt-remove-comments-before-package-declaration
        JavaParser.getStaticConfiguration().setAttributeComments(false);

        CompilationUnit compilationUnit;
        try
        {
            compilationUnit = JavaParser.parse(fileInputStream);
        }
        finally
        {
            fileInputStream.close();
        }
        
        // System.out.println(compilationUnit);

        // https://github.com/javaparser/javaparser/issues/858
        compilationUnit.accept(new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(MethodCallExpr n, Void arg) {
                if (n.getName().getIdentifier().equals("println")) {
                    return null;
                }
                if (n.getName().getIdentifier().equals("print")) {
                    return null;
                }
                return super.visit(n, arg);
            }
        }, null);

        writeFileToLocation(compilationUnit, newFileLocation);
    }

    private static void writeFileToLocation(CompilationUnit compilationUnit, String fileLocation)  throws FileNotFoundException , ParseException {
        System.out.println("after PreProcessing: \n");
        System.out.println(compilationUnit);
        String fileWritingLocation = fileLocation;
        File file = new File(fileWritingLocation);
        FileWriter fw;
        try {
            fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(compilationUnit.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done writing file : " + file.getName());
    }
}