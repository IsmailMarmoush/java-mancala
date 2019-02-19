package com.marmoush.kalah.adapter.file;

import com.esotericsoftware.yamlbeans.YamlConfig;
import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

// TODO Start using Either instead of Exception
public class FileAdapter {
  private FileAdapter() {}

  public static Map<String, Object> parseYaml(String fileName) throws IOException {
    return parseYaml(fileName, true);
  }

  public static Map<String, Object> parseYaml(String fileName, boolean ignoreUnknown) throws IOException {
    String string = of(fileName);
    YamlConfig yc = new YamlConfig();
    yc.readConfig.setIgnoreUnknownProperties(ignoreUnknown);
    return (Map<String, Object>) new YamlReader(string, yc).read();
  }

  public static String of(String fileName) throws IOException {
    InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
    if (is != null) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      return reader.lines().collect(Collectors.joining(System.lineSeparator()));
    } else {
      throw new IOException("Input Stream is null");
    }
  }
}
