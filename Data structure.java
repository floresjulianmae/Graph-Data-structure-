import java.util.*;

class Graph {
    private Map<String, List<String>> adjList = new HashMap<>();

    // Add a person (node)
    public void addPerson(String person) {
        adjList.putIfAbsent(person, new ArrayList<>());
    }

    // Add a friendship (edge) between two people
    public void addFriendship(String person1, String person2) {
        adjList.get(person1).add(person2);
        adjList.get(person2).add(person1); // Since the graph is undirected
    }

    // Display the graph (people and their friendships)
    public void displayGraph() {
        for (String person : adjList.keySet()) {
            System.out.println(person + " -> " + adjList.get(person));
        }
    }

    // Find the shortest path (friendship chain) between two people using BFS
    public List<String> findShortestPath(String start, String end) {
        Queue<List<String>> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(Arrays.asList(start));
        visited.add(start);

        while (!queue.isEmpty()) {
            List<String> path = queue.poll();
            String currentPerson = path.get(path.size() - 1);

            if (currentPerson.equals(end)) {
                return path; // Found the shortest path
            }

            for (String friend : adjList.getOrDefault(currentPerson, new ArrayList<>())) {
                if (!visited.contains(friend)) {
                    visited.add(friend);
                    List<String> newPath = new ArrayList<>(path);
                    newPath.add(friend);
                    queue.add(newPath);
                }
            }
        }

        return null; // No path found
    }
}

public class SocialNetwork {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Graph graph = new Graph();

        System.out.println("Welcome to the Social Network App!");
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Person");
            System.out.println("2. Add Friendship");
            System.out.println("3. Display Network");
            System.out.println("4. Find Shortest Path (Friendship Chain)");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter person name: ");
                    String person = scanner.nextLine();
                    graph.addPerson(person);
                    break;
                case 2:
                    System.out.print("Enter first person: ");
                    String person1 = scanner.nextLine();
                    System.out.print("Enter second person: ");
                    String person2 = scanner.nextLine();
                    graph.addFriendship(person1, person2);
                    break;
                case 3:
                    graph.displayGraph();
                    break;
                case 4:
                    System.out.print("Enter starting person: ");
                    String start = scanner.nextLine();
                    System.out.print("Enter ending person: ");
                    String end = scanner.nextLine();
                    List<String> path = graph.findShortestPath(start, end);
                    if (path != null) {
                        System.out.println("Shortest Path (Friendship Chain): " + String.join(" -> ", path));
                    } else {
                        System.out.println("No path found.");
                    }
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
};