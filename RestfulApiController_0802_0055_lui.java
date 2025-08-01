// 代码生成时间: 2025-08-02 00:55:19
package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.libs.Json;
import play.mvc.Http;
import java.util.HashMap;
import java.util.Map;
import models.Entity;

public class RestfulApiController extends Controller {

    // 获取所有实体的列表
    public Result getAllEntities() {
        try {
            // 这里假设有一个方法getAll从数据库获取所有实体
            java.util.List<Entity> entities = Entity.getAll();
            return ok(Json.toJson(entities));
        } catch (Exception e) {
            // 错误处理
            return internalServerError("Error retrieving entities: " + e.getMessage());
        }
    }

    // 创建一个新的实体
    public Result createEntity() {
        Http.Request request = request();
        Entity entity = Json.fromJson(request.body().asJson(), Entity.class);
        if (entity == null) {
            return badRequest("Invalid entity data");
        }
        try {
            entity.save();
            return created(Json.toJson(entity));
        } catch (Exception e) {
            return internalServerError("Error creating entity: " + e.getMessage());
        }
    }

    // 获取单个实体信息
    public Result getEntity(Long id) {
        try {
            Entity entity = Entity.findById(id);
            if (entity == null) {
                return notFound("Entity not found");
            }
            return ok(Json.toJson(entity));
        } catch (Exception e) {
            return internalServerError("Error retrieving entity: " + e.getMessage());
        }
    }

    // 更新实体信息
    public Result updateEntity(Long id) {
        Http.Request request = request();
        Entity entity = Json.fromJson(request.body().asJson(), Entity.class);
        if (entity == null || entity.id != id) {
            return badRequest("Invalid entity data");
        }
        try {
            entity.update();
            return ok(Json.toJson(entity));
        } catch (Exception e) {
            return internalServerError("Error updating entity: " + e.getMessage());
        }
    }

    // 删除实体
    public Result deleteEntity(Long id) {
        try {
            Entity entity = Entity.findById(id);
            if (entity == null) {
                return notFound("Entity not found");
            }
            entity.delete();
            return ok(Json.toJson(entity));
        } catch (Exception e) {
            return internalServerError("Error deleting entity: " + e.getMessage());
        }
    }

    // 错误处理
    public Result notFound(String message) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", message);
        return status(NOT_FOUND, Json.toJson(errorResponse));
    }
}
