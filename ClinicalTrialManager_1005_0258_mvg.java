// 代码生成时间: 2025-10-05 02:58:25
package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import models.ClinicalTrial;
import play.db.jpa.JPAApi;
import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletableFuture;

public class ClinicalTrialManager extends Controller {

    @Inject
    private JPAApi jpaApi;

    // Retrieves a list of all clinical trials
    public CompletionStage<Result> listTrials() {
        return CompletableFuture.supplyAsync(() -> {
            List<ClinicalTrial> trials = (List<ClinicalTrial>) jpaApi.em().createQuery("SELECT c FROM ClinicalTrial c").getResultList();
            return ok(views.html.clinicaltrials.render(trials));
        }).exceptionally(e -> {
            return internalServerError("An error occurred: " + e.getMessage());
        });
    }

    // Retrieves a specific clinical trial by its ID
    public CompletionStage<Result> getTrial(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            ClinicalTrial trial = jpaApi.em().find(ClinicalTrial.class, id);
            if(trial == null) {
                return notFound("Clinical trial not found");
            }
            return ok(views.html.clinicaltrial.render(trial));
        }).exceptionally(e -> {
            return internalServerError("An error occurred: " + e.getMessage());
        });
    }

    // Creates a new clinical trial
    public CompletionStage<Result> createTrial() {
        return CompletableFuture.supplyAsync(() -> {
            ClinicalTrial trial = jpaApi.em().merge(formFactory.form(ClinicalTrial.class).bindFromRequest().get());
            return redirect(routes.ClinicalTrialManager.getTrial(trial.getId()));
        }).exceptionally(e -> {
            return internalServerError("An error occurred: " + e.getMessage());
        });
    }

    // Updates an existing clinical trial
    public CompletionStage<Result> updateTrial(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            ClinicalTrial trial = jpaApi.em().find(ClinicalTrial.class, id);
            if(trial == null) {
                return notFound("Clinical trial not found");
            }
            trial = jpaApi.em().merge(formFactory.form(ClinicalTrial.class).bindFromRequest().get());
            return redirect(routes.ClinicalTrialManager.getTrial(trial.getId()));
        }).exceptionally(e -> {
            return internalServerError("An error occurred: " + e.getMessage());
        });
    }

    // Deletes a clinical trial
    public CompletionStage<Result> deleteTrial(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            ClinicalTrial trial = jpaApi.em().find(ClinicalTrial.class, id);
            if(trial == null) {
                return notFound("Clinical trial not found");
            }
            jpaApi.em().remove(trial);
            return redirect(routes.ClinicalTrialManager.listTrials());
        }).exceptionally(e -> {
            return internalServerError("An error occurred: " + e.getMessage());
        });
    }
}
