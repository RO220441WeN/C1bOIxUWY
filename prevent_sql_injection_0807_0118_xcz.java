// 代码生成时间: 2025-08-07 01:18:04
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple controller to demonstrate SQL injection prevention using Play Framework.
 */
public class PreventSqlInjectionController extends Controller {

    private final JPAApi jpaApi;

    /**
     * Injects the JPAApi to allow database operations.
     *
     * @param jpaApi The JPA API instance.
     */
    @Inject
    public PreventSqlInjectionController(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Transactional(readOnly = true)
    public Result preventSqlInjection(String userQuery) {
        try {
            // This method demonstrates how to prevent SQL injection by using named parameters.
            // userQuery should be a simple string that is used as a part of the query.
            // It should be validated and/or sanitized before being used in a query.
            // For demonstration purposes, we assume that userQuery is safe.
            
            String query = "SELECT * FROM users WHERE username LIKE :username";
            Query sqlQuery = jpaApi.em().createQuery(query);
            sqlQuery.setParameter("username", userQuery + "%");
            
            List<Object> results = sqlQuery.getResultList();
            return ok(results.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", ")));
        } catch (Exception e) {
            // Log and handle the exception
            return internalServerError("An error occurred: " + e.getMessage());
        }
    }
}
