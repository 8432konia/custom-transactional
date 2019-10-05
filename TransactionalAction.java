package annotations;

import play.db.jpa.JPAApi;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.concurrent.CompletionStage;

public class TransactionalAction extends Action<CustomTransactional> {

    private JPAApi jpaApi;

    @Inject
    public TransactionalAction(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public CompletionStage<Result> call(final Http.Request req) {
        return jpaApi.withTransaction(configuration.value(), configuration.readOnly(), (EntityManager em) -> delegate.call(req));
    }
}