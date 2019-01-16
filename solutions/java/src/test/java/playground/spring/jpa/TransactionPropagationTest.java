package playground.spring.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import playground.spring.BaseSpringTest;
import playground.spring.repository.FilmRepository;
import playground.spring.service.TransactionService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionPropagationTest extends BaseSpringTest {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private FilmRepository filmRepository;

    @Test
    void propagation_required_should_create_a_new_transaction_if_none_exists() {
        TransactionStatus status = transactionService.required(this::select);

        assertTrue(status.isNewTransaction());
    }

    @Test
    void propagation_required_should_not_create_a_new_transaction_if_one_exists() {
        transactionService.required(() -> {
            select();

            TransactionStatus status = transactionService.required(this::select);

            assertFalse(status.isNewTransaction());
        });
    }

    private void select() {
        filmRepository.count();
    }
}
