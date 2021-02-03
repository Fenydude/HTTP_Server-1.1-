import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ConfigurationServer extends Thread{
    private List<String> headsList = new LinkedList<>();
    private String directory = "D:\\SpringLerning\\HTTPServer1.1\\HttpServer\\files";
    private static final String PATH_TO_FILE = "D:\\SpringLerning\\HTTPServer1.1\\HttpServer\\src\\main\\resources\\headers.txt";
    private static final String ROOT_PATH = "D:\\SpringLerning\\HTTPServer1.1\\HttpServer";

    @Override
    public void run() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String configuration = scanner.nextLine();
            String[] key = configuration.split(" ");
            switch (key[0]) {
                case "-header": {
                    this.scanHeaders();
                    for (String s : headsList){
                        System.out.println(s);
                    }
                    break;
                }
                case "-directory":{
                    if (key.length<2){
                        throw new RuntimeException("NullArgumentException");
                    }else {
                        this.changeDirectory(key);
                    }
                    break;
                }
                default:{
                }
            }
        }
    }
    private void scanHeaders(){
        if (!headsList.isEmpty()){
            headsList.clear();
        }
        try (FileReader f = new FileReader(PATH_TO_FILE)) {
            StringBuffer sb = new StringBuffer();
            while (f.ready()) {
                char c = (char) f.read();
                if (c == '\n') {
                    headsList.add(sb.toString());
                    sb = new StringBuffer();
                } else {
                    sb.append(c);
                }
            }
            if (sb.length() > 0) {
                headsList.add(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeDirectory(String[] key){
        if (key.length == 2){
            directory = key[1];
            System.out.println(directory);
            return;
        }

        if (key[1].equals("-r") && key.length == 3){
            directory = ROOT_PATH + key[2];
            System.out.println(directory);
            return;
        }else{
            try {
                throw new IOException("NotFoundArgument");
            } catch (IOException e) {
                return;
            }
        }
    }


    public List<String> getHeadsList() {
        return headsList;
    }

    public String getDirectory() {
        return directory;
    }
}
