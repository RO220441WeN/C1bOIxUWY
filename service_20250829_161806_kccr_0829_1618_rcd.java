// 代码生成时间: 2025-08-29 16:18:06
import java.io.*;
import java.util.zip.*;
import play.mvc.*;

public class FileDecompressor extends Controller {

    // 解压文件的方法
    // @param sourcePath 压缩文件的路径
    // @param destinationPath 解压后文件存放的路径
    public static void decompressFile(String sourcePath, String destinationPath) {
        File sourceFile = new File(sourcePath);
        File destinationDir = new File(destinationPath);

        // 确保压缩文件存在
        if (!sourceFile.exists()) {
            throw new FileNotFoundException(