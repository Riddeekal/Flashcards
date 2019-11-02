package flashcards;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    static HashMap<String, String> myMap = new HashMap<>();
    static boolean active = true;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException{
        for (int i = 0; i<args.length;i++){
            if ("-import".equals(args[i])){
                File file = new File(args[i+1]);
                if (file.isFile()){
                    Scanner scanner1 = new Scanner(file);
                    int sum = 0;
                    while(scanner1.hasNextLine()){
                        String fileKey = scanner1.nextLine();
                        String fileValue = scanner1.nextLine();
                        myMap.put(fileKey,fileValue);
                        sum++;
                    }
                    System.out.println(sum + " cards have been loaded.");
                    scanner1.close();
                }else{
                    System.out.println("File not found.");
                }
            }
        }

        while (active){
            System.out.println("Input the action (add, remove, import, export, ask, exit):");

            String userSelection = scanner.nextLine();
            if (userSelection.equals("add")){
                add();
            }else if(userSelection.equals("remove")){
                remove();
            }else if (userSelection.equals("import")){
                Import();
            }else if (userSelection.equals("export")){
                export();
            }else if (userSelection.equals("ask")){
                ask();
            }else{
                exit();
                for (int i = 0; i < args.length; i++){
                    if ("-export".equals(args[i])){
                        File file = new File(args[i+1]);
                        PrintWriter writer = new PrintWriter(file);
                        int sum = 0;
                        for (String s: myMap.keySet()){
                            writer.println(s);
                            writer.println(myMap.get(s));
                            sum++;
                        }
                        System.out.println(sum + " cards have been saved.");
                        writer.close();
                    }
                }
                active = false;
            }
        }
    }

    public static void export() throws FileNotFoundException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("File name:");
        String userFile = scanner.next();
        //FileWriter writer = new FileWriter(userFile,true);
        try{
            File file = new File(userFile);
            PrintWriter writer = new PrintWriter(file);
            int sum = 0;
            for (String s: myMap.keySet()){
                writer.println(s);
                writer.println(myMap.get(s));
                sum++;
            }
            System.out.println(sum + " cards have been saved.");
            writer.close();
        }catch(FileNotFoundException e){
            System.out.println("File not exported.");
        }
        //scanner.close();
    }


    public static void Import() throws FileNotFoundException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("File name:");
        String userFile = scanner.next();
        File file = new File(userFile);
        if (file.isFile()){
            Scanner scanner1 = new Scanner(file);
            int sum = 0;
            while(scanner1.hasNextLine()){
                String fileKey = scanner1.nextLine();
                String fileValue = scanner1.nextLine();
                myMap.put(fileKey,fileValue);
                sum++;
            }
            System.out.println(sum + " cards have been loaded.");
            scanner1.close();
        }else{
            System.out.println("File not found.");
        }
        //scanner.close();
    }
    public static void ask(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many times to ask?");
        int num = scanner.nextInt();
        scanner.nextLine();
        Random random = new Random();
        List<String> myList = new ArrayList<>(myMap.keySet());
        for (int x = 0; x<num; x++){
            String randomKey = myList.get(random.nextInt(myList.size()));
            String randomValue = myMap.get(randomKey);
            System.out.println("Print the definition of \"" + randomKey + "\":");
            String c = scanner.nextLine();
            boolean flag = false;
            String culprit = null;
            for (String s: myMap.keySet()){
                if (myMap.get(s).equals(c)){
                    flag = true;
                    culprit = s;
                    break;
                }
            }
            if (c.equals(randomValue)){
                System.out.println("Correct answer.");
            }else if(!c.equals(randomValue) && flag){
                System.out.println("Wrong answer. The correct one is \"" + randomValue + "\", you've just written the definition of \"" + culprit + "\".");
            }else{
                System.out.println("Wrong answer. The correct one is \"" + randomValue + "\".");
            }

        }
        //scanner.close();
    }
    public static void remove(){
        System.out.println("The card:");
        Scanner scanner = new Scanner(System.in);
        String a = scanner.nextLine();
        if (myMap.containsKey(a)){
            myMap.remove(a);
            System.out.println("The card has been removed.");
        }else{
            System.out.println("Can't remove \"" + a + "\": there is no such card.");
        }
        //scanner.close();
    }
    public static void exit(){
        System.out.println("Bye bye!");

    }
    public static void add(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("The card:");
        String a = scanner.nextLine();
        if (myMap.containsKey(a)){
            System.out.println("The card \"" + a + "\" already exists.");
            return;
        }else{
            System.out.println("The definition of the card:");
            String b = scanner.nextLine();
            List<String> myMapValues = new ArrayList<>(myMap.values());
            for (String s: myMapValues){
                if (s.equals(b)){
                    System.out.println("The definition \"" + b + "\" already exists. Try again:");
                    return;
                }
            }
            myMap.put(a,b);
            System.out.println("The pair (\"" + a + "\":\"" + b + "\") has been added.");
            //scanner.close();

        }
    }
}
