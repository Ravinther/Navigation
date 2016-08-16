package com.sygic.aura.blackbox;

import android.text.TextUtils;
import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.AppendTrack;
import com.googlecode.mp4parser.authoring.tracks.TextTrackImpl;
import com.googlecode.mp4parser.authoring.tracks.TextTrackImpl.Line;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Mp4parserHelper {
    public static void concatenateVideoFiles(String[] inputFiles, String output) {
        try {
            List<Movie> movies = new LinkedList();
            for (String file : inputFiles) {
                movies.add(MovieCreator.build(file));
            }
            List<Track> videoTracks = new LinkedList();
            List<Track> audioTracks = new LinkedList();
            for (Movie m : movies) {
                for (Track track : m.getTracks()) {
                    if (track.getHandler().equals("vide")) {
                        videoTracks.add(track);
                    }
                    if (track.getHandler().equals("soun")) {
                        audioTracks.add(track);
                    }
                }
            }
            Movie concatMovie = new Movie();
            concatMovie.addTrack(new AppendTrack((Track[]) videoTracks.toArray(new Track[videoTracks.size()])));
            if (!audioTracks.isEmpty()) {
                concatMovie.addTrack(new AppendTrack((Track[]) audioTracks.toArray(new Track[audioTracks.size()])));
            }
            saveVideoFile(concatMovie, output);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void addSubtitleTrackToVideo(String videoPath, String[] infoPaths) {
        List<String> infoLines = readFiles(infoPaths);
        TextTrackImpl subTitleEng = new TextTrackImpl();
        subTitleEng.getTrackMetaData().setLanguage("eng");
        for (int i = 0; i < infoLines.size(); i++) {
            if (infoLines.get(i) != null) {
                String[] infos = ((String) infoLines.get(i)).split("\\|", -1);
                if (infos != null) {
                    String text = "";
                    if (infos.length >= 1) {
                        text = text + infos[0];
                    }
                    if (infos.length >= 2 && !infos[2].isEmpty()) {
                        text = text + ",  " + infos[2] + "\n";
                    }
                    if (infos.length >= 2 && !infos[1].isEmpty()) {
                        text = text + infos[1];
                    }
                    if (!TextUtils.isEmpty(text)) {
                        subTitleEng.getSubs().add(new Line((long) (i * 1000), (long) ((i + 1) * 1000), text));
                    }
                }
            }
        }
        try {
            String tmpPath = videoPath + ".tmp";
            File tempFile = new File(tmpPath);
            new File(videoPath).renameTo(tempFile);
            Movie movie = MovieCreator.build(tmpPath);
            movie.addTrack(subTitleEng);
            saveVideoFile(movie, videoPath);
            tempFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveVideoFile(Movie movie, String outPath) {
        try {
            Container out = new DefaultMp4Builder().build(movie);
            RandomAccessFile rf = new RandomAccessFile(outPath, "rw");
            FileChannel fc = rf.getChannel();
            fc.position(0);
            out.writeContainer(fc);
            fc.close();
            rf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> readFiles(String[] infoPaths) {
        List<String> result = new ArrayList();
        for (String infoFile : infoPaths) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(infoFile));
                while (true) {
                    String line = br.readLine();
                    if (line == null) {
                        break;
                    }
                    result.add(line);
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
