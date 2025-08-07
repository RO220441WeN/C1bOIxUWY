// 代码生成时间: 2025-08-07 15:49:18
package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http;
import play.mvc.BodyParser;
import views.html.themes;
import play.cache.SyncCacheApi;

import javax.inject.Inject;
import java.util.Optional;

// 使用Play Framework构建的主题切换控制器
public class ThemeController extends Controller {

    // 注入缓存API以存储用户主题选择
    private final SyncCacheApi cache;

    @Inject
    public ThemeController(SyncCacheApi cache) {
        this.cache = cache;
    }

    // GET请求用于加载主题切换表单页面
    public Result loadThemeForm() {
        return ok(themes.render());
    }

    // POST请求用于处理主题切换表单提交
    @BodyParser.Of(BodyParser.FormData.class)
    public Result submitThemeForm() {
        Optional<String> theme = request().body().asFormUrlEncoded().get("theme");
        if (!theme.isPresent()) {
            // 如果主题参数不存在，返回错误
            return badRequest("Theme parameter is missing.");
        }
        // 将用户选择的主题存储在缓存中
        cache.set("userTheme", theme.get(), "1h"); // 缓存1小时
        // 重定向到主页以应用新主题
        return redirect(routes.ThemeController.loadThemeForm());
    }
}
