package map;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class MapToMaps {

    public static void mapsToMaps(){
        Map<String, Integer> channelToSubscribers    = new TreeMap<>(); // channel, numSubscribers
        Map<String, String> channelToPublisher       = new TreeMap<>(); // channel, publisher
        Map<String, Integer> publisherToSubscribers  = new TreeMap<>(); // publisher, numSubscribers

        // channel -> number of subscribers
        // K -> V1
        channelToSubscribers.put("JustForLaughs", 120_000);
        channelToSubscribers.put("JustForGags", 10_000);
        channelToSubscribers.put("ContemplationTechniques", 10_000);
        channelToSubscribers.put("A New Earth", 20_000);

        // channel -> publisher
        // K -> V2
        channelToPublisher.put("JustForLaughs", "Charlie Chaplin");
        channelToPublisher.put("JustForGags", "Charlie Chaplin");
        channelToPublisher.put("ContemplationTechniques", "Echhart Tolle");
        channelToPublisher.put("A New Earth", "Echhart Tolle");

        // 1. Setup "publisherToSubscribers"
        // publisher -> number of subscribers (total)
        // V2 -> V1
        channelToPublisher.forEach((k, v) -> {
            if (publisherToSubscribers.containsKey(v)) {
                publisherToSubscribers.put(v, channelToSubscribers.get(k) + publisherToSubscribers.get(v));
            } else {
                publisherToSubscribers.put(v, channelToSubscribers.get(k));
            }
        });

        // 2. Output "publisherToSubscribers"
        publisherToSubscribers.forEach((k, v) -> System.out.println("Publisher: " + k + "; numSubscribers:" + v));

        // 3. Who has the most/least subscribers?
        Map.Entry<String, Integer> mostSubscribers = publisherToSubscribers
                .entrySet()
                .stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .orElseThrow();

        Map.Entry<String, Integer> leastSubscribers = publisherToSubscribers
                .entrySet()
                .stream()
                .min(Comparator.comparingInt(Map.Entry::getValue))
                .orElseThrow();

//        Comparator<Map.Entry<String, Integer>> comparator = (Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) -> a.getValue() - b.getValue();

        System.out.println("Publisher with most subscribers: " + mostSubscribers.getKey() + " " + mostSubscribers.getValue());
        System.out.println("Publisher with fewest subscribers: " + leastSubscribers.getKey() + " " + leastSubscribers.getValue());
    }

    public static void main(String[] args) {
        mapsToMaps();
    }

}
