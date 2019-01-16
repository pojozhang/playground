package playground.spring.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionStatus;
import playground.spring.BaseSpringTest;
import playground.spring.repository.FilmRepository;
import playground.spring.service.TransactionService;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.transaction.interceptor.TransactionAspectSupport.currentTransactionStatus;

class TransactionPropagationTest extends BaseSpringTest {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void PROPAGATION_REQUIRED_should_create_a_new_transaction_if_none_exists() {
        TransactionStatus status = transactionService.required(this::select);

        assertTrue(status.isNewTransaction());
    }

    @Test
    void PROPAGATION_REQUIRED_should_not_create_a_new_transaction_if_one_exists() {
        transactionService.required(() -> {
            select();

            TransactionStatus status = transactionService.required(this::select);

            assertFalse(status.isNewTransaction());
        });
    }

    @Test
    void PROPAGATION_REQUIRES_NEW_should_create_a_new_transaction_even_if_one_exists() {
        transactionService.required(() -> {
            select();

            TransactionStatus status = transactionService.requiresNew(this::select);

            assertTrue(status.isNewTransaction());
        });
    }

    @Test
    void PROPAGATION_MANDATORY_should_throw_an_exception_if_no_transaction_exists() {
        assertThrows(IllegalTransactionStateException.class, () -> transactionService.mandatory(this::select));
    }

    @Test
    void PROPAGATION_SUPPORTS_should_execute_transactionally_if_one_transaction_exists() {
        transactionService.required(() -> {
            select();

            DefaultTransactionStatus status = (DefaultTransactionStatus) transactionService.supports(this::select);

            assertTrue(status.hasTransaction());
        });
    }

    @Test
    void PROPAGATION_SUPPORTS_should_execute_non_transactionally_if_no_transaction_exists() {
        DefaultTransactionStatus status = (DefaultTransactionStatus) transactionService.supports(this::select);

        assertFalse(status.hasTransaction());
    }

    @Test
    void PROPAGATION_NOT_SUPPORTED_should_execute_non_transactionally_if_one_transaction_exists() {
        transactionService.required(() -> {
            select();

            DefaultTransactionStatus status = (DefaultTransactionStatus) transactionService.notSupported(this::select);

            assertFalse(status.hasTransaction());
        });
    }

    @Test
    void PROPAGATION_NOT_SUPPORTED_should_execute_non_transactionally_if_no_transaction_exists() {
        DefaultTransactionStatus status = (DefaultTransactionStatus) transactionService.notSupported(this::select);

        assertFalse(status.hasTransaction());
    }

    @Test
    void PROPAGATION_NEVER_should_execute_non_transactionally_if_no_transaction_exists() {
        DefaultTransactionStatus status = (DefaultTransactionStatus) transactionService.never(this::select);

        assertFalse(status.hasTransaction());
    }

    @Test
    void PROPAGATION_NEVER_should_throw_an_exception_if_one_transaction_exists() {
        transactionService.required(() -> {
            select();

            assertThrows(IllegalTransactionStateException.class, () -> transactionService.never(this::select));
        });
    }

    @Test
    void PROPAGATION_NESTED_should_behave_like_PROPAGATION_REQUIRED_if_no_transaction_exists() {
        TransactionStatus status = transactionService.nested(() -> {
            select();

            assertFalse(currentTransactionStatus().hasSavepoint());
        });

        assertTrue(status.isNewTransaction());
    }

    @Test
    void PROPAGATION_NESTED_should_create_a_savepoint_if_one_transaction_exists() {
        transactionService.required(() -> {
            select();

            transactionService.nested(() -> {
                jdbcTemplate.execute("select 1;");

                assertTrue(currentTransactionStatus().hasSavepoint());
            });
        });
    }

    private void select() {
        filmRepository.count();
    }
}
