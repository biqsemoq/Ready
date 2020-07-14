package xyz.semoteo.ready.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;

public class FileUtils
{
  public static List<String> read(File inputFile)
  {
    List<String> readContent = new ArrayList();
    try
    {
      BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF8"));
      String str;
      while ((str = in.readLine()) != null) {
        readContent.add(str);
      }
      in.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return readContent;
  }
  
  public static void write(File outputFile, List<String> writeContent, boolean overrideContent)
  {
    try
    {
      Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"));
      for (String outputLine : writeContent) {
        out.write(outputLine + System.getProperty("line.separator"));
      }
      out.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public static File getConfigDir()
  {
    File file = new File(Minecraft.getMinecraft().mcDataDir, "Ready");
    if (!file.exists()) {
      file.mkdir();
    }
    return file;
  }
  
  public static File getConfigFile(String name)
  {
    File file = new File(getConfigDir(), String.format("%s.yarrakteoclientbypassesaac", new Object[] { name }));
    if (!file.exists()) {
      try
      {
        file.createNewFile();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    return file;
  }
}