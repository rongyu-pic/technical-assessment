package com.govtech.pic.suggest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Suggester implements LogEntryProcessor {
    public Suggester() {

    }

    /*
      @param logEntryMap - Map containing keys and values in each log line
      This is a call back method that LogReader.read() calls on every line in the input file, after parsing them into key-value pairs
    */
    public void processEntries(Map<String, String> logEntryMap) {

    }

    /**
     * Returns a list of suggestions for a given user query.
     * @param query the string that the user has typed so far
     * @param k the maximum number of suggestions requested
     */
    public List<String> getTopSuggestions(String query, int k) {
        ArrayList<String> result = new ArrayList<>();
        result.add("Software Engineer");
        result.add("Software Engineering Manager");
        result.add("Software Architect");
        result.add("Software Development Engineer in Test");
        result.add("Software Tester");
        result.add("Software Consultant");

        return result;
    }

    // main() for command-line testing
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Need to provide log file (query.log) as argument");
        }
        final String inputFile = args[0];
        Suggester suggester = new Suggester();
        LogReader logReader = new LogReader(inputFile, suggester);

        long elapsedTime = -System.currentTimeMillis();
        logReader.read();
        elapsedTime += System.currentTimeMillis();

        System.out.println(elapsedTime + "ms to read file");

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        double total = (double) Runtime.getRuntime().totalMemory() / (double) (1024 * 1024);
        double free = (double) Runtime.getRuntime().freeMemory() / (double) (1024 * 1024);
        System.out.printf("Total: %.2fMB, Free: %.2fMB, Used: %.2fMB%n", total, free, total - free);

        try {
            System.out.println("Type 'quit' or 'exit' when you're done.");
            while (true) {
                System.out.print("query> ");
                String line = in.readLine();
                if ("".equals(line)) continue;
                if (line == null || "quit".equals(line) || "exit".equals(line)) break;

                elapsedTime = -System.currentTimeMillis();
                List<String> suggestions = suggester.getTopSuggestions(line, 6);
                elapsedTime += System.currentTimeMillis();

                System.out.println("Suggestions for '" + line + "' " + suggestions + " fetched in " + elapsedTime + "ms");
            }
            System.out.println();
        } finally {
            in.close();
        }
    }
}
