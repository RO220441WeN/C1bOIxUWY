// 代码生成时间: 2025-08-10 06:30:31
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// FolderOrganizer类用于整理文件夹结构
public class FolderOrganizer {

    // 构造函数，指定需要整理的文件夹路径
    public FolderOrganizer(String folderPath) {
        this.folderPath = folderPath;
    }

    // 私有变量，保存文件夹路径
    private String folderPath;

    // 整理文件夹结构的方法
    public void organize() throws IOException {
        // 检查文件夹是否存在
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            throw new IllegalArgumentException("The specified path is not a valid directory.");
        }

        // 获取文件夹内所有文件和子文件夹
        File[] files = folder.listFiles();
        if (files == null) {
            throw new IOException("Cannot read the contents of the directory.");
        }

        // 按文件类型对文件进行排序
        Arrays.sort(files, Comparator.comparing(File::getName));

        // 遍历每个文件和子文件夹
        for (File file : files) {
            if (file.isDirectory()) {
                // 如果是文件夹，则递归调用organize方法
                new FolderOrganizer(file.getAbsolutePath()).organize();
            }
            // 如果是文件，可以在这里添加文件处理逻辑
        }
    }

    // 主程序入口点
    public static void main(String[] args) {
        try {
            // 创建FolderOrganizer实例，并指定需要整理的文件夹路径
            FolderOrganizer organizer = new FolderOrganizer("./yourFolderHere");
            // 调用organize方法进行文件夹整理
            organizer.organize();
            System.out.println("Folder organization completed.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occurred while organizing the folder.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
