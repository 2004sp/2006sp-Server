package com.rs2.cache.js5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Verifies proposed XTEA keys against the local revision 443 location groups. */
public final class KeyHunter {
    private KeyHunter() {
    }

    public static void main(String[] args) throws Exception {
        File cache = new File(args.length > 0 ? args[0] : "cache");
        File fallbacks = new File(args.length > 1 ? args[1] : "map-location-fallbacks-443.tsv");
        File candidates = new File(args.length > 2 ? args[2] : "map-key-candidates-443.tsv");
        File recovered = new File(args.length > 3 ? args[3] : "recovered-keys-443.tsv");
        File unresolved = new File(args.length > 4 ? args[4] : "unresolved-443.tsv");

        List<Square> squares = loadFallbacks(fallbacks);
        Map<Integer, List<Candidate>> byRegion = loadCandidates(candidates);
        Js5CacheStore store = new Js5CacheStore(cache);
        Js5ReferenceTable maps = store.readReferenceTable(5);
        int tested = 0;
        int found = 0;
        try (PrintWriter good = new PrintWriter(new FileWriter(recovered));
             PrintWriter bad = new PrintWriter(new FileWriter(unresolved))) {
            good.println("# region\tx\ty\tgroup\tkey0\tkey1\tkey2\tkey3\tsource\tdecoded_length");
            bad.println("# x\ty\tregion\tgroup\tcandidates\tstatus");
            for (Square square : squares) {
                int group = maps.getGroupId("l" + square.x + "_" + square.y);
                if (group < 0) {
                    bad.println(square.row(-1, 0, "missing-location-group"));
                    continue;
                }
                List<Candidate> regionCandidates = byRegion.get(square.region);
                if (regionCandidates == null) regionCandidates = new ArrayList<Candidate>();
                Candidate winner = null;
                int decodedLength = -1;
                String lastError = "no-candidates";
                Set<String> seen = new LinkedHashSet<String>();
                for (Candidate candidate : regionCandidates) {
                    String signature = Arrays.toString(candidate.key);
                    if (!seen.add(signature)) continue;
                    tested++;
                    try {
                        Map<Integer, byte[]> files = store.readFiles(5, group, candidate.key);
                        if (files.size() != 1) {
                            lastError = "decoded-file-count-" + files.size();
                            continue;
                        }
                        byte[] data = files.values().iterator().next();
                        if (data == null || data.length == 0) {
                            lastError = "empty-decoded-group";
                            continue;
                        }
                        if (winner != null && !Arrays.equals(winner.key, candidate.key)) {
                            throw new IllegalStateException("Multiple keys decoded region " + square.region);
                        }
                        winner = candidate;
                        decodedLength = data.length;
                    } catch (IOException exception) {
                        lastError = clean(exception.getMessage());
                    }
                }
                if (winner == null) {
                    bad.println(square.row(group, seen.size(), lastError));
                    continue;
                }
                found++;
                good.println(square.region + "\t" + square.x + "\t" + square.y + "\t" + group
                        + "\t" + winner.key[0] + "\t" + winner.key[1] + "\t" + winner.key[2]
                        + "\t" + winner.key[3] + "\t" + winner.source + "\t" + decodedLength);
            }
        } finally {
            store.close();
        }
        System.out.println("Revision 443 candidate verification complete: " + tested
                + " candidates tested, " + found + "/" + squares.size() + " regions recovered.");
        System.out.println("Recovered:  " + recovered.getPath());
        System.out.println("Unresolved: " + unresolved.getPath());
    }

    private static List<Square> loadFallbacks(File file) throws IOException {
        List<Square> squares = new ArrayList<Square>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.length() == 0 || line.charAt(0) == '#') continue;
                String[] words = line.split("\\s+");
                if (words.length < 2) continue;
                squares.add(new Square(Integer.parseInt(words[0]), Integer.parseInt(words[1])));
            }
        }
        return squares;
    }

    private static Map<Integer, List<Candidate>> loadCandidates(File file) throws IOException {
        Map<Integer, List<Candidate>> candidates = new LinkedHashMap<Integer, List<Candidate>>();
        if (!file.isFile()) return candidates;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.length() == 0 || line.charAt(0) == '#') continue;
                String[] words = line.split("\\s+");
                if (words.length < 5) throw new IOException("Invalid candidate row: " + line);
                int region = Integer.parseInt(words[0]);
                int keyOffset = words.length >= 9 ? 4 : 1;
                if (words.length < keyOffset + 4) throw new IOException("Invalid candidate row: " + line);
                int[] key = new int[4];
                for (int i = 0; i < 4; i++) key[i] = Integer.parseInt(words[keyOffset + i]);
                if (key[0] == 0 && key[1] == 0 && key[2] == 0 && key[3] == 0) continue;
                int sourceOffset = keyOffset + 4;
                String source = words.length > sourceOffset ? clean(words[sourceOffset]) : "unknown";
                List<Candidate> list = candidates.get(region);
                if (list == null) {
                    list = new ArrayList<Candidate>();
                    candidates.put(region, list);
                }
                list.add(new Candidate(key, source));
            }
        }
        return candidates;
    }

    private static String clean(String value) {
        if (value == null || value.length() == 0) return "unknown";
        return value.replace('\t', ' ').replace('\r', ' ').replace('\n', ' ');
    }

    private static final class Square {
        final int x;
        final int y;
        final int region;

        Square(int x, int y) {
            this.x = x;
            this.y = y;
            this.region = (x << 8) | y;
        }

        String row(int group, int count, String status) {
            return x + "\t" + y + "\t" + region + "\t" + group + "\t" + count
                    + "\t" + clean(status);
        }
    }

    private static final class Candidate {
        final int[] key;
        final String source;

        Candidate(int[] key, String source) {
            this.key = key;
            this.source = source;
        }
    }
}
