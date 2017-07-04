package com.nbcuni.test.cms.utils;

import com.nbcuni.test.webdriver.Utilities;
import liquibase.util.csv.opencsv.CSVReader;

import java.io.*;
import java.net.URI;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {

    /**
     * Description : method for pack derictory into zip file
     *
     * @param directory
     *            - File object represent the directory
     * @param to
     *            - Full path to zip file
     * @throws IOException expception in case of unsuccessful pack
     */
    public static synchronized void pack(File directory, String to) throws IOException {
        URI base = directory.toURI();
        Deque<File> queue = new LinkedList<File>();
        queue.push(directory);
        OutputStream out = new FileOutputStream(new File(to));
        Closeable res = out;
        try {
            ZipOutputStream zout = new ZipOutputStream(out);
            res = zout;
            while (!queue.isEmpty()) {
                directory = queue.pop();
                for (File child : directory.listFiles()) {
                    String name = base.relativize(child.toURI()).getPath();
                    if (child.isDirectory()) {
                        queue.push(child);
                        name = name.endsWith("/") ? name : name + "/";
                        zout.putNextEntry(new ZipEntry(name));
                    } else {
                        zout.putNextEntry(new ZipEntry(name));
                        InputStream in = new FileInputStream(child);
                        try {
                            byte[] buffer = new byte[1024];
                            while (true) {
                                int readCount = in.read(buffer);
                                if (readCount < 0) {
                                    break;
                                }
                                zout.write(buffer, 0, readCount);
                            }
                        } finally {
                            in.close();
                        }
                        zout.closeEntry();
                    }
                }
            }
        } finally {
            res.close();

        }
    }

    public static synchronized void writeToFile(final File file, final List<String> dataToWrite, final Boolean append) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(file, append);
        } catch (IOException e) {
            Utilities.logSevereMessage("Unable to create file writer for file" + file.getName());
            e.printStackTrace();

        }
        for (Object line : dataToWrite) {
            try {
                fw.write(line + "\n");
            } catch (IOException e) {
                Utilities.logSevereMessage("Unable to write " + line + " into file");
            }
        }
        try {
            fw.flush();
            fw.close();
        } catch (IOException e) {
            Utilities.logSevereMessage("There is an error during closing file stream");
        }
    }

    public static synchronized void writeToFile(final File file, final String dataToWrite, final Boolean append) {
        List<String> list = new ArrayList<String>();
        list.add(dataToWrite);
        writeToFile(file, list, append);
    }

    public static synchronized List<String> readFromFile(final String filePath) {
        List<String> data = new ArrayList<String>();
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(filePath);
            br = new BufferedReader(fr);
        } catch (IOException e) {
            Utilities.logSevereMessage("Unable to create file writer for file" + filePath);
        }
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                line = line.trim();
                data.add(line);
            }
        } catch (IOException e) {
            Utilities.logSevereMessage("Unable to read from file");
            return null;
        }
        try {
            br.close();
        } catch (IOException e) {
            Utilities.logSevereMessage("There is an error during closing file stream");
        }
        return data;
    }

    public static synchronized List<String[]> readCVS(final String filePath) {
        List<String[]> lines = new LinkedList<String[]>();
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            Utilities.logSevereMessageThenFail("System can't find path specified" + filePath);
        }
        String[] nextLine;
        try {
            while ((nextLine = reader.readNext()) != null) {
                lines.add(nextLine);
            }
        } catch (IOException e) {
            Utilities.logSevereMessageThenFail("Error during .csv file parsing" + filePath);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                Utilities.logSevereMessageThenFail("Uable to close stream" + filePath);
            }
        }
        return lines;
    }

    public static String readFileToSingleString(File file) {
        String content = null;
        try {
            content = new Scanner(file).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            Utilities.logSevereMessage("Error during reading from file");
        }
        return content;
    }
}
