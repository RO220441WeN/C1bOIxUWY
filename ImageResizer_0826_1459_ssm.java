// 代码生成时间: 2025-08-26 14:59:06
import play.mvc.Controller;
import play.mvc.Result;
import play.jobs.Job;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Callable;
import play.Play;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import play.libs.Akka;

public class ImageResizer extends Controller {

    // 定义常量
    private static final String IMAGES_DIR = Play.application().configuration().getString("images.directory") + "/";
    private static final int MAX_WIDTH = Play.application().configuration().getInt("images.maxWidth");
    private static final int MAX_HEIGHT = Play.application().configuration().getInt("images.maxHeight");
    private static final String[] ALLOWED_FORMATS = new String[] { "jpg", "jpeg", "png" };
    private static final int THREAD_POOL_SIZE = 4; // 可以根据需要调整线程池大小
    private static final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    public static void resizeImages() {
        // 获取目录中的图片文件
        File[] files = new File(IMAGES_DIR).listFiles();
        List<Callable<Result>> tasks = new ArrayList<Callable<Result>>();
        for (File file : files) {
            if (isValidImageFile(file)) {
                tasks.add(new Callable<Result>() {
                    public Result call() throws Exception {
                        return resizeImage(file);
                    }
                });
            }
        }
        runTasks(tasks);
    }

    private static Result resizeImage(File file) {
        try {
            BufferedImage originalImage = ImageIO.read(file);
            double ratio = findRatio(originalImage.getWidth(), originalImage.getHeight(), MAX_WIDTH, MAX_HEIGHT);
            int newWidth = (int) (originalImage.getWidth() * ratio);
            int newHeight = (int) (originalImage.getHeight() * ratio);
            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
            g2d.dispose();
            saveImage(file, resizedImage);
            return ok("Image " + file.getName() + " resized");
        } catch (IOException e) {
            return internalServerError("Failed to resize image: " + e.getMessage());
        }
    }

    private static boolean isValidImageFile(File file) {
        if (file.isDirectory() || !file.isFile()) {
            return false;
        }
        String fileName = file.getName().toLowerCase();
        for (String format : ALLOWED_FORMATS) {
            if (fileName.endsWith("." + format)) {
                return true;
            }
        }
        return false;
    }

    private static double findRatio(int originalWidth, int originalHeight, int maxWidth, int maxHeight) {
        double ratioWidth = (double) maxWidth / (double) originalWidth;
        double ratioHeight = (double) maxHeight / (double) originalHeight;
        return Math.min(ratioWidth, ratioHeight);
    }

    private static void saveImage(File file, BufferedImage image) throws IOException {
        String fileName = file.getName().substring(0, file.getName().lastIndexOf(".")) + "_resized" + file.getName().substring(file.getName().lastIndexOf("."));
        File outputfile = new File(file.getParent(), fileName);
        ImageIO.write(image, getFileExtension(file.getName()), outputfile);
    }

    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private static void runTasks(List<Callable<Result>> tasks) {
        try {
            executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while resizing images", e);
        } finally {
            executorService.shutdown();
        }
    }
}
